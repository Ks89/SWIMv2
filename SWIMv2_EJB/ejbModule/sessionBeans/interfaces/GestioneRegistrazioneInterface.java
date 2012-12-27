package sessionBeans.interfaces;

import java.sql.Blob;
import java.util.List;

import entityBeans.Abilita;

public interface GestioneRegistrazioneInterface {
	public boolean registrazioneUtente(String email, String password, String nome, String cognome, Blob fotoProfilo, List<Abilita> abilita);
	public boolean registrazioneUtentePerTest(String email, String password, String nome, String cognome);
	public boolean registrazioneAdminTest();
	public Abilita getAbilitaByNome(String nome);
}
