package sessionBeans;

import javax.ejb.Stateless; 
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import sessionBeans.localInterfaces.GestioneLoginLocal;
import utililies.PasswordHasher;
import utililies.sessionRemote.GestioneLoginRemote;
import entityBeans.Amministratore;
import entityBeans.Utente;
import exceptions.HashingException;
import exceptions.LoginException;

/**
 * Classe che rappresenta il session bean GestioneLogin.
 */
@Stateless
public class GestioneLogin implements GestioneLoginRemote, GestioneLoginLocal {

	@PersistenceContext(unitName = "SWIMdb")
	private EntityManager entityManager;


	/**
	 * Metodo che controlla se il login e' stato effettuato da parte di un amministratore.
	 * @param email String che rappresenta l'email dell'amministratore che esegue il login
	 * @param passwordInserita String che rappresenta la password inserita dall'amministratore
	 * @return <b>true</b> se un amministratore ha effettuato il login, <b>false</b> altrimenti
	 * @exception LoginException con causa ALCUNIPARAMETRINULLIOVUOTI
	 * @exception HashingException con causa ALCUNIPARAMETRINULLIOVUOTI oppure ERRORESCONOSCIUTO
	 */
	@Override
	public boolean eseguiLoginAmministratore(String email, String passwordInserita) throws LoginException, HashingException {
		if(email==null || email.equals("") || passwordInserita==null || passwordInserita.equals("")) {
			throw new LoginException(LoginException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		}

		Amministratore amministratore = this.getAmministratoreByEmail(email);
		if (amministratore==null) {
			return false;
		} 

		//se amministratore non e' null
		String hashPassword = amministratore.getPassword();
		return verificaPassword(passwordInserita, hashPassword);
	}


	/**
	 * Metodo che controlla se il login e' stato effettuato da parte di un utente.
	 * @param email String che rappresenta l'email dell'utente che esegue il login
	 * @param passwordInserita String che rappresenta la password inserita dall'utente
	 * @return <b>true</b> se un utente ha effettuato il login, <b>false</b> altrimenti
	 * @exception LoginException con causa ALCUNIPARAMETRINULLIOVUOTI
	 * @exception HashingException con causa ALCUNIPARAMETRINULLIOVUOTI oppure ERRORESCONOSCIUTO
	 */
	@Override
	public boolean esegueLoginUtente(String email, String passwordInserita) throws LoginException, HashingException {
		if(email==null || email.equals("") || passwordInserita==null || passwordInserita.equals("")) {
			throw new LoginException(LoginException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		}

		Utente utente = this.getUtenteByEmail(email);
		if (utente==null) {
			return false;
		} 

		//se utente non e' null
		String hashPassword = utente.getPassword();
		return verificaPassword(passwordInserita, hashPassword);
	}

	/**
	 * Metodo privato per verificare se la password inserita dall'utente e' uguale a quella hashata in sha256 nel database
	 * @param passwordInserita String che rappresenta la password inserita dall'utente o dall'amministratore durante il login
	 * @param passwordHashLetta String che rappresenta la password hashata dell'utente o dell'amministratore
	 * @return <b>true</b> se le password sono uguali, <b>false</b> altrimenti
	 * @exception LoginException con causa ALCUNIPARAMETRINULLIOVUOTI
	 * @exception HashingException con causa ALCUNIPARAMETRINULLIOVUOTI oppure ERRORESCONOSCIUTO
	 */
	private boolean verificaPassword(String passwordInserita, String passwordHashLetta) throws LoginException, HashingException {
		if(passwordInserita==null || passwordInserita.equals("")) {
			throw new LoginException(LoginException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		}
		return PasswordHasher.verifyPassword(passwordInserita, passwordHashLetta);
	}


	/**
	 * Metodo per l'estrazione dell'amministratore dal database data la sua email
	 * @param email e' l'email dell'amministratore
	 * @return <b>l'amministratore</b> corrispondente all'email, se esiste, <b>null</b> altrimenti
	 * @throws LoginException con causa ALCUNIPARAMETRINULLIOVUOTI
	 */
	@Override
	public Amministratore getAmministratoreByEmail(String email) throws LoginException {
		if(email==null || email.equals("")) {
			throw new LoginException(LoginException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		} else {
			return entityManager.find(Amministratore.class, email);
		}
	}

	/**
	 * Metodo per l'estrazione dell'utente dal database data la sua email
	 * @param email = String che rappresente l'email dell'utente
	 * @return <b>utente</b> corrispondente all'email, se esiste, <b>null</b> altrimenti
	 * @throws LoginException con causa ALCUNIPARAMETRINULLIOVUOTI
	 */
	@Override
	public Utente getUtenteByEmail(String email) throws LoginException {
		if(email==null || email.equals("")) {
			throw new LoginException(LoginException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		} else {
			return entityManager.find(Utente.class, email);
		}
	}
}
