package sessionBeans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entityBeans.Amicizia;
import entityBeans.AmiciziaPK;
import entityBeans.Collaborazione;
import entityBeans.Utente;

import sessionBeans.interfaces.GestioneAmicizieInterface;
import sessionBeans.localInterfaces.GestioneAmicizieLocal;
import utililies.sessionRemote.GestioneAmicizieRemote;

@Stateless
public class GestioneAmicizie implements GestioneAmicizieLocal, GestioneAmicizieRemote, GestioneAmicizieInterface {

	@PersistenceContext(unitName = "SWIMdb")
	private EntityManager entityManager;
	
	
	
	/* (non-Javadoc)
	 * @see sessionBeans.GestioneAmicizieInterface#getCollaborazione(java.lang.Long)
	 */
	@Override
	public Collaborazione getCollaborazione(Long id) {
		Collaborazione collaborazione = (Collaborazione)entityManager.find(Collaborazione.class, id);
		return collaborazione;
	}
	
	
	/* (non-Javadoc)
	 * @see sessionBeans.GestioneAmicizieInterface#richiediAmicizia(java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public Amicizia richiediAmicizia(String emailUtente1, String emailUtente2, boolean diretta) {
		
		Utente utente1 = this.getUtenteByEmail(emailUtente1);
		Utente utente2 = this.getUtenteByEmail(emailUtente2);

		if(utente1==null || utente2==null) {
			return null;
		}
		
		AmiciziaPK amiciziaPK=new AmiciziaPK();
		Amicizia amicizia = new Amicizia();
		
		amiciziaPK.setUtente1(utente1);
		amiciziaPK.setUtente2(utente2);
		
		amicizia.setAmiciziaPK(amiciziaPK);
		amicizia.setDiretta(diretta);
		
		
		entityManager.persist(amicizia);
		entityManager.flush();
		
		return amicizia;
	}
	
	
	/* (non-Javadoc)
	 * @see sessionBeans.GestioneAmicizieInterface#accettaAmicizia(java.lang.String, java.lang.String)
	 */
	@Override
	public void accettaAmicizia(String emailUtente1, String emailUtente2) {
		
		GregorianCalendar calendar = new GregorianCalendar();
		Utente utente1 = this.getUtenteByEmail(emailUtente1);
		Utente utente2 = this.getUtenteByEmail(emailUtente2);
		AmiciziaPK amiciziaPK=new AmiciziaPK();
		
		amiciziaPK.setUtente1(utente1);
		amiciziaPK.setUtente2(utente2);
		
		
		Amicizia amicizia = entityManager.find(Amicizia.class, amiciziaPK);
		amicizia.setDataAccettazione(calendar.getTime());
		
		entityManager.persist(amicizia); //non obbligatori, funziona senza sia questo che il flush SOLO per l'update
		entityManager.flush();
	}
	
	
	/* (non-Javadoc)
	 * @see sessionBeans.GestioneAmicizieInterface#rifiutaAmicizia(java.lang.String, java.lang.String)
	 */
	@Override
	public void rifiutaAmicizia(String emailUtente1, String emailUtente2) {
		
		Utente utente1 = this.getUtenteByEmail(emailUtente1);
		Utente utente2 = this.getUtenteByEmail(emailUtente2);
		AmiciziaPK amiciziaPK=new AmiciziaPK();
		
		amiciziaPK.setUtente1(utente1);
		amiciziaPK.setUtente2(utente2);
		
		Amicizia amicizia=entityManager.find(Amicizia.class, amiciziaPK);
		entityManager.remove(amicizia);
	}
	
	
	
	
	
	/* (non-Javadoc)
	 * @see sessionBeans.GestioneAmicizieInterface#getAmici(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Utente> getAmici(String emailUtente) {
		Query query = entityManager.createNamedQuery("Amicizia.getUtentAmici1");
		query.setParameter("emailUtente", emailUtente);
		List<Utente> amici = (List<Utente>)query.getResultList();
		query=entityManager.createNamedQuery("Amicizia.getUtentAmici2");
		query.setParameter("emailUtente", emailUtente);
		amici.addAll((List<Utente>)query.getResultList());
		return amici;
	}
	
	/* (non-Javadoc)
	 * @see sessionBeans.GestioneAmicizieInterface#getSuggerimenti(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Utente> getSuggerimenti(String emailUtenteAppenaAmico, String emailUtente){ //questo metodo verrà chiamato due volte, una volta per ogni utente, con le mail scambiate
		
		int numeroDiSuggerimenti=3;
		int i=0;
		Random rnd=new Random();
		Utente utenteDaConsigliare= this.getUtenteByEmail(emailUtente);
		List<Utente> amiciDiAmico= this.getAmici(emailUtenteAppenaAmico);
		List<Utente> amici=this.getAmici(emailUtente);
		List<Utente> suggerimenti = new ArrayList<Utente>();
			
			
		
		if(amiciDiAmico.size()<=1){//l'amico ha solo lui come amico
			return null;
		}
		do
		{
			int x= rnd.nextInt(amiciDiAmico.size());
			if ((amiciDiAmico.get(x).equals(utenteDaConsigliare))&&(amici.contains(amiciDiAmico.get(x)))){
				amiciDiAmico.remove(x);
			}
			else{
				suggerimenti.add(amiciDiAmico.get(x));
				amiciDiAmico.remove(x);
			}
		} while(i==3 ||amiciDiAmico.isEmpty());
		return suggerimenti;
	}
	
	
	/* (non-Javadoc)
	 * @see sessionBeans.GestioneAmicizieInterface#getUtenteByEmail(java.lang.String)
	 */
	@Override
	public Utente getUtenteByEmail(String email) {
		Utente utente = entityManager.find(Utente.class, email);
		return utente;
	}
	
	
}
