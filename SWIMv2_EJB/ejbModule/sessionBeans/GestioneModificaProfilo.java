package sessionBeans;

import java.sql.Blob;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entityBeans.Abilita;
import entityBeans.Possiede;
import entityBeans.PossiedePK;
import entityBeans.Utente;

import sessionBeans.localInterfaces.GestioneModificaProfiloLocal;
import utililies.sessionRemote.GestioneModificaProfiloRemote;

@Stateless
public class GestioneModificaProfilo implements GestioneModificaProfiloLocal, GestioneModificaProfiloRemote {

	@PersistenceContext(unitName = "SWIMdb")
	private EntityManager entityManager;
	
	@Override
	public boolean modificaFoto (String emailUtente, Blob fotoProfilo){
		Utente utente;
		
		utente=this.getUtenteByEmail(emailUtente);
		if(utente==null){
			return false;
		}
		utente.setFotoProfilo(fotoProfilo);
		
		entityManager.persist(utente);
		entityManager.flush();
		
		return true;
	}
	
	@Override
	public boolean modificaInsiemePersonaleAbilita (String emailUtente, List<Abilita> abilita){
		Utente utente;
		
		utente=this.getUtenteByEmail(emailUtente);
		if(utente==null){
			return false;
		}
		
		for (Abilita a : abilita) {
			Possiede possiede= new Possiede();
			PossiedePK possiedepk = new PossiedePK();
			possiedepk.setAbilita(a);
			possiedepk.setUtente(utente);
			possiede.setPossiedePK(possiedepk);
			
			entityManager.persist(possiede);
			entityManager.flush();
		}
		return true;
		
	}
	
	
	
	@Override
	public Utente getUtenteByEmail(String email) {
		return entityManager.find(Utente.class, email);
	}
	
}
