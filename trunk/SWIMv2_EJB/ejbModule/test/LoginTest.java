package test;

import static org.junit.Assert.fail;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import exceptions.HashingException;
import exceptions.LoginException;

import utililies.PasswordHasher;
import utililies.sessionRemote.GestioneLoginRemote;
import utililies.sessionRemote.GestioneRegistrazioneRemote;

public class LoginTest {

	private static final String MAIL_PEPPINO = "peppino@gmail.com";
	private static final String MAIL_UTENTE_INUTILE = "pippo@gmail.com";
	private static final String PASSWORD_INUTILE = "blabla";
	private static final String MAIL_AMMINISTRATORE = "admin@swim.it";
	private static final String PASSWORD = "pippo";

	private GestioneLoginRemote gestioneLogin;
//	private GestioneRegistrazioneRemote gestioneRegistrazione;
//	private static TestUtilsRemote testUtils;

	public LoginTest() throws NamingException {
		Properties env = new Properties();
		env.setProperty("java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory");
		env.setProperty("java.naming.provider.url", "localhost:1099");
		env.setProperty("java.naming.factory.url.pkgs", "org.jboss.naming");

		Object obj = (new InitialContext(env)).lookup("SWIMv2_EAR/GestioneLogin/remote-utililies.sessionRemote.GestioneLoginRemote");
		gestioneLogin = (GestioneLoginRemote) PortableRemoteObject.narrow(obj, GestioneLoginRemote.class);

//		obj = (new InitialContext(env)).lookup("SWIMv2_EAR/GestioneRegistrazione/remote-utililies.sessionRemote.GestioneRegistrazioneRemote");
//		gestioneRegistrazione = (GestioneRegistrazioneRemote) PortableRemoteObject.narrow(obj, GestioneRegistrazioneRemote.class);

//		obj = (new InitialContext(env)).lookup("SWIMv2_EAR/TestUtils/remote-test.TestUtilsRemote");
//		testUtils = (TestUtilsRemote) PortableRemoteObject.narrow(obj, TestUtilsRemote.class);
	}

//	@Before
//	public void setUpLoginTest() {
//		//testUtils.svuotaTabellaDatabase("Utente"); perche' funzioni bisogna non avere nulla del db, altrimenti viola l'integrita' referenziale
//		//testUtils.svuotaDatabase(); nel caso si voglia svuotare tutto il db prima di fare i test
//
//		//gestioneRegistrazione.registraUtente(MAIL_PEPPINO,PASSWORD);
//		//gestioneRegistrazione.registraAmministratore(MAIL_AMMINISTRATORE, PASSWORD);
//	}

	@Test
	public void testEseguiLoginUtente() {
		try {
		//inserire nel db l'utente peppino@gmail.com con password a2242ead55c94c3deb7cf2340bfef9d5bcaca22dfe66e646745ee4371c633fc8
		String passwordHashata = PasswordHasher.hashPassword(PASSWORD);

		//il metodo hasha la password e se trova quella hashata uguale nel db da true
		Assert.assertTrue(gestioneLogin.esegueLoginUtente(MAIL_PEPPINO, PASSWORD));

		//ovviamente se rihashi una password non e' quella originale
		Assert.assertFalse(gestioneLogin.esegueLoginUtente(MAIL_PEPPINO, passwordHashata));

		//questo utente non e' presente e allora dice false
		Assert.assertFalse(gestioneLogin.esegueLoginUtente(MAIL_UTENTE_INUTILE, PASSWORD_INUTILE)); 
	} catch (LoginException e) {
		fail("LoginException: " + e);
	} catch (HashingException e) {
		fail("HashingException: " + e);
	}
	}

	@Test
	public void testEseguiLoginAmministratore() {
		try {
			//inserire nel db l'admin admin@swim.it con password a2242ead55c94c3deb7cf2340bfef9d5bcaca22dfe66e646745ee4371c633fc8
			String passwordHashata = PasswordHasher.hashPassword(PASSWORD);

			//il metodo hasha la password e se trova quella hashata uguale nel db da true
			Assert.assertTrue(gestioneLogin.eseguiLoginAmministratore(MAIL_AMMINISTRATORE, PASSWORD));

			//ovviamente se rihashi una password non e' quella originale
			Assert.assertFalse(gestioneLogin.eseguiLoginAmministratore(MAIL_AMMINISTRATORE, passwordHashata));

			//questo admin non e' presente e allora dice false
			Assert.assertFalse(gestioneLogin.eseguiLoginAmministratore(MAIL_UTENTE_INUTILE, PASSWORD_INUTILE)); 
		} catch (LoginException e) {
			fail("LoginException: " + e);
		} catch (HashingException e) {
			fail("HashingException: " + e);
		}
	}
}
