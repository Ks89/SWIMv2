package utililies;

import org.apache.commons.codec.digest.DigestUtils;

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
	public static String hashPassword(String password) {
		System.out.println(DigestUtils.sha256Hex(password));
		return DigestUtils.sha256Hex(password);
	}

	/**
	 * Matodo per verificare se la password "pura" e quella hashata sono uguali
	 * @param password e' la password di cui si vuole calcolare l'hash
	 * @param hashPasswordDatabase e' l'hash della password conosciuta prelevata dal database
	 * @return true se le password coindono, false altrimenti
	 */
	public static boolean verifyPassword(String password, String hashPasswordDatabase) {
		return hashPassword(password).equals(hashPasswordDatabase);
	}
}
