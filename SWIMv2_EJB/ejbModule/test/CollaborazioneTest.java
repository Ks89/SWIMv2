package test;

import static org.junit.Assert.assertTrue;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import org.junit.Test;

import utililies.sessionRemote.GestioneCollaborazioniRemote;
import entityBeans.Collaborazione;
import entityBeans.Utente;

public class CollaborazioneTest {

	private GestioneCollaborazioniRemote gestioneCollaborazioni;
	
	public CollaborazioneTest() throws NamingException {
		Properties env = new Properties();
		env.setProperty("java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory");
	 	env.setProperty("java.naming.provider.url", "localhost:1099");
	 	env.setProperty("java.naming.factory.url.pkgs", "org.jboss.naming");
	 	
	 	//non cancellarla, da tenere per quando sistemiamo i progetti e li mettiamo su google code
//	 	Object obj = (new InitialContext(env)).lookup("SWIMv2_EAR/GestioneCollaborazioni/remote-utililies.sessionRemote.GestioneCollaborazioniRemote");
	 	Object obj = (new InitialContext(env)).lookup("GestioneCollaborazioni/remote-utililies.sessionRemote.GestioneCollaborazioniRemote");
	 	gestioneCollaborazioni = (GestioneCollaborazioniRemote) PortableRemoteObject.narrow(obj, GestioneCollaborazioniRemote.class);
		
	}
	
	@Test
	public void testRichiediAiuto() {
		Collaborazione collaborazioneAppenaInserita = gestioneCollaborazioni.richiediAiuto("peppino@gmail.com", "davide@gmail.com", "colNuova", "collaborzione inserita in attesa di risposta");
		gestioneCollaborazioni.richiediAiuto("peppino@gmail.com", "davide@gmail.com", "colNuova", "collaborzione inserita in attesa di risposta");
		gestioneCollaborazioni.richiediAiuto("peppino@gmail.com", "davide@gmail.com", "colNuova", "collaborzione inserita in attesa di risposta");
		gestioneCollaborazioni.richiediAiuto("peppino@gmail.com", "davide@gmail.com", "colNuova", "collaborzione inserita in attesa di risposta");
		gestioneCollaborazioni.richiediAiuto("peppino@gmail.com", "davide@gmail.com", "colNuova", "collaborzione inserita in attesa di risposta");
		gestioneCollaborazioni.richiediAiuto("peppino@gmail.com", "davide@gmail.com", "colNuova", "collaborzione inserita in attesa di risposta");
		gestioneCollaborazioni.richiediAiuto("peppino@gmail.com", "davide@gmail.com", "colNuova", "collaborzione inserita in attesa di risposta");
		gestioneCollaborazioni.richiediAiuto("peppino@gmail.com", "davide@gmail.com", "colNuova", "collaborzione inserita in attesa di risposta");
		
		gestioneCollaborazioni.richiediAiuto("peppino@gmail.com", "stefano@gmail.com", "colNuova", "collaborzione inserita in attesa di risposta");
		gestioneCollaborazioni.richiediAiuto("peppino@gmail.com", "stefano@gmail.com", "colNuova", "collaborzione inserita in attesa di risposta");
		gestioneCollaborazioni.richiediAiuto("peppino@gmail.com", "stefano@gmail.com", "colNuova", "collaborzione inserita in attesa di risposta");
		
		
		System.out.println("fsdfsd" + collaborazioneAppenaInserita);
		
//		Collaborazione collaborazioneDaDb = gestioneCollaborazioni.getCollaborazione(collaborazioneAppenaInserita.getCollaborazionePK().getId(), 
//				collaborazioneAppenaInserita.getCollaborazionePK().getUtenteRichiedente().getEmail(), collaborazioneAppenaInserita.getCollaborazionePK().getUtenteRicevente().getEmail());
//		
//		System.out.println(collaborazioneDaDb);
//		
//		assertTrue(collaborazioneDaDb.equals(collaborazioneAppenaInserita));
	}
	
	@Test
	public void testAccettaCollaborazione() {
		
	}
	
	@Test
	public void testRilasciaFeedback() {
		
	}
	
	@Test
	public void testRifiutaCollaborazione() {
		
	}
	
	@Test
	public void testGetPunteggioFeedback() {
		
	}
	
	@Test
	public void testGetCollaborazioniFeedbackNonRilasciato() {
		
	}
	
	@Test
	public void testGetCollaborazioniCreate() {
		
	}
	
	@Test
	public void testGetCollaborazioniAccettate() {
		
	}
}
