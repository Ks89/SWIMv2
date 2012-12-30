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