package test;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import junit.framework.Assert;

import org.junit.Test;

import entityBeans.Utente;
import exceptions.HashingException;

import utililies.PasswordHasher;
import utililies.sessionRemote.GestioneLoginRemote;

public class LoginTest {

	private GestioneLoginRemote gestioneLogin;

	public LoginTest() throws NamingException {
		Properties env = new Properties();
		env.setProperty("java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory");
		env.setProperty("java.naming.provider.url", "localhost:1099");
		env.setProperty("java.naming.factory.url.pkgs", "org.jboss.naming");

		//non cancellarla, da tenere per quando sistemiamo i progetti e li mettiamo su google code
		//	 	Object obj = (new InitialContext(env)).lookup("SWIMdbEar/GestioneCollaborazioni/remote-utililies.sessionRemote.GestioneCollaborazioniRemote");
		Object obj = (new InitialContext(env)).lookup("GestioneLogin/remote-utililies.sessionRemote.GestioneLoginRemote");
		gestioneLogin = (GestioneLoginRemote) PortableRemoteObject.narrow(obj, GestioneLoginRemote.class);

	}

	@Test
	public void testGetUtenteByEmail() {
		//per fare il test bisogna prima popolare il db da workbench, in futuro quando jack avra' fatto la registrazione e committata
		//uso quei metodi per registrare un po' di utenti e usarli per testare
		try {
			String passwordHashata = PasswordHasher.hashPassword("pippo");
			System.out.println(passwordHashata);
			
			Utente utente = gestioneLogin.getUtenteByEmail("asas@aaa.it");
			System.out.println(utente.toString()); //figata che funziona grazia a lombok 
			
			Assert.assertTrue(gestioneLogin.esegueLoginUtente("asas@aaa.it", "pippo")); //il metodo hasha la password e se trova quella hashata uguale nel db da true
			Assert.assertFalse(gestioneLogin.esegueLoginUtente("asas@aaa.it", passwordHashata)); //ovviamente se rihashi una password non e' quella originale
			Assert.assertFalse(gestioneLogin.esegueLoginUtente("peppino@gmail.com", "blabla")); //questo utente non e' presente e allora dice false
		} catch (HashingException e) {
			Assert.fail(e.toString());
		}

	}
}
