package it.swim.util;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import entityBeans.Utente;

/**
 *	Classe di appoggio che serve per memorizzare l'utente e la lista di suggerimenti di amici da fornire all'utente richiedente dell'amicizia
 *	con l'utente in utente.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UtenteConSuggerimenti {

	private Utente utente;
	
	private List<Utente> suggerimenti;
	
}
