package sessionBeans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class GestioneAmministratore implements GestioneAmministratoreLocal {

	@PersistenceContext(unitName = "SWIMdb")
	private EntityManager entityManager;
}
