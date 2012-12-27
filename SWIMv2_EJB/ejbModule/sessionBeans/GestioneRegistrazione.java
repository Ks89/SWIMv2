package sessionBeans;

import java.sql.Blob;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import sessionBeans.localInterfaces.GestioneRegistrazioneLocal;
import utililies.PasswordHasher;
import utililies.sessionRemote.GestioneRegistrazioneRemote;

import entityBeans.Abilita;
import entityBeans.Amministratore;
import entityBeans.Possiede;
import entityBeans.PossiedePK;
import entityBeans.Utente;

@Stateless
public class GestioneRegistrazione implements GestioneRegistrazioneLocal, GestioneRegistrazioneRemote {

	@PersistenceContext(unitName = "SWIMdb")
	private EntityManager entityManager;
	
	/**
	 * Registra un utente con i dati pssati come parametro. Per il momento torna true se la registrazione va a buon fine.
	 */
	public boolean registrazioneUtente(String email, String password, String nome, String cognome, Blob fotoProfilo, List<Abilita> abilita){
		Utente utente= new Utente();
		Possiede possiede= new Possiede();
		PossiedePK possiedepk= new PossiedePK();
		//Mettere cognome e nome con l'iniziale maiuscola
		if(emailCorretta(email) && emailNonAncoraUtilizzata(email) && nome!="" && cognome!="" && password!="" && abilita.size()>=1)//cognome e nome non nulli e abilita.size è un controllo che facciamo qua, o direttamente con javascript?
		{
				utente.setEmail(email);
				utente.setNome(nome);
				utente.setCognome(cognome);
				utente.setPassword(PasswordHasher.hashPassword(password));
				utente.setFotoProfilo(fotoProfilo);
				entityManager.persist(utente);
				entityManager.flush();
				
				for (Abilita a : abilita) {
					possiedepk.setAbilita(a);
					possiedepk.setUtente(utente);
					possiede.setPossiedePK(possiedepk);
					entityManager.persist(possiede);
				}
				entityManager.flush();
				return true;
		}
		return false;
	}
	
	/**
	 * Metodo per creare utenti fittizzi per test
	 */
	public boolean registrazioneUtentePerTest(String email, String password, String nome, String cognome){
		Utente utente= new Utente();
		utente.setEmail(email);
		utente.setNome(nome);
		utente.setCognome(cognome);
		utente.setPassword(PasswordHasher.hashPassword(password));
		utente.setFotoProfilo(null);
		entityManager.persist(utente);
		entityManager.flush();
		return true;
	}
	
	/**
	 * Metodo utile per fase di test allo scopo di creare l'amministratore
	 */
	public boolean registrazioneAdminTest(){
		Amministratore admin= new Amministratore();
		admin.setEmail("admin@swim.it");
		admin.setPassword("pippo");
		entityManager.persist(admin);
		entityManager.flush();
		return true;
	}
	
	/**
	 * Controlla la sintassi dell'indirizzo email passato come parametro
	 * @param email
	 * @return
	 */
	private boolean emailCorretta(String email){
		//Settiamo il pattern per il confronto
        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
 
        //Eseguiamo il match della stringa data con il pattern     
        Matcher m = p.matcher(email);
 
        //Salviamo il risultato del match
        boolean matchFound = m.matches();
        StringTokenizer st = new StringTokenizer(email, ".");
        String lastToken = null;
        while (st.hasMoreTokens()) {
            lastToken = st.nextToken();
        }
 
        // validate the country code
        if (matchFound && lastToken.length() >= 2
                && email.length() - 1 != lastToken.length()) {
 
            return true;
        } else {
            return false;
        }
	}
	
	/**
	 * Controlla che la mail passata come parametro non sia già usata da un altro utente
	 * @param email
	 * @return true se la mail non è già stata utilizzata da un altro utente, false altrimenti
	 */
	private boolean emailNonAncoraUtilizzata(String email){
		return entityManager.find(Utente.class, email)==null;
	}

}
