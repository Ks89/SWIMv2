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

@Stateless
public class GestioneProposte implements GestioneProposteLocal, GestioneProposteRemote {

	@PersistenceContext(unitName = "SWIMdb")
	private EntityManager entityManager;

	
	public Abilita getAbilitaByNome(String nome) {
		return entityManager.find(Abilita.class, nome);
	}
	
	/**
	 * Metodo con cui l'Amministratore vede tutte le proposte abilita' ANCORA
	 * NON GESTITE, indipendentemente dall'utente che l'ha fatta
	 * 
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PropostaAbilita> getProposteAbilitaNonConfermate() {
		Query query = entityManager.createNamedQuery("PropostaAbilita.getTutteProposteAbilitaNonConfermate");
		return (List<PropostaAbilita>) query.getResultList();
	}

	/**
	 * Metodo con cui l'Amministratore vede tutte le proposte abilita' GIA'
	 * APPROVATE, indipendentemente dall'utente che l'ha fatta
	 * 
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PropostaAbilita> getProposteAbilitaConfermate() {
		Query query = entityManager.createNamedQuery("PropostaAbilita.getTutteProposteAbilitaConfermate");
		return (List<PropostaAbilita>) query.getResultList();
	}

	@Override
	public PropostaAbilita getPropostaAbilitaById(Long id) {
		return entityManager.find(PropostaAbilita.class, id);
	}

	@Override
	public void approvaPropostaAbilita(Long id, String descrizione) {
		GregorianCalendar calendar = new GregorianCalendar();

		PropostaAbilita propostaAbilita = this.getPropostaAbilitaById(id);
		propostaAbilita.setDataAccettazione(calendar.getTime());
		entityManager.persist(propostaAbilita);
		entityManager.flush();

		Abilita abilita = new Abilita();
		abilita.setNome(propostaAbilita.getAbilitaProposta());
		abilita.setDescrizione(descrizione);
		entityManager.persist(abilita);
		entityManager.flush();
	}

	@Override
	public void rifiutaPropostaAbilita(Long id) {
		PropostaAbilita propostaAbilita = entityManager.find(PropostaAbilita.class, id);
		entityManager.remove(propostaAbilita);
	}

	/**
	 * Metodo per proporre un'abilita'
	 * 
	 * @param emailUtenteChePropone
	 * @param nomeAbilitaProposta
	 * @param motivazione
	 * @return
	 */
	@Override
	public PropostaAbilita inserisciPropostaUtente(String emailUtenteChePropone, String nomeAbilitaProposta, String motivazione) {
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
	 * Metodo con cui l'amministratore inserisce una proposta autonomamente e la
	 * approva automaticamente. Al posto di emailutente vi sara' l'email
	 * dell'amministratore
	 * 
	 * @param emailUtenteChePropone
	 * @param nomeAbilitaProposta
	 * @param motivazione
	 */
	@Override
	public Abilita inserisciAbilitaAutonomamente(String nomeNuovaAbilita, String descrizione) {
		Amministratore amministratore = this.getAmministratoreUnico();
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

	
	
	//TODO METODO ANCORA DA TESTARE
	@Override
	public Abilita confermaPropostaAbilitaSpecificandoAttributi(Long idPropostaAbilita, String nomeNuovaAbilita, String descrizione) {
		GregorianCalendar calendar = new GregorianCalendar();

		Amministratore amministratore = this.getAmministratoreUnico();
		if (amministratore != null) {

			//ora confermo la proposta precedente
			PropostaAbilita propostaAbilita = this.getPropostaAbilitaById(idPropostaAbilita);
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
	 * 
	 * @param email
	 *            = l'email dell'amministratore
	 * @return <b>utente</b> corrispondente all'email, se esiste, <b>null</b>
	 *         altrimenti
	 */
	@Override
	public Utente getUtenteByEmail(String email) {
		return entityManager.find(Utente.class, email);
	}

	@Override
	public Amministratore getAmministratoreUnico() {
		return entityManager.find(Amministratore.class, "admin@swim.it");
	}

}
