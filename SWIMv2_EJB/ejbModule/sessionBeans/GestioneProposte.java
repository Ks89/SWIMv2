package sessionBeans;

import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import sessionBeans.localInterfaces.GestioneProposteLocal;
import utililies.sessionRemote.GestioneProposteRemote;

import entityBeans.Abilita;
import entityBeans.Amministratore;
import entityBeans.PropostaAbilita;
import entityBeans.Utente;
import exceptions.ProposteException;

/**
 * Classe che rappresenta il session bean GestioneProposte.
 */
@Stateless
public class GestioneProposte implements GestioneProposteRemote, GestioneProposteLocal{

	@PersistenceContext(unitName = "SWIMdb")
	private EntityManager entityManager;


	/* (non-Javadoc)
	 * @see sessionBeans.GestioneProposteInterface#getAbilitaByNome(java.lang.String)
	 */
	@Override
	public Abilita getAbilitaByNome(String nome) throws ProposteException {
		if(nome==null || nome.equals("")) {
			throw new ProposteException(ProposteException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		} else {
			return entityManager.find(Abilita.class, nome);
		}
	}

	/* (non-Javadoc)
	 * @see sessionBeans.GestioneProposteInterface#getProposteAbilitaNonConfermate()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PropostaAbilita> getProposteAbilitaNonConfermate() {
		Query query = entityManager.createNamedQuery("PropostaAbilita.getTutteProposteAbilitaNonConfermate");
		return (List<PropostaAbilita>) query.getResultList();
	}

	/* (non-Javadoc)
	 * @see sessionBeans.GestioneProposteInterface#getProposteAbilitaConfermate()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PropostaAbilita> getProposteAbilitaConfermate() {
		Query query = entityManager.createNamedQuery("PropostaAbilita.getTutteProposteAbilitaConfermate");
		return (List<PropostaAbilita>) query.getResultList();
	}

	/* (non-Javadoc)
	 * @see sessionBeans.GestioneProposteInterface#getPropostaAbilitaById(java.lang.Long)
	 */
	@Override
	public PropostaAbilita getPropostaAbilitaById(Long id) throws ProposteException {
		if(id==null) {
			throw new ProposteException(ProposteException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		} else {
			return entityManager.find(PropostaAbilita.class, id);
		}
	}

	/* (non-Javadoc)
	 * @see sessionBeans.GestioneProposteInterface#approvaPropostaAbilita(java.lang.Long, java.lang.String)
	 */
	@Override
	public PropostaAbilita approvaPropostaAbilita(Long id, String descrizione) throws ProposteException {
		GregorianCalendar calendar = new GregorianCalendar();
		
		if(id==null) {
			throw new ProposteException(ProposteException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		}
		
		PropostaAbilita propostaAbilita = this.getPropostaAbilitaById(id);
		if(propostaAbilita!=null) {
			propostaAbilita.setDataAccettazione(calendar.getTime());
			entityManager.persist(propostaAbilita);
			entityManager.flush();

			Abilita abilita = new Abilita();
			abilita.setNome(propostaAbilita.getAbilitaProposta());
			abilita.setDescrizione(descrizione);
			entityManager.persist(abilita);
			entityManager.flush();
		}
		return propostaAbilita;
	}

	/* (non-Javadoc)
	 * @see sessionBeans.GestioneProposteInterface#rifiutaPropostaAbilita(java.lang.Long)
	 */
	@Override
	public PropostaAbilita rifiutaPropostaAbilita(Long id) throws ProposteException {
		if(id==null) {
			throw new ProposteException(ProposteException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		}
		
		PropostaAbilita propostaAbilita = entityManager.find(PropostaAbilita.class, id);
		if(propostaAbilita!=null) {
			entityManager.remove(propostaAbilita);
		}
		return propostaAbilita;
	}

	
	/* (non-Javadoc)
	 * @see sessionBeans.GestioneProposteInterface#inserisciPropostaUtente(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public PropostaAbilita inserisciPropostaUtente(String emailUtenteChePropone, String nomeAbilitaProposta, String motivazione) throws ProposteException {
		if(emailUtenteChePropone==null || emailUtenteChePropone.equals("") || nomeAbilitaProposta==null || nomeAbilitaProposta.equals("")) {
			throw new ProposteException(ProposteException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		}
		
		Utente utenteCheProponeAbilita = this.getUtenteByEmail(emailUtenteChePropone);

		if (utenteCheProponeAbilita == null) {
			return null;
		}

		PropostaAbilita propostaAbilita = new PropostaAbilita();
		propostaAbilita.setUtente(utenteCheProponeAbilita);
		propostaAbilita.setAbilitaProposta(nomeAbilitaProposta);
		propostaAbilita.setMotivazione(motivazione);

		entityManager.persist(propostaAbilita);
		entityManager.flush();

		return propostaAbilita;
	}

	
	/* (non-Javadoc)
	 * @see sessionBeans.GestioneProposteInterface#inserisciAbilitaAutonomamente(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Abilita inserisciAbilitaAutonomamente(String emailAmministratore, String nomeNuovaAbilita, String descrizione) throws ProposteException {
		if(emailAmministratore==null || emailAmministratore.equals("") || nomeNuovaAbilita==null || nomeNuovaAbilita.equals("")) {
			throw new ProposteException(ProposteException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		}
		
		Amministratore amministratore = this.getAmministratoreByEmail(emailAmministratore);
		if (amministratore != null) {
			Abilita abilita = new Abilita();
			abilita.setNome(nomeNuovaAbilita);
			abilita.setDescrizione(descrizione);

			entityManager.persist(abilita);
			entityManager.flush();
			return abilita;
		} else {
			return null;
		}
	}



	/* (non-Javadoc)
	 * @see sessionBeans.GestioneProposteInterface#confermaPropostaAbilitaSpecificandoAttributi(java.lang.String, java.lang.Long, java.lang.String, java.lang.String)
	 */
	@Override
	public Abilita confermaPropostaAbilitaSpecificandoAttributi(String emailAmministratore, Long idPropostaAbilita, String nomeNuovaAbilita, String descrizione) throws ProposteException {
		GregorianCalendar calendar = new GregorianCalendar();

		if(emailAmministratore==null || emailAmministratore.equals("") || idPropostaAbilita == null || nomeNuovaAbilita==null || nomeNuovaAbilita.equals("")) {
			throw new ProposteException(ProposteException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		}
		
		Amministratore amministratore = this.getAmministratoreByEmail(emailAmministratore);
		PropostaAbilita propostaAbilita = this.getPropostaAbilitaById(idPropostaAbilita);

		if (amministratore!=null && propostaAbilita!=null) {

			//ora confermo la proposta precedente
			propostaAbilita.setDataAccettazione(calendar.getTime());
			entityManager.persist(propostaAbilita);
			entityManager.flush();

			Abilita abilita = new Abilita();
			abilita.setNome(nomeNuovaAbilita);
			abilita.setDescrizione(descrizione);

			entityManager.persist(abilita);
			entityManager.flush();
			return abilita;
		} else {
			return null;
		}
	}



	/* (non-Javadoc)
	 * @see sessionBeans.GestioneProposteInterface#getUtenteByEmail(java.lang.String)
	 */
	@Override
	public Utente getUtenteByEmail(String email) throws ProposteException {
		if(email==null || email.equals("")) {
			throw new ProposteException(ProposteException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		} else {
			return entityManager.find(Utente.class, email);
		}
	}
	

	/* (non-Javadoc)
	 * @see sessionBeans.GestioneProposteInterface#getAmministratoreByEmail(java.lang.String)
	 */
	@Override
	public Amministratore getAmministratoreByEmail(String email) throws ProposteException {
		if(email==null || email.equals("")) {
			throw new ProposteException(ProposteException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		} else {
			return entityManager.find(Amministratore.class, email);
		}
	}

}
