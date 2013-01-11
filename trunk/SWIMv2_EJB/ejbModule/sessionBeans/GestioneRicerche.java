package sessionBeans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entityBeans.Abilita;
import entityBeans.Utente;
import exceptions.RicercheException;

import sessionBeans.localInterfaces.GestioneRicercheLocal;
import utililies.sessionRemote.GestioneRicercheRemote;

@Stateless
public class GestioneRicerche implements GestioneRicercheLocal, GestioneRicercheRemote{

	@PersistenceContext(unitName = "SWIMdb")
	private EntityManager entityManager;
	
	/**
	 * Metodo per ottenere il risultato della ricerca aiuto per utenti registrati, in base alle abilita scelte
	 * @param abilita= lista di abilita che l'utente ricercato deve avere
	 * @param email= String rappresentante l'email dell'utente che ha richiesto la ricerca
	 * @return <b>risultatoRicerca</b> una lista di utenti che possiede tutte le abilita selezionate, <b>null</b> se la ricerca non ha prodotto risultati
	 * @throws RicercheException con causa ALCUNIPARAMETRINULLIVUOTI
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
	
	/**
	 * Metodo per ottenere il risultato della ricerca aiuto per visitatori, in base alle abilita scelte
	 * @param abilita= lista di abilita che l'utente ricercato deve avere
	 * @return <b>risultatoRicerca</b> una lista di utenti che possiede tutte le abilita selezionate, <b>null</b> se la ricerca non ha prodotto risultati
	 * @throws RicercheException con causa ALCUNIPARAMETRINULLIVUOTI
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
	
	/**
	 * Metodo per ottenere una lista di utenti ricercati per nome e cognome
	 * @param nome= String rappresentante il nome dell'utente ricercato
	 * @param cognome= String rappresentante il cognome dell'utente ricercato
	 * @return <b>risultatoRicerca</b> una lista di utenti aventi nome e cognome uguali a quelli passati come parametro, <b>null</b> se la ricerca non ha prodotto risultati
	 * @throws RicercheException con causa ALCUNIPARAMETRINULLIVUOTI
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Utente> ricercaUtenti(String nome, String cognome, String email)throws RicercheException{
		Query query;
		if((nome == null || nome.equals("")) && (cognome==null || cognome.equals("")))
			throw new RicercheException(RicercheException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		if(nome!=null && cognome!=null && !nome.equals("") && !cognome.equals(""))
		{	
			query = entityManager.createNamedQuery("Utente.getUtentiByNomeCognome");
			query.setParameter("nomeUtente", nome);
			query.setParameter("cognomeUtente", cognome);
			query.setParameter("emailUtente", email);
		}
		else if(nome!=null && (cognome==null || cognome.equals("")))
		{	
			query = entityManager.createNamedQuery("Utente.getUtentiByNome");
			query.setParameter("nomeUtente", nome);
			query.setParameter("emailUtente", email);
		}
		else
		{
			query = entityManager.createNamedQuery("Utente.getUtentiByCognome");
			query.setParameter("cognomeUtente", cognome);
			query.setParameter("emailUtente", email);
		}
		
		List<Utente> risultatoRicerca = (List<Utente>)query.getResultList();
		return risultatoRicerca;
	}
	
	/**
	 * Metodo per ottenere la lista completa delle abilita generali selezionabili dal singolo utente
	 * @return <b>insiemeAbilita</b> lista di abilita, <b>null</b> se nel sistema non sono presento abilita
	 */
	@SuppressWarnings("unchecked")
	public List<Abilita> insiemeAbilitaGenerali(){
		Query query = entityManager.createNamedQuery("Abilita.getInsiemeAbilitaGenerali");
		List<Abilita> insiemeAbilita = (List<Abilita>)query.getResultList();
		return insiemeAbilita;
	}
	
	/**
	 * Metodo per ottenere la lista delle abilita personali dell'utente passato come parametro
	 * @param emailUtente= String rappresentante la mail dell'utente di cui si vogliono conoscere le abilita
	 * @return <b>insiemeAbilita</b> lista di abilita, <b>null</b> se l'utente non ha abilita
	 * @throws RicercheException con causa ALCUNIPARAMETRINULLIVUOTI
	 */
	@SuppressWarnings("unchecked")
	public List<Abilita> insiemeAbilitaPersonaliUtente(String emailUtente)throws RicercheException{
		if(emailUtente==null || emailUtente.length()==0)
			throw new RicercheException(RicercheException.Causa.ALCUNIPARAMETRINULLIOVUOTI);
		Query query = entityManager.createNamedQuery("Possiede.getAbilitaByUtente");
		query.setParameter("emailUtente", emailUtente);
		List<Abilita> insiemeAbilitaUtente = (List<Abilita>)query.getResultList();
		return insiemeAbilitaUtente;

	}
	
	/**
	 * Metodo per ottenere l'utente conoscendo il suo indirizzo email
	 * @param emailUtente= String rappresentante la mail dell'utente
	 * @return <b>Utente</b> avente la mail uguale a quella passata come parametro, <b>null</b> se non esiste un utente con quella mail
	 * @throws RicercheException con causa ALCUNIPARAMETRINULLIVUOTI
	 */
	public Utente getUtenteByEmail(String email){
		return entityManager.find(Utente.class, email);
	}
}
