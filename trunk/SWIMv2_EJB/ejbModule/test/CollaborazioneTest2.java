package test;

import static org.junit.Assert.*;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import org.junit.BeforeClass;
import org.junit.Test;

import utililies.sessionRemote.GestioneCollaborazioniRemote;
import exceptions.CollaborazioneException;

public class CollaborazioneTest2 {

	private static GestioneCollaborazioniRemote gestioneCollaborazioni;

	private static final String MAIL_PEPPINO = "peppino@gmail.com";
	
	@BeforeClass
	public static void setUpTestCollaborazione() {
		Properties env = new Properties();
		env.setProperty("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
		env.setProperty("java.naming.provider.url", "localhost:1099");
		env.setProperty("java.naming.factory.url.pkgs", "org.jboss.naming");
		try {
			Object obj = (new InitialContext(env)).lookup("SWIMv2_EAR/GestioneCollaborazioni/remote-utililies.sessionRemote.GestioneCollaborazioniRemote");
			gestioneCollaborazioni = (GestioneCollaborazioniRemote) PortableRemoteObject.narrow(obj, GestioneCollaborazioniRemote.class);
		
		} catch (NamingException e) {
			System.out.println(e.toString());
			return;
		}
	}

	@Test
	public void testCollaborazioniDaTerminare(){
		try {
			assertEquals(2,gestioneCollaborazioni.getCollaborazioniDaTerminare(MAIL_PEPPINO).size());
		} catch (CollaborazioneException e) {
			fail(e.getCausa().name());
		}
	}
}
