package it.swim.util.exceptions;

/**
 * Classe che rappresenta l'eccezione lanciata durante l'upload della foto profilo
 */
public class FotoException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static enum Causa {NONRICONOSCIUTACOMEFOTO}; 
	private Causa causa;

	/**
	 * Richiama la superclasse.
	 */
	public FotoException() {
		super();
	}

	/**
	 * Richiama la superclasse.
	 * @param message
	 * @param cause
	 */
	public FotoException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Richiama la superclasse.
	 * @param message
	 */
	public FotoException(String message) {
		super(message);
	}

	/**
	 * Richiama la superclasse.
	 * @param cause
	 */
	public FotoException(Throwable cause) {
		super(cause);
	}

	/**
	 * Costruttore della classe che inizializza la causa che ha sollevato l'eccezione.
	 * @param causa Tipo enumerativo che rappresenta la causa che ha sollevato l'eccezione.
	 */
	public FotoException(Causa causa) {
		this.causa = causa;
	}

	/**
	 * @return Tipo enumerativo che rappresenta la causa che ha soolevato l'eccezione.
	 */
	public Causa getCausa() {
		return causa;
	}

}