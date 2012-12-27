package sessionBeans;

import javax.annotation.PostConstruct; 
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import lombok.extern.slf4j.Slf4j;
import sessionBeans.localInterfaces.GestioneLoginLocal;
import utililies.PasswordHasher;
import utililies.sessionRemote.GestioneLoginRemote;
import entityBeans.Amministratore;
import entityBeans.Utente;

@Slf4j
@Stateless
public class GestioneLogin implements GestioneLoginRemote, GestioneLoginLocal {

	@PersistenceContext(unitName = "SWIMdb")
	private EntityManager entityManager;

	
	@PostConstruct
	public void postConstruct() {
		log.info("Creato GestioneLogin");
	}
	
	/**
	 * Metodo che controlla se il login e' stato effettuato da parte di un amministratore
	 * 
	 * @param email
	 *            e' l'email inserita durante il login
	 * @param passwordInserita
	 *            e' la password inserita durante il login
	 * @return true se un amministratore ha effettuato il login, false altrimenti
	 */
	@Override
	public boolean eseguiLoginAmministratore(String email, String passwordInserita) {
		Amministratore amministratore = this.getAmministratoreByEmail(email);
		if (amministratore==null) {
			return false;
		} 
		log.debug("pippo");
		//se non e' null
		String hashPassword = amministratore.getPassword();
		return verificaPassword(passwordInserita, hashPassword);
	}


	/**
	 * Metodo che controlla se il login e' stato effettuato da parte di un utente
	 * 
	 * @param email
	 *            e' l'email inserita durante il login
	 * @param passwordInserita
	 *            e' la password inserita durante il login
	 * @return true se un utente ha effettuato il login, false altrimenti
	 */
	@Override
	public boolean esegueLoginUtente(String email, String passwordInserita) {
		Utente utente = this.getUtenteByEmail(email);
		if (utente==null) {
			return false;
		} 

		//se non e' null
		String hashPassword = utente.getPassword();
		return verificaPassword(passwordInserita, hashPassword);
	}

	/**
	 * @param passwordInserita
	 *            e' la password inserita al momento del login
	 * @param passwordHashLetta
	 *            e' la password hash letta dal databse
	 * @return true se le password corrispondono
	 */
	private boolean verificaPassword(String passwordInserita, String passwordHashLetta) {
		return PasswordHasher.verifyPassword(passwordInserita, passwordHashLetta);
	}


	/**
	 * Metodo per l'estrazione dell'amministratore dal database data la sua email
	 * @param email e' l'email dell'amministratore
	 * @return <b>l'amministratore</b> corrispondente all'email, se esiste, <b>null</b> altrimenti
	 */
	@Override
	public Amministratore getAmministratoreByEmail(String email) {
		return entityManager.find(Amministratore.class, email);
	}

	/**
	 * Metodo per l'estrazione dell'utente dal database data la sua email
	 * @param email e' l'email dell'amministratore
	 * @return <b>l'utente</b> corrispondente all'email, se esiste, <b>null</b> altrimenti
	 */
	@Override
	public Utente getUtenteByEmail(String email) {
		return entityManager.find(Utente.class, email);
	}
}
