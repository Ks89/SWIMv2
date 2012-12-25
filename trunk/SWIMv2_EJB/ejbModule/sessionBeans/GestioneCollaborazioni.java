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

	public Collaborazione getCollaborazione(int id, String emailRichiedente, String emailRicevente) {
		Query query = entityManager.createNamedQuery("Collaborazione.getCollaborazioneByChiave");
		query.setParameter("id", id);
		query.setParameter("emailRichiedente", emailRichiedente);
		query.setParameter("emailRicevente", emailRicevente);
		return (Collaborazione)query.getSingleResult();
	}
	
	public Collaborazione richiediAiuto(String emailRichiedente, String emailRicevente, String nome, String descrizione) {
		Utente utenteRichiedente = this.getUtenteByEmail(emailRichiedente);
		Utente utenteRicevente = this.getUtenteByEmail(emailRicevente);

		System.out.println("sadasdas" + utenteRichiedente);

		System.out.println("sadasdsafasfas" + utenteRicevente);

		if(utenteRichiedente==null || utenteRicevente==null) {
			return null;
		}
		
		Collaborazione collaborazione = new Collaborazione();
		collaborazione.setUtenteRichiedente(utenteRichiedente);
		collaborazione.setUtenteRicevente(utenteRicevente);
		collaborazione.setNome(nome);
		collaborazione.setDescrizione(descrizione);

		System.out.println(collaborazione);
		
		entityManager.persist(collaborazione);
		entityManager.flush();
		
		return collaborazione;
	}
	
	public void accettaCollaborazione(int id, String emailRichiedente, String emailRicevente) {
		GregorianCalendar calendar = new GregorianCalendar();

		Query query = entityManager.createNamedQuery("Collaborazione.accettaCollaborazione");
		query.setParameter("dataStipula", calendar.getTime());
		query.setParameter("id", id);
		query.setParameter("emailRichiedente", emailRichiedente);
		query.setParameter("emailRicevente", emailRicevente);
		
//		Utente utenteRichiedente = this.getUtenteByEmail(emailRichiedente);
//		Utente utenteRicevente = this.getUtenteByEmail(emailRicevente);
//		
//		CollaborazionePK collaborazionePK = new CollaborazionePK();
//		collaborazionePK.setId(id);
//		collaborazionePK.setUtenteRichiedente(utenteRichiedente);
//		collaborazionePK.setUtenteRicevente(utenteRicevente);
//		
//		Collaborazione collaborazione = entityManager.find(Collaborazione.class, collaborazionePK);
//		collaborazione.setDataStipula(calendar.getTime()); 
//		
//		entityManager.persist(collaborazione);
//		entityManager.flush();
		query.executeUpdate();
//		return (query.executeUpdate() > 0);
	}
	
	public boolean rilasciaFeedback(int id, String emailRichiedente, String emailRicevente, String punteggioFB, String commentoFB) {
		Query query = entityManager.createNamedQuery("Collaborazione.rilasciaFeedback");
		query.setParameter("punteggioFeedback", punteggioFB);
		query.setParameter("commentoFeedback", commentoFB);
		query.setParameter("id", id);
		query.setParameter("emailRichiedente", emailRichiedente);
		query.setParameter("emailRicevente", emailRicevente);
		
		return (query.executeUpdate() > 0);
	}
	
	public boolean rifiutaCollaborazione(int id, String emailRichiedente, String emailRicevente) {
//		Utente utenteRichiedente = this.getUtenteByEmail(emailRichiedente);
//		Utente utenteRicevente = this.getUtenteByEmail(emailRicevente);
//		
//		CollaborazionePK collaborazionePK = new CollaborazionePK();
//		collaborazionePK.setId(id);
//		collaborazionePK.setUtenteRichiedente(utenteRichiedente);
//		collaborazionePK.setUtenteRicevente(utenteRicevente);
//		
//		Collaborazione collaborazione = entityManager.find(Collaborazione.class, collaborazionePK);
//		collaborazione.setDataStipula(calendar.getTime()); 
//		
//		entityManager.remove(collaborazione);
//		entityManager.flush();
		
		
		Query query = entityManager.createNamedQuery("Collaborazione.rifiutaCollaborazione");
		query.setParameter("id", id);
		query.setParameter("emailRichiedente", emailRichiedente);
		query.setParameter("emailRicevente", emailRicevente);

		return (query.executeUpdate() > 0); //si puo' usare executeUpdate anche per fare la DELETE
	}

	public void getPunteggioFeedback() {
		
	}	
	
	@SuppressWarnings("unchecked")
	public List<Collaborazione> getCollaborazioniFeedbackNonRilasciato(String emailRichiedente) {
		Query query = entityManager.createNamedQuery("Collaborazione.getCollaborazioniFeedbackNonRilasciatoByEmail");
		query.setParameter("emailRichiedente", emailRichiedente);
		
		return (List<Collaborazione>)query.getResultList();
	}


	@SuppressWarnings("unchecked")
	public List<Collaborazione> getCollaborazioniCreate(String emailRichiedente) {
		Query query = entityManager.createNamedQuery("Collaborazione.getCollaborazioniCreateByEmail");
		query.setParameter("emailRichiedente", emailRichiedente);
		
		return (List<Collaborazione>)query.getResultList();
	}

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
