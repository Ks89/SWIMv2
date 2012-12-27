package sessionBeans;

import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import lombok.extern.slf4j.Slf4j;

import sessionBeans.localInterfaces.GestioneCollaborazioniLocal;
import utililies.sessionRemote.GestioneCollaborazioniRemote;
import entityBeans.Collaborazione;
import entityBeans.Utente;

@Slf4j
@Stateless
public class GestioneCollaborazioni implements GestioneCollaborazioniRemote, GestioneCollaborazioniLocal {

	@PersistenceContext(unitName = "SWIMdb")
	private EntityManager entityManager;

	/**
	 * Metodo per ottenere la collaborazione con l'id specificato
	 * @param id = Id univoco della collaborazione
	 * @return <b>collaborazione</b> corrispondente all'id, se esiste, <b>null</b> altrimenti
	 */
	public Collaborazione getCollaborazione(Long id) {
		log.info("getCollaborazione() : " + id); 
		Collaborazione collaborazione = (Collaborazione)entityManager.find(Collaborazione.class, id);
		log.info("getCollaborazione() : " + collaborazione); 
		return collaborazione;
	}
	
	/**
	 * Metodo per richiedere aiuto ad un altro utente, cioe' creare una nuova collaborazione.
	 * @param emailRichiedente
	 * @param emailRicevente
	 * @param nome
	 * @param descrizione
	 */
	public Collaborazione richiediAiuto(String emailRichiedente, String emailRicevente, String nome, String descrizione) {
		log.info("richiediAiuto() : " + emailRichiedente + " , " + emailRicevente + " , " + nome + " , " + descrizione); 
		
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
		
		entityManager.persist(collaborazione);
		entityManager.flush();
		
		log.info("richiediAiuto() : " + utenteRichiedente); 
		log.info("richiediAiuto() : " + utenteRicevente); 
		log.info("richiediAiuto() : " + collaborazione); 
		return collaborazione;
	}
	
	
	public void accettaCollaborazione(Long id) {
		log.info("terminaCollaborazione() : " + id); 

		GregorianCalendar calendar = new GregorianCalendar();

		Collaborazione collaborazione = entityManager.find(Collaborazione.class, id);
		collaborazione.setDataStipula(calendar.getTime());
		
		entityManager.persist(collaborazione); //non obbligatori, funziona senza sia questo che il flush SOLO per l'update
		entityManager.flush();
		
		log.info("accettaCollaborazione() : " + collaborazione); 
	}
	
	public void terminaCollaborazione(Long id) {
		log.info("terminaCollaborazione() : " + id); 

		GregorianCalendar calendar = new GregorianCalendar();

		Collaborazione collaborazione = entityManager.find(Collaborazione.class, id);
		collaborazione.setDataTermine(calendar.getTime());
		
		entityManager.persist(collaborazione); //non obbligatori, funziona senza sia questo che il flush SOLO per l'update
		entityManager.flush();
		
		log.info("terminaCollaborazione() : " + collaborazione); 
	}
	
	public void rifiutaCollaborazione(Long id) {
		Collaborazione collaborazione = entityManager.find(Collaborazione.class, id);

		log.info("rifiutaCollaborazione() : " + collaborazione); 

		entityManager.remove(collaborazione);
	}
	
	public void rilasciaFeedback(Long id, Integer punteggioFB, String commentoFB) {
		Collaborazione collaborazione = entityManager.find(Collaborazione.class, id);
		collaborazione.setPunteggioFeedback(punteggioFB);
		collaborazione.setCommentoFeedback(commentoFB);
		
		entityManager.persist(collaborazione); //non obbligatori, funziona senza sia questo che il flush SOLO per l'update
		entityManager.flush();
		
		log.info("rilasciaFeedback() : " + collaborazione); 
	}
	
	public Double getPunteggioFeedback(String emailRicevente) {
		log.info("getPunteggioFeedback() : " + emailRicevente); 

		Query query = entityManager.createNamedQuery("Collaborazione.getPunteggioFeedbackByEmail");
		query.setParameter("emailRicevente", emailRicevente);
		Double punteggio = (Double) query.getSingleResult();

		log.info("getPunteggioFeedback() : " + punteggio); 
		return punteggio;
	}	
	
	@SuppressWarnings("unchecked")
	public List<Collaborazione> getCollaborazioniCreateFeedbackNonRilasciato(String emailRichiedente) {
		log.info("getCollaborazioniCreateFeedbackNonRilasciato() : " + emailRichiedente); 

		Query query = entityManager.createNamedQuery("Collaborazione.getCollaborazioniCreateFeedbackNonRilasciatoByEmail");
		query.setParameter("emailRichiedente", emailRichiedente);
		List<Collaborazione> collaborazione = (List<Collaborazione>)query.getResultList();

		log.info("getCollaborazioniCreateFeedbackNonRilasciato() : " + collaborazione); 
		return collaborazione;
	}


	@SuppressWarnings("unchecked")
	public List<Collaborazione> getCollaborazioniCreate(String emailRichiedente) {
		log.info("getCollaborazioniCreate() : " + emailRichiedente); 

		Query query = entityManager.createNamedQuery("Collaborazione.getCollaborazioniCreateByEmail");
		query.setParameter("emailRichiedente", emailRichiedente);
		List<Collaborazione> collaborazione = (List<Collaborazione>)query.getResultList();

		log.info("getCollaborazioniCreate() : " + collaborazione); 
		return collaborazione;	
	}

	/**
	 * Metodo che fornisce la lista delle collaborazioni accettate dall'utente ricevente identificato come emailRicevente
	 */
	@SuppressWarnings("unchecked")
	public List<Collaborazione> getCollaborazioniAccettate(String emailRicevente) {
		log.info("getCollaborazioniAccettate() : " + emailRicevente); 

		Query query = entityManager.createNamedQuery("Collaborazione.getCollaborazioniAccettateByEmail");
		query.setParameter("emailRicevente", emailRicevente);
		List<Collaborazione> collaborazione = (List<Collaborazione>)query.getResultList();

		log.info("getCollaborazioniAccettate() : " + collaborazione); 
		return collaborazione;	
	}
	
	/**
	 * Metodo per l'estrazione dell'utente dal database data la sua email
	 * @param email = l'email dell'amministratore
	 * @return <b>utente</b> corrispondente all'email, se esiste, <b>null</b> altrimenti
	 */
	@Override
	public Utente getUtenteByEmail(String email) {
		log.info("getUtenteByEmail() : " + email); 

		Utente utente = entityManager.find(Utente.class, email);
		
		log.info("getUtenteByEmail() : " + utente); 
		return utente;
	}
}
