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
import exceptions.HashingException;
import exceptions.RegistrazioneException;

public interface GestioneRegistrazioneInterface {
	
	/**
	 * Metodo che registra un utente con i dati passati come parametro. 
	 * @param String email, l'email dell'utente
	 * @param String password, la password scelta dall'utente
	 * @param String nome, il nome dell'utente
	 * @param String cognome, il cognome dell'utentenu
	 * @param Blob fotoProfilo, la foto del profilo scelta dall'utente
	 * @param List<Abilita>, l'insieme delle abilita selezionate dall'utente all'atto della registrazione
	 * @return <b>utente</b> appena creato, <b>null</b> nel caso l'operazione non vada a buon fine
	 * @throws RegistrazioneException   
	 * @throws HashingException
	 */
	public Utente registrazioneUtente(String email, String password, String nome, String cognome, Blob fotoProfilo, List<Abilita> abilita)  throws HashingException, RegistrazioneException;
	
	
	/**
	 * Metodo che registra l'amministratore con i dati passati come parametro. 
	 * @param String email, l'email dell'amministratore
	 * @param String password, la password dell'amministratore
	 * @return <b>true</b> se la registrazione è andata a buon fine, <b>false</b> altrimenti
	 * @throws RegistrazioneException   
	 * @throws HashingException
	 */
	public boolean registrazioneAmministratore(String email, String password)  throws HashingException, RegistrazioneException;
	
	
	 /**
	 * Metodo per ottenere l'abilita in base al nome passato come parametro
	 * @param String nome, il nome dell'abilita cercata
	 * @return l'abilita cercata, <b>null</b> nel caso non esistano abilita con quel nome
	 */
	public Abilita getAbilitaByNome(String nome);
}
