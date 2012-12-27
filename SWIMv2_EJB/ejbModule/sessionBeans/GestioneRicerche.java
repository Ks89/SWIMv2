package sessionBeans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Criteria;

import entityBeans.Abilita;
import entityBeans.Collaborazione;
import entityBeans.Utente;

import sessionBeans.localInterfaces.GestioneRicercheLocal;
import utililies.sessionRemote.GestioneRicercheRemote;

@Stateless
public class GestioneRicerche implements GestioneRicercheLocal, GestioneRicercheRemote{

	@PersistenceContext(unitName = "SWIMdb")
	private EntityManager entityManager;
	
	/* (non-Javadoc)
	 * @see sessionBeans.GestioneRicercheInterface#ricercaAiuto(java.util.List)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Utente> ricercaAiuto(List<Abilita> abilita){
		Query query = entityManager.createNamedQuery("PossiedePK.getUtenteByAbilita");
		query.setParameter("insiemeAbilita", abilita);
		query.setParameter("numAbilita", abilita.size());
		List<Utente> risultatoRicerca = (List<Utente>)query.getResultList();
		return risultatoRicerca;
	}
	
	/* (non-Javadoc)
	 * @see sessionBeans.GestioneRicercheInterface#ricercaUtenti(java.lang.String, java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Utente> ricercaUtenti(String nome, String cognome){
		Query query = entityManager.createNamedQuery("Utente.getUtentiByNomeCognome");
		query.setParameter("nomeUtente", nome);
		query.setParameter("cognomeUtente", cognome);
		List<Utente> risultatoRicerca = (List<Utente>)query.getResultList();
		return risultatoRicerca;
	}
}
