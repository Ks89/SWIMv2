package test;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import org.junit.Test;

import utililies.sessionRemote.GestioneCollaborazioniRemote;
import entityBeans.Collaborazione;

public class CollaborazioneTest {

	private GestioneCollaborazioniRemote gestioneCollaborazioni;

	public CollaborazioneTest() throws NamingException {
		Properties env = new Properties();
		env.setProperty("java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory");
		env.setProperty("java.naming.provider.url", "localhost:1099");
		env.setProperty("java.naming.factory.url.pkgs", "org.jboss.naming");

		//non cancellarla, da tenere per quando sistemiamo i progetti e li mettiamo su google code
		//	 	Object obj = (new InitialContext(env)).lookup("SWIMv2_EAR/GestioneCollaborazioni/remote-utililies.sessionRemote.GestioneCollaborazioniRemote");
		Object obj = (new InitialContext(env)).lookup("GestioneCollaborazioni/remote-utililies.sessionRemote.GestioneCollaborazioniRemote");
		gestioneCollaborazioni = (GestioneCollaborazioniRemote) PortableRemoteObject.narrow(obj, GestioneCollaborazioniRemote.class);

	}

	/**
	 * Test che inserisce 2 collaborazioni e poi verifica se nel db esse siano state effettivamente aggiunte.
	 */
	@Test
	public void testRichiediAiuto() {
		Collaborazione collaborazioneAppenaInserita1 = gestioneCollaborazioni.richiediAiuto("peppino@gmail.com", "davide@gmail.com", "colNuova1", "collaborzione inserita in attesa di risposta1");
		Collaborazione collaborazioneAppenaInserita2 = gestioneCollaborazioni.richiediAiuto("giovannino@gmail.com", "peppino@gmail.com", "colNuova2", "collaborzione inserita in attesa di risposta2");

		Collaborazione collaborazioneDaDb1 = gestioneCollaborazioni.getCollaborazione(collaborazioneAppenaInserita1.getId());

		Collaborazione collaborazioneDaDb2 = gestioneCollaborazioni.getCollaborazione(collaborazioneAppenaInserita2.getId());

		assertTrue(collaborazioneDaDb1.toString().equals(collaborazioneAppenaInserita1.toString()));
		assertTrue(collaborazioneDaDb2.toString().equals(collaborazioneAppenaInserita2.toString()));
	}

	@Test
	public void testAccettaCollaborazione() {
		//inserisco una collaborazione
		Collaborazione collaborazioneAppenaInserita1 = gestioneCollaborazioni.richiediAiuto("peppino@gmail.com", "davide@gmail.com", "dsfsdfsd", "fdgdsgsdgsdd");

		//rileggo inserimento
		Collaborazione collaborazioneDaDb1 = gestioneCollaborazioni.getCollaborazione(collaborazioneAppenaInserita1.getId());

		//la accetto
		gestioneCollaborazioni.accettaCollaborazione(collaborazioneDaDb1.getId());

		//rileggo dopo aver accettato
		Collaborazione collaborazioneDaDbAccettata = gestioneCollaborazioni.getCollaborazione(collaborazioneAppenaInserita1.getId());

		assertTrue(collaborazioneDaDbAccettata.getDataStipula()!=null);
	}

	@Test
	public void testRilasciaFeedback() {
		//inserisco una collaborazione
		Collaborazione collaborazioneAppenaInserita1 = gestioneCollaborazioni.richiediAiuto("peppino@gmail.com", "davide@gmail.com", "dsfsdfsd", "fdgdsgsdgsdd");

		//rileggo inserimento
		Collaborazione collaborazioneDaDb1 = gestioneCollaborazioni.getCollaborazione(collaborazioneAppenaInserita1.getId());

		//rilascio il feedback
		gestioneCollaborazioni.rilasciaFeedback(collaborazioneDaDb1.getId(), 3 , "bravo bravo, very good");

		//rileggo feedback rilasciato
		Collaborazione collaborazioneDaDb2 = gestioneCollaborazioni.getCollaborazione(collaborazioneAppenaInserita1.getId());

		assertTrue(collaborazioneDaDb2.getPunteggioFeedback()==3 && collaborazioneDaDb2.getCommentoFeedback()!=null);
	}

