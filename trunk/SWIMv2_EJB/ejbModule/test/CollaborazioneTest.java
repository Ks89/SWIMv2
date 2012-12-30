package test;

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

public class CollaborazioneTest {

	private static GestioneCollaborazioniRemote gestioneCollaborazioni;

	private static final String MAIL_PEPPINO = "peppino@gmail.com";
	private static final String MAIL_GIOVANNINO = "giovannino@gmail.com";
	private static final String MAIL_DAVIDE = "davide@gmail.com";

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
	public void testRichiediAiuto() {
		try {
			Collaborazione collaborazioneAppenaInserita1 = gestioneCollaborazioni.richiediAiuto(MAIL_PEPPINO, MAIL_DAVIDE, "d44 sfsdfsd", "fdgdsgsdgsdd");

			Collaborazione collaborazioneAppenaInserita2 = gestioneCollaborazioni.richiediAiuto(MAIL_GIOVANNINO, MAIL_PEPPINO, "colNuova2", "collaborzione inserita in attesa di risposta2");

			Collaborazione collaborazioneDaDb1 = gestioneCollaborazioni.getCollaborazione(collaborazioneAppenaInserita1.getId());

			Collaborazione collaborazioneDaDb2 = gestioneCollaborazioni.getCollaborazione(collaborazioneAppenaInserita2.getId());

			assertTrue(collaborazioneDaDb1.toString().equals(collaborazioneAppenaInserita1.toString()));
			assertTrue(collaborazioneDaDb2.toString().equals(collaborazioneAppenaInserita2.toString()));
		} catch (CollaborazioneException e) {
			fail(e.getCausa().name());
		} catch (LoginException e) {
			fail(e.getCausa().name());
		}
	}

	@Test
	public void testAccettaCollaborazione() {
		try {
			// inserisco una collaborazione
			Collaborazione collaborazioneAppenaInserita1 = gestioneCollaborazioni.richiediAiuto(MAIL_PEPPINO, MAIL_DAVIDE, "dsfsdfsd", "fdgdsgsdgsdd");

			// rileggo inserimento
			Collaborazione collaborazioneDaDb1 = gestioneCollaborazioni.getCollaborazione(collaborazioneAppenaInserita1.getId());

			// la accetto
			gestioneCollaborazioni.accettaCollaborazione(collaborazioneDaDb1.getId());

			// rileggo dopo aver accettato
			Collaborazione collaborazioneDaDbAccettata = gestioneCollaborazioni.getCollaborazione(collaborazioneAppenaInserita1.getId());

			assertTrue(collaborazioneDaDbAccettata.getDataStipula() != null);
		} catch (CollaborazioneException e) {
			fail(e.getCausa().name());
		} catch (LoginException e) {
			fail(e.getCausa().name());
		}
	}

	@Test
	public void testRilasciaFeedback() {
		try {
			// inserisco una collaborazione
			Collaborazione collaborazioneAppenaInserita1 = gestioneCollaborazioni.richiediAiuto(MAIL_PEPPINO, MAIL_DAVIDE, "dsfsdfsd", "fdgdsgsdgsdd");
			assertTrue(collaborazioneAppenaInserita1!=null);
			
			// rileggo inserimento
			Collaborazione collaborazioneDaDb1 = gestioneCollaborazioni.getCollaborazione(collaborazioneAppenaInserita1.getId());
			assertTrue(collaborazioneDaDb1!=null);
			
			//stipolo e poi termino una collaborazione, altrimenti non potrei rilasciare il feedback
			Collaborazione collaborazioneAccettata = gestioneCollaborazioni.accettaCollaborazione(collaborazioneDaDb1.getId());
			Collaborazione collaborazioneTerminata = gestioneCollaborazioni.terminaCollaborazione(collaborazioneDaDb1.getId());
			assertTrue(collaborazioneAccettata!=null && collaborazioneTerminata!=null);
			
			// rilascio il feedback
			Collaborazione collaborazioneFbRilasciato = gestioneCollaborazioni.rilasciaFeedback(collaborazioneDaDb1.getId(), 3, "bravo bravo, very good");
			assertTrue(collaborazioneFbRilasciato!=null);

			// rileggo feedback rilasciato
			Collaborazione collaborazioneDaDb2 = gestioneCollaborazioni.getCollaborazione(collaborazioneAppenaInserita1.getId());
			assertTrue(collaborazioneDaDb2!=null);

			//nota bene il commento feedback puo' anche essere null, non e' obbligatiorio, ma in questo test, provo il caso in cui sia stato inserito
			//quindi nell'assert devo verificare che sia stato inserito davvero
			assertTrue(collaborazioneDaDb2.getCommentoFeedback() != null && collaborazioneDaDb2.getPunteggioFeedback() == 3);
		} catch (CollaborazioneException e) {
			fail(e.getCausa().name());
		} catch (LoginException e) {
			fail(e.getCausa().name());
		}
	}

