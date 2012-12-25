package sessionBeans;

import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import utililies.sessionRemote.GestioneCollaborazioniRemote;
import entityBeans.Collaborazione;
import entityBeans.Utente;

@Stateless
public class GestioneCollaborazioni implements GestioneCollaborazioniRemote {

	@PersistenceContext(unitName = "SWIMdb")
	private EntityManager entityManager;

	public Collaborazione getCollaborazione(Long id) {
		return (Collaborazione)entityManager.find(Collaborazione.class, id);
	}
	
	public Collaborazione richiediAiuto(String emailRichiedente, String emailRicevente, String nome, String descrizione) {
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
		
		return collaborazione;
	}
	
	public void accettaCollaborazione(Long id) {
		GregorianCalendar calendar = new GregorianCalendar();

		Collaborazione collaborazione = entityManager.find(Collaborazione.class, id);
		collaborazione.setDataStipula(calendar.getTime());
		
		entityManager.persist(collaborazione); //non obbligatori, funziona senza sia questo che il flush SOLO per l'update
		entityManager.flush();
	}
	
	public void terminaCollaborazione(Long id) {
		GregorianCalendar calendar = new GregorianCalendar();

		Collaborazione collaborazione = entityManager.find(Collaborazione.class, id);
		collaborazione.setDataTermine(calendar.getTime());
		
		entityManager.persist(collaborazione); //non obbligatori, funziona senza sia questo che il flush SOLO per l'update
		entityManager.flush();
	}
	
	public void rifiutaCollaborazione(Long id) {
		Collaborazione collaborazione = entityManager.find(Collaborazione.class, id);
		entityManager.remove(collaborazione);
	}
	
	public void rilasciaFeedback(Long id, Integer punteggioFB, String commentoFB) {
		Collaborazione collaborazione = entityManager.find(Collaborazione.class, id);
		collaborazione.setPunteggioFeedback(punteggioFB);
		collaborazione.setCommentoFeedback(commentoFB);
		
		entityManager.persist(collaborazione); //non obbligatori, funziona senza sia questo che il flush SOLO per l'update
		entityManager.flush();
	}
	
	public Double getPunteggioFeedback(String emailRicevente) {
		Query query = entityManager.createNamedQuery("Collaborazione.getPunteggioFeedbackByEmail");
		query.setParameter("emailRicevente", emailRicevente);
		
		Double punteggioFeedback = (Double) query.getSingleResult();
		
		System.out.println("___________________________________________________________________________qwertyuiop" + punteggioFeedback);
		return punteggioFeedback;
	}	
	
	@SuppressWarnings("unchecked")
	public List<Collaborazione> getCollaborazioniCreateFeedbackNonRilasciato(String emailRichiedente) {
		Query query = entityManager.createNamedQuery("Collaborazione.getCollaborazioniCreateFeedbackNonRilasciatoByEmail");
		query.setParameter("emailRichiedente", emailRichiedente);
		
		return (List<Collaborazione>)query.getResultList();
	}


	@SuppressWarnings("unchecked")
	public List<Collaborazione> getCollaborazioniCreate(String emailRichiedente) {
		Query query = entityManager.createNamedQuery("Collaborazione.getCollaborazioniCreateByEmail");
		query.setParameter("emailRichiedente", emailRichiedente);
		
		return (List<Collaborazione>)query.getResultList();
	}

	/**
	 * Metodo che fornisce la lista delle collaborazioni accettate dall'utente ricevente identificato come emailRicevente
	 */
	@SuppressWarnings("unchecked")
	public List<Collaborazione> getCollaborazioniAccettate(String emailRicevente) {
		Query query = entityManager.createNamedQuery("Collaborazione.getCollaborazioniAccettateByEmail");
		query.setParameter("emailRicevente", emailRicevente);
		
		return (List<Collaborazione>)query.getResultList();
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
