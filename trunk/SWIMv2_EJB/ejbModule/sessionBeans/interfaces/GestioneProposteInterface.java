package sessionBeans.interfaces;

import java.util.List;

import entityBeans.Abilita;
import entityBeans.Amministratore;
import entityBeans.PropostaAbilita;
import entityBeans.Utente;
import exceptions.ProposteException;

public interface GestioneProposteInterface {

	/**
	 * Metodo per ottenere l'abilita' dato il nome.
	 * @param nome = String che rappresenta il nome univoco dell'abilita'
	 * @return <b>abilita</b> con il nome specificato, se esiste, <b>null</b> altrimenti
	 * @throws ProposteException con causa ALCUNIPARAMETRINULLIOVUOTI
	 */
	public Abilita getAbilitaByNome(String nome) throws ProposteException;

	/**
	 * Metodo con cui l'Amministratore puo' ottenere tutte le proposte abilita' ANCORA
	 * NON CONFERMATE, indipendentemente dall'utente che l'ha fatta
	 * @return <b>lista delle proposte</b>, cioe' una List<PropostaAbilita> che rappresenta le proposte non confermate. 
	 * Se non e' possibile ottenere tale lista, reistituisce <b>null</b>.
	 */
	public List<PropostaAbilita> getProposteAbilitaNonConfermate();

	/**
	 * Metodo con cui l'Amministratore puo' ottenere tutte le proposte abilita' GIA'
	 * CONFERMATE, indipendentemente dall'utente che l'ha fatta.
	 * @return <b>lista delle proposte</b>, cioe' una List<PropostaAbilita> che rappresenta le proposte gia' confermate. 
	 * Se non e' possibile ottenere tale lista, reistituisce <b>null</b>.
	 */
	public List<PropostaAbilita> getProposteAbilitaConfermate();

	/**
	 * Metodo per ottenere una proposta di abilita con un preciso id.
	 * @param id = Long che rappresenta l'id univoco della proposta
	 * @return <b>proposta</b> con l'id specificato, se esiste, <b>null</b> altrimenti
	 * @throws ProposteException con causa ALCUNIPARAMETRINULLIOVUOTI
	 */
	public PropostaAbilita getPropostaAbilitaById(Long id) throws ProposteException;

	/**
	 * Metodo per approvare una proposta di abilita'. Tale metodo permette solamente di specificare la descrizione della nuova abilita', ma
	 * non il nome. Infatti, il nome dell'abilita' corrispondera' automaticamente all'attributo "abilitaProposta" di PropostaAbilita.
	 * @param id = Long che rappresenta l'id univoco della proposta
	 * @param descrizione = String che rappresenta la descrizione della nuova abilita'. Puo' essere null.
	 * @return <b>proposta</b> con l'id specificato, se approvata correttamente, <b>null</b> altrimenti
	 * @throws ProposteException con causa ALCUNIPARAMETRINULLIOVUOTI o ABILITAGIAPRESENTE
	 */
	public PropostaAbilita approvaPropostaAbilita(Long id, String descrizione) throws ProposteException;

	/**
	 * Metodo per rifiutare una proposta di abilita'. Tale metodo permette di rimuovere una proposta, senza aggiungere l'abilita'.
	 * @param id = Long che rappresenta l'id univoco della proposta
	 * @return <b>proposta</b> con l'id specificato, se rifiutata correttamente, <b>null</b> altrimenti
	 * @throws ProposteException con causa ALCUNIPARAMETRINULLIOVUOTI
	 */
	public PropostaAbilita rifiutaPropostaAbilita(Long id) throws ProposteException;

	/**
	 * Metodo per inserire una nuova proposta. Tale metodo puo' essere utilizzato dall'utente, ma non dall'amministratore.
	 * Infatti, quest'ultimo puo' utilizzare inserisciAbilitaAutonomamente().
	 * @param emailUtenteChePropone = String che rappresenta l'email dell'utente che propone
	 * @param nomeAbilitaProposta = String che rappresenta il nome dell'abilita proposta
	 * @param motivazione = String che rappresenta il motivo per cui l'utente propone l'aggiunta della nuova abilita'
	 * con nome nomeAbilitaProposta. Puo' essere null.
	 * @return <b>proposta</b> inserita, <b>null</b> altrimenti
	 * @throws ProposteException con causa ALCUNIPARAMETRINULLIOVUOTI
	 */
	public PropostaAbilita inserisciPropostaUtente(String emailUtenteChePropone, String nomeAbilitaProposta, String motivazione) throws ProposteException;

	/**
	 * Metodo con cui l'amministratore inserisce direttamente un'abilita', senza inseire precedentemente una proposta abilita'.
	 * @param emailAmministratore = String che rappresenta l'email dell'amministratore che inserisce l'abilita' autonomamente
	 * @param nomeNuovaAbilita = String che rappresenta il nome dell'abilita scelto dall'amministratore
	 * @param descrizione = String che rappresenta la descrizione dell'abilita' da inserire. Puo' essere null.
	 * @return <b>abilita</b> inserita autonomamente dall'amministratore, <b>null</b> altrimenti
	 * @throws ProposteException con causa ALCUNIPARAMETRINULLIOVUOTI o ABILITAGIAPRESENTE
	 */
	public Abilita inserisciAbilitaAutonomamente(String emailAmministratore, String nomeNuovaAbilita, String descrizione) throws ProposteException;

	/**
	 * Metodo con cui l'amministratore identificato dalla sua email puo' confermare una proposta abilita' di un utente, 
	 * specificando pero' un nuovo nome abilita' ed eventualmente la descrizione. 
	 * @param emailAmministratore = String che rappresenta l'email dell'amministratore che approva la proposta di abilita'
	 * @param idPropostaAbilita = Long che rappresenta l'id della proposta abilita'
	 * @param nomeNuovaAbilita = String che rappresenta il nuovo nome dell'abilita', senza considerare quello proposto dall'utente
	 * @param descrizione = String che rappresenta la descrizione dell'abilita' da inserire. Puo' essere null.
	 * @return <b>abilita</b> inserita dall'amministratore dopo aver confermato la proposta di abilita', ma inserendo dati differenti 
	 * da quelli proposti dall'utente, <b>null</b> altrimenti
	 * @throws ProposteException con causa ALCUNIPARAMETRINULLIOVUOTI o ABILITAGIAPRESENTE
	 */
	public Abilita confermaPropostaAbilitaSpecificandoAttributi(String emailAmministratore, Long idPropostaAbilita, String nomeNuovaAbilita, String descrizione) throws ProposteException;

	/**
	 * Metodo per l'estrazione dell'utente dal database data la sua email
	 * @param email = String che rappresente l'email dell'utente
	 * @return <b>utente</b> corrispondente all'email, se esiste, <b>null</b> altrimenti
	 * @throws ProposteException  con causa  ALCUNIPARAMETRINULLIOVUOTI
	 */
	public Utente getUtenteByEmail(String email) throws ProposteException;

	/**
	 * Metodo per l'estrazione dell'amministratore dal database data la sua email
	 * @param email e' l'email dell'amministratore
	 * @return <b>l'amministratore</b> corrispondente all'email, se esiste, <b>null</b> altrimenti
	 * @throws ProposteException con causa ALCUNIPARAMETRINULLIOVUOTI
	 */
	public Amministratore getAmministratoreByEmail(String email) throws ProposteException;

}