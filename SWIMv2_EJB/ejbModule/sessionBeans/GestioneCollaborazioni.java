package sessionBeans;

import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import sessionBeans.localInterfaces.GestioneCollaborazioniLocal;
import utililies.sessionRemote.GestioneCollaborazioniRemote;

import entityBeans.Collaborazione;
import entityBeans.Utente;
import exceptions.CollaborazioneException;
import exceptions.LoginException;

/**
 * Classe che rappresenta il session bean GestioneCollaborazioni.
 */
@Stateless
public class GestioneCollaborazioni implements GestioneCollaborazioniRemote, GestioneCollaborazioniLocal {

	@PersistenceContext(unitName = "SWIMdb")
	private EntityManager entityManager;

	/**
	 * Metodo per ottenere la collaborazione con l'id specificato
	 * @param id = Long che rappresenta l'id univoco della collaborazione
	 * @return <b>collaborazione</b> con l'id specificato, se esiste, <b>null</b> altrimenti
	 * @throws LoginException con causa ALCUNIPARAMETRINULLIOVUOTI
	 */
	@Override
	public Collaborazione getCollaborazione(Long id) throws LoginException {
		if(id==null) {
			throw new LoginException(LoginException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		}
		return (Collaborazione)entityManager.find(Collaborazione.class, id);
	}

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
	@Override
	public Collaborazione richiediAiuto(String emailRichiedente, String emailRicevente, String nome, String descrizione) throws CollaborazioneException, LoginException {
		if(emailRichiedente==null || emailRichiedente.equals("") || emailRicevente==null || emailRicevente.equals("") ||
				nome==null || nome.equals("")) {
			throw new CollaborazioneException(CollaborazioneException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		}

		Utente utenteRichiedente = this.getUtenteByEmail(emailRichiedente);
		Utente utenteRicevente = this.getUtenteByEmail(emailRicevente);

		if(utenteRichiedente==null || utenteRicevente==null) {
			return null;
		}

		Collaborazione collaborazione = new Collaborazione();
		collaborazione.setUtenteRichiedente(utenteRichiedente);
		collaborazione.setUtenteRicevente(utenteRicevente);
		collaborazione.setNome(nome);
		collaborazione.setDescrizione(descrizione);
		collaborazione.setNotificaAlRichiedente(false);

		entityManager.persist(collaborazione);
		entityManager.flush();

		return collaborazione;
	}


	/**
	 * Metodo per accettare una collaborazione che puo' essere utilizzato dall'utente ricevente per accettare una richiesta di aiuto 
	 * dell'utente richiedente. Tale metodo imposta la data di stipula della collaborazione con l'istante in cui l'utente ricevente 
	 * l'accetta (chiamando questo metodo).
	 * @param id = Long che rappresenta l'id univoco della collaborazione
	 * @return <b>collaborazione</b> con l'id specificato, se accettata correttamente, <b>null</b> altrimenti
	 * @throws LoginException con causa ALCUNIPARAMETRINULLIOVUOTI
	 */
	@Override
	public Collaborazione accettaCollaborazione(Long id) throws LoginException {
		GregorianCalendar calendar = new GregorianCalendar();
		
		if(id==null) {
			throw new LoginException(LoginException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		}
		
		Collaborazione collaborazione = entityManager.find(Collaborazione.class, id);
		if(collaborazione!=null) {
			collaborazione.setDataStipula(calendar.getTime());
			entityManager.persist(collaborazione);
			entityManager.flush();
		}
		return collaborazione;
	}
	
	
	/**
	 * Metodo per impostare la collaborazione come già notificata al richiedente
	 * @param id = Long che rappresenta l'id univoco della collaborazione
	 * @return <b>collaborazione</b> con l'id specificato, se accettata correttamente, <b>null</b> altrimenti
	 * @throws LoginException con causa ALCUNIPARAMETRINULLIOVUOTI
	 */
	@Override
	public Collaborazione notificaAvvenuta(Long id) throws LoginException {
		
		if(id==null) {
			throw new LoginException(LoginException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		}
		
		Collaborazione collaborazione = entityManager.find(Collaborazione.class, id);
		if(collaborazione!=null) {
			collaborazione.setNotificaAlRichiedente(true);
			entityManager.persist(collaborazione);
			entityManager.flush();
		}
		return collaborazione;
	}

	/**
	 * Metodo per terminare una collaborazione che puo' essere utilizzato dall'utente richiedente per terminare una richiesta di aiuto 
	 * precedentemente aperta. Tale metodo imposta la data di termina della collaborazione con l'istante in cui l'utente richiedente 
	 * la termina (chiamando questo metodo).
	 * @param id = Long che rappresenta l'id univoco della collaborazione
	 * @return <b>collaborazione</b> con l'id specificato, se terminata correttamente, <b>null</b> altrimenti
	 * @throws LoginException con causa ALCUNIPARAMETRINULLIOVUOTI
	 */
	@Override
	public Collaborazione terminaCollaborazione(Long id) throws LoginException {
		GregorianCalendar calendar = new GregorianCalendar();

		if(id==null) {
			throw new LoginException(LoginException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		}
		
		Collaborazione collaborazione = entityManager.find(Collaborazione.class, id);
		if(collaborazione!=null) {
			collaborazione.setDataTermine(calendar.getTime());
			entityManager.persist(collaborazione);
			entityManager.flush();
		}
		return collaborazione;
	}

	/**
	 * Metodo per rifiutare una collaborazione. Puo' essere chiamato da un utente che ha ricevuto una richiesta di aiuto che non vuole accettare.
	 * @param id = Long che rappresenta l'id univoco della collaborazione
	 * @return <b>true</b> se la collaborazione e' stata rimossa correttamente, <b>false</b> altrimenti.
	 * @throws LoginException con causa ALCUNIPARAMETRINULLIOVUOTI
	 */
	@Override
	public boolean rifiutaCollaborazione(Long id) throws LoginException {
		if(id==null) {
			throw new LoginException(LoginException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		}
		
		Collaborazione collaborazione = entityManager.find(Collaborazione.class, id);
		if(collaborazione!=null) {
			entityManager.remove(collaborazione);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Metodo che puo' essere utilizzato dall'utente richidente per rilasciare il feedback dopo aver stipulato e poi terminato una collaborazione.
	 * @param id = Long che rappresenta l'id univoco della collaborazione
	 * @param punteggioFB = Integer che rappresenta il punteggio di feedback della collaborazione
	 * @param commentoFB = String che rappresenta il commento di feedback della collaborazione
	 * @return <b>collaborazione</b> con l'id specificato, se il feedback e' stato rilasciato correttamente, <b>null</b> altrimenti
	 * @throws LoginException con causa ALCUNIPARAMETRINULLIOVUOTI
	 */
	@Override
	public Collaborazione rilasciaFeedback(Long id, Integer punteggioFB, String commentoFB) throws LoginException {
		if(id==null || punteggioFB==null) {
			throw new LoginException(LoginException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		}
		
		Collaborazione collaborazione = entityManager.find(Collaborazione.class, id);
		if(collaborazione!=null && collaborazione.getDataStipula()!=null && collaborazione.getDataTermine()!=null) {
			collaborazione.setPunteggioFeedback(punteggioFB);
			collaborazione.setCommentoFeedback(commentoFB);
			entityManager.persist(collaborazione);
			entityManager.flush();
		}
		return collaborazione;
	}


	/**
	 * Metodo per ottenere il punteggio di feedback di un utente.
	 * @param emailRicevente = String che rappresenta l'email dell'utente ricevente delle collaborazioni, cioe' coloi a cui
	 * viene rilasciato il feedback dall'utente richieente.
	 * @return <b>il punteggio di feedback</b>, cioe' un double che rappresenta la media dei voti di tutte le collaborazioni
	 * di cui ha ricevuto un punteggio di feecback. Se non e' possibile ottenere tale valore, reistituisce <b>null</b>.
	 * @throws LoginException con causa ALCUNIPARAMETRINULLIOVUOTI
	 */
	@Override
	public Double getPunteggioFeedback(String emailRicevente) throws LoginException {
		if(emailRicevente==null || emailRicevente.equals("")) {
			throw new LoginException(LoginException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		}
		
		Query query = entityManager.createNamedQuery("Collaborazione.getPunteggioFeedbackByEmail");
		query.setParameter("emailRicevente", emailRicevente);
		return (Double) query.getSingleResult();
	}	


	/**
	 * Metodo per ottenere la lista delle collaborazioni create dall'utente richiedente, di cui non ha ancora rilasciato un feedback.
	 * @param emailRichiedente = String che rappresenta l'email dell'utente richiedente della richiesta di aiuto (collaborazione)
	 * @return <b>lista delle collaborazioni</b>, cioe' una List<Collaborazione> che rappresenta le collaborazioni, ancora senza feedback. 
	 * Se non e' possibile ottenere tale lista, reistituisce <b>null</b>.
	 * @throws LoginException con causa ALCUNIPARAMETRINULLIOVUOTI
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Collaborazione> getCollaborazioniCreateFeedbackNonRilasciato(String emailRichiedente) throws LoginException {
		if(emailRichiedente==null || emailRichiedente.equals("")) {
			throw new LoginException(LoginException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		}
		
		Query query = entityManager.createNamedQuery("Collaborazione.getCollaborazioniCreateFeedbackNonRilasciatoByEmail");
		query.setParameter("emailRichiedente", emailRichiedente);
		return (List<Collaborazione>)query.getResultList();
	}
	
	
	/**
	 * Metodo per ottenere la lista delle collaborazioni create dall'utente richiedente, di cui bisogna notificarne l'avvenuta conferma.
	 * @param emailRichiedente = String che rappresenta l'email dell'utente richiedente della richiesta di aiuto (collaborazione)
	 * @return <b>lista delle collaborazioni</b>, cioe' una List<Collaborazione> che rappresenta le collaborazioni, ancora senza feedback. 
	 * Se non e' possibile ottenere tale lista, reistituisce <b>null</b>.
	 * @throws LoginException con causa ALCUNIPARAMETRINULLIOVUOTI
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Collaborazione> getCollaborazioniDaNotificare(String emailRichiedente) throws LoginException {
		if(emailRichiedente==null || emailRichiedente.equals("")) {
			throw new LoginException(LoginException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		}
		
		Query query = entityManager.createNamedQuery("Collaborazione.getCollaborazioniDaNotificare");
		query.setParameter("emailRichiedente", emailRichiedente);
		return (List<Collaborazione>)query.getResultList();
	}


	/**
	 * Metodo per ottenere la lista delle collaborazioni create dall'utente richiedente.
	 * @param emailRichiedente = String che rappresenta l'email dell'utente richiedente della richiesta di aiuto (collaborazione)
	 * @return <b>lista delle collaborazioni</b>, ovvero una List<Collaborazione> che rappresenta le 
	 * collaborazioni iniziate dall'utente, cioe' le richieste di aiuto dell'utente richiedente.
	 * Se non e' possibile ottenere tale lista, reistituisce <b>null</b>.
	 * @throws LoginException con causa ALCUNIPARAMETRINULLIOVUOTI
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Collaborazione> getCollaborazioniCreate(String emailRichiedente) throws LoginException {
		if(emailRichiedente==null || emailRichiedente.equals("")) {
			throw new LoginException(LoginException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		}
		
		Query query = entityManager.createNamedQuery("Collaborazione.getCollaborazioniCreateByEmail");
		query.setParameter("emailRichiedente", emailRichiedente);
		return (List<Collaborazione>)query.getResultList();	
	}

	/**
	 * Metodo che fornisce la lista delle collaborazioni accettate dall'utente ricevente identificato come emailRicevente
	 * @param emailRicevente = String che rappresenta l'email dell'utente ricevente della richiesta di aiuto (collaborazione)
	 * @return <b>lista delle collaborazioni</b>, ovvero una List<Collaborazione> che rappresenta le 
	 * collaborazioni accettate dall'utente ricevente. Se non e' possibile ottenere tale lista, reistituisce <b>null</b>.
	 * @throws LoginException con causa ALCUNIPARAMETRINULLIOVUOTI
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Collaborazione> getCollaborazioniAccettate(String emailRicevente) throws LoginException {
		if(emailRicevente==null || emailRicevente.equals("")) {
			throw new LoginException(LoginException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		}
		
		Query query = entityManager.createNamedQuery("Collaborazione.getCollaborazioniAccettateByEmail");
		query.setParameter("emailRicevente", emailRicevente);
		return (List<Collaborazione>)query.getResultList();	
	}

	
	/**
	 * Metodo che fornisce la lista delle richieste di aiuto verso l'utente ricevente, ancora non accettate.
	 * @param emailRicevente = String che rappresenta l'email dell'utente ricevente della richiesta di aiuto (collaborazione)
	 * @return <b>lista delle collaborazioni</b>, ovvero una List<Collaborazione> che rappresenta le 
	 * collaborazioni accettate dall'utente ricevente. Se non e' possibile ottenere tale lista, reistituisce <b>null</b>.
	 * @throws LoginException con causa ALCUNIPARAMETRINULLIOVUOTI
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Collaborazione> getNotificheRichiesteAiuto(String emailRicevente) throws LoginException {
		if(emailRicevente==null || emailRicevente.equals("")) {
			throw new LoginException(LoginException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		}
		
		Query query = entityManager.createNamedQuery("Collaborazione.getNotificheRichiesteAiuto");
		query.setParameter("emailRicevente", emailRicevente);
		return (List<Collaborazione>)query.getResultList();	
	}
	
	
	/**
	 * Metodo che fornisce la lista delle collaborazioni non ancora terminate.
	 * @param emailRichiedente = String che rappresenta l'email dell'utente di cui si vuole ottenere le collaborazioni
	 * @return <b>lista delle collaborazioni</b>, ovvero una List<Collaborazione> che rappresenta le 
	 * collaborazioni non ancora terminate. Se non e' possibile ottenere tale lista, reistituisce <b>null</b>.
	 * @throws LoginException con causa ALCUNIPARAMETRINULLIOVUOTI
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Collaborazione> getCollaborazioniDaTerminare(String emailRichiedente) throws LoginException {
		//if(emailRichiedente==null || emailRichiedente.equals("")) {
		//	throw new LoginException(LoginException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		//}
		
		Query query = entityManager.createNamedQuery("Collaborazione.getCollaborazioniDaTerminare");
		query.setParameter("emailRichiedente", emailRichiedente);
		return (List<Collaborazione>)query.getResultList();	
	}
	
	/**
	 * Metodo che fornisce la lista delle collaborazioni terminate con il feedback rilasciato
	 * @param emailRichiedente = String che rappresenta l'email dell'utente di cui si vuole ottenere le collaborazioni
	 * @return <b>lista delle collaborazioni</b>, ovvero una List<Collaborazione> che rappresenta le 
	 * collaborazioni non ancora terminate. Se non e' possibile ottenere tale lista, reistituisce <b>null</b>.
	 * @throws LoginException con causa ALCUNIPARAMETRINULLIOVUOTI
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Collaborazione> getCollaborazioniTerminateConFeedBack(String emailRichiedente) throws LoginException {
		if(emailRichiedente==null || emailRichiedente.equals("")) {
		throw new LoginException(LoginException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		}
		
		Query query = entityManager.createNamedQuery("Collaborazione.getCollaborazioniTerminateConFeedBack");
		query.setParameter("emailRichiedente", emailRichiedente);
		return (List<Collaborazione>)query.getResultList();	
	}
	
	/**
	 * Metodo che fornisce la lista delle collaborazioni dove l'utente corrispondente alla mail passata come parametro
	 * è l'utente ricevente, ed è stato rilasciato un feedback relativo a quella collaborazione.
	 * @param emailUtente = String che rappresenta l'email dell'utente di cui si vuole ottenere le collaborazioni
	 * @return <b>lista delle collaborazioni</b>, ovvero una List<Collaborazione> che rappresenta le 
	 * collaborazioni non ancora terminate. Se non e' possibile ottenere tale lista, reistituisce <b>null</b>.
	 * @throws LoginException con causa ALCUNIPARAMETRINULLIOVUOTI
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Collaborazione> getCollaborazioniRiceventeConFeedBack(String emailUtente) throws LoginException {
		if(emailUtente==null || emailUtente.equals("")) {
			throw new LoginException(LoginException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		}
		
		Query query = entityManager.createNamedQuery("Collaborazione.getCollaborazioniRichiesteConFeedBack");
		query.setParameter("emailUtente", emailUtente);
		return (List<Collaborazione>)query.getResultList();	
	}
	
	/**
	 * Metodo per l'estrazione dell'utente dal database data la sua email
	 * @param email = String che rappresente l'email dell'utente
	 * @return <b>utente</b> corrispondente all'email, se esiste, <b>null</b> altrimenti
	 * @throws LoginException  con causa  ALCUNIPARAMETRINULLIOVUOTI
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