	@Test
	public void testRifiutaCollaborazione() {
		try {
			// inserisco una collaborazione
			Collaborazione collaborazioneAppenaInserita1 = gestioneCollaborazioni.richiediAiuto(MAIL_PEPPINO, MAIL_DAVIDE, "dsfsdfsd", "fdgdsgsdgsdd");

			// rileggo inserimento
			Collaborazione collaborazioneDaDb1 = gestioneCollaborazioni.getCollaborazione(collaborazioneAppenaInserita1.getId());

			// la rifiuto
			gestioneCollaborazioni.rifiutaCollaborazione(collaborazioneDaDb1.getId());

			// la rileggo dopo averla rifiutata
			Collaborazione rimossa = gestioneCollaborazioni.getCollaborazione(collaborazioneDaDb1.getId());

			assertTrue(rimossa == null);
		} catch (CollaborazioneException e) {
			fail(e.getCausa().name());
		} catch (LoginException e) {
			fail(e.getCausa().name());
		}
	}

	@Test
	public void testGetPunteggioFeedback() {
		try {
			// inserisco delle collaborazioni di cui 4 accettate (ricevute) da
			// peppino
			Collaborazione collaborazione1 = gestioneCollaborazioni.richiediAiuto(MAIL_DAVIDE, MAIL_PEPPINO, "vdbvsd", "ewgwe");
			Collaborazione collaborazione2 = gestioneCollaborazioni.richiediAiuto(MAIL_DAVIDE, MAIL_GIOVANNINO, "bdb", "fgweg");
			Collaborazione collaborazione3 = gestioneCollaborazioni.richiediAiuto(MAIL_GIOVANNINO, MAIL_PEPPINO, "rbrw", "ehweh");
			gestioneCollaborazioni.richiediAiuto(MAIL_GIOVANNINO, MAIL_DAVIDE, "vegq", "bsdg");
			Collaborazione collaborazione5 = gestioneCollaborazioni.richiediAiuto(MAIL_DAVIDE, MAIL_PEPPINO, "dsfsdfsd", "ewgwe");

			// vengono stipulate dagli utenti riceventi tutte le collaborazioni
			// eccetto la 4 (per fare un esempio generico)
			gestioneCollaborazioni.accettaCollaborazione(collaborazione1.getId());
			gestioneCollaborazioni.accettaCollaborazione(collaborazione2.getId());
			gestioneCollaborazioni.accettaCollaborazione(collaborazione3.getId());
			gestioneCollaborazioni.accettaCollaborazione(collaborazione5.getId());

			// termino le collaborazioni stipulate
			gestioneCollaborazioni.terminaCollaborazione(collaborazione1.getId());
			gestioneCollaborazioni.terminaCollaborazione(collaborazione2.getId());
			gestioneCollaborazioni.terminaCollaborazione(collaborazione3.getId());
			gestioneCollaborazioni.terminaCollaborazione(collaborazione5.getId());

			// rilascio il feedback per tutte le collaborazioni stipulate e
			// soprattutto terminate
			gestioneCollaborazioni.rilasciaFeedback(collaborazione1.getId(), 3, "asasasa");
			gestioneCollaborazioni.rilasciaFeedback(collaborazione2.getId(), 2, "dssdg");
			gestioneCollaborazioni.rilasciaFeedback(collaborazione3.getId(), 1, "gvrsgvs");
			gestioneCollaborazioni.rilasciaFeedback(collaborazione5.getId(), 4, "regh");

			Double punteggio = gestioneCollaborazioni.getPunteggioFeedback(MAIL_PEPPINO);

			System.out.println("Punteggio feedback test= " + punteggio);
			// quindi il punteggio dell'utente MAIL_PEPPINO dovrebbe risultare
			// 3+1+4/3 = 2.6667
			assertTrue(punteggio == 2.6667);
		} catch (CollaborazioneException e) {
			fail(e.getCausa().name());
		} catch (LoginException e) {
			fail(e.getCausa().name());
		}
	}

