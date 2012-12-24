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
import exceptions.UtenteNonTrovatoException;

@Stateless
public class GestioneCollaborazioni implements GestioneCollaborazioniRemote {

	@PersistenceContext(unitName = "SWIMdb")
	private EntityManager entityManager;

	public void richiediAiuto(String emailRichiedente, String emailRicevente, String nome, String descrizione) throws UtenteNonTrovatoException {
		Utente utenteRichiedente = this.controllaEsistenzaEmailUtente(emailRichiedente);
		Utente utenteRicevente = this.controllaEsistenzaEmailUtente(emailRicevente);

		Collaborazione collaborazione = new Collaborazione();
		collaborazione.setUtenteRichiedente(utenteRichiedente);
		collaborazione.setUtenteRicevente(utenteRicevente);
		collaborazione.setNome(nome);
		collaborazione.setDescrizione(descrizione);

		entityManager.persist(collaborazione);
		entityManager.flush();
		
	}
	
	public boolean accettaCollaborazione(String id, String emailRichiedente, String emailRicevente) {
		GregorianCalendar calendar = new GregorianCalendar();

		Query query = entityManager.createNamedQuery("Collaborazione.accettaCollaborazione");
		query.setParameter("dataStipula", calendar.getTime());
		query.setParameter("id", id);
		query.setParameter("emailRichiedente", emailRichiedente);
		query.setParameter("emailRicevente", emailRicevente);
		
		return (query.executeUpdate() > 0);
	}
	
	public boolean rilasciaFeedback(String id, String emailRichiedente, String emailRicevente, String punteggioFB, String commentoFB) {
		Query query = entityManager.createNamedQuery("Collaborazione.rilasciaFeedback");
		query.setParameter("punteggioFeedback", punteggioFB);
		query.setParameter("commentoFeedback", commentoFB);
		query.setParameter("id", id);
		query.setParameter("emailRichiedente", emailRichiedente);
		query.setParameter("emailRicevente", emailRicevente);
		
		return (query.executeUpdate() > 0);
	}
	
	public boolean rifiutaCollaborazione(String id, String emailRichiedente, String emailRicevente) {
		Query query = entityManager.createNamedQuery("Collaborazione.rifiutaCollaborazione");
		query.setParameter("id", id);
		query.setParameter("emailRichiedente", emailRichiedente);
		query.setParameter("emailRicevente", emailRicevente);

		return (query.executeUpdate() > 0); //si usare executeUpdate anche per fare la DELETE
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
	 * Metodo di verifica dell'esistenza dello studente
	 * @param codStudente e' il codice persona dello studente di cui si vuole verificare l'esistenza
	 * @return l'oggetto contenente lo studente
	 * @throws StudenteInesistenteException nel caso in cui lo studente con codice studente specificato non esista
	 * @author Ricky
	 */
	private Utente controllaEsistenzaEmailUtente(String email) throws UtenteNonTrovatoException {
		Utente utente = this.getUtenteByEmail(email);
		if (utente == null) {
			throw new UtenteNonTrovatoException("Impossibile trovare l'utente con email: " + email);
		}
		return utente;
	}


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
