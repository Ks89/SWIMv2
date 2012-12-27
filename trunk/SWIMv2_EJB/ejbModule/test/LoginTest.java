package test;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import junit.framework.Assert;

import org.junit.Test;

import utililies.PasswordHasher;
import utililies.sessionRemote.GestioneLoginRemote;

public class LoginTest {

	private static final String MAIL_PEPPINO = "peppino@gmail.com";
	private static final String PASSWORD = "pippo";
	private static final String MAIL_UTENTE_INUTILE = "pippo@gmail.com";
	private static final String PASSWORD_INUTILE = "blabla";
	
	private GestioneLoginRemote gestioneLogin;

	public LoginTest() throws NamingException {
		Properties env = new Properties();
		env.setProperty("java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory");
		env.setProperty("java.naming.provider.url", "localhost:1099");
		env.setProperty("java.naming.factory.url.pkgs", "org.jboss.naming");

		Object obj = (new InitialContext(env)).lookup("SWIMv2_EAR/GestioneLogin/remote-utililies.sessionRemote.GestioneLoginRemote");
		gestioneLogin = (GestioneLoginRemote) PortableRemoteObject.narrow(obj, GestioneLoginRemote.class);

	}

	@Test
	public void testEseguiLoginUtente() {
		//per fare il test bisogna prima popolare il db da workbench, in futuro quando jack avra' fatto la registrazione e committata
		//uso quei metodi per registrare un po' di utenti e usarli per testare
		
		//inserire nel db l'utente asas@aaa.it con password a2242ead55c94c3deb7cf2340bfef9d5bcaca22dfe66e646745ee4371c633fc8
		String passwordHashata = PasswordHasher.hashPassword(PASSWORD);

		//il metodo hasha la password e se trova quella hashata uguale nel db da true
		Assert.assertTrue(gestioneLogin.esegueLoginUtente(MAIL_PEPPINO, PASSWORD));
		
		//ovviamente se rihashi una password non e' quella originale
		Assert.assertFalse(gestioneLogin.esegueLoginUtente(MAIL_PEPPINO, passwordHashata));
		
		//questo utente non e' presente e allora dice false
		Assert.assertFalse(gestioneLogin.esegueLoginUtente(MAIL_UTENTE_INUTILE, PASSWORD_INUTILE)); 
	}
	
	@Test
	public void testEseguiLoginAmministratore() {
		//per fare il test bisogna prima popolare il db da workbench, in futuro quando jack avra' fatto la registrazione e committata
		//uso quei metodi per registrare un po' di utenti e usarli per testare
		
		//inserire nel db l'admin asas@aaa.it con password a2242ead55c94c3deb7cf2340bfef9d5bcaca22dfe66e646745ee4371c633fc8
		String passwordHashata = PasswordHasher.hashPassword(PASSWORD);

		//il metodo hasha la password e se trova quella hashata uguale nel db da true
		Assert.assertTrue(gestioneLogin.eseguiLoginAmministratore(MAIL_PEPPINO, PASSWORD));
		
		//ovviamente se rihashi una password non e' quella originale
		Assert.assertFalse(gestioneLogin.esegueLoginUtente(MAIL_PEPPINO, passwordHashata));
		
		//questo utente non e' presente e allora dice false
		Assert.assertFalse(gestioneLogin.esegueLoginUtente(MAIL_UTENTE_INUTILE, PASSWORD_INUTILE)); 
	}
}
