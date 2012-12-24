package sessionBeans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class GestioneProposte implements GestioneProposteLocal {

	@PersistenceContext(unitName = "SWIMdb")
	private EntityManager entityManager;
	
}
