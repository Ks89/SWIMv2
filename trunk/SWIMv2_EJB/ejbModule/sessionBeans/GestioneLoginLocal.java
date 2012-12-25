package sessionBeans;

import javax.ejb.Local;

import entityBeans.Amministratore;
import entityBeans.Utente;

@Local
public interface GestioneLoginLocal {

	/**
	 * Metodo che controlla se il login e' stato effettuato da parte di un amministratore
	 * 
	 * @param email
	 *            e' l'email inserita durante il login
	 * @param passwordInserita
	 *            e' la password inserita durante il login
	 * @return true se un amministratore ha effettuato il login, false altrimenti
	 */
	public boolean eseguiLoginAmministratore(String email,String passwordInserita);

	/**
	 * Metodo che controlla se il login e' stato effettuato da parte di un utente
	 * 
	 * @param email
	 *            e' l'email inserita durante il login
	 * @param passwordInserita
	 *            e' la password inserita durante il login
	 * @return true se un utente ha effettuato il login, false altrimenti
	 */
	public boolean esegueLoginUtente(String email, String passwordInserita);

	/**
	 * Metodo per l'estrazione dell'amministratore dal database data la sua email
	 * @param email e' l'email dell'amministratore
	 * @return <b>l'amministratore</b> corrispondente all'email, se esiste, <b>null</b> altrimenti
	 */
	public Amministratore getAmministratoreByEmail(String email);

	/**
	 * Metodo per l'estrazione dell'utente dal database data la sua email
	 * @param email e' l'email dell'amministratore
	 * @return <b>l'amministratore</b> corrispondente all'email, se esiste, <b>null</b> altrimenti
	 */
	public Utente getUtenteByEmail(String email);

}