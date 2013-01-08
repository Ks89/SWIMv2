package test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import org.junit.BeforeClass;
import org.junit.Test;

import utililies.sessionRemote.GestioneCollaborazioniRemote;
import entityBeans.Collaborazione;
import exceptions.CollaborazioneException;
import exceptions.LoginException;

public class CollaborazioneTest2 {

	private static GestioneCollaborazioniRemote gestioneCollaborazioni;

	private static final String MAIL_PEPPINO = "peppino@gmail.com";
	private static final String MAIL_GIOVANNINO = "giovannino@gmail.com";
	private static final String MAIL_DAVIDE = "davide@gmail.com"; 
	
	private static TestUtilsRemote testUtils;

	@BeforeClass
	public static void setUpTestCollaborazione() {
		Properties env = new Properties();
		env.setProperty("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
		env.setProperty("java.naming.provider.url", "localhost:1099");
		env.setProperty("java.naming.factory.url.pkgs", "org.jboss.naming");
		try {
			Object obj = (new InitialContext(env)).lookup("SWIMv2_EAR/GestioneCollaborazioni/remote-utililies.sessionRemote.GestioneCollaborazioniRemote");
			gestioneCollaborazioni = (GestioneCollaborazioniRemote) PortableRemoteObject.narrow(obj, GestioneCollaborazioniRemote.class);
		
			obj = (new InitialContext(env)).lookup("SWIMv2_EAR/TestUtils/remote-test.TestUtilsRemote");
			testUtils = (TestUtilsRemote) PortableRemoteObject.narrow(obj, TestUtilsRemote.class);
			
			
		} catch (NamingException e) {
			System.out.println(e.toString());
			return;
		}
	}

	@Test
	public void testCollaborazioniDaTerminare(){
		try {
			assertEquals(2,gestioneCollaborazioni.getCollaborazioniDaTerminare(MAIL_PEPPINO).size());
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
