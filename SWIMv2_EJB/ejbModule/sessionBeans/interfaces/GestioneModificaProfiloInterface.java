package sessionBeans.interfaces;

import java.sql.Blob;
import java.util.List;

import entityBeans.Abilita;
import entityBeans.Utente;

public interface GestioneModificaProfiloInterface {

	/**
	 * Metodo per modificare la foto dell'utente specificando la sua email.
	 * @param emailUtente : String che rappresenta l'email dell'utente
	 * @param fotoProfilo : Blob che rappresenta la nuova immagine del profilo
	 * @return <b>true</b> se la modifica riesce, <b>false</b> altrimenti
	 */
	public abstract boolean modificaFoto(String emailUtente, Blob fotoProfilo);

	/**
	 * Metodo per modificare la foto dell'utente specificando la sua email.
	 * @param emailUtente : String che rappresenta l'email dell'utente
	 * @param abilita : List<Abilita> che rappresenta le abilita' da aggiungere all'insieme personale dell'utente
	 * @return <b>true</b> se la modifica riesce, <b>false</b> altrimenti
	 */
	public abstract boolean modificaInsiemePersonaleAbilita(String emailUtente,
			List<Abilita> abilita);

	/**
	 * Metodo per modificare la foto dell'utente specificando la sua email.
	 * @param email : String che rappresenta l'email dell'utente
	 * @return <b>utente</b> se e' presente nel database con l'email specificata, <b>null</b> altrimenti.
	 */
	public abstract Utente getUtenteByEmail(String email);

}