package sessionBeans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class GestioneRegistrazione implements GestioneRegistrazioneLocal {

	@PersistenceContext(unitName = "SWIMdb")
	private EntityManager entityManager;
	
}
