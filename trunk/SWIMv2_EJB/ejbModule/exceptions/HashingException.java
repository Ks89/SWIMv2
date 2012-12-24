package exceptions;

/**
 * Classe di gestione delle eccezioni relative all'hashing
 */
public class HashingException extends Exception{
	
	private static final long serialVersionUID = -6035188230802066250L;

	/**
	 * Costruttore di default
	 */
	public HashingException()
	{
	}
	
	/**
	 * Costruttore con parametro
	 * @param message e' il messaggio di errore
	 */
	public HashingException(String message)
	{
		super("Errore causato dall'hashing: " + message);
	}
}
