package sessionBeans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import sessionBeans.localInterfaces.GestioneAmicizieLocal;
import utililies.sessionRemote.GestioneAmicizieRemote;

@Stateless
public class GestioneAmicizie implements GestioneAmicizieLocal, GestioneAmicizieRemote {

	@PersistenceContext(unitName = "SWIMdb")
	private EntityManager entityManager;
	
	
	
}
