/*
Copyright 2012-2015 Stefano Cappa, Jacopo Bulla, Davide Caio
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
    http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

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
