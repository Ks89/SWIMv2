package utililies;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import exceptions.HashingException;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;


///ATTENZIONE STA CLASSE SARA' RIMOSSA IN FUTURO. AVEVO TROVATO UN MODO PIU' FURBO PER FARLO IN UNA SOLA RIGA, CMQ
//APPENA HO VOGLIA CERCO LA SOLUZIONE...QUESTA CMQ FUNZIONA PER ORA.

/**
 * Classe usata per l'hashing di tipo SHA-256 di una password
 */
public class PasswordHasher {

	/**
	 * Funzione di calcolo dell'hash con l'algoritmo SHA-256
	 * @param password e' la password di cui si vuole calcolare l'hash
	 * @return la stringa contenente l'hash della password data in input 
	 * @throws HashingException nel caso di problemi con il recupero dei metodi che implementano l'algoritmo SHA-256
	 */
	public static String hashPassword(String password) throws HashingException {
		try {
			byte[] passwordBytes=password.getBytes();
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hashBytes = digest.digest(passwordBytes);
			return Base64.encode(hashBytes);
		} catch(NoSuchAlgorithmException e) {
			throw new HashingException(e.getMessage());
		}
	}

	/**
	 * Matodo per la verifica di corrispondenza tra due hash di due password
	 * @param password e' la password di cui si vuole calcolare l'hash per il confronto
	 * @param hashString e' l'hash della password conosciuta con cui confrontare 
	 * @return true se i due hash coincidono quindi se le due password coincidono, false altrimenti
	 * @throws HashingException nel caso ci siano problemi con la funzione di hash
	 */
	public static boolean verifyPassword(String password, String hashString) throws HashingException  {
		return hashPassword(password).equals(hashString);
	}
}
