/*
Copyright 2012-2015 Stefano Cappa, Jacopo Bulla, Davide Caio
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
    http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import org.junit.BeforeClass;
import org.junit.Test;

import entityBeans.Abilita;
import entityBeans.PropostaAbilita;
import exceptions.ProposteException;

import utililies.sessionRemote.GestioneProposteRemote;

public class PropostaAbilitaTest {

	private static final String MAIL_PEPPINO = "peppino@gmail.com";
	private static final String MAIL_GIOVANNINO = "giovannino@gmail.com";
	private static final String MAIL_DAVIDE = "davide@gmail.com";
	private static final String MAIL_ADMIN = "admin@swim.it";
	private static final String NOME_ABILITA1 = "nomeAbilita1";
	private static final String NOME_ABILITA2 = "nomeAbilita2";
	private static final String NOME_ABILITA3 = "nomeAbilita3";
	private static final String NOME_ABILITA4 = "nomeAbilita4";
	private static final String NOME_ABILITA5 = "nomeAbilita5";


	private static GestioneProposteRemote gestioneProposte;
	private static TestUtilsRemote testUtils;

	@BeforeClass
	public static void setUpTestCollaborazione(){
		Properties env = new Properties();
		env.setProperty("java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory");
		env.setProperty("java.naming.provider.url", "localhost:1099");
		env.setProperty("java.naming.factory.url.pkgs", "org.jboss.naming");

		try {
			Object obj = (new InitialContext(env)).lookup("SWIMv2_EAR/GestioneProposte/remote-utililies.sessionRemote.GestioneProposteRemote");
			gestioneProposte = (GestioneProposteRemote) PortableRemoteObject.narrow(obj, GestioneProposteRemote.class);

			obj = (new InitialContext(env)).lookup("SWIMv2_EAR/TestUtils/remote-test.TestUtilsRemote");
			testUtils = (TestUtilsRemote) PortableRemoteObject.narrow(obj, TestUtilsRemote.class);
		} catch (NamingException e) {
			System.out.println(e.toString());
			return;
		}
	}

	@Test
	public void testGetProposteAbilitaNonConfermate() {
		testUtils.svuotaTabellaDatabase("PropostaAbilita");
		testUtils.svuotaTabellaDatabase("Possiede");
		testUtils.svuotaTabellaDatabase("Abilita");

		try {
			//inserisco delle proposteabilita
			PropostaAbilita propostaAppenaInserita1 = gestioneProposte.inserisciPropostaUtente(MAIL_PEPPINO, NOME_ABILITA1, "sadada");
			gestioneProposte.inserisciPropostaUtente(MAIL_PEPPINO, NOME_ABILITA2, "bdb");
			PropostaAbilita propostaAppenaInserita3 = gestioneProposte.inserisciPropostaUtente(MAIL_GIOVANNINO, NOME_ABILITA3, "rbrw");
			PropostaAbilita propostaAppenaInserita4 = gestioneProposte.inserisciPropostaUtente(MAIL_GIOVANNINO, NOME_ABILITA4, "vegq");
			gestioneProposte.inserisciPropostaUtente(MAIL_DAVIDE, NOME_ABILITA5, "dsfsdfsd");

			//l'admin conferma 3 proposte
			gestioneProposte.approvaPropostaAbilita(propostaAppenaInserita1.getId(), "descrizioneAbilita");
			gestioneProposte.approvaPropostaAbilita(propostaAppenaInserita3.getId(), "descrizioneasdasAbilita");
			gestioneProposte.approvaPropostaAbilita(propostaAppenaInserita4.getId(), "descriziosadneAbilita");

			//ottengo lista delle proposte (tra tutte quella mandate e non solo di uno specifico utente) ancora non approvate, cioe' la 2 e la 5
			List<PropostaAbilita> proposte = gestioneProposte.getProposteAbilitaNonConfermate();

			assertTrue(proposte.size()==2);
		} catch (ProposteException e) {
			fail("ProposteException: " + e);
		}
	}

	@Test
	public void testGetProposteAbilitaConfermate() {
		testUtils.svuotaTabellaDatabase("PropostaAbilita");
		testUtils.svuotaTabellaDatabase("Possiede");
		testUtils.svuotaTabellaDatabase("Abilita");

		try {
			//inserisco delle proposteabilita
			PropostaAbilita propostaAppenaInserita1 = gestioneProposte.inserisciPropostaUtente(MAIL_PEPPINO, NOME_ABILITA1, "sadada");
			gestioneProposte.inserisciPropostaUtente(MAIL_PEPPINO, NOME_ABILITA2, "bdb");
			gestioneProposte.inserisciPropostaUtente(MAIL_GIOVANNINO, NOME_ABILITA3, "rbrw");

			//l'admin conferma solo una proposta
			gestioneProposte.approvaPropostaAbilita(propostaAppenaInserita1.getId(), "descrizioneAbilita");

			//ottengo lista delle proposte (tra tutte quella mandate e non solo di uno specifico utente) gia' approvate, cioe' la 1
			List<PropostaAbilita> proposte = gestioneProposte.getProposteAbilitaConfermate();

			assertTrue(proposte.size()==1);
		} catch (ProposteException e) {
			fail("ProposteException: " + e);
		}

	}

	@Test
	public void testApprovaPropostaAbilita() {
		testUtils.svuotaTabellaDatabase("PropostaAbilita");
		testUtils.svuotaTabellaDatabase("Possiede");
		testUtils.svuotaTabellaDatabase("Abilita");

		try {
			PropostaAbilita propostaAppenaInserita1 = gestioneProposte.inserisciPropostaUtente(MAIL_PEPPINO, NOME_ABILITA1, "fsdsgsd");
			gestioneProposte.approvaPropostaAbilita(propostaAppenaInserita1.getId(), "abilita nuova appena inserita");

			PropostaAbilita propostaRiletta = gestioneProposte.getPropostaAbilitaById(propostaAppenaInserita1.getId());

			assertTrue(propostaRiletta.getDataAccettazione()!=null);
		} catch (ProposteException e) {
			fail("ProposteException: " + e);
		}
	}


	@Test
	public void testRifiutaPropostaAbilita() {
		testUtils.svuotaTabellaDatabase("PropostaAbilita");
		testUtils.svuotaTabellaDatabase("Possiede");
		testUtils.svuotaTabellaDatabase("Abilita");

		try {
			PropostaAbilita propostaAppenaInserita1 = gestioneProposte.inserisciPropostaUtente(MAIL_GIOVANNINO, NOME_ABILITA1, "ssafaf");
			gestioneProposte.rifiutaPropostaAbilita(propostaAppenaInserita1.getId());

			PropostaAbilita propostaRiletta = gestioneProposte.getPropostaAbilitaById(propostaAppenaInserita1.getId());

			assertTrue(propostaRiletta==null);
		} catch (ProposteException e) {
			fail("ProposteException: " + e);
		}
	}


	@Test
	public void testInserisciAbilitaAutonomamente() {
		testUtils.svuotaTabellaDatabase("PropostaAbilita");
		testUtils.svuotaTabellaDatabase("Possiede");
		testUtils.svuotaTabellaDatabase("Abilita");

		try {
			Abilita abilitaInserita = gestioneProposte.inserisciAbilitaAutonomamente(MAIL_ADMIN, NOME_ABILITA1, "fsdsgsd");

			Abilita abilitaAppenaInseritaRiletta = gestioneProposte.getAbilitaByNome(NOME_ABILITA1);

			assertEquals(abilitaInserita,abilitaAppenaInseritaRiletta);
		} catch (ProposteException e) {
			fail("ProposteException: " + e);
		}
	}


	@Test
	public void testConfermaPropostaAbilitaSpecificandoAttributi() {
		//TODO METTERE QUI IL TEST DEL METODO confermaPropostaAbilitaSpecificandoAttributi
	}

}
