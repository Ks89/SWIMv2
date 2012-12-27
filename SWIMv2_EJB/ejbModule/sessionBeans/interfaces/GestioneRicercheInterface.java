package sessionBeans.interfaces;

import java.util.List;

import entityBeans.Abilita;
import entityBeans.Utente;

public interface GestioneRicercheInterface {
	
	public abstract List<Utente> ricercaAiuto(List<Abilita> abilita);

	public abstract List<Utente> ricercaUtenti(String nome, String cognome);
}