	@Test
	public void testRifiutaCollaborazione() {
		//inserisco una collaborazione
		Collaborazione collaborazioneAppenaInserita1 = gestioneCollaborazioni.richiediAiuto("peppino@gmail.com", "davide@gmail.com", "dsfsdfsd", "fdgdsgsdgsdd");

		//rileggo inserimento
		Collaborazione collaborazioneDaDb1 = gestioneCollaborazioni.getCollaborazione(collaborazioneAppenaInserita1.getId());

		//la rifiuto
		gestioneCollaborazioni.rifiutaCollaborazione(collaborazioneDaDb1.getId());

		//la rileggo dopo averla rifiutata
		Collaborazione rimossa = gestioneCollaborazioni.getCollaborazione(collaborazioneDaDb1.getId());
		assertTrue(rimossa==null);
	}

	@Test
	public void testGetPunteggioFeedback() {
		//inserisco delle collaborazioni di cui 4 accettate (ricevute) da peppino
		Collaborazione collaborazione1 = gestioneCollaborazioni.richiediAiuto("davide@gmail.com","peppino@gmail.com", "vdbvsd", "ewgwe");
		Collaborazione collaborazione2 = gestioneCollaborazioni.richiediAiuto("davide@gmail.com","giovannino@gmail.com", "bdb", "fgweg");
		Collaborazione collaborazione3 = gestioneCollaborazioni.richiediAiuto("giovannino@gmail.com", "peppino@gmail.com", "rbrw", "ehweh");
		gestioneCollaborazioni.richiediAiuto("giovannino@gmail.com", "davide@gmail.com", "vegq", "bsdg");
		Collaborazione collaborazione5 = gestioneCollaborazioni.richiediAiuto("davide@gmail.com","peppino@gmail.com", "dsfsdfsd", "ewgwe");

		//vengono stipulate dagli utenti riceventi tutte le collaborazioni eccetto la 4 (per fare un esempio generico)
		gestioneCollaborazioni.accettaCollaborazione(collaborazione1.getId());
		gestioneCollaborazioni.accettaCollaborazione(collaborazione2.getId());
		gestioneCollaborazioni.accettaCollaborazione(collaborazione3.getId());
		gestioneCollaborazioni.accettaCollaborazione(collaborazione5.getId());

		//termino le collaborazioni stipulate
		gestioneCollaborazioni.terminaCollaborazione(collaborazione1.getId());
		gestioneCollaborazioni.terminaCollaborazione(collaborazione2.getId()); 
		gestioneCollaborazioni.terminaCollaborazione(collaborazione3.getId());
		gestioneCollaborazioni.terminaCollaborazione(collaborazione5.getId());

		//rilascio il feedback per tutte le collaborazioni stipulate e soprattutto terminate
		gestioneCollaborazioni.rilasciaFeedback(collaborazione1.getId(), 3, "asasasa"); //e' quella che uso per fare la media del punteggioFB
		gestioneCollaborazioni.rilasciaFeedback(collaborazione2.getId(), 2, "dssdg");
		gestioneCollaborazioni.rilasciaFeedback(collaborazione3.getId(), 1, "gvrsgvs"); //e' quella che uso per fare la media del punteggioFB
		gestioneCollaborazioni.rilasciaFeedback(collaborazione5.getId(), 4, "regh"); //e' quella che uso per fare la media del punteggioFB

		Double punteggio = gestioneCollaborazioni.getPunteggioFeedback("peppino@gmail.com"); //usare il Double perche' avg nella query da un Double

		System.out.println("Punteggio feedback test= " + punteggio);
		//quindi il punteggio dell'utente peppino@gmail.com dovrebbe risultare 3+1+4/3 = 2.6667
		assertTrue(punteggio==2.6667);
	}

