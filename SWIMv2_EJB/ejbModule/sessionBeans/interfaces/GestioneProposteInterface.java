package sessionBeans.interfaces;

import java.util.List;

import entityBeans.Amministratore;
import entityBeans.PropostaAbilita;
import entityBeans.Utente;

public interface GestioneProposteInterface {

	/**
	 * Metodo con cui l'Amministratore vede tutte le proposte abilita' ANCORA NON GESTITE, indipendentemente dall'utente che l'ha fatta
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PropostaAbilita> getProposteAbilitaNonConfermate();

	/**
	 * Metodo con cui l'Amministratore vede tutte le proposte abilita' GIA' APPROVATE, indipendentemente dall'utente che l'ha fatta
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PropostaAbilita> getProposteAbilitaConfermate();

	public void approvaPropostaAbilita(Long id, String descrizione);

	public void rifiutaPropostaAbilita(Long id);

	/**
	 * Metodo per proporre un'abilita'
	 * @param emailUtenteChePropone
	 * @param nomeAbilitaProposta
	 * @param motivazione
	 * @return
	 */
	public PropostaAbilita inserisciPropostaUtente(
			String emailUtenteChePropone, String nomeAbilitaProposta,
			String motivazione);

	/**
	 * Metodo con cui l'amministratore inserisce una proposta autonomamente e la approva automaticamente.
	 * Al posto di emailutente vi sara' l'email dell'amministratore
	 * @param emailUtenteChePropone
	 * @param nomeAbilitaProposta
	 * @param motivazione
	 */
	public void inserisciPropostaAutonomamente(String emailAmministratore,
			String nomeAbilitaProposta, String motivazione, String descrizione);

	/**
	 * Metodo per l'estrazione dell'utente dal database data la sua email
	 * @param email = l'email dell'amministratore
	 * @return <b>utente</b> corrispondente all'email, se esiste, <b>null</b> altrimenti
	 */
	public Utente getUtenteByEmail(String email);

	public Amministratore getAmministratoreByEmail(String email);

}