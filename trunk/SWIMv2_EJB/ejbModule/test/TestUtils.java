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
