package test;

import static org.junit.Assert.*;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import org.junit.BeforeClass;
import org.junit.Test;

import utililies.sessionRemote.GestioneAmicizieRemote;

import entityBeans.Amicizia;
import entityBeans.Utente;
import exceptions.AmiciziaException;

public class AmiciziaTest {

	private static  GestioneAmicizieRemote gestioneAmicizie;
	private static TestUtilsRemote testUtils;

	private static final String MAIL_PEPPINO = "peppino@gmail.com";
	private static final String MAIL_GIOVANNINO = "giovannino@gmail.com";
	private static final String MAIL_DAVIDE = "davide@gmail.com";
	private static final String MAIL_JACOPO = "bulla.jacopo@gmail.com";


	@BeforeClass
	public static void setUpAmicizaTest(){
		Properties env = new Properties();
		env.setProperty("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
		env.setProperty("java.naming.provider.url", "localhost:1099");
		env.setProperty("java.naming.factory.url.pkgs", "org.jboss.naming");
		try {

			Object obj = (new InitialContext(env)).lookup("SWIMv2_EAR/GestioneAmicizie/remote-utililies.sessionRemote.GestioneAmicizieRemote");
			gestioneAmicizie = (GestioneAmicizieRemote) PortableRemoteObject.narrow(obj, GestioneAmicizieRemote.class);
			obj = (new InitialContext(env)).lookup("SWIMv2_EAR/TestUtils/remote-test.TestUtilsRemote");
			testUtils = (TestUtilsRemote) PortableRemoteObject.narrow(obj, TestUtilsRemote.class);

		} catch (NamingException e) {
			System.out.println(e.toString());
			return;
		}
	}

	@Test
	public void test() {
		testUtils.svuotaTabellaDatabase("Amicizia");
		Amicizia amicizia1=gestioneAmicizie.richiediAmicizia(MAIL_GIOVANNINO, MAIL_DAVIDE, true);
		Amicizia amicizia2=gestioneAmicizie.richiediAmicizia(MAIL_GIOVANNINO, MAIL_PEPPINO, true);
		Utente utenteDave=gestioneAmicizie.getUtenteByEmail(MAIL_DAVIDE);
		Utente utenteJova= gestioneAmicizie.getUtenteByEmail(MAIL_GIOVANNINO);
		Utente utenteJaco= gestioneAmicizie.getUtenteByEmail(MAIL_JACOPO);
		Utente utentePeppi= gestioneAmicizie.getUtenteByEmail(MAIL_PEPPINO);		


		assertNotNull(amicizia1);
		assertNotNull(amicizia2);
		assertFalse(gestioneAmicizie.amiciziaInoltrata(MAIL_JACOPO, MAIL_DAVIDE));
		assertEquals(0, gestioneAmicizie.getAmici(MAIL_JACOPO).size());

		try{
			assertTrue(gestioneAmicizie.accettaAmicizia(MAIL_GIOVANNINO, MAIL_DAVIDE)!=null);
			assertEquals(1,gestioneAmicizie.getAmici(MAIL_GIOVANNINO).size());
			assertTrue(gestioneAmicizie.getAmici(MAIL_GIOVANNINO).contains(utenteDave));
			assertEquals(1,gestioneAmicizie.getUtentiCheVoglionoAmicizia(MAIL_PEPPINO).size());
			assertTrue(gestioneAmicizie.getUtentiCheVoglionoAmicizia(MAIL_PEPPINO).contains(utenteJova));
			assertTrue(gestioneAmicizie.amiciziaInoltrata(MAIL_PEPPINO, MAIL_GIOVANNINO));
			assertTrue(gestioneAmicizie.amiciziaInoltrata(MAIL_GIOVANNINO, MAIL_PEPPINO));
			assertFalse(gestioneAmicizie.rifiutaAmicizia(MAIL_PEPPINO, MAIL_GIOVANNINO));
			assertTrue(gestioneAmicizie.rifiutaAmicizia(MAIL_GIOVANNINO, MAIL_PEPPINO));

			amicizia2=gestioneAmicizie.richiediAmicizia(MAIL_GIOVANNINO, MAIL_JACOPO, true);
			assertTrue(gestioneAmicizie.accettaAmicizia(MAIL_GIOVANNINO, MAIL_JACOPO)!=null);		
			assertEquals(utenteJova, gestioneAmicizie.getAmici(MAIL_JACOPO).get(0));
			assertEquals(2, gestioneAmicizie.getAmici(MAIL_GIOVANNINO).size());
			assertFalse(gestioneAmicizie.getAmici(MAIL_GIOVANNINO).contains(utenteJova));
			amicizia2=gestioneAmicizie.richiediAmicizia(MAIL_PEPPINO, MAIL_GIOVANNINO, true);
			assertTrue(gestioneAmicizie.accettaAmicizia(MAIL_PEPPINO, MAIL_GIOVANNINO)!=null);
		} catch (AmiciziaException e) {
			fail(e.getCausa().name());
		}

		assertEquals(3,gestioneAmicizie.getAmici(MAIL_GIOVANNINO).size());
		assertTrue(gestioneAmicizie.getAmici(MAIL_GIOVANNINO).contains(utenteJaco));
		assertTrue(gestioneAmicizie.getAmici(MAIL_GIOVANNINO).contains(utentePeppi));
		assertEquals(2, gestioneAmicizie.getSuggerimenti(MAIL_GIOVANNINO, MAIL_PEPPINO).size());
		assertTrue(gestioneAmicizie.getSuggerimenti(MAIL_GIOVANNINO, MAIL_PEPPINO).contains(utenteDave));
		assertNull(gestioneAmicizie.getSuggerimenti(MAIL_PEPPINO, MAIL_GIOVANNINO));
		assertTrue(gestioneAmicizie.sonoAmici(MAIL_GIOVANNINO, MAIL_DAVIDE));
		assertTrue(gestioneAmicizie.sonoAmici(MAIL_DAVIDE, MAIL_GIOVANNINO));
		assertEquals(2,gestioneAmicizie.getUtentiCheHannoAccettatoLaRichiestaDiretti(MAIL_GIOVANNINO).size());
		assertTrue(gestioneAmicizie.setAmiciziaNotificata(MAIL_GIOVANNINO, MAIL_JACOPO));
		assertEquals(1,gestioneAmicizie.getUtentiCheHannoAccettatoLaRichiestaDiretti(MAIL_GIOVANNINO).size());
	}

}
