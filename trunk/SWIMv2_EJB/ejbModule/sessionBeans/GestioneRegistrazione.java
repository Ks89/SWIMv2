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
import exceptions.RicercheException;

@Stateless
public class GestioneRegistrazione implements GestioneRegistrazioneLocal, GestioneRegistrazioneRemote {

	@PersistenceContext(unitName = "SWIMdb")
	private EntityManager entityManager;

	/**
	 * Metodo che registra un utente con i dati passati come parametro. 
	 * @param String email, l'email dell'utente
	 * @param String password, la password scelta dall'utente
	 * @param String nome, il nome dell'utente
	 * @param String cognome, il cognome dell'utentenu
	 * @param Blob fotoProfilo, la foto del profilo scelta dall'utente
	 * @param List<Abilita>, l'insieme delle abilita selezionate dall'utente all'atto della registrazione
	 * @return <b>utente</b> appena creato, <b>null</b> nel caso l'operazione non vada a buon fine
	 * @throws RegistrazioneException   
	 * @throws HashingException
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
	 * Metodo che registra l'amministratore con i dati passati come parametro. 
	 * @param String email, l'email dell'amministratore
	 * @param String password, la password dell'amministratore
	 * @return <b>true</b> se la registrazione è andata a buon fine, <b>false</b> altrimenti
	 * @throws RegistrazioneException   
	 * @throws HashingException
	 */
	public boolean registrazioneAmministratore(String email, String password)  throws HashingException, RegistrazioneException {
		Amministratore admin= new Amministratore();
		if(emailCorretta(email) && !password.equals(""))
		{
			admin.setEmail(email);
			admin.setPassword(PasswordHasher.hashPassword(password));
			entityManager.persist(admin);
			entityManager.flush();
			return true;
		}
		else if(!emailCorretta(email))
			throw new RegistrazioneException(RegistrazioneException.Causa.SINTASSIEMAILNONCORRETTA);
		else
			throw new RegistrazioneException(RegistrazioneException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		
	}

	 /**
	 * Metodo per ottenere l'abilita in base al nome passato come parametro
	 * @param String nome, il nome dell'abilita cercata
	 * @return l'abilita cercata, <b>null</b> nel caso non esistano abilita con quel nome
	 */
	public Abilita getAbilitaByNome(String nome){
		return entityManager.find(Abilita.class, nome);
	}

	/**
	 * Metodo che controlla la sintassi dell'indirizzo email passato come parametro
	 * @param Sgtring email, l'email da controllare
	 * @return <b>true</b> se l'email è sintatticamente corretta, <b>false</b> altrimenti
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
	 * Metodo che controlla che la mail passata come parametro non sia già stata usata per un'altra registrazione
	 * @param String email
	 * @return <b>true</b> se la mail non è già stata utilizzata da un altro utente, <b>false</b> altrimenti
	 */
	private boolean emailNonAncoraUtilizzata(String email){
		return entityManager.find(Utente.class, email)==null;
	}

}
