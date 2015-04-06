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

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class TestUtils implements TestUtilsRemote {

	private static final String entityBeans[] = {"Possiede", "Abilita", "Amicizia", "Amministratore", "Collaborazione", "PropostaAbilita", "Utente"};

	@PersistenceContext(unitName = "SWIMdb")
	private static EntityManager entityManager;


	/**
	 * Svuota il database
	 */
	public void svuotaDatabase() {
		for(String nomeTabellaEntityBeanDaSvuotare : entityBeans) {
			entityManager.createQuery("DELETE FROM " + nomeTabellaEntityBeanDaSvuotare).executeUpdate();
		}
	}

	/**
	 * Svuota una tabella del database
	 */
	public void svuotaTabellaDatabase(String nomeTabellaEntityBeanDaSvuotare) {
		entityManager.createQuery("DELETE FROM " + nomeTabellaEntityBeanDaSvuotare).executeUpdate();
	}
}
