package sessionBeans.interfaces;

import java.util.List;

import entityBeans.Abilita;
import entityBeans.Utente;

public interface GestioneRicercheInterface {
	
	public List<Utente> ricercaAiuto(List<Abilita> abilita);
	public List<Utente> ricercaUtenti(String nome, String cognome);
	public List<Abilita> insiemeAbilitaGenerali();
	public List<Abilita> insiemeAbilitaPersonaliUtente(String emailUtente);
	
}
