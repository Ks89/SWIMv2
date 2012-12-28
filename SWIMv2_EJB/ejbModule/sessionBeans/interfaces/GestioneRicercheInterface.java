package sessionBeans.interfaces;

import java.util.List;

import entityBeans.Abilita;
import entityBeans.Utente;
import exceptions.RicercheException;

public interface GestioneRicercheInterface {
	
	public List<Utente> ricercaAiuto(List<Abilita> abilita) throws RicercheException;
	public List<Utente> ricercaUtenti(String nome, String cognome) throws RicercheException;
	public List<Abilita> insiemeAbilitaGenerali();
	public List<Abilita> insiemeAbilitaPersonaliUtente(String emailUtente) throws RicercheException;
	
}
