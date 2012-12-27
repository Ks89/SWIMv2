package sessionBeans.interfaces;

import java.util.List;

import entityBeans.Amicizia;
import entityBeans.Collaborazione;
import entityBeans.Utente;

public interface GestioneAmicizieInterface {

	public abstract Amicizia richiediAmicizia(String emailUtente1,
			String emailUtente2, boolean diretta);

	public abstract boolean accettaAmicizia(String emailUtente1,
			String emailUtente2);

	public abstract boolean rifiutaAmicizia(String emailUtente1,
			String emailUtente2);

	@SuppressWarnings("unchecked")
	public abstract List<Utente> getAmici(String emailUtente);

	public abstract List<Utente> getSuggerimenti(String emailUtenteAppenaAmico,
			String emailUtente);

	/**
	 * Metodo per l'estrazione dell'utente dal database data la sua email
	 * @param email = l'email dell'amministratore
	 * @return <b>utente</b> corrispondente all'email, se esiste, <b>null</b> altrimenti
	 */
	public abstract Utente getUtenteByEmail(String email);

}