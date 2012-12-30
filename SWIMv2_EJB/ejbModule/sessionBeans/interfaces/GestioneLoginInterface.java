package sessionBeans.interfaces;

import entityBeans.Amministratore;
import entityBeans.Utente;
import exceptions.HashingException;
import exceptions.LoginException;

public interface GestioneLoginInterface {

	/**
	 * Metodo che controlla se il login e' stato effettuato da parte di un amministratore.
	 * @param email String che rappresenta l'email dell'amministratore che esegue il login
	 * @param passwordInserita String che rappresenta la password inserita dall'amministratore
	 * @return <b>true</b> se un amministratore ha effettuato il login, <b>false</b> altrimenti
	 * @exception LoginException con causa ALCUNIPARAMETRINULLIOVUOTI
	 * @exception HashingException con causa ALCUNIPARAMETRINULLIOVUOTI oppure ERRORESCONOSCIUTO
	 */
	public boolean eseguiLoginAmministratore(String email, String passwordInserita) throws LoginException, HashingException;

	/**
	 * Metodo che controlla se il login e' stato effettuato da parte di un utente.
	 * @param email String che rappresenta l'email dell'utente che esegue il login
	 * @param passwordInserita String che rappresenta la password inserita dall'utente
	 * @return <b>true</b> se un utente ha effettuato il login, <b>false</b> altrimenti
	 * @exception LoginException con causa ALCUNIPARAMETRINULLIOVUOTI
	 * @exception HashingException con causa ALCUNIPARAMETRINULLIOVUOTI oppure ERRORESCONOSCIUTO
	 */
	public boolean esegueLoginUtente(String email, String passwordInserita) throws LoginException, HashingException;

	/**
	 * Metodo per l'estrazione dell'amministratore dal database data la sua email
	 * @param email e' l'email dell'amministratore
	 * @return <b>l'amministratore</b> corrispondente all'email, se esiste, <b>null</b> altrimenti
	 * @throws LoginException con causa ALCUNIPARAMETRINULLIOVUOTI
	 */
	public Amministratore getAmministratoreByEmail(String email) throws LoginException;

	/**
	 * Metodo per l'estrazione dell'utente dal database data la sua email
	 * @param email = String che rappresente l'email dell'utente
	 * @return <b>utente</b> corrispondente all'email, se esiste, <b>null</b> altrimenti
	 * @throws LoginException con causa ALCUNIPARAMETRINULLIOVUOTI
	 */
	public Utente getUtenteByEmail(String email) throws LoginException;

}