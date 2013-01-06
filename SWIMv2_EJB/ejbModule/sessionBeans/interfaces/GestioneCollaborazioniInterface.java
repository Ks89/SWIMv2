package sessionBeans.interfaces;

import java.util.List;

import entityBeans.Collaborazione;
import entityBeans.Utente;
import exceptions.CollaborazioneException;
import exceptions.LoginException;

public interface GestioneCollaborazioniInterface {

	/**
	 * Metodo per ottenere la collaborazione con l'id specificato
	 * @param id = Long che rappresenta l'id univoco della collaborazione
	 * @return <b>collaborazione</b> con l'id specificato, se esiste, <b>null</b> altrimenti
	 * @throws LoginException con causa ALCUNIPARAMETRINULLIOVUOTI
	 */
	public Collaborazione getCollaborazione(Long id) throws LoginException;

	/**
	 * Metodo per richiedere aiuto ad un altro utente, specificando richiedente, ricevente, nome e descrizione della collaborazione.
	 * Tale metodo inserisca una nuova collaborazione all'interno della tabella Collaborazione. Poiche' la chiave primaria e' un id sequenziale,
	 * non resituisce errori per inserimenti multipli.
	 * @param emailRichiedente = String che rappresenta l'email dell'utente richiedente, cioe' colui che richiede aiuto
	 * @param emailRicevente = String che rappresenta l'email dell'utente ricevente, cioe' colui a cui si richiede aiuto
	 * @param nome = String che rappresenta il nome della collaborazione
	 * @param descrizione = String che rappresenta la descizione della collaborazione
	 * @return <b>collaborazione</b> appena creata, <b>null</b> se non e' stato possibile crearla
	 * @exception CollaborazioneException con causa ALCUNIPARAMETRINULLIOVUOTI
	 * @throws LoginException con causa ALCUNIPARAMETRINULLIOVUOTI
	 */
	public Collaborazione richiediAiuto(String emailRichiedente, String emailRicevente, String nome, String descrizione) throws CollaborazioneException, LoginException;

	/**
	 * Metodo per accettare una collaborazione che puo' essere utilizzato dall'utente ricevente per accettare una richiesta di aiuto 
	 * dell'utente richiedente. Tale metodo imposta la data di stipula della collaborazione con l'istante in cui l'utente ricevente 
	 * l'accetta (chiamando questo metodo).
	 * @param id = Long che rappresenta l'id univoco della collaborazione
	 * @return <b>collaborazione</b> con l'id specificato, se accettata correttamente, <b>null</b> altrimenti
	 * @throws LoginException con causa ALCUNIPARAMETRINULLIOVUOTI
	 */
	public Collaborazione accettaCollaborazione(Long id) throws LoginException;

	/**
	 * Metodo per terminare una collaborazione che puo' essere utilizzato dall'utente richiedente per terminare una richiesta di aiuto 
	 * precedentemente aperta. Tale metodo imposta la data di termina della collaborazione con l'istante in cui l'utente richiedente 
	 * la termina (chiamando questo metodo).
	 * @param id = Long che rappresenta l'id univoco della collaborazione
	 * @return <b>collaborazione</b> con l'id specificato, se terminata correttamente, <b>null</b> altrimenti
	 * @throws LoginException con causa ALCUNIPARAMETRINULLIOVUOTI
	 */
	public Collaborazione terminaCollaborazione(Long id) throws LoginException;

	/**
	 * Metodo per rifiutare una collaborazione. Puo' essere chiamato da un utente che ha ricevuto una richiesta di aiuto che non vuole accettare.
	 * @param id = Long che rappresenta l'id univoco della collaborazione
	 * @return <b>true</b> se la collaborazione e' stata rimossa correttamente, <b>false</b> altrimenti.
	 * @throws LoginException con causa ALCUNIPARAMETRINULLIOVUOTI
	 */
	public boolean rifiutaCollaborazione(Long id) throws LoginException;

	/**
	 * Metodo che puo' essere utilizzato dall'utente richidente per rilasciare il feedback dopo aver stipulato e poi terminato una collaborazione.
	 * @param id = Long che rappresenta l'id univoco della collaborazione
	 * @param punteggioFB = Integer che rappresenta il punteggio di feedback della collaborazione
	 * @param commentoFB = String che rappresenta il commento di feedback della collaborazione
	 * @return <b>collaborazione</b> con l'id specificato, se il feedback e' stato rilasciato correttamente, <b>null</b> altrimenti
	 * @throws LoginException con causa ALCUNIPARAMETRINULLIOVUOTI
	 */
	public Collaborazione rilasciaFeedback(Long id, Integer punteggioFB, String commentoFB) throws LoginException;

	/**
	 * Metodo per ottenere il punteggio di feedback di un utente.
	 * @param emailRicevente = String che rappresenta l'email dell'utente ricevente delle collaborazioni, cioe' coloi a cui
	 * viene rilasciato il feedback dall'utente richieente.
	 * @return <b>il punteggio di feedback</b>, cioe' un double che rappresenta la media dei voti di tutte le collaborazioni
	 * di cui ha ricevuto un punteggio di feecback. Se non e' possibile ottenere tale valore, reistituisce <b>null</b>.
	 * @throws LoginException con causa ALCUNIPARAMETRINULLIOVUOTI
	 */
	public Double getPunteggioFeedback(String emailRicevente) throws LoginException;

	/**
	 * Metodo per ottenere la lista delle collaborazioni create dall'utente richiedente, di cui non ha ancora rilasciato un feedback.
	 * @param emailRichiedente = String che rappresenta l'email dell'utente richiedente della richiesta di aiuto (collaborazione)
	 * @return <b>lista delle collaborazioni</b>, cioe' una List<Collaborazione> che rappresenta le collaborazioni, ancora senza feedback. 
	 * Se non e' possibile ottenere tale lista, reistituisce <b>null</b>.
	 * @throws LoginException con causa ALCUNIPARAMETRINULLIOVUOTI
	 */
	public List<Collaborazione> getCollaborazioniCreateFeedbackNonRilasciato(String emailRichiedente) throws LoginException;

	/**
	 * Metodo per ottenere la lista delle collaborazioni create dall'utente richiedente.
	 * @param emailRichiedente = String che rappresenta l'email dell'utente richiedente della richiesta di aiuto (collaborazione)
	 * @return <b>lista delle collaborazioni</b>, ovvero una List<Collaborazione> che rappresenta le 
	 * collaborazioni iniziate dall'utente, cioe' le richieste di aiuto dell'utente richiedente.
	 * Se non e' possibile ottenere tale lista, reistituisce <b>null</b>.
	 * @throws LoginException con causa ALCUNIPARAMETRINULLIOVUOTI
	 */
	public List<Collaborazione> getCollaborazioniCreate(String emailRichiedente) throws LoginException;

	/**
	 * Metodo che fornisce la lista delle collaborazioni accettate dall'utente ricevente identificato come emailRicevente
	 * @param emailRicevente = String che rappresenta l'email dell'utente ricevente della richiesta di aiuto (collaborazione)
	 * @return <b>lista delle collaborazioni</b>, ovvero una List<Collaborazione> che rappresenta le 
	 * collaborazioni accettate dall'utente ricevente. Se non e' possibile ottenere tale lista, reistituisce <b>null</b>.
	 * @throws LoginException con causa ALCUNIPARAMETRINULLIOVUOTI
	 */
	public List<Collaborazione> getCollaborazioniAccettate(String emailRicevente) throws LoginException;

	/**
	 * Metodo che fornisce la lista delle richieste di aiuto verso l'utente ricevente, ancora non accettate.
	 * @param emailRicevente = String che rappresenta l'email dell'utente ricevente della richiesta di aiuto (collaborazione)
	 * @return <b>lista delle collaborazioni</b>, ovvero una List<Collaborazione> che rappresenta le 
	 * collaborazioni accettate dall'utente ricevente. Se non e' possibile ottenere tale lista, reistituisce <b>null</b>.
	 * @throws LoginException con causa ALCUNIPARAMETRINULLIOVUOTI
	 */
	public List<Collaborazione> getNotificheRichiesteAiuto(String emailRicevente) throws LoginException;
	
	/**
	 * Metodo per l'estrazione dell'utente dal database data la sua email
	 * @param email = String che rappresente l'email dell'utente
	 * @return <b>utente</b> corrispondente all'email, se esiste, <b>null</b> altrimenti
	 * @throws LoginException  con causa  ALCUNIPARAMETRINULLIOVUOTI
	 */
	public Utente getUtenteByEmail(String email) throws LoginException;

	public List<Collaborazione> getCollaborazioniDaTerminare(String emailRichiedente)
			throws LoginException;

	public List<Collaborazione> getCollaborazioniTerminateConFeedBack(
			String emailRichiedente) throws LoginException;

}