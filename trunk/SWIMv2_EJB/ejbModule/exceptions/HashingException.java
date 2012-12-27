package exceptions;

public class HashingException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static enum Causa {ALCUNIPARAMETRINULLIOVUOTI,ERRORESCONOSCIUTO}; 
	private Causa causa;

	/**
	 * Richiama la superclasse.
	 */
	public HashingException() {
		super();
	}

	/**
	 * Richiama la superclasse.
	 * @param message
	 * @param cause
	 */
	public HashingException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Richiama la superclasse.
	 * @param message
	 */
	public HashingException(String message) {
		super(message);
	}

	/**
	 * Richiama la superclasse.
	 * @param cause
	 */
	public HashingException(Throwable cause) {
		super(cause);
	}

	/**
	 * Costruttore della classe che inizializza la causa che ha sollevato l'eccezione.
	 * @param causa Tipo enumerativo che rappresenta la causa che ha sollevato l'eccezione.
	 */
	public HashingException(Causa causa) {
		this.causa = causa;
	}

	/**
	 * @return Tipo enumerativo che rappresenta la causa che ha soolevato l'eccezione.
	 */
	public Causa getCausa() {
		return causa;
	}

}