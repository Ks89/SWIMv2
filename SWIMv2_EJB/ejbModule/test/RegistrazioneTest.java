package test;

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

	public RegistrazioneTest() throws NamingException {
		Properties env = new Properties();
		env.setProperty("java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory");
		env.setProperty("java.naming.provider.url", "localhost:1099");
		env.setProperty("java.naming.factory.url.pkgs", "org.jboss.naming");

		//non cancellarla, da tenere per quando sistemiamo i progetti e li mettiamo su google code
		//	 	Object obj = (new InitialContext(env)).lookup("SWIMv2_EAR/GestioneCollaborazioni/remote-utililies.sessionRemote.GestioneCollaborazioniRemote");
		Object obj = (new InitialContext(env)).lookup("GestioneRegistrazione/remote-utililies.sessionRemote.GestioneRegistrazioneRemote");
		gestioneRegistrazione = (GestioneRegistrazioneRemote) PortableRemoteObject.narrow(obj, GestioneRegistrazioneRemote.class);


	}

	@SuppressWarnings("null")//non so se serve, me lo consigliava eclipse
	@Test
	public void EseguiRegistrazioneUtente(){
		List<Abilita> abilita = null;
		Abilita temp=new Abilita();
		//Non ci si può registrare senza almeno una abilità
		Assert.assertFalse(gestioneRegistrazione.registrazioneUtente("bulla.jacopo@gmail.com", "password", "Enrico", "Rossi", null, abilita));
		temp.setDescrizione("1° Abilità");
		temp.setNome("1ab");
		abilita.add(temp);
		//Registrazione a buon fine con una abilità
		Assert.assertTrue(gestioneRegistrazione.registrazioneUtente("bulla.jacopo@gmail.com", "password", "Jacopo", "Bulla", null, abilita));
		//Non ci possono essere due utenti con la stessa mail
		Assert.assertFalse(gestioneRegistrazione.registrazioneUtente("bulla.jacopo@gmail.com", "password", "Andrea", "Bazzi", null, abilita));
		temp.setDescrizione("1° Abilità");
		temp.setNome("1ab");
		abilita.add(temp);
		//Registrazione a buon fine con due abilità
		Assert.assertTrue(gestioneRegistrazione.registrazioneUtente("tommaso.ganelli@gmail.com", "password", "Tommaso", "Ganelli", null, abilita));
		
	}
}
