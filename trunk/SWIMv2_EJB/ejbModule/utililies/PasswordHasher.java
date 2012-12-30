package utililies;

import org.apache.commons.codec.digest.DigestUtils;

import exceptions.HashingException;

/**
 * Classe che calcola l'hash di una password tramite algoritmo SHA-256 e permette anche di verificare l'uguaglianza
 * tra una stringa "in chiaro" e una hashata. L'hash generato e' esadecimale e non base64 perche' altrimenti sarebbe reversibile.
 */
public class PasswordHasher {

	/**
	 * Metodo per il calcolo dell'hash con l'algoritmo SHA-256
	 * @param password String che rappresenta la password di cui si vuole calcolare l'hash
	 * @return <b>hash passowrd</b> cioe' una String contenente l'hash della password
	 * @exception HashingException con causa ALCUNIPARAMETRINULLIOVUOTI oppure ERRORESCONOSCIUTO
	 */
	public static String hashPassword(String password) throws HashingException {
		if(password==null || password.equals("")) {
			throw new HashingException(HashingException.Causa.ALCUNIPARAMETRINULLIOVUOTI); 
		}
		
		String hashedPassowrd;
		try {
			hashedPassowrd = DigestUtils.sha256Hex(password);
		} catch (Exception e) {
			throw new HashingException(HashingException.Causa.ERRORESCONOSCIUTO); 
		}
		return hashedPassowrd;
	}

	/**
	 * Matodo per verificare se la password "in chiaro" e quella hashata sono uguali
	 * @param password String che rappresenta la password di cui si vuole calcolare l'hash
	 * @param hashPasswordDatabase String che rappresenta l'hash della password conosciuta prelevata dal database
	 * @return <b>true</b> se le password coindono, <b>false</b> altrimenti
	 * @exception HashingException con causa ALCUNIPARAMETRINULLIOVUOTI
	 */
	public static boolean verifyPassword(String password, String hashPasswordDatabase)  throws HashingException {
		if(password==null || password.equals("") || hashPasswordDatabase==null || hashPasswordDatabase.equals("")) {
			throw new HashingException(HashingException.Causa.ALCUNIPARAMETRINULLIOVUOTI); 
		}
		return hashPassword(password).equals(hashPasswordDatabase);
	}
}
