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

package sessionBeans.interfaces;

import java.sql.Blob;
import java.util.List;

import entityBeans.Abilita;
import entityBeans.Utente;

public interface GestioneModificaProfiloInterface {

	/**
	 * Metodo per modificare la foto dell'utente specificando la sua email.
	 * @param emailUtente : String che rappresenta l'email dell'utente
	 * @param fotoProfilo : Blob che rappresenta la nuova immagine del profilo
	 * @return <b>true</b> se la modifica riesce, <b>false</b> altrimenti
	 */
	public abstract boolean modificaFoto(String emailUtente, Blob fotoProfilo);

	/**
	 * Metodo per modificare la foto dell'utente specificando la sua email.
	 * @param emailUtente : String che rappresenta l'email dell'utente
	 * @param abilita : List<Abilita> che rappresenta le abilita' da aggiungere all'insieme personale dell'utente
	 * @return <b>true</b> se la modifica riesce, <b>false</b> altrimenti
	 */
	public abstract boolean modificaInsiemePersonaleAbilita(String emailUtente,
			List<Abilita> abilita);

	/**
	 * Metodo per modificare la foto dell'utente specificando la sua email.
	 * @param email : String che rappresenta l'email dell'utente
	 * @return <b>utente</b> se e' presente nel database con l'email specificata, <b>null</b> altrimenti.
	 */
	public abstract Utente getUtenteByEmail(String email);

}