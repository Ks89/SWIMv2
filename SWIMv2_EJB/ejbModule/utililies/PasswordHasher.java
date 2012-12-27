package utililies;

import org.apache.commons.codec.digest.DigestUtils;

import exceptions.HashingException;

/**
 * Classe che calcola l'hash di una password tramite algoritmo SHA-256 e permette anche di verificare l'uguaglianza
 * tra una stringa "pura" e una hashata. L'hash generato e' esadecimale e non base64 perche' altrimenti sarebbe reversibile
 */
public class PasswordHasher {

	/**
	 * Metodo per il calcolo dell'hash con l'algoritmo SHA-256
	 * @param password e' la password di cui si vuole calcolare l'hash
	 * @return una stringa contenente l'hash della password
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
	 * Matodo per verificare se la password "pura" e quella hashata sono uguali
	 * @param password e' la password di cui si vuole calcolare l'hash
	 * @param hashPasswordDatabase e' l'hash della password conosciuta prelevata dal database
	 * @return true se le password coindono, false altrimenti
	 */
	public static boolean verifyPassword(String password, String hashPasswordDatabase)  throws HashingException {
		if(password==null || password.equals("") || hashPasswordDatabase==null || hashPasswordDatabase.equals("")) {
			throw new HashingException(HashingException.Causa.ALCUNIPARAMETRINULLIOVUOTI); 
		}
		return hashPassword(password).equals(hashPasswordDatabase);
	}
}
