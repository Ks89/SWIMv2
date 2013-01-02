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
import entityBeans.Utente;
import exceptions.HashingException;
import exceptions.ProposteException;
import exceptions.RegistrazioneException;
import utililies.sessionRemote.GestioneProposteRemote;
import utililies.sessionRemote.GestioneRegistrazioneRemote;
import utililies.sessionRemote.GestioneRicercheRemote;

public class RegistrazioneTest {

	private static final String MAIL_PEPPINO = "peppino@gmail.com";
	private static final String MAIL_GIOVANNINO = "giovannino@gmail.com";
	private static final String MAIL_DAVIDE = "davide@gmail.com";
	private static final String MAIL_JACOPO = "bulla.jacopo@gmail.com";
	private static final String PASSWORD = "pippo";
	private static final String MAIL_ADMIN = "admin@swim.it";

	private GestioneRegistrazioneRemote gestioneRegistrazione;
	private GestioneProposteRemote gestioneProposte;
	private GestioneRicercheRemote gestioneRicerche;
	private static TestUtilsRemote testUtils;

	public RegistrazioneTest() throws NamingException, RegistrazioneException {
		Properties env = new Properties();
		env.setProperty("java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory");
		env.setProperty("java.naming.provider.url", "localhost:1099");
		env.setProperty("java.naming.factory.url.pkgs", "org.jboss.naming");

		//non cancellarla, da tenere per quando sistemiamo i progetti e li mettiamo su google code
		//	 	Object obj = (new InitialContext(env)).lookup("SWIMv2_EAR/GestioneCollaborazioni/remote-utililies.sessionRemote.GestioneCollaborazioniRemote");
		try{
			Object obj = (new InitialContext(env)).lookup("SWIMv2_EAR/GestioneRegistrazione/remote-utililies.sessionRemote.GestioneRegistrazioneRemote");
			gestioneRegistrazione = (GestioneRegistrazioneRemote) PortableRemoteObject.narrow(obj, GestioneRegistrazioneRemote.class);

			obj = (new InitialContext(env)).lookup("SWIMv2_EAR/TestUtils/remote-test.TestUtilsRemote");
			testUtils = (TestUtilsRemote) PortableRemoteObject.narrow(obj, TestUtilsRemote.class);

			obj = (new InitialContext(env)).lookup("SWIMv2_EAR/GestioneProposte/remote-utililies.sessionRemote.GestioneProposteRemote");
			gestioneProposte = (GestioneProposteRemote) PortableRemoteObject.narrow(obj, GestioneProposteRemote.class);
			
			obj = (new InitialContext(env)).lookup("SWIMv2_EAR/GestioneRicerche/remote-utililies.sessionRemote.GestioneRicercheRemote");
			gestioneRicerche = (GestioneRicercheRemote) PortableRemoteObject.narrow(obj, GestioneRicercheRemote.class);

		}
		catch (NamingException e) {
			System.out.println(e.toString());
			return;
		}

		try {
			//svuota DB
			testUtils.svuotaDatabase();
			//aggiunge amministratore
			gestioneRegistrazione.registrazioneAmministratore("admin@swim.it","pippo");
			//Inserisce due abilità nel DB
			gestioneProposte.inserisciAbilitaAutonomamente(MAIL_ADMIN, "1ab","prima Abilita");
			gestioneProposte.inserisciAbilitaAutonomamente(MAIL_ADMIN, "2ab","seconda Abilita");
		} catch (HashingException e) {
			fail("HashingException: " + e);
		} catch (ProposteException e) {
			fail("ProposteException: " + e);
		}

	}

	@Test
	public void eseguiRegistrazioneUtente(){
		try {
			Utente utente=new Utente();
			List<Abilita> abilita = new ArrayList<Abilita>();
			//Per eseguire questo test dovete inserire due abilita nel db. Una nome=1ab Descrizione=prima Abilita l'altra=2ab Descrizione=seconda Abilita 
			//Non ci si puo' registrare senza almeno una abilita
			try{
				gestioneRegistrazione.registrazioneUtente(MAIL_JACOPO, PASSWORD, "Enrico", "Rossi", null, abilita);
				fail();
			}
			catch(RegistrazioneException ex)
			{
				Assert.assertTrue(true);
			}
			abilita.add(gestioneRegistrazione.getAbilitaByNome("1ab"));

			//Non ci si puo' registrare senza nome
			try{
				gestioneRegistrazione.registrazioneUtente(MAIL_JACOPO, PASSWORD, "", "Rossi", null, abilita);
				fail();
			}
			catch(RegistrazioneException ex)
			{
				Assert.assertTrue(true);
			}
			//Non ci si puo' registrare senza cognome
			try{
				gestioneRegistrazione.registrazioneUtente(MAIL_JACOPO, PASSWORD, "Enrico", "", null, abilita);
				fail();
			}
			catch(RegistrazioneException ex)
			{
				Assert.assertTrue(true);
			}
			//Non ci si puo' registrare senza password
			try{
				gestioneRegistrazione.registrazioneUtente(MAIL_JACOPO, "", "Enrico", "Rossi", null, abilita);
				fail();
			}
			catch(RegistrazioneException ex)
			{
				Assert.assertTrue(true);
			}
			//Registrazione a buon fine con una abilita
			Assert.assertTrue(gestioneRegistrazione.registrazioneUtente("bulla.jacopo@gmail.com", "pippo", "jacopo", "Bulla", null, abilita).equals(gestioneRicerche.getUtenteByEmail("bulla.jacopo@gmail.com")));
			//Non ci possono essere due utenti con la stessa mail
			try{
				gestioneRegistrazione.registrazioneUtente("bulla.jacopo@gmail.com", "pippo", "Andrea", "Bazzi", null, abilita);
				fail();
			}
			catch(RegistrazioneException ex)
			{
				Assert.assertTrue(true);
			}

			//Registrazione a buon fine con due abilita
			abilita.add(gestioneRegistrazione.getAbilitaByNome("2ab"));
			Assert.assertTrue(gestioneRegistrazione.registrazioneUtente("tommaso.ganelli@gmail.com", "pippo", "Tommaso", "Ganelli", null, abilita).equals(gestioneRicerche.getUtenteByEmail("tommaso.ganelli@gmail.com")));
		} catch (HashingException e) {
			
			fail("HashingException: " + e);
		}
		catch(RegistrazioneException ex)
		{
			fail("RegistrazioneException: " +ex);
		}
	} 
}
