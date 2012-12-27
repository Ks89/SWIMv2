package test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import utililies.sessionRemote.GestioneCollaborazioniRemote;

public class TestUtils {

	private static final String entityBeans[] = { "Abilita", "Amicizia", "Amministratore", "Collaborazione", "Possiede", "PropostaAbilita", "Utente"};
	private static final String MAIL_PEPPINO = "peppino@gmail.com";
	private static final String MAIL_GIOVANNINO = "giovannino@gmail.com";
	private static final String MAIL_DAVIDE = "davide@gmail.com";
	
	private static GestioneCollaborazioniRemote gestioneCollaborazioni;

	@PersistenceContext(unitName = "SWIMdb")
	private static EntityManager entityManager;

	
	/**
	 * Svuota il database
	 */
	public static void svuotaDatabase() {
		for(String entityBean : entityBeans) {
			entityManager.createQuery("DELETE " + entityBean + " eb").executeUpdate();
		}
	}

	public static void inserisciUtenti() {
		
	}

	public static void inserisciCollaborazioni() {
//		gestioneCollaborazioni.richiediAiuto(MAIL_PEPPINO, MAIL_DAVIDE, "vdbvsd", "ewgwe");
//		gestioneCollaborazioni.richiediAiuto(MAIL_PEPPINO, MAIL_DAVIDE, "bdb", "fgweg");
//		gestioneCollaborazioni.richiediAiuto(MAIL_GIOVANNINO, MAIL_PEPPINO, "rbrw", "ehweh");
//		gestioneCollaborazioni.richiediAiuto(MAIL_GIOVANNINO, MAIL_DAVIDE, "vegq", "bsdg");
//		gestioneCollaborazioni.richiediAiuto(MAIL_PEPPINO, MAIL_DAVIDE, "dsfsdfsd", "ewgwe");
	}

}
