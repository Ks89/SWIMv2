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


	/**
	 * Metodo per ottenere l'abilita' dato il nome.
	 * @param nome = String che rappresenta il nome univoco dell'abilita'
	 * @return <b>abilita</b> con il nome specificato, se esiste, <b>null</b> altrimenti
	 * @throws ProposteException con causa ALCUNIPARAMETRINULLIOVUOTI
	 */
	@Override
	public Abilita getAbilitaByNome(String nome) throws ProposteException {
		if(nome==null || nome.equals("")) {
			throw new ProposteException(ProposteException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		} else {
			return entityManager.find(Abilita.class, nome);
		}
	}

	/**
	 * Metodo con cui l'Amministratore puo' ottenere tutte le proposte abilita' ANCORA
	 * NON CONFERMATE, indipendentemente dall'utente che l'ha fatta
	 * @return <b>lista delle proposte</b>, cioe' una List<PropostaAbilita> che rappresenta le proposte non confermate. 
	 * Se non e' possibile ottenere tale lista, reistituisce <b>null</b>.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PropostaAbilita> getProposteAbilitaNonConfermate() {
		Query query = entityManager.createNamedQuery("PropostaAbilita.getTutteProposteAbilitaNonConfermate");
		return (List<PropostaAbilita>) query.getResultList();
	}

	/**
	 * Metodo con cui l'Amministratore puo' ottenere tutte le proposte abilita' GIA'
	 * CONFERMATE, indipendentemente dall'utente che l'ha fatta.
	 * @return <b>lista delle proposte</b>, cioe' una List<PropostaAbilita> che rappresenta le proposte gia' confermate. 
	 * Se non e' possibile ottenere tale lista, reistituisce <b>null</b>.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PropostaAbilita> getProposteAbilitaConfermate() {
		Query query = entityManager.createNamedQuery("PropostaAbilita.getTutteProposteAbilitaConfermate");
		return (List<PropostaAbilita>) query.getResultList();
	}

	/**
	 * Metodo per ottenere una proposta di abilita con un preciso id.
	 * @param id = Long che rappresenta l'id univoco della proposta
	 * @return <b>proposta</b> con l'id specificato, se esiste, <b>null</b> altrimenti
	 * @throws ProposteException con causa ALCUNIPARAMETRINULLIOVUOTI
	 */
	@Override
	public PropostaAbilita getPropostaAbilitaById(Long id) throws ProposteException {
		if(id==null) {
			throw new ProposteException(ProposteException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		} else {
			return entityManager.find(PropostaAbilita.class, id);
		}
	}

	/**
	 * Metodo per approvare una proposta di abilita'. Tale metodo permette solamente di specificare la descrizione della nuova abilita', ma
	 * non il nome. Infatti, il nome dell'abilita' corrispondera' automaticamente all'attributo "abilitaProposta" di PropostaAbilita.
	 * @param id = Long che rappresenta l'id univoco della proposta
	 * @param descrizione = String che rappresenta la descrizione della nuova abilita'. Puo' essere null.
	 * @return <b>proposta</b> con l'id specificato, se approvata correttamente, <b>null</b> altrimenti
	 * @throws ProposteException con causa ALCUNIPARAMETRINULLIOVUOTI o ABILITAGIAPRESENTE
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

			//se l'abilita che si vuole inserire e' gia' presente lancia l'eccezione
			Abilita abilitaGiaPresente = this.getAbilitaByNome(propostaAbilita.getAbilitaProposta());
			if(abilitaGiaPresente != null) {
				throw new ProposteException(ProposteException.Causa.ABILITAGIAPRESENTE);
			}
			
			Abilita abilita = new Abilita();
			abilita.setNome(propostaAbilita.getAbilitaProposta());
			abilita.setDescrizione(descrizione);
			entityManager.persist(abilita);
			entityManager.flush();
		}
		return propostaAbilita;
	}

	/**
	 * Metodo per rifiutare una proposta di abilita'. Tale metodo permette di rimuovere una proposta, senza aggiungere l'abilita'.
	 * @param id = Long che rappresenta l'id univoco della proposta
	 * @return <b>proposta</b> con l'id specificato, se rifiutata correttamente, <b>null</b> altrimenti
	 * @throws ProposteException con causa ALCUNIPARAMETRINULLIOVUOTI
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


	/**
	 * Metodo per inserire una nuova proposta. Tale metodo puo' essere utilizzato dall'utente, ma non dall'amministratore.
	 * Infatti, quest'ultimo puo' utilizzare inserisciAbilitaAutonomamente().
	 * @param emailUtenteChePropone = String che rappresenta l'email dell'utente che propone
	 * @param nomeAbilitaProposta = String che rappresenta il nome dell'abilita proposta
	 * @param motivazione = String che rappresenta il motivo per cui l'utente propone l'aggiunta della nuova abilita'
	 * con nome nomeAbilitaProposta. Puo' essere null.
	 * @return <b>proposta</b> inserita, <b>null</b> altrimenti
	 * @throws ProposteException con causa ALCUNIPARAMETRINULLIOVUOTI
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

	/**
	 * Metodo con cui l'amministratore inserisce direttamente un'abilita', senza inseire precedentemente una proposta abilita'.
	 * @param emailAmministratore = String che rappresenta l'email dell'amministratore che inserisce l'abilita' autonomamente
	 * @param nomeNuovaAbilita = String che rappresenta il nome dell'abilita scelto dall'amministratore
	 * @param descrizione = String che rappresenta la descrizione dell'abilita' da inserire. Puo' essere null.
	 * @return <b>abilita</b> inserita autonomamente dall'amministratore, <b>null</b> altrimenti
	 * @throws ProposteException con causa ALCUNIPARAMETRINULLIOVUOTI o ABILITAGIAPRESENTE
	 */
	@Override
	public Abilita inserisciAbilitaAutonomamente(String emailAmministratore, String nomeNuovaAbilita, String descrizione) throws ProposteException {
		if(emailAmministratore==null || emailAmministratore.equals("") || nomeNuovaAbilita==null || nomeNuovaAbilita.equals("")) {
			throw new ProposteException(ProposteException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		}

		Amministratore amministratore = this.getAmministratoreByEmail(emailAmministratore);
		
		//se l'abilita che si vuole inserire e' gia' presente lancia l'eccezione
		Abilita abilitaGiaPresente = this.getAbilitaByNome(nomeNuovaAbilita);
		if(abilitaGiaPresente != null) {
			throw new ProposteException(ProposteException.Causa.ABILITAGIAPRESENTE);
		}

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



	/**
	 * Metodo con cui l'amministratore identificato dalla sua email puo' confermare una proposta abilita' di un utente, 
	 * specificando pero' un nuovo nome abilita' ed eventualmente la descrizione. 
	 * @param emailAmministratore = String che rappresenta l'email dell'amministratore che approva la proposta di abilita'
	 * @param idPropostaAbilita = Long che rappresenta l'id della proposta abilita'
	 * @param nomeNuovaAbilita = String che rappresenta il nuovo nome dell'abilita', senza considerare quello proposto dall'utente
	 * @param descrizione = String che rappresenta la descrizione dell'abilita' da inserire. Puo' essere null.
	 * @return <b>abilita</b> inserita dall'amministratore dopo aver confermato la proposta di abilita', ma inserendo dati differenti 
	 * da quelli proposti dall'utente, <b>null</b> altrimenti
	 * @throws ProposteException con causa ALCUNIPARAMETRINULLIOVUOTI o ABILITAGIAPRESENTE
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

			//se l'abilita che si vuole inserire e' gia' presente lancia l'eccezione
			Abilita abilitaGiaPresente = this.getAbilitaByNome(nomeNuovaAbilita);
			if(abilitaGiaPresente != null) {
				throw new ProposteException(ProposteException.Causa.ABILITAGIAPRESENTE);
			}
			
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



	/**
	 * Metodo per l'estrazione dell'utente dal database data la sua email
	 * @param email = String che rappresente l'email dell'utente
	 * @return <b>utente</b> corrispondente all'email, se esiste, <b>null</b> altrimenti
	 * @throws ProposteException  con causa  ALCUNIPARAMETRINULLIOVUOTI
	 */
	@Override
	public Utente getUtenteByEmail(String email) throws ProposteException {
		if(email==null || email.equals("")) {
			throw new ProposteException(ProposteException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		} else {
			return entityManager.find(Utente.class, email);
		}
	}


	/**
	 * Metodo per l'estrazione dell'amministratore dal database data la sua email
	 * @param email e' l'email dell'amministratore
	 * @return <b>l'amministratore</b> corrispondente all'email, se esiste, <b>null</b> altrimenti
	 * @throws ProposteException con causa ALCUNIPARAMETRINULLIOVUOTI
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
