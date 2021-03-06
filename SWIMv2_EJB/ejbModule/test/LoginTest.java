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

import static org.junit.Assert.fail;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import junit.framework.Assert;

import org.junit.Test;

import exceptions.HashingException;
import exceptions.LoginException;

import utililies.PasswordHasher;
import utililies.sessionRemote.GestioneLoginRemote;

public class LoginTest {

	private static final String MAIL_PEPPINO = "peppino@gmail.com";
	private static final String MAIL_UTENTE_INUTILE = "pippo@gmail.com";
	private static final String PASSWORD_INUTILE = "blabla";
	private static final String MAIL_AMMINISTRATORE = "admin@swim.it";
	private static final String PASSWORD = "pippo";

	private GestioneLoginRemote gestioneLogin;

	public LoginTest() throws NamingException {
		Properties env = new Properties();
		env.setProperty("java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory");
		env.setProperty("java.naming.provider.url", "localhost:1099");
		env.setProperty("java.naming.factory.url.pkgs", "org.jboss.naming");

		Object obj = (new InitialContext(env)).lookup("SWIMv2_EAR/GestioneLogin/remote-utililies.sessionRemote.GestioneLoginRemote");
		gestioneLogin = (GestioneLoginRemote) PortableRemoteObject.narrow(obj, GestioneLoginRemote.class);
	}

	@Test
	public void testEseguiLoginUtente() {
		try {
		//inserire nel db l'utente peppino@gmail.com con password a2242ead55c94c3deb7cf2340bfef9d5bcaca22dfe66e646745ee4371c633fc8
		String passwordHashata = PasswordHasher.hashPassword(PASSWORD);

		//il metodo hasha la password e se trova quella hashata uguale nel db da true
		Assert.assertTrue(gestioneLogin.esegueLoginUtente(MAIL_PEPPINO, PASSWORD));

		//ovviamente se rihashi una password non e' quella originale
		Assert.assertFalse(gestioneLogin.esegueLoginUtente(MAIL_PEPPINO, passwordHashata));

		//questo utente non e' presente e allora dice false
		Assert.assertFalse(gestioneLogin.esegueLoginUtente(MAIL_UTENTE_INUTILE, PASSWORD_INUTILE)); 
	} catch (LoginException e) {
		fail("LoginException: " + e);
	} catch (HashingException e) {
		fail("HashingException: " + e);
	}
	}

	@Test
	public void testEseguiLoginAmministratore() {
		try {
			//inserire nel db l'admin admin@swim.it con password a2242ead55c94c3deb7cf2340bfef9d5bcaca22dfe66e646745ee4371c633fc8
			String passwordHashata = PasswordHasher.hashPassword(PASSWORD);

			//il metodo hasha la password e se trova quella hashata uguale nel db da true
			Assert.assertTrue(gestioneLogin.eseguiLoginAmministratore(MAIL_AMMINISTRATORE, PASSWORD));

			//ovviamente se rihashi una password non e' quella originale
			Assert.assertFalse(gestioneLogin.eseguiLoginAmministratore(MAIL_AMMINISTRATORE, passwordHashata));

			//questo admin non e' presente e allora dice false
			Assert.assertFalse(gestioneLogin.eseguiLoginAmministratore(MAIL_UTENTE_INUTILE, PASSWORD_INUTILE)); 
		} catch (LoginException e) {
			fail("LoginException: " + e);
		} catch (HashingException e) {
			fail("HashingException: " + e);
		}
	}
}
