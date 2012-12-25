package sessionBeans;

import java.util.List;

import javax.ejb.Local;

import entityBeans.Collaborazione;
import entityBeans.Utente;

@Local
public interface GestioneCollaborazioniLocal {

	public Collaborazione getCollaborazione(int id, String emailRichiedente, String emailRicevente);

	public Collaborazione richiediAiuto(String emailRichiedente, String emailRicevente,
			String nome, String descrizione);

	public void accettaCollaborazione(int id, String emailRichiedente,
			String emailRicevente);

	public boolean rilasciaFeedback(int id, String emailRichiedente,
			String emailRicevente, String punteggioFB, String commentoFB);

	public boolean rifiutaCollaborazione(int id, String emailRichiedente,
			String emailRicevente);

	public void getPunteggioFeedback();

	public List<Collaborazione> getCollaborazioniFeedbackNonRilasciato(
			String emailRichiedente);

	public List<Collaborazione> getCollaborazioniCreate(String emailRichiedente);

	public List<Collaborazione> getCollaborazioniAccettate(String emailRicevente);

	/**
	 * Metodo per l'estrazione dell'utente dal database data la sua email
	 * @param email e' l'email dell'amministratore
	 * @return <b>l'amministratore</b> corrispondente all'email, se esiste, <b>null</b> altrimenti
	 */
	public Utente getUtenteByEmail(String email);

}