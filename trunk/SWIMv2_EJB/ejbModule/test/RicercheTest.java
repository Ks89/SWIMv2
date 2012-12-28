package test;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import junit.framework.Assert;

import org.junit.Test;

import entityBeans.Abilita;
import exceptions.HashingException;

import utililies.sessionRemote.GestioneProposteRemote;
import utililies.sessionRemote.GestioneRegistrazioneRemote;
import utililies.sessionRemote.GestioneRicercheRemote;

public class RicercheTest {
	private static final String MAIL_PEPPINO = "peppino@gmail.com";
	private static final String MAIL_GIOVANNINO = "giovannino@gmail.com";
	private static final String MAIL_DAVIDE = "davide@gmail.com";
	private static final String MAIL_JACOPO = "bulla.jacopo@gmail.com";
	private static final String PASSWORD = "pippo";
	private GestioneRicercheRemote gestioneRicerche;
	private static TestUtilsRemote testUtils;
	private GestioneProposteRemote gestioneProposte;
	private GestioneRegistrazioneRemote gestioneRegistrazione;
	
	public RicercheTest() throws NamingException {
		Properties env = new Properties();
		env.setProperty("java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory");
		env.setProperty("java.naming.provider.url", "localhost:1099");
		env.setProperty("java.naming.factory.url.pkgs", "org.jboss.naming");
		List<Abilita> abilita = new ArrayList<Abilita>();

		//non cancellarla, da tenere per quando sistemiamo i progetti e li mettiamo su google code
		//	 	Object obj = (new InitialContext(env)).lookup("SWIMv2_EAR/GestioneCollaborazioni/remote-utililies.sessionRemote.GestioneCollaborazioniRemote");
		try{
			Object obj = (new InitialContext(env)).lookup("SWIMv2_EAR/GestioneRegistrazione/remote-utililies.sessionRemote.GestioneRegistrazioneRemote");
			gestioneRicerche = (GestioneRicercheRemote) PortableRemoteObject.narrow(obj, GestioneRicercheRemote.class);

			obj = (new InitialContext(env)).lookup("SWIMv2_EAR/TestUtils/remote-test.TestUtilsRemote");
			testUtils = (TestUtilsRemote) PortableRemoteObject.narrow(obj, TestUtilsRemote.class);

			obj = (new InitialContext(env)).lookup("SWIMv2_EAR/GestioneProposte/remote-utililies.sessionRemote.GestioneProposteRemote");
			gestioneProposte = (GestioneProposteRemote) PortableRemoteObject.narrow(obj, GestioneProposteRemote.class);
			
			obj = (new InitialContext(env)).lookup("SWIMv2_EAR/GestioneRegistrazione/remote-utililies.sessionRemote.GestioneRegistrazioneRemote");
			gestioneRegistrazione = (GestioneRegistrazioneRemote) PortableRemoteObject.narrow(obj, GestioneRegistrazioneRemote.class);

		}
		catch (NamingException e) {
			System.out.println(e.toString());
			return;
		}

		try {
			//svuota DB
			testUtils.svuotaDatabase();
			//aggiunge amministratore
			gestioneRegistrazione.registrazioneAdminTest();
			//Inserisce due abilità nel DB
			gestioneProposte.inserisciAbilitaAutonomamente("1ab","Descrizione");
			gestioneProposte.inserisciAbilitaAutonomamente("2ab","Descrizione");
			gestioneProposte.inserisciAbilitaAutonomamente("3ab","Descrizione");
			gestioneProposte.inserisciAbilitaAutonomamente("4ab","Descrizione");
			gestioneProposte.inserisciAbilitaAutonomamente("5ab","Descrizione");
			gestioneProposte.inserisciAbilitaAutonomamente("6ab","Descrizione");
			gestioneProposte.inserisciAbilitaAutonomamente("7ab","Descrizione");
			gestioneProposte.inserisciAbilitaAutonomamente("8ab","Descrizione");
			//aggiunge due utenti nel DB
			gestioneRegistrazione.registrazioneUtentePerTest(MAIL_PEPPINO, PASSWORD , "peppino", "peppo");
			gestioneRegistrazione.registrazioneUtentePerTest(MAIL_GIOVANNINO, PASSWORD, "davide", "caio");
			gestioneRegistrazione.registrazioneUtentePerTest(MAIL_DAVIDE, PASSWORD, "davide", "caio");
			abilita.add(gestioneRegistrazione.getAbilitaByNome("1ab"));
			gestioneRegistrazione.registrazioneUtente("bulla.jacopo@gmail.com", "pippo", "jacopo", "Bulla", null, abilita);
			abilita.add(gestioneRegistrazione.getAbilitaByNome("2ab"));
			gestioneRegistrazione.registrazioneUtente("tommaso.ganelli@gmail.com", "pippo", "Tommaso", "Ganelli", null, abilita);
			gestioneRegistrazione.registrazioneUtente("tommaso.ficcanaso@gmail.com", "pippo", "tommy", "gigio", null, abilita);
			abilita.add(gestioneRegistrazione.getAbilitaByNome("3ab"));
			abilita.add(gestioneRegistrazione.getAbilitaByNome("5ab"));
			gestioneRegistrazione.registrazioneUtente("cip.ciop@gmail.com", "pippo", "cip", "cioppo", null, abilita);
			
		} catch (HashingException e) {
			fail("HashingException: " + e);
		}

	}
	
	@Test
	public void prova(){
		Assert.assertTrue(true);
	}
	
	
	
}
