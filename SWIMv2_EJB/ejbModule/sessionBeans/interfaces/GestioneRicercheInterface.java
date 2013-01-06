package sessionBeans.interfaces;

import java.util.List;

import entityBeans.Abilita;
import entityBeans.Utente;
import exceptions.RicercheException;

public interface GestioneRicercheInterface {
	
	public List<Abilita> insiemeAbilitaGenerali();
	public List<Abilita> insiemeAbilitaPersonaliUtente(String emailUtente) throws RicercheException;
	public Utente getUtenteByEmail(String email);
	List<Utente> ricercaAiuto(List<Abilita> abilita, String email) throws RicercheException;
	List<Utente> ricercaAiutoVisitatore(List<Abilita> abilita) throws RicercheException;
	List<Utente> ricercaUtenti(String nome, String cognome, String email) throws RicercheException;
}
