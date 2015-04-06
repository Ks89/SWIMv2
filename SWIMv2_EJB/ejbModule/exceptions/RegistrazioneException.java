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

package exceptions;

public class RegistrazioneException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static enum Causa {ALCUNIPARAMETRINULLIOVUOTI,SINTASSIEMAILNONCORRETTA,ERRORESCONOSCIUTO,EMAILGIAUTILIZZATA}; 
	private Causa causa;

	/**
	 * Richiama la superclasse.
	 */
	public RegistrazioneException() {
		super();
	}

	/**
	 * Richiama la superclasse.
	 * @param message
	 * @param cause
	 */
	public RegistrazioneException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Richiama la superclasse.
	 * @param message
	 */
	public RegistrazioneException(String message) {
		super(message);
	}

	/**
	 * Richiama la superclasse.
	 * @param cause
	 */
	public RegistrazioneException(Throwable cause) {
		super(cause);
	}

	/**
	 * Costruttore della classe che inizializza la causa che ha sollevato l'eccezione.
	 * @param causa Tipo enumerativo che rappresenta la causa che ha sollevato l'eccezione.
	 */
	public RegistrazioneException(Causa causa) {
		this.causa = causa;
	}

	/**
	 * @return Tipo enumerativo che rappresenta la causa che ha soolevato l'eccezione.
	 */
	public Causa getCausa() {
		return causa;
	}

}