package sessionBeans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class GestioneModificaProfilo implements GestioneModificaProfiloLocal {

	@PersistenceContext(unitName = "SWIMdb")
	private EntityManager entityManager;
	
}
