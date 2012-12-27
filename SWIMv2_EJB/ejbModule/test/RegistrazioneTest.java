package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import junit.framework.Assert;

import org.junit.Test;

import entityBeans.Abilita;
import utililies.sessionRemote.GestioneRegistrazioneRemote;

public class RegistrazioneTest {

	private GestioneRegistrazioneRemote gestioneRegistrazione;
	private static TestUtilsRemote testUtils;
	
	public RegistrazioneTest() throws NamingException {
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
		}
		catch (NamingException e) {
			System.out.println(e.toString());
			return;
		}
	}

	
	@Test
	public void eseguiRegistrazioneUtente(){
		testUtils.svuotaTabellaDatabase("Utente");
		List<Abilita> abilita = new ArrayList<Abilita>();
		Abilita temp = new Abilita();
		//Per eseguire questo test dovete inserire due abilita nel db. Una nome=1ab Descrizione=prima Abilita l'altra=2ab Descrizione=seconda Abilita 
		//Non ci si puo' registrare senza almeno una abilita
		Assert.assertFalse(gestioneRegistrazione.registrazioneUtente("bulla.jacopo@gmail.com", "password", "Enrico", "Rossi", null, abilita));
		temp.setDescrizione("prima Abilita");
		temp.setNome("1ab");
		abilita.add(temp);
		//Non ci si puo' registrare senza nome
		Assert.assertFalse(gestioneRegistrazione.registrazioneUtente("bulla.jacopo@gmail.com", "password", "", "Rossi", null, abilita));
		//Non ci si puo' registrare senza cognome
		Assert.assertFalse(gestioneRegistrazione.registrazioneUtente("bulla.jacopo@gmail.com", "password", "Enrico", "", null, abilita));
		//Non ci si puo' registrare senza password
		Assert.assertFalse(gestioneRegistrazione.registrazioneUtente("bulla.jacopo@gmail.com", "", "Enrico", "Rossi", null, abilita));
		//Registrazione a buon fine con una abilita
		Assert.assertTrue(gestioneRegistrazione.registrazioneUtente("bulla.jacopo@gmail.com", "password", "Jacopo", "Bulla", null, abilita));
		//Non ci possono essere due utenti con la stessa mail
		Assert.assertFalse(gestioneRegistrazione.registrazioneUtente("bulla.jacopo@gmail.com", "password", "Andrea", "Bazzi", null, abilita));
		temp.setDescrizione("seconda Abilita");
		temp.setNome("2ab");
		abilita.add(temp);
		//Registrazione a buon fine con due abilita
		Assert.assertTrue(gestioneRegistrazione.registrazioneUtente("tommaso.ganelli@gmail.com", "password", "Tommaso", "Ganelli", null, abilita));
		
	}
}
