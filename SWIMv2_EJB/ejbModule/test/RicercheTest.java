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
import exceptions.RicercheException;

import utililies.sessionRemote.GestioneProposteRemote;
import utililies.sessionRemote.GestioneRegistrazioneRemote;
import utililies.sessionRemote.GestioneRicercheRemote;

public class RicercheTest {
	private static final String MAIL_PEPPINO = "peppino@gmail.com";
	private static final String MAIL_GIOVANNINO = "giovannino@gmail.com";
	private static final String MAIL_DAVIDE = "davide@gmail.com";
	private static final String MAIL_JACOPO = "bulla.jacopo@gmail.com";
	private static final String MAIL_ADMIN = "admin@swim.it";
	private static final String PASSWORD = "pippo";

	private GestioneRicercheRemote gestioneRicerche;
	private static TestUtilsRemote testUtils;
	private GestioneProposteRemote gestioneProposte;
	private GestioneRegistrazioneRemote gestioneRegistrazione;
	private List<Abilita> abilita = new ArrayList<Abilita>();
	
	public RicercheTest() throws NamingException, RegistrazioneException {
		Properties env = new Properties();
		env.setProperty("java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory");
		env.setProperty("java.naming.provider.url", "localhost:1099");
		env.setProperty("java.naming.factory.url.pkgs", "org.jboss.naming");
		

		//non cancellarla, da tenere per quando sistemiamo i progetti e li mettiamo su google code
		//	 	Object obj = (new InitialContext(env)).lookup("SWIMv2_EAR/GestioneCollaborazioni/remote-utililies.sessionRemote.GestioneCollaborazioniRemote");
		try{
			Object obj = (new InitialContext(env)).lookup("SWIMv2_EAR/GestioneRicerche/remote-utililies.sessionRemote.GestioneRicercheRemote");
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
			gestioneRegistrazione.registrazioneAmministratore("admin@swim.it","pippo");
			//Inserisce due abilità nel DB
			gestioneProposte.inserisciAbilitaAutonomamente(MAIL_ADMIN, "1ab","Descrizione");
			gestioneProposte.inserisciAbilitaAutonomamente(MAIL_ADMIN,"2ab","Descrizione");
			gestioneProposte.inserisciAbilitaAutonomamente(MAIL_ADMIN,"3ab","Descrizione");
			gestioneProposte.inserisciAbilitaAutonomamente(MAIL_ADMIN,"4ab","Descrizione");
			gestioneProposte.inserisciAbilitaAutonomamente(MAIL_ADMIN,"5ab","Descrizione");
			gestioneProposte.inserisciAbilitaAutonomamente(MAIL_ADMIN,"6ab","Descrizione");
			gestioneProposte.inserisciAbilitaAutonomamente(MAIL_ADMIN,"7ab","Descrizione");
			gestioneProposte.inserisciAbilitaAutonomamente(MAIL_ADMIN,"8ab","Descrizione");
			//aggiunge due utenti nel DB
			abilita.add(gestioneRegistrazione.getAbilitaByNome("1ab"));
			gestioneRegistrazione.registrazioneUtente("peppino@gmail.com", "pippo", "peppino", "peppo", null, abilita);
			gestioneRegistrazione.registrazioneUtente("davide@gmail.com", "pippo", "Davide", "Caio", null, abilita);
			gestioneRegistrazione.registrazioneUtente("giovannino@gmail.com", "pippo", "Giovanni", "gio", null, abilita);
			gestioneRegistrazione.registrazioneUtente("bulla.jacopo@gmail.com", "pippo", "jacopo", "Bulla", null, abilita);
			abilita.add(gestioneRegistrazione.getAbilitaByNome("2ab"));
			gestioneRegistrazione.registrazioneUtente("tommaso.ganelli@gmail.com", "pippo", "Tommaso", "Ganelli", null, abilita);
			gestioneRegistrazione.registrazioneUtente("tommaso.ficcanaso@gmail.com", "pippo", "tommy", "gigio", null, abilita);
			abilita.add(gestioneRegistrazione.getAbilitaByNome("3ab"));
			abilita.add(gestioneRegistrazione.getAbilitaByNome("5ab"));
			gestioneRegistrazione.registrazioneUtente("cip.ciop@gmail.com", "pippo", "cip", "cioppo", null, abilita);
			
		} catch (HashingException e) {
			fail("HashingException: " + e);
		} catch (ProposteException e) {
			fail("ProposteException: " + e);
		}
	}
	
