package sessionBeans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import utililies.PasswordHasher;

import entityBeans.Amministratore;
import entityBeans.Utente;
import exceptions.HashingException;

@Stateless
public class GestioneLogin implements GestioneLoginLocal {

	@PersistenceContext(unitName = "SWIMdb")
	private EntityManager entityManager;
	
	/**
	 * Metodo che controlla se il login e' stato effettuato da parte di un amministratore
	 * 
	 * @param email
	 *            e' l'email inserita durante il login
	 * @param passwordInserita
	 *            e' la password inserita durante il login
	 * @return true se un amministratore ha effettuato il login, false altrimenti
	 */
	@SuppressWarnings("unchecked")
	private boolean controllaLoginAmministratore(String email, String passwordInserita) throws HashingException {
		
		Query query = entityManager.createNamedQuery("Amministratore.getAmministratoreByEmail");
		query.setParameter("emailAdmin", email);
		
		List<Object> risultatoQuery=(List<Object>)query.getResultList();
		
		if (risultatoQuery.isEmpty()) { // email non trovata
			return false;
		} else { // email trovata
			Amministratore amministratore = (Amministratore)risultatoQuery.get(0);
			String hashPassword = amministratore.getPassword();
			
			return verificaPassword(passwordInserita, hashPassword);
		}
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
	@SuppressWarnings("unchecked")
	private boolean controllaLoginUtente(String email, String passwordInserita) throws HashingException {
		
		Query query = entityManager.createNamedQuery("Utente.getUtenteByEmail");
		query.setParameter("emailUtente", email);
		
		Object risultatoQuery = (Object)query.getSingleResult();
		
		if (risultatoQuery!=null) { // email non trovata
			return false;
		} else { // email trovata
			Utente utente = (Utente) risultatoQuery;
			String hashPassword = utente.getPassword();

			return verificaPassword(passwordInserita, hashPassword);
		}
	}
			
	/**
	 * @param passwordInserita
	 *            e' la password inserita al momento del login
	 * @param passwordHashLetta
	 *            e' la password hash letta dal databse
	 * @return true se le password corrispondono
	 */
	private boolean verificaPassword(String passwordInserita, String passwordHashLetta) throws HashingException {
		return PasswordHasher.verifyPassword(passwordInserita, passwordHashLetta);
	}
	
	
	/**
	 * Metodo per l'estrazione dell'amministratore dal database data la sua email
	 * @param email e' l'email dell'amministratore
	 * @return <b>l'amministratore</b> corrispondente all'email, se esiste, <b>null</b> altrimenti
	 */
	@SuppressWarnings("unchecked")
	public Amministratore getAmministratoreByEmail(String email) {
		
		Query query = entityManager.createNamedQuery("Amministratore.getAmministratoreByEmail");
		query.setParameter("emailAdmin", email);
		
		List<Object> risultatoQuery = (List<Object>)query.getResultList();
		
//		entityManager.find(arg0, arg1)
		
		if(risultatoQuery.isEmpty()) {
			return null;
		} else {
			return (Amministratore)risultatoQuery.get(0);
		}
	}
	
	/**
	 * Metodo per l'estrazione dell'utente dal database data la sua email
	 * @param email e' l'email dell'amministratore
	 * @return <b>l'amministratore</b> corrispondente all'email, se esiste, <b>null</b> altrimenti
	 */
	@SuppressWarnings("unchecked")
	public Utente getUtenteByEmail(String email) {
		
		Query query = entityManager.createNamedQuery("Utente.getUtenteByEmail");
		query.setParameter("emailUtente", email);
		
		List<Object> risultatoQuery = (List<Object>)query.getResultList();
		
		if(risultatoQuery.isEmpty()) {
			return null;
		} else {
			return (Utente)risultatoQuery.get(0);
		}
	}
}
