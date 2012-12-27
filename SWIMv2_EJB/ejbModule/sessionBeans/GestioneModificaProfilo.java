package sessionBeans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import sessionBeans.localInterfaces.GestioneModificaProfiloLocal;
import utililies.sessionRemote.GestioneModificaProfiloRemote;

@Stateless
public class GestioneModificaProfilo implements GestioneModificaProfiloLocal, GestioneModificaProfiloRemote {

	@PersistenceContext(unitName = "SWIMdb")
	private EntityManager entityManager;
	
}