	@Test
	public void testGetCollaborazioniFeedbackNonRilasciato() {
		try {
			// inserisco delle collaborazioni
			Collaborazione collaborazioneAppenaInserita1 = gestioneCollaborazioni.richiediAiuto(MAIL_PEPPINO, MAIL_DAVIDE, "vdbvsd", "ewgwe");
			gestioneCollaborazioni.richiediAiuto(MAIL_PEPPINO, MAIL_DAVIDE, "bdb", "fgweg");
			Collaborazione collaborazioneAppenaInserita3 = gestioneCollaborazioni.richiediAiuto(MAIL_GIOVANNINO, MAIL_PEPPINO, "rbrw", "ehweh");
			gestioneCollaborazioni.richiediAiuto(MAIL_GIOVANNINO, MAIL_DAVIDE, "vegq", "bsdg");
			Collaborazione collaborazioneAppenaInserita5 = gestioneCollaborazioni.richiediAiuto(MAIL_PEPPINO, MAIL_DAVIDE, "dsfsdfsd", "ewgwe");

			// stipulo 3 collaborazioni
			gestioneCollaborazioni.accettaCollaborazione(collaborazioneAppenaInserita1.getId());
			gestioneCollaborazioni.accettaCollaborazione(collaborazioneAppenaInserita3.getId());
			gestioneCollaborazioni.accettaCollaborazione(collaborazioneAppenaInserita5.getId());

			// termino le 3 collaborazioni, ma non rilascio il feedback
			gestioneCollaborazioni.terminaCollaborazione(collaborazioneAppenaInserita1.getId());
			gestioneCollaborazioni.terminaCollaborazione(collaborazioneAppenaInserita3.getId());
			gestioneCollaborazioni.terminaCollaborazione(collaborazioneAppenaInserita5.getId());

			// ora ottengo le collaborazioni create da MAIL_PEPPINO di per cui
			// non
			// e' ancora stato rilasciato il feedback
			List<Collaborazione> collaborazioni = gestioneCollaborazioni.getCollaborazioniCreateFeedbackNonRilasciato(MAIL_PEPPINO);

			assertTrue(collaborazioni.size() >= 2);
		} catch (CollaborazioneException e) {
			fail(e.getCausa().name());
		} catch (LoginException e) {
			fail(e.getCausa().name());
		}
	}

	@Test
	public void testGetCollaborazioniCreate() {
		try {
			// inserisco delle collaborazioni
			gestioneCollaborazioni.richiediAiuto(MAIL_PEPPINO, MAIL_DAVIDE, "vdbvsd", "ewgwe");
			gestioneCollaborazioni.richiediAiuto(MAIL_PEPPINO, MAIL_DAVIDE, "bdb", "fgweg");
			gestioneCollaborazioni.richiediAiuto(MAIL_GIOVANNINO, MAIL_PEPPINO, "rbrw", "ehweh");
			gestioneCollaborazioni.richiediAiuto(MAIL_GIOVANNINO, MAIL_DAVIDE, "vegq", "bsdg");
			gestioneCollaborazioni.richiediAiuto(MAIL_PEPPINO, MAIL_DAVIDE, "dsfsdfsd", "ewgwe");

			// ora ottengo le collaborazioni create da MAIL_PEPPINO
			// indipendentemente se terminate, stipulate, in corso ecc...
			List<Collaborazione> collaborazioni = gestioneCollaborazioni.getCollaborazioniCreate(MAIL_PEPPINO);
			assertTrue(collaborazioni.size() >= 2);
		} catch (CollaborazioneException e) {
			fail(e.getCausa().name());
		} catch (LoginException e) {
			fail(e.getCausa().name());
		}
	}

	@Test
	public void testGetCollaborazioniAccettate() {
		try {
			// inserisco delle collaborazioni
			Collaborazione collaborazioneAppenaInserita1 = gestioneCollaborazioni.richiediAiuto(MAIL_PEPPINO, MAIL_DAVIDE, "vdbvsd", "ewgwe");
			gestioneCollaborazioni.richiediAiuto(MAIL_PEPPINO, MAIL_DAVIDE, "bdb", "fgweg");
			Collaborazione collaborazioneAppenaInserita3 = gestioneCollaborazioni.richiediAiuto(MAIL_GIOVANNINO, MAIL_PEPPINO, "rbrw", "ehweh");
			gestioneCollaborazioni.richiediAiuto(MAIL_GIOVANNINO, MAIL_DAVIDE, "vegq", "bsdg");
			gestioneCollaborazioni.richiediAiuto(MAIL_DAVIDE, MAIL_PEPPINO, "dsfsdfsd", "ewgwe");

			// poiche' 2 di esse sono ricevute da MAIL_PEPPINO, il risulato del
			// metodo dovrebbe avere 2 collaborazioni che escono dalla query

			// stipulo 2 collaborazioni uno accettata da MAIL_PEPPINO e una da
			// MAIL_DAVIDE, quindi il risultato viene ridotto a una sola tupla
			gestioneCollaborazioni.accettaCollaborazione(collaborazioneAppenaInserita1.getId());
			gestioneCollaborazioni.accettaCollaborazione(collaborazioneAppenaInserita3.getId());

			// ora voglio ottenere le collaborazioni STIPULATE in cui l'utente
			// ricevente e' MAIL_PEPPINO, cioe' quelle che lui ha accettato di
			// fare
			// e visto che di quelle che ha stipulato ne ha accettata una sola
			// avra'
			// un solo elemento, cioe' la collaborazione di nome "rbrw".
			List<Collaborazione> collaborazioni = gestioneCollaborazioni.getCollaborazioniAccettate(MAIL_PEPPINO);

			// perche' la query dara' un solo elemento (o piu' nel caso esegua piu' volte i test)
			assertTrue(collaborazioni.size() >= 1); 
		} catch (CollaborazioneException e) {
			fail(e.getCausa().name());
		} catch (LoginException e) {
			fail(e.getCausa().name());
		}
	}
}
