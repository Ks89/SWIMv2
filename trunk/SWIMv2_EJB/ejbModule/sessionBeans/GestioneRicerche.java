package sessionBeans;

import java.util.List;

import javassist.expr.Cast;

import javax.ejb.Stateless;
import javax.faces.convert.IntegerConverter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.security.xacml.sunxacml.attr.IntegerAttribute;

import entityBeans.Abilita;
import entityBeans.Utente;
import exceptions.RicercheException;

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
	public List<Utente> ricercaAiuto(List<Abilita> abilita, String email)throws RicercheException{
		if(abilita == null || abilita.size()<1)
			throw new RicercheException(RicercheException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		Query query = entityManager.createNamedQuery("Possiede.getUtenteByAbilita");
		query.setParameter("insiemeAbilita", abilita);
		query.setParameter("numAbilita", Long.valueOf(abilita.size()));
		query.setParameter("emailUtente", email);
		List<Utente> risultatoRicerca = (List<Utente>)query.getResultList();
		return risultatoRicerca;
	}
	
	/* (non-Javadoc)
	 * @see sessionBeans.GestioneRicercheInterface#ricercaAiuto(java.util.List)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Utente> ricercaAiutoVisitatore(List<Abilita> abilita)throws RicercheException{
		if(abilita == null || abilita.size()<1)
			throw new RicercheException(RicercheException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		Query query = entityManager.createNamedQuery("Possiede.getUtenteByAbilitaVisitatore");
		query.setParameter("insiemeAbilita", abilita);
		query.setParameter("numAbilita", Long.valueOf(abilita.size()));
		List<Utente> risultatoRicerca = (List<Utente>)query.getResultList();
		return risultatoRicerca;
	}
	
	/* (non-Javadoc)
	 * @see sessionBeans.GestioneRicercheInterface#ricercaUtenti(java.lang.String, java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Utente> ricercaUtenti(String nome, String cognome, String email)throws RicercheException{
		if(nome == null || cognome==null || nome.length()==0 || cognome.length()==0)
			throw new RicercheException(RicercheException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		Query query = entityManager.createNamedQuery("Utente.getUtentiByNomeCognome");
		query.setParameter("nomeUtente", nome);
		query.setParameter("cognomeUtente", cognome);
		query.setParameter("emailUtente", email);
		List<Utente> risultatoRicerca = (List<Utente>)query.getResultList();
		return risultatoRicerca;
	}
	
	@SuppressWarnings("unchecked")
	public List<Abilita> insiemeAbilitaGenerali(){
		Query query = entityManager.createNamedQuery("Abilita.getInsiemeAbilitaGenerali");
		List<Abilita> insiemeAbilita = (List<Abilita>)query.getResultList();
		return insiemeAbilita;
	}
	
	@SuppressWarnings("unchecked")
	public List<Abilita> insiemeAbilitaPersonaliUtente(String emailUtente)throws RicercheException{
		if(emailUtente==null || emailUtente.length()==0)
			throw new RicercheException(RicercheException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		Query query = entityManager.createNamedQuery("Possiede.getAbilitaByUtente");
		query.setParameter("emailUtente", emailUtente);
		List<Abilita> insiemeAbilitaUtente = (List<Abilita>)query.getResultList();
		return insiemeAbilitaUtente;

	}
	
	
	public Utente getUtenteByEmail(String email){
		return entityManager.find(Utente.class, email);
	}
}
