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

/**
 * Classe che rappresenta il session bean GestioneCollaborazioni.
 */
@Stateless
public class GestioneCollaborazioni implements GestioneCollaborazioniRemote, GestioneCollaborazioniLocal {

	@PersistenceContext(unitName = "SWIMdb")
	private EntityManager entityManager;

	
	@Override
	@SuppressWarnings("unchecked")
	public List<Collaborazione> getNotificheCollaborazioniAccettateDaUtentiRiceventi(String emailRichiedente) throws CollaborazioneException {
		if(emailRichiedente==null || emailRichiedente.equals("")) {
			throw new CollaborazioneException(CollaborazioneException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		}
		
		Query query = entityManager.createNamedQuery("Collaborazione.getNotificheCollaborazioniAccettateDaUtentiRiceventi");
		query.setParameter("emailRichiedente", emailRichiedente);
		return (List<Collaborazione>)query.getResultList(); 
	}
	
	
	
	@Override
	public Collaborazione setCollaborazioneNotificata(Long id) throws CollaborazioneException {
		if(id==null) {
			throw new CollaborazioneException(CollaborazioneException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		}
		
		Collaborazione collaborazione = entityManager.find(Collaborazione.class, id);
		
		//se la collaborazione e' !=null ed e' stata accettata dal ricevente
		if(collaborazione!=null && collaborazione.getDataStipula()!=null) { 
			collaborazione.setNotificaAlRichiedente(true);
			entityManager.persist(collaborazione);
			entityManager.flush();
		}
		return collaborazione;
	}
	
	
	@Override
	public Collaborazione getCollaborazione(Long id) throws CollaborazioneException {
		if(id==null) {
			throw new CollaborazioneException(CollaborazioneException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		}
		return (Collaborazione)entityManager.find(Collaborazione.class, id);
	}

	@Override
	public Collaborazione richiediAiuto(String emailRichiedente, String emailRicevente, String nome, String descrizione) throws CollaborazioneException {
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

		entityManager.persist(collaborazione);
		entityManager.flush();

		return collaborazione;
	}


	@Override
	public Collaborazione accettaCollaborazione(Long id) throws CollaborazioneException {
		GregorianCalendar calendar = new GregorianCalendar();
		
		if(id==null) {
			throw new CollaborazioneException(CollaborazioneException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		}
		
		Collaborazione collaborazione = entityManager.find(Collaborazione.class, id);
		if(collaborazione!=null) {
			collaborazione.setDataStipula(calendar.getTime());
			entityManager.persist(collaborazione);
			entityManager.flush();
		}
		return collaborazione;
	}

	@Override
	public Collaborazione terminaCollaborazione(Long id) throws CollaborazioneException {
		GregorianCalendar calendar = new GregorianCalendar();

		if(id==null) {
			throw new CollaborazioneException(CollaborazioneException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		}
		
		Collaborazione collaborazione = entityManager.find(Collaborazione.class, id);
		if(collaborazione!=null) {
			collaborazione.setDataTermine(calendar.getTime());
			entityManager.persist(collaborazione);
			entityManager.flush();
		}
		return collaborazione;
	}

	@Override
	public boolean rifiutaCollaborazione(Long id) throws CollaborazioneException {
		if(id==null) {
			throw new CollaborazioneException(CollaborazioneException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		}
		
		Collaborazione collaborazione = entityManager.find(Collaborazione.class, id);
		if(collaborazione!=null) {
			entityManager.remove(collaborazione);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Collaborazione rilasciaFeedback(Long id, Integer punteggioFB, String commentoFB) throws CollaborazioneException {
		if(id==null || punteggioFB==null) {
			throw new CollaborazioneException(CollaborazioneException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
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


	@Override
	public Double getPunteggioFeedback(String emailRicevente) throws CollaborazioneException {
		if(emailRicevente==null || emailRicevente.equals("")) {
			throw new CollaborazioneException(CollaborazioneException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		}
		
		Query query = entityManager.createNamedQuery("Collaborazione.getPunteggioFeedbackByEmail");
		query.setParameter("emailRicevente", emailRicevente);
		return (Double) query.getSingleResult();
	}	


	@Override
	@SuppressWarnings("unchecked")
	public List<Collaborazione> getCollaborazioniCreateFeedbackNonRilasciato(String emailRichiedente) throws CollaborazioneException {
		if(emailRichiedente==null || emailRichiedente.equals("")) {
			throw new CollaborazioneException(CollaborazioneException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		}
		
		Query query = entityManager.createNamedQuery("Collaborazione.getCollaborazioniCreateFeedbackNonRilasciatoByEmail");
		query.setParameter("emailRichiedente", emailRichiedente);
		return (List<Collaborazione>)query.getResultList();
	}


	@Override
	@SuppressWarnings("unchecked")
	public List<Collaborazione> getCollaborazioniCreate(String emailRichiedente) throws CollaborazioneException {
		if(emailRichiedente==null || emailRichiedente.equals("")) {
			throw new CollaborazioneException(CollaborazioneException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		}
		
		Query query = entityManager.createNamedQuery("Collaborazione.getCollaborazioniCreateByEmail");
		query.setParameter("emailRichiedente", emailRichiedente);
		return (List<Collaborazione>)query.getResultList();	
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Collaborazione> getCollaborazioniAccettate(String emailRicevente) throws CollaborazioneException {
		if(emailRicevente==null || emailRicevente.equals("")) {
			throw new CollaborazioneException(CollaborazioneException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		}
		
		Query query = entityManager.createNamedQuery("Collaborazione.getCollaborazioniAccettateByEmail");
		query.setParameter("emailRicevente", emailRicevente);
		return (List<Collaborazione>)query.getResultList();	
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public List<Collaborazione> getNotificheRichiesteAiuto(String emailRicevente) throws CollaborazioneException {
		if(emailRicevente==null || emailRicevente.equals("")) {
			throw new CollaborazioneException(CollaborazioneException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		}
		
		Query query = entityManager.createNamedQuery("Collaborazione.getNotificheRichiesteAiuto");
		query.setParameter("emailRicevente", emailRicevente);
		return (List<Collaborazione>)query.getResultList();	
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Collaborazione> getCollaborazioniDaTerminare(String emailRichiedente) throws CollaborazioneException {
		if(emailRichiedente==null || emailRichiedente.equals("")) {
			throw new CollaborazioneException(CollaborazioneException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		}
		
		Query query = entityManager.createNamedQuery("Collaborazione.getCollaborazioniDaTerminare");
		query.setParameter("emailRichiedente", emailRichiedente);
		return (List<Collaborazione>)query.getResultList();	
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Collaborazione> getCollaborazioniTerminateConFeedBack(String emailRichiedente) throws CollaborazioneException {
		if(emailRichiedente==null || emailRichiedente.equals("")) {
			throw new CollaborazioneException(CollaborazioneException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		}
		
		Query query = entityManager.createNamedQuery("Collaborazione.getCollaborazioniTerminateConFeedBack");
		query.setParameter("emailRichiedente", emailRichiedente);
		return (List<Collaborazione>)query.getResultList();	
	}
	
	@Override
	public Utente getUtenteByEmail(String email) throws CollaborazioneException {
		if(email==null || email.equals("")) {
			throw new CollaborazioneException(CollaborazioneException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		} else {
			return entityManager.find(Utente.class, email);
		}
	}
}
