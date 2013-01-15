package sessionBeans.interfaces;

import java.util.List;

import entityBeans.Amicizia;
import entityBeans.Utente;
import exceptions.AmiciziaException;

public interface GestioneAmicizieInterface {

	
	/**
	 * Metodo per creare una richiesta di Amicizia
	 * 
	 * @param emailUtente1
	 *            rappresenta la email del richiedente
	 * @param emailUtente2
	 *            rappresenta la email dell'utente cui il richiedende vuole
	 *            l'amicizia
	 * @return <b>amicizia</b> se tutto è andato a buon fine,<b>null</b>
	 *         altrimenti
	 */
	
	
	public Amicizia richiediAmicizia(String emailUtente1,
			String emailUtente2, boolean diretta);

	
	/**
	 * Metodo per confermare una richiesta di Amicizia
	 * 
	 * @param emailUtente1
	 *            rappresenta la email del richiedente
	 * @param emailUtente2
	 *            rappresenta la email dell'utente che sta accettando la
	 *            richiesta di amicizia
	 * @return <b>amicizia</b> se tutto e' andato a buon fine,<b>null</b> altrimenti
	 * @throws AmiciziaException con cause: ALCUNIPARAMETRINULLIOVUOTI, UTENTINONTROVATI, UPDATEAMICIZIAFALLITO
	 */
	
	
	public Amicizia accettaAmicizia(String emailUtente1, String emailUtente2) throws AmiciziaException;

	
	
	/**
	 * Metodo per rifiutare una richiesta di Amicizia
	 * 
	 * @param emailUtente1
	 *            rappresenta la email del richiedente
	 * @param emailUtente2
	 *            rappresenta la email dell'utente che sta rifiutando la
	 *            richiesta di amicizia
	 * @return <b>true</b> se tutto è andato a buon fine,<b>false</b> altrimenti
	 */
	
	
	public boolean rifiutaAmicizia(String emailUtente1,
			String emailUtente2);

	
	
	/**
	 * Metodo che ritorna la lista degli amici di un utente
	 * 
	 * @param emailUtente
	 *            rappresenta la email dell'utente di cui si vuole conoscere la
	 *            lista degli amici
	 * @return <b>amici</b> che rappresenta la lista degli amici
	 *         dell'utente,<b>null</b> se l'utente non ha amici
	 */
	
	
	public List<Utente> getAmici(String emailUtente);

	
	/**
	 * Metodo che ritorna una lista di suggerimenti di amicizia per un utente di
	 * amici appartenenti al nuovo amico
	 * 
	 * @param emailUtenteAppenaAmico
	 *            rappresenta la email dell'utente da cui prendere i
	 *            suggerimenti
	 * @param emailUtente
	 *            rappresenta la email dell'utente a cui si vogliono suggerire
	 *            amici
	 * @return <b>suggerimenti</b> che rappresenta la lista degli amici da
	 *         suggerire,<b>null</b> se l'Utente da qui si vogliono suggerimenti
	 *         ha solo l'amico dell'amicizia appena stipulata,
	 *         <b>suggerimenti</b> vuoto (attenzione non null) se l'altro utente
	 *         possiede solo amici che anche tu possiedi.
	 */
	
	
	public List<Utente> getSuggerimenti(String emailUtenteAppenaAmico,
			String emailUtente);

	/**
	 * Metodo per l'estrazione dell'utente dal database data la sua email
	 * @param email = l'email dell'amministratore
	 * @return <b>utente</b> corrispondente all'email, se esiste, <b>null</b> altrimenti
	 */
	public Utente getUtenteByEmail(String email);

	
	/**
	 * Metodo che ritorna true se un l'utente1 e l'utente2 sono amici
	 * 
	 * @param emailUtente1
	 * @param emailUtente2
	 * @return <b>true</b> se sono amici,<b>false</b> altrimenti
	 */
	
	
	public boolean sonoAmici(String emailUtente1, String emailUtente2);

	
	/**
	 * Metodo che ritorna la lista degli utenti che vogliono diventare amici
	 * dell'utente che ha l'emailUtente passata come parametro
	 * 
	 * @param emailUtente
	 *            rappresenta la email dell'utente di cui si vuole conoscere la
	 *            lista degli utenti che gli hanno richiesto l'amicizia
	 * @return <b>utentiChevoglionoDiventareAmici</b> che rappresenta la lista
	 *         degli amici dell'utente,<b>null</b> se l'utente non ha richieste
	 *         di amicizia
	 */
	
	
	public List<Utente> getUtentiCheVoglionoAmicizia(String emailUtente);

	
	/**
	 * Metodo che setta l'amiciza come notificata, ovviamente al richiedente
	 * perchè al richiesto uscirà quando accetta la richiesta
	 * 
	 * @param emailUtente1
	 *            rappresenta la email del richiedente (cioè quello che è stato
	 *            notificato per porre a true questo valore)
	 * @param emailUtente2
	 *            rappresenta la email dell'utente che sta accettando la
	 *            richiesta di amicizia
	 * @return <b>true</b> se tutto è andato a buon fine,<b>false</b> altrimenti
	 */
	
	
	public boolean setAmiciziaNotificata(String emailUtente1, String emailUtente2);

	/**
	 * Metodo che ritorna la lista degli utenti che hanno appena accettato la
	 * tua amicizia, chiesta in modo indiretto
	 * 
	 * @param emailUtente
	 *            rappresenta la email dell'utente di cui si vuole sapere la
	 *            lista
	 * @return <b>utenti</b> se esistono utenti che hanno appena accettato
	 *         l'amicizia,<b>NULL</b> altrimenti
	 */
	
	
	
	public List<Utente> getUtentiCheHannoAccettatoLaRichiestaIndiretti(
			String emailUtente);

	
	/**
	 * Metodo che ritorna true se esiste già una amicizia inoltrata tra i due utenti
	 * 
	 * @param emailUtente1
	 * @param emailUtente2
	 * @return <b>true</b> se esiste,<b>false</b> altrimenti
	 */
	
	
	
	public boolean amiciziaInoltrata(String emailUtente1, String emailUtente2);
	
	/**
	 * Metodo che ritorna la lista degli utenti che hanno appena accettato la
	 * tua amicizia, chiesta in modo indiretto
	 * 
	 * @param emailUtente
	 *            rappresenta la email dell'utente di cui si vuole sapere la
	 *            lista
	 * @return <b>utenti</b> se esistono utenti che hanno appena accettato
	 *         l'amicizia,<b>NULL</b> altrimenti
	 */
	
	public List<Utente> getUtentiCheHannoAccettatoLaRichiestaDiretti(String emailUtente);

}