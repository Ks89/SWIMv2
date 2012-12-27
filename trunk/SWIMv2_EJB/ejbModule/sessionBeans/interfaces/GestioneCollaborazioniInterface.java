package sessionBeans.interfaces;

import java.util.List;

import entityBeans.Collaborazione;
import entityBeans.Utente;
import exceptions.CollaborazioneException;

public interface GestioneCollaborazioniInterface {

	public Collaborazione getCollaborazione(Long id);

	public Collaborazione richiediAiuto(String emailRichiedente, String emailRicevente, String nome, String descrizione) throws CollaborazioneException;

	public void accettaCollaborazione(Long id);
	
	public void rifiutaCollaborazione(Long id);
	
	public void terminaCollaborazione(Long id);

	public void rilasciaFeedback(Long id, Integer punteggioFB, String commentoFB);

	public Double getPunteggioFeedback(String emailRicevente);

	public List<Collaborazione> getCollaborazioniCreateFeedbackNonRilasciato(
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