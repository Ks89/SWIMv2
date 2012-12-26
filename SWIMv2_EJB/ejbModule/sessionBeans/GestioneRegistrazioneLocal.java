package sessionBeans;

import java.sql.Blob;
import java.util.List;

import javax.ejb.Local;

import entityBeans.Abilita;

@Local
public interface GestioneRegistrazioneLocal {
	public boolean registrazioneUtente(String email, String password, String nome, String cognome, Blob fotoProfilo, List<Abilita> abilita);
}