	@Test
	public void elencoAbilitaGenerali(){
		List<Abilita> ab = new ArrayList<Abilita>();
		ab.add(new Abilita("1ab","Descrizione"));
		ab.add(new Abilita("2ab","Descrizione"));
		ab.add(new Abilita("3ab","Descrizione"));
		ab.add(new Abilita("4ab","Descrizione"));
		ab.add(new Abilita("5ab","Descrizione"));
		ab.add(new Abilita("6ab","Descrizione"));
		ab.add(new Abilita("7ab","Descrizione"));
		ab.add(new Abilita("8ab","Descrizione"));
		for (Abilita abilita : ab) {
			Assert.assertTrue(gestioneRicerche.insiemeAbilitaGenerali().contains(abilita));
		}
		Assert.assertTrue(gestioneRicerche.insiemeAbilitaGenerali().size()==ab.size());
		
	}
	
	@Test
	public void ricercaUtenti() throws RicercheException
	{
		Utente u=gestioneRicerche.getUtenteByEmail("peppino@gmail.com");
		Assert.assertTrue(gestioneRicerche.ricercaUtenti("Peppino", "Peppo","bulla.jacopo@gmail.com").contains(u));
		Assert.assertFalse(gestioneRicerche.ricercaUtenti("Jacopo", "Bulla","bulla.jacopo@gmail.com").contains(u));
		try{
			gestioneRicerche.ricercaUtenti("", "Peppo","bulla.jacopo@gmail.com");
			Assert.assertTrue(false);
		}
		catch(RicercheException ex)
		{
			Assert.assertTrue(true);
		}
		try{
			gestioneRicerche.ricercaUtenti("Peppino", "","bulla.jacopo@gmail.com");
			Assert.assertTrue(false);
		}
		catch(RicercheException ex)
		{
			Assert.assertTrue(true);
		}
		try{
			gestioneRicerche.ricercaUtenti("", "","bulla.jacopo@gmail.com");
			Assert.assertTrue(false);
		}
		catch(RicercheException ex)
		{
			Assert.assertTrue(true);
		}
	}
	
	@Test
	public void elencoAbilitaPersonaliUtente() throws RicercheException
	{
		List<Abilita> ab = new ArrayList<Abilita>();
		ab.add(new Abilita("1ab","Descrizione"));
		ab.add(new Abilita("2ab","Descrizione"));
		Assert.assertTrue(gestioneRicerche.insiemeAbilitaPersonaliUtente("tommaso.ganelli@gmail.com").contains(ab.get(0)));
		Assert.assertTrue(gestioneRicerche.insiemeAbilitaPersonaliUtente("tommaso.ganelli@gmail.com").contains(ab.get(1)));
		try{
			gestioneRicerche.insiemeAbilitaPersonaliUtente("");
			Assert.assertTrue(false);
		}
		catch(RicercheException ex)
		{
			Assert.assertTrue(true);
		}
	}
	
	@Test
	public void elencoUtentiByAbilita() throws RicercheException
	{
		List<Abilita> ab = new ArrayList<Abilita>();
		ab.add(new Abilita("1ab","Descrizione"));
		ab.add(new Abilita("2ab","Descrizione"));
		List<Utente> utenti= new ArrayList<Utente>();
		utenti.add(gestioneRicerche.getUtenteByEmail("tommaso.ganelli@gmail.com"));
		utenti.add(gestioneRicerche.getUtenteByEmail("tommaso.ficcanaso@gmail.com"));
		utenti.add(gestioneRicerche.getUtenteByEmail("cip.ciop@gmail.com"));
		Assert.assertTrue(gestioneRicerche.ricercaAiutoVisitatore(ab).contains(utenti.get(0)));
		Assert.assertTrue(gestioneRicerche.ricercaAiutoVisitatore(ab).contains(utenti.get(1)));
		Assert.assertTrue(gestioneRicerche.ricercaAiutoVisitatore(ab).contains(utenti.get(2)));
		ab.add(new Abilita("3ab","Descrizione"));
		ab.add(new Abilita("5ab","Descrizione"));
		Assert.assertFalse(gestioneRicerche.ricercaAiutoVisitatore(ab).contains(utenti.get(0)));
		Assert.assertFalse(gestioneRicerche.ricercaAiutoVisitatore(ab).contains(utenti.get(1)));
		Assert.assertTrue(gestioneRicerche.ricercaAiutoVisitatore(ab).contains(utenti.get(2)));
		Assert.assertTrue(gestioneRicerche.ricercaAiutoVisitatore(ab).size()==1);
		ab.add(new Abilita("8ab","Descrizione"));
		Assert.assertTrue(gestioneRicerche.ricercaAiutoVisitatore(ab).size()==0);
		List<Abilita> abilita = new ArrayList<Abilita>();
		try{
			gestioneRicerche.ricercaAiutoVisitatore(abilita);
			Assert.assertTrue(false);
		}
		catch(RicercheException ex)
		{
			Assert.assertTrue(true);
		}
	}
	
}