	@Test
	public void testGetCollaborazioniFeedbackNonRilasciato() {
		//inserisco delle collaborazioni
		Collaborazione collaborazioneAppenaInserita1 = gestioneCollaborazioni.richiediAiuto("peppino@gmail.com", "davide@gmail.com", "vdbvsd", "ewgwe");
		gestioneCollaborazioni.richiediAiuto("peppino@gmail.com", "davide@gmail.com", "bdb", "fgweg");
		Collaborazione collaborazioneAppenaInserita3 = gestioneCollaborazioni.richiediAiuto("giovannino@gmail.com", "peppino@gmail.com", "rbrw", "ehweh");
		gestioneCollaborazioni.richiediAiuto("giovannino@gmail.com", "davide@gmail.com", "vegq", "bsdg");
		Collaborazione collaborazioneAppenaInserita5 = gestioneCollaborazioni.richiediAiuto("peppino@gmail.com", "davide@gmail.com", "dsfsdfsd", "ewgwe");

		//stipulo 3 collaborazioni
		gestioneCollaborazioni.accettaCollaborazione(collaborazioneAppenaInserita1.getId());
		gestioneCollaborazioni.accettaCollaborazione(collaborazioneAppenaInserita3.getId());
		gestioneCollaborazioni.accettaCollaborazione(collaborazioneAppenaInserita5.getId());

		//termino le 3 collaborazioni, ma non rilascio il feedback
		gestioneCollaborazioni.terminaCollaborazione(collaborazioneAppenaInserita1.getId());
		gestioneCollaborazioni.terminaCollaborazione(collaborazioneAppenaInserita3.getId());
		gestioneCollaborazioni.terminaCollaborazione(collaborazioneAppenaInserita5.getId());

		//ora ottengo le collaborazioni create da peppino@gmail.com di  per cui non e' ancora stato rilasciato il feedback
		List<Collaborazione> collaborazioni = gestioneCollaborazioni.getCollaborazioniCreateFeedbackNonRilasciato("peppino@gmail.com");

		assertTrue(collaborazioni.size()>=2);
	}

	@Test
	public void testGetCollaborazioniCreate() {
		//inserisco delle collaborazioni
		gestioneCollaborazioni.richiediAiuto("peppino@gmail.com", "davide@gmail.com", "vdbvsd", "ewgwe");
		gestioneCollaborazioni.richiediAiuto("peppino@gmail.com", "davide@gmail.com", "bdb", "fgweg");
		gestioneCollaborazioni.richiediAiuto("giovannino@gmail.com", "peppino@gmail.com", "rbrw", "ehweh");
		gestioneCollaborazioni.richiediAiuto("giovannino@gmail.com", "davide@gmail.com", "vegq", "bsdg");
		gestioneCollaborazioni.richiediAiuto("peppino@gmail.com", "davide@gmail.com", "dsfsdfsd", "ewgwe");

		//ora ottengo le collaborazioni create da peppino@gmail.com indipendentemente se terminate, stipulate, in corso ecc...
		List<Collaborazione> collaborazioni = gestioneCollaborazioni.getCollaborazioniCreate("peppino@gmail.com");
		assertTrue(collaborazioni.size()>=2);
	}

	@Test
	public void testGetCollaborazioniAccettate() {
		//inserisco delle collaborazioni
		Collaborazione collaborazioneAppenaInserita1 = gestioneCollaborazioni.richiediAiuto("peppino@gmail.com", "davide@gmail.com", "vdbvsd", "ewgwe");
		gestioneCollaborazioni.richiediAiuto("peppino@gmail.com", "davide@gmail.com", "bdb", "fgweg");
		Collaborazione collaborazioneAppenaInserita3 = gestioneCollaborazioni.richiediAiuto("giovannino@gmail.com", "peppino@gmail.com", "rbrw", "ehweh");
		gestioneCollaborazioni.richiediAiuto("giovannino@gmail.com", "davide@gmail.com", "vegq", "bsdg");
		gestioneCollaborazioni.richiediAiuto("davide@gmail.com", "peppino@gmail.com", "dsfsdfsd", "ewgwe");

		//poiche' 2 di esse sono ricevute da peppino@gmail.com, il risulato del metodo dovrebbe avere 2 collaborazioni che escono dalla query

		//stipulo 2 collaborazioni uno accettata da peppino@gmail.com e una da davide@gmail.com, quindi il risultato viene ridotto a una sola tupla
		gestioneCollaborazioni.accettaCollaborazione(collaborazioneAppenaInserita1.getId());
		gestioneCollaborazioni.accettaCollaborazione(collaborazioneAppenaInserita3.getId());

		//ora voglio ottenere le collaborazioni STIPULATE in cui l'utente ricevente e' peppino@gmail.com, cioe' quelle che lui ha accettato di fare
		//e visto che di quelle che ha stipulato ne ha accettata una sola avra' un solo elemento, cioe' la collaborazione di nome "rbrw".
		List<Collaborazione> collaborazioni = gestioneCollaborazioni.getCollaborazioniAccettate("peppino@gmail.com");

		System.out.println("Lista collaborazioni accettate (quindi stipulate) dal ricevente dataStipula");
		for(Collaborazione collaborazione : collaborazioni) {
			System.out.println(collaborazione); //stampa il toString dell'oggetto grazie a lombok
		}
		assertTrue(collaborazioni.size()>=1); //perche' la query dara' un solo elemento (o piu' nel caso esegua piu' volte i test)
	}
}
