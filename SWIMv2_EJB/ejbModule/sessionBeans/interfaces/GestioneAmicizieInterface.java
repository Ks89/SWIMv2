package sessionBeans.interfaces;

import java.util.List;

import entityBeans.Amicizia;
import entityBeans.Utente;
import exceptions.AmiciziaException;

public interface GestioneAmicizieInterface {

	public Amicizia richiediAmicizia(String emailUtente1,
			String emailUtente2, boolean diretta);

	public Amicizia accettaAmicizia(String emailUtente1, String emailUtente2) throws AmiciziaException;

	public boolean rifiutaAmicizia(String emailUtente1,
			String emailUtente2);

	public List<Utente> getAmici(String emailUtente);

	public List<Utente> getSuggerimenti(String emailUtenteAppenaAmico,
			String emailUtente);

	/**
	 * Metodo per l'estrazione dell'utente dal database data la sua email
	 * @param email = l'email dell'amministratore
	 * @return <b>utente</b> corrispondente all'email, se esiste, <b>null</b> altrimenti
	 */
	public Utente getUtenteByEmail(String email);

	public boolean sonoAmici(String emailUtente1, String emailUtente2);

	public List<Utente> getUtentiCheVoglionoAmicizia(String emailUtente);

	public boolean setAmiciziaNotificata(String emailUtente1, String emailUtente2);

	public List<Utente> getUtentiCheHannoAccettatoLaRichiestaIndiretti(
			String emailUtente);

	public boolean amiciziaInoltrata(String emailUtente1, String emailUtente2);

	List<Utente> getUtentiCheHannoAccettatoLaRichiestaDiretti(String emailUtente);

}