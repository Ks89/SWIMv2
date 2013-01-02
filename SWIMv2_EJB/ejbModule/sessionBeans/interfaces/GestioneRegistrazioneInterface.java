package sessionBeans.interfaces;

import java.sql.Blob;
import java.util.List;

import entityBeans.Abilita;
import entityBeans.Utente;
import exceptions.HashingException;
import exceptions.RegistrazioneException;

public interface GestioneRegistrazioneInterface {
	public Utente registrazioneUtente(String email, String password, String nome, String cognome, Blob fotoProfilo, List<Abilita> abilita)  throws HashingException, RegistrazioneException;
	public boolean registrazioneAmministratore(String email, String password)  throws HashingException, RegistrazioneException;
	public Abilita getAbilitaByNome(String nome);
}
