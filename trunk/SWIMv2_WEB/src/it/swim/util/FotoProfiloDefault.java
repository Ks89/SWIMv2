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
