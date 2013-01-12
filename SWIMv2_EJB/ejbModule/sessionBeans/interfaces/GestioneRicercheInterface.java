package sessionBeans.interfaces;

import java.util.List;

import entityBeans.Abilita;
import entityBeans.Utente;
import exceptions.RicercheException;

public interface GestioneRicercheInterface {
	
	/**
	 * Metodo per ottenere la lista completa delle abilita generali selezionabili dal singolo utente
	 * @return <b>insiemeAbilita</b> lista di abilita, <b>null</b> se nel sistema non sono presento abilita
	 */
	public List<Abilita> insiemeAbilitaGenerali();

	/**
	 * Metodo per ottenere la lista delle abilita personali dell'utente passato come parametro
	 * @param emailUtente= String rappresentante la mail dell'utente di cui si vogliono conoscere le abilita
	 * @return <b>insiemeAbilita</b> lista di abilita, <b>null</b> se l'utente non ha abilita
	 * @throws RicercheException con causa ALCUNIPARAMETRINULLIVUOTI
	 */
	public List<Abilita> insiemeAbilitaPersonaliUtente(String emailUtente) throws RicercheException;
	
	
	/**
	 * Metodo per ottenere l'utente conoscendo il suo indirizzo email
	 * @param emailUtente= String rappresentante la mail dell'utente
	 * @return <b>Utente</b> avente la mail uguale a quella passata come parametro, <b>null</b> se non esiste un utente con quella mail
	 */
	public Utente getUtenteByEmail(String email);
	
	/**
	 * Metodo per ottenere il risultato della ricerca aiuto per utenti registrati, in base alle abilita scelte
	 * @param abilita= lista di abilita che l'utente ricercato deve avere
	 * @param email= String rappresentante l'email dell'utente che ha richiesto la ricerca
	 * @return <b>risultatoRicerca</b> una lista di utenti che possiede tutte le abilita selezionate, <b>null</b> se la ricerca non ha prodotto risultati
	 * @throws RicercheException con causa ALCUNIPARAMETRINULLIVUOTI
	 */
	public List<Utente> ricercaAiuto(List<Abilita> abilita, String email) throws RicercheException;
	
	/**
	 * Metodo per ottenere il risultato della ricerca aiuto per visitatori, in base alle abilita scelte
	 * @param abilita= lista di abilita che l'utente ricercato deve avere
	 * @return <b>risultatoRicerca</b> una lista di utenti che possiede tutte le abilita selezionate, <b>null</b> se la ricerca non ha prodotto risultati
	 * @throws RicercheException con causa ALCUNIPARAMETRINULLIVUOTI
	 */
	public List<Utente> ricercaAiutoVisitatore(List<Abilita> abilita) throws RicercheException;
	
	/**
	 * Metodo per ottenere una lista di utenti ricercati per nome e cognome
	 * @param nome= String rappresentante il nome dell'utente ricercato
	 * @param cognome= String rappresentante il cognome dell'utente ricercato
	 * @return <b>risultatoRicerca</b> una lista di utenti aventi nome e cognome uguali a quelli passati come parametro, <b>null</b> se la ricerca non ha prodotto risultati
	 * @throws RicercheException con causa ALCUNIPARAMETRINULLIVUOTI
	 */
	public List<Utente> ricercaUtenti(String nome, String cognome, String email) throws RicercheException;
}
