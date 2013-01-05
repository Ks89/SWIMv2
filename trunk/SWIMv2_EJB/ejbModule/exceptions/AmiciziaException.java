package exceptions;

/**
 * Classe che rappresenta l'eccezione lancia dal session bean GestioneAmicizie
 */
public class AmiciziaException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static enum Causa {ALCUNIPARAMETRINULLIOVUOTI,UPDATEAMICIZIAFALLITO,ERRORESCONOSCIUTO,UTENTINONTROVATI}; 
	private Causa causa;

	/**
	 * Richiama la superclasse.
	 */
	public AmiciziaException() {
		super();
	}

	/**
	 * Richiama la superclasse.
	 * @param message
	 * @param cause
	 */
	public AmiciziaException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Richiama la superclasse.
	 * @param message
	 */
	public AmiciziaException(String message) {
		super(message);
	}

	/**
	 * Richiama la superclasse.
	 * @param cause
	 */
	public AmiciziaException(Throwable cause) {
		super(cause);
	}

	/**
	 * Costruttore della classe che inizializza la causa che ha sollevato l'eccezione.
	 * @param causa Tipo enumerativo che rappresenta la causa che ha sollevato l'eccezione.
	 */
	public AmiciziaException(Causa causa) {
		this.causa = causa;
	}

	/**
	 * @return Tipo enumerativo che rappresenta la causa che ha soolevato l'eccezione.
	 */
	public Causa getCausa() {
		return causa;
	}

}