package sessionBeans.interfaces;

import java.sql.Blob;
import java.util.List;

import entityBeans.Abilita;
import entityBeans.Utente;
import exceptions.HashingException;
import exceptions.RegistrazioneException;

public interface GestioneRegistrazioneInterface {
	public Utente registrazioneUtente(String email, String password, String nome, String cognome, Blob fotoProfilo, List<Abilita> abilita)  throws HashingException, RegistrazioneException;
	public boolean registrazioneUtentePerTest(String email, String password, String nome, String cognome) throws HashingException;
	public boolean registrazioneAdminTest()  throws HashingException;
	public Abilita getAbilitaByNome(String nome);
}
