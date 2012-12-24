package sessionBeans;

import java.util.List;

import javax.ejb.Local;

import entityBeans.Collaborazione;
import entityBeans.Utente;
import exceptions.UtenteNonTrovatoException;

@Local
public interface GestioneCollaborazioniLocal {

	public void richiediAiuto(String emailRichiedente, String emailRicevente,
			String nome, String descrizione) throws UtenteNonTrovatoException;

	public boolean accettaCollaborazione(String id, String emailRichiedente,
			String emailRicevente);

	public boolean rilasciaFeedback(String id, String emailRichiedente,
			String emailRicevente, String punteggioFB, String commentoFB);

	public boolean rifiutaCollaborazione(String id, String emailRichiedente,
			String emailRicevente);

	public void getPunteggioFeedback();

	@SuppressWarnings("unchecked")
	public List<Collaborazione> getCollaborazioniFeedbackNonRilasciato(
			String emailRichiedente);

	@SuppressWarnings("unchecked")
	public List<Collaborazione> getCollaborazioniCreate(String emailRichiedente);

	@SuppressWarnings("unchecked")
	public List<Collaborazione> getCollaborazioniAccettate(String emailRicevente);

	/**
	 * Metodo per l'estrazione dell'utente dal database data la sua email
	 * @param email e' l'email dell'amministratore
	 * @return <b>l'amministratore</b> corrispondente all'email, se esiste, <b>null</b> altrimenti
	 */
	@SuppressWarnings("unchecked")
	public Utente getUtenteByEmail(String email);

}