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

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 *	Classe che carica un'immagine di default come foto del profilo.
 */
public class FotoProfiloDefault {

	private static FotoProfiloDefault instance = new FotoProfiloDefault();
	private BufferedImage fotoProfiloDefault;
	private boolean errore;

	private FotoProfiloDefault() {
		try {
			fotoProfiloDefault = ImageIO.read(this.getClass().getResource("/profilo_default.jpg"));
		} catch (IOException e) {
			errore = true;
		}
	}
	
	/**
	 * Metodo che permette di ottenere l'istanza della classe.
	 * @return istanza della classe FotoProfiloDefault.
	 */
	public static FotoProfiloDefault getInstance() {
		return instance;
	}

	/**
	 * @return <b>BufferedImage</b> contenente la foto profilo dui default.
	 */
	public BufferedImage getFotoProfiloDefault() {
		return fotoProfiloDefault;
	}
	
	/**
	 * @return <b>boolean</b> che rappresenta l'errore, se presente.
	 */
	public boolean isErrore() {
		return errore;
	}
}
