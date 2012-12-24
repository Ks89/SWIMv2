package test;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import org.junit.Test;

import utililies.sessionRemote.GestioneCollaborazioniRemote;
import entityBeans.Utente;

public class ColalborazioneTest {

	private GestioneCollaborazioniRemote gestioneCollaborazioni;
	
	public ColalborazioneTest() throws NamingException {
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
	public void testGetUtenteByEmail()
	{
		//utente non esistente
		Utente utente = gestioneCollaborazioni.getUtenteByEmail("stefano@gmail.com");
		System.out.println(utente);
		//Assert.assertNull("Utente non esistente", utente);
	}
}
