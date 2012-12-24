package exceptions;

/**
 * Classe per eccezioni sulla ricerca dell'utente
 */
public class UtenteNonTrovatoException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6305913098882233010L;

	/**
	 * Costruttore di default
	 */
	public UtenteNonTrovatoException()
	{
	}
	
	/**
	 * Costruttore con parametro
	 * @param message e' il messaggio di errore
	 */
	public UtenteNonTrovatoException(String message)
	{
		super("Utente non trovato: " + message);
	}
}