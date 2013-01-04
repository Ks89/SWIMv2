package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


/**
 * Test completo iniziando dalla registrazione, per poi poter eseguire gli altri
 */

@RunWith(Suite.class)
@Suite.SuiteClasses(
		{
			RegistrazioneTest.class,
			LoginTest.class,
			RicercheTest.class,
			AmiciziaTest.class,
			CollaborazioneTest.class,
			PropostaAbilitaTest.class
		})

public class TestCompleto {}