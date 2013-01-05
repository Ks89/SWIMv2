package sessionBeans;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entityBeans.Amicizia;
import entityBeans.AmiciziaPK;
import entityBeans.Utente;

import sessionBeans.interfaces.GestioneAmicizieInterface;
import sessionBeans.localInterfaces.GestioneAmicizieLocal;
import utililies.sessionRemote.GestioneAmicizieRemote;

/**
 * @author Dave
 * 
 */
@Stateless
public class GestioneAmicizie implements GestioneAmicizieLocal,
		GestioneAmicizieRemote, GestioneAmicizieInterface {

	@PersistenceContext(unitName = "SWIMdb")
	private EntityManager entityManager;

	/**
	 * Metodo per creare una richiesta di Amicizia
	 * 
	 * @param emailUtente1
	 *            rappresenta la email del richiedente
	 * @param emailUtente2
	 *            rappresenta la email dell'utente cui il richiedende vuole
	 *            l'amicizia
	 * @return <b>amicizia</b> se tutto � andato a buon fine,<b>null</b>
	 *         altrimenti
	 */
	@Override
	public Amicizia richiediAmicizia(String emailUtente1, String emailUtente2,
			boolean diretta) {

		Utente utente1 = this.getUtenteByEmail(emailUtente1);
		Utente utente2 = this.getUtenteByEmail(emailUtente2);

		if (utente1 == null || utente2 == null) {
			return null;
		}

		AmiciziaPK amiciziaPK = new AmiciziaPK();
		Amicizia amicizia = new Amicizia();

		amiciziaPK.setUtente1(utente1);
		amiciziaPK.setUtente2(utente2);

		amicizia.setAmiciziaPK(amiciziaPK);
		amicizia.setDiretta(diretta);
		amicizia.setNotificaAlRichiedente(false);

		entityManager.persist(amicizia);
		entityManager.flush();

		return amicizia;
	}

	/**
	 * Metodo per confermare una richiesta di Amicizia
	 * 
	 * @param emailUtente1
	 *            rappresenta la email del richiedente
	 * @param emailUtente2
	 *            rappresenta la email dell'utente che sta accettando la
	 *            richiesta di amicizia
	 * @return <b>amicizia</b> se tutto e' andato a buon fine,<b>null</b> altrimenti
	 */
	@Override
	public Amicizia accettaAmicizia(String emailUtente1, String emailUtente2) {
		Amicizia amicizia;
		GregorianCalendar calendar = new GregorianCalendar();
		Utente utente1 = this.getUtenteByEmail(emailUtente1);
		Utente utente2 = this.getUtenteByEmail(emailUtente2);

		if (utente1 == null || utente2 == null) {
			return null;
		}

		AmiciziaPK amiciziaPK = new AmiciziaPK();

		amiciziaPK.setUtente1(utente1);
		amiciziaPK.setUtente2(utente2);
		try {
			amicizia = entityManager.find(Amicizia.class, amiciziaPK);
			amicizia.setDataAccettazione(calendar.getTime());
			entityManager.persist(amicizia); // non obbligatori, funziona senza
												// sia
												// questo che il flush SOLO per
												// l'update
		} catch (IllegalArgumentException e) {
			return null;
		}
		entityManager.flush();

		return amicizia;
	}

	/**
	 * Metodo che setta l'amiciza come notificata, ovviamente al richiedente
	 * perch� al richiesto uscir� quando accetta la richiesta
	 * 
	 * @param emailUtente1
	 *            rappresenta la email del richiedente (cio� quello che � stato
	 *            notificato per porre a true questo valore)
	 * @param emailUtente2
	 *            rappresenta la email dell'utente che sta accettando la
	 *            richiesta di amicizia
	 * @return <b>true</b> se tutto � andato a buon fine,<b>false</b> altrimenti
	 */
	@Override
	public boolean setAmiciziaNotificata(String emailUtente1,
			String emailUtente2) {

		Utente utente1 = this.getUtenteByEmail(emailUtente1);
		Utente utente2 = this.getUtenteByEmail(emailUtente2);

		if (utente1 == null || utente2 == null) {
			return false;
		}

		AmiciziaPK amiciziaPK = new AmiciziaPK();

		amiciziaPK.setUtente1(utente1);
		amiciziaPK.setUtente2(utente2);
		try {
			Amicizia amicizia = entityManager.find(Amicizia.class, amiciziaPK);
			amicizia.setNotificaAlRichiedente(true);
			entityManager.persist(amicizia); // non obbligatori, funziona senza
												// sia
												// questo che il flush SOLO per
												// l'update
		} catch (IllegalArgumentException e) {
			return false;
		}
		entityManager.flush();

		return true;
	}

	/**
	 * Metodo che ritorna la lista degli utenti che hanno appena accettato la
	 * tua amicizia, chiesta in modo indiretto
	 * 
	 * @param emailUtente
	 *            rappresenta la email dell'utente di cui si vuole sapere la
	 *            lista
	 * @return <b>utenti</b> se esistono utenti che hanno appena accettato
	 *         l'amicizia,<b>NULL</b> altrimenti
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Utente> getUtentiCheHannoAccettatoLaRichiestaIndiretti(
			String emailUtente) {
		Query query = entityManager
				.createNamedQuery("Amicizia.getUtentCheHannoAppenaAccettatoLaTuaRichiestaIndiretta");
		query.setParameter("emailUtente", emailUtente);
		List<Utente> utenti = (List<Utente>) query.getResultList();
		return utenti;
	}

	// metodo che ti ritorna gli uttenti che hanno accettato la tua amicizia
	// diretta


	/**
	 * Metodo che ritorna la lista degli utenti che hanno appena accettato la
	 * tua amicizia, chiesta in modo indiretto
	 * 
	 * @param emailUtente
	 *            rappresenta la email dell'utente di cui si vuole sapere la
	 *            lista
	 * @return <b>utenti</b> se esistono utenti che hanno appena accettato
	 *         l'amicizia,<b>NULL</b> altrimenti
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Utente> getUtentiCheHannoAccettatoLaRichiestaDiretti(
			String emailUtente) {
		Query query = entityManager
				.createNamedQuery("Amicizia.getUtentCheHannoAppenaAccettatoLaTuaRichiestaDiretta");
		query.setParameter("emailUtente", emailUtente);
		List<Utente> utenti = (List<Utente>) query.getResultList();
		return utenti;
	}
	
	
	/**
	 * Metodo per rifiutare una richiesta di Amicizia
	 * 
	 * @param emailUtente1
	 *            rappresenta la email del richiedente
	 * @param emailUtente2
	 *            rappresenta la email dell'utente che sta rifiutando la
	 *            richiesta di amicizia
	 * @return <b>true</b> se tutto � andato a buon fine,<b>false</b> altrimenti
	 */
	@Override
	public boolean rifiutaAmicizia(String emailUtente1, String emailUtente2) {

		Utente utente1 = this.getUtenteByEmail(emailUtente1);
		Utente utente2 = this.getUtenteByEmail(emailUtente2);

		if (utente1 == null || utente2 == null) {
			return false;
		}

		AmiciziaPK amiciziaPK = new AmiciziaPK();

		amiciziaPK.setUtente1(utente1);
		amiciziaPK.setUtente2(utente2);
		try {
			Amicizia amicizia = entityManager.find(Amicizia.class, amiciziaPK);
			entityManager.remove(amicizia);
		} catch (IllegalArgumentException e) {
			return false;
		}
		entityManager.flush();
		return true;
	}

	/**
	 * Metodo che ritorna la lista degli amici di un utente
	 * 
	 * @param emailUtente
	 *            rappresenta la email dell'utente di cui si vuole conoscere la
	 *            lista degli amici
	 * @return <b>amici</b> che rappresenta la lista degli amici
	 *         dell'utente,<b>null</b> se l'utente non ha amici
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Utente> getAmici(String emailUtente) {
		Query query = entityManager.createNamedQuery("Amicizia.getUtentAmici1");
		query.setParameter("emailUtente", emailUtente);
		List<Utente> amici = (List<Utente>) query.getResultList();
		query = entityManager.createNamedQuery("Amicizia.getUtentAmici2");
		query.setParameter("emailUtente", emailUtente);
		amici.addAll((List<Utente>) query.getResultList());
		return amici;
	}

	/**
	 * Metodo che ritorna la lista degli utenti che vogliono diventare amici
	 * dell'utente che ha l'emailUtente passata come parametro
	 * 
	 * @param emailUtente
	 *            rappresenta la email dell'utente di cui si vuole conoscere la
	 *            lista degli utenti che gli hanno richiesto l'amicizia
	 * @return <b>utentiChevoglionoDiventareAmici</b> che rappresenta la lista
	 *         degli amici dell'utente,<b>null</b> se l'utente non ha richieste
	 *         di amicizia
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Utente> getUtentiCheVoglionoAmicizia(String emailUtente) {
		Query query = entityManager
				.createNamedQuery("Amicizia.getUtentCheTiHannoPropostoAmicizia");
		query.setParameter("emailUtente", emailUtente);
		List<Utente> utentiCheVoglionoDiventareAmici = (List<Utente>) query.getResultList();
		return utentiCheVoglionoDiventareAmici;
	}

	/**
	 * Metodo che ritorna true se un l'utente1 e l'utente2 sono amici
	 * 
	 * @param emailUtente1
	 * @param emailUtente2
	 * @return <b>true</b> se sono amici,<b>false</b> altrimenti
	 */
	@Override
	public boolean sonoAmici(String emailUtente1, String emailUtente2) {
		Utente utente1 = this.getUtenteByEmail(emailUtente1);
		Utente utente2 = this.getUtenteByEmail(emailUtente2);
		if (this.getAmici(emailUtente1).contains(utente2)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean amiciziaInoltrata(String emailUtente1, String emailUtente2) {
		Utente utente1 = this.getUtenteByEmail(emailUtente1);
		Utente utente2 = this.getUtenteByEmail(emailUtente2);
		AmiciziaPK amiciziaPK = new AmiciziaPK();

		amiciziaPK.setUtente1(utente1);
		amiciziaPK.setUtente2(utente2);
		Amicizia amicizia = entityManager.find(Amicizia.class, amiciziaPK);
		if (amicizia != null) {
			return true;
		}
		else {
			amiciziaPK.setUtente1(utente2);
			amiciziaPK.setUtente2(utente1);
			amicizia = entityManager.find(Amicizia.class, amiciziaPK);
			if(amicizia!=null){
				return true;
			}
		}
		return false;
	}

	/**
	 * Metodo che ritorna una lista di suggerimenti di amicizia per un utente di
	 * amici appartenenti al nuovo amico
	 * 
	 * @param emailUtenteAppenaAmico
	 *            rappresenta la email dell'utente da cui prendere i
	 *            suggerimenti
	 * @param emailUtente
	 *            rappresenta la email dell'utente a cui si vogliono suggerire
	 *            amici
	 * @return <b>suggerimenti</b> che rappresenta la lista degli amici da
	 *         suggerire,<b>null</b> se l'Utente da qui si vogliono suggerimenti
	 *         ha solo l'amico dell'amicizia appena stipulata,
	 *         <b>suggerimenti</b> vuoto (attenzione non null) se l'altro utente
	 *         possiede solo amici che anche tu possiedi.
	 */
	@Override
	public List<Utente> getSuggerimenti(String emailUtenteAppenaAmico,
			String emailUtente) { // questo metodo verr� chiamato due volte, una
									// volta per ogni utente, con le mail
									// scambiate

		int numeroDiSuggerimenti = 3;
		int i = 0;
		int x;
		Random rnd = new Random();
		Utente utenteDaConsigliare = this.getUtenteByEmail(emailUtente);
		List<Utente> amiciDiAmico = this.getAmici(emailUtenteAppenaAmico);
		List<Utente> amici = this.getAmici(emailUtente);
		List<Utente> suggerimenti = new ArrayList<Utente>();

		if (amiciDiAmico.size() <= 1) {// l'amico ha solo lui come amico
			return null;
		}
		do {
			x = rnd.nextInt(amiciDiAmico.size());
			if ((amiciDiAmico.get(x).equals(utenteDaConsigliare))
					|| (amici.contains(amiciDiAmico.get(x)))) {
				amiciDiAmico.remove(x);
			} else {
				suggerimenti.add(amiciDiAmico.get(x));
				amiciDiAmico.remove(x);
				i++;
			}
		} while (i < numeroDiSuggerimenti && amiciDiAmico.size() > 0);
		return suggerimenti;
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
		Utente utente = entityManager.find(Utente.class, email);
		return utente;
	}

}
