package sessionBeans;

import java.sql.Blob;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.loader.custom.Return;

import sessionBeans.localInterfaces.GestioneRegistrazioneLocal;
import utililies.PasswordHasher;
import utililies.sessionRemote.GestioneRegistrazioneRemote;

import entityBeans.Abilita;
import entityBeans.Amministratore;
import entityBeans.Possiede;
import entityBeans.PossiedePK;
import entityBeans.Utente;
import exceptions.HashingException;
import exceptions.RegistrazioneException;

@Stateless
public class GestioneRegistrazione implements GestioneRegistrazioneLocal, GestioneRegistrazioneRemote {

	@PersistenceContext(unitName = "SWIMdb")
	private EntityManager entityManager;

	/**
	 * Registra un utente con i dati passati come parametro. 
	 * @throws RegistrazioneException 
	 * @return Nela caso la registrazione sia andata a buon fine, torna l'utente appena registrato, altrimenti torna un utente null  
	 */
	public Utente registrazioneUtente(String email, String password, String nome, String cognome, Blob fotoProfilo, List<Abilita> abilita)  throws HashingException, RegistrazioneException {
		Utente utente;
		if(emailCorretta(email) && emailNonAncoraUtilizzata(email) && !nome.equals("") && !cognome.equals("") && !password.equals("") && abilita.size()>=1)
		{
			utente=new Utente();
			utente.setEmail(email);
			utente.setNome(nome.substring(0, 1).toUpperCase()+nome.substring(1, nome.length()).toLowerCase());
			utente.setCognome(cognome.substring(0, 1).toUpperCase()+cognome.substring(1, cognome.length()).toLowerCase());
			utente.setPassword(PasswordHasher.hashPassword(password));
			utente.setFotoProfilo(fotoProfilo);
			entityManager.persist(utente);
			entityManager.flush();

			

			for (Abilita a : abilita) {
				Possiede possiede= new Possiede();
				PossiedePK possiedepk = new PossiedePK();
				possiedepk.setAbilita(a);
				possiedepk.setUtente(utente);
				possiede.setPossiedePK(possiedepk);
				
				entityManager.persist(possiede);
				entityManager.flush();
			}
			entityManager.flush();
		}
		else if(!emailCorretta(email))
			throw new RegistrazioneException(RegistrazioneException.Causa.SINTASSIEMAILNONCORRETTA);
		else if(!emailNonAncoraUtilizzata(email))
			throw new RegistrazioneException(RegistrazioneException.Causa.EMAILGIAUTILIZZATA);
		else
			throw new RegistrazioneException(RegistrazioneException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		return utente;
	}

	/**
	 * Metodo per creare utenti fittizzi per test
	 */
	public boolean registrazioneUtentePerTest(String email, String password, String nome, String cognome) throws HashingException{
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
	 * Metodo utile in fase di test, perchè certe operazioni richiedono la presenza dell'amministratore.
	 * Crea un amministratore con email=admin@swim.it e password=pippo
	 */
	public boolean registrazioneAdminTest()  throws HashingException {
		Amministratore admin= new Amministratore();
		admin.setEmail("admin@swim.it");
		admin.setPassword(PasswordHasher.hashPassword("pippo"));
		entityManager.persist(admin);
		entityManager.flush();
		return true;
	}

	/**
	 * Utile per il test. Torna una abilita in base al nome passato come parametro
	 * @param Nome dell'abilita richiesta
	 * @return l'abilita, o null se non si è trovata nessuna abilita con questo nome
	 */
	public Abilita getAbilitaByNome(String nome){
		return entityManager.find(Abilita.class, nome);
	}

	/**
	 * Controlla la sintassi dell'indirizzo email passato come parametro
	 * @param email da verificare
	 * @return true se l'email è sintatticamente corretta, false altrimenti
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
	 * Controlla che la mail passata come parametro non sia già stata usata per un'altra registrazione
	 * @param email
	 * @return true se la mail non è già stata utilizzata da un altro utente, false altrimenti
	 */
	private boolean emailNonAncoraUtilizzata(String email){
		return entityManager.find(Utente.class, email)==null;
	}

}
