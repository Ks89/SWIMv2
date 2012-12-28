package sessionBeans.interfaces;

import java.sql.Blob;
import java.util.List;

import entityBeans.Abilita;
import entityBeans.Utente;

public interface GestioneModificaProfiloInterface {

	public abstract boolean modificaFoto(String emailUtente, Blob fotoProfilo);

	public abstract boolean modificaSetAbilita(String emailUtente,
			List<Abilita> abilita);

	public abstract Utente getUtenteByEmail(String email);

}