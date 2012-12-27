package test;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import org.junit.Test;

import utililies.sessionRemote.GestioneRicercheRemote;

public class RicercheTest {
	private GestioneRicercheRemote gestioneRicerche;

	public RicercheTest() throws NamingException {
		Properties env = new Properties();
		env.setProperty("java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory");
		env.setProperty("java.naming.provider.url", "localhost:1099");
		env.setProperty("java.naming.factory.url.pkgs", "org.jboss.naming");

		Object obj = (new InitialContext(env)).lookup("SWIMv2_EAR/GestioneLogin/remote-utililies.sessionRemote.GestioneRicercheRemote");
		gestioneRicerche = (GestioneRicercheRemote) PortableRemoteObject.narrow(obj, GestioneRicercheRemote.class);

	}
}
