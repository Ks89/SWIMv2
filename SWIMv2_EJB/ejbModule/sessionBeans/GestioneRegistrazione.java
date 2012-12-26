package sessionBeans;

import java.sql.Blob;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import utililies.PasswordHasher;

import entityBeans.Abilita;
import entityBeans.Possiede;
import entityBeans.PossiedePK;
import entityBeans.Utente;

@Stateless
public class GestioneRegistrazione implements GestioneRegistrazioneLocal {

	@PersistenceContext(unitName = "SWIMdb")
	private EntityManager entityManager;
	
	public boolean registrazioneUtente(String email, String password, String nome, String cognome, Blob fotoProfilo, List<Abilita> abilita){
		Utente utente= new Utente();
		Possiede possiede= new Possiede();
		PossiedePK possiedepk= new PossiedePK();
		if(emailCorretta(email) && emailNonAncoraUtilizzata(email) && nome!=null && cognome!=null && abilita.size()>=1)//cognome e nome non nulli e abilita.size è un controllo che facciamo qua, o direttamente con javascript?
		{
				utente.setEmail(email);
				utente.setNome(nome);
				utente.setCognome(cognome);
				utente.setPassword(PasswordHasher.hashPassword(password));
				utente.setFotoProfilo(fotoProfilo);
				entityManager.persist(utente);
				for (Abilita a : abilita) {
					possiedepk.setAbilita(a);
					possiedepk.setUtente(utente);
					possiede.setPossiedePK(possiedepk);
					entityManager.persist(possiede);
				}
				entityManager.flush();
				return true;
		}
		return false;
	}
	
	private boolean emailCorretta(String email){
		//Settiamo il pattern per il confronto
        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
 
        //Eseguiamo il match della stringa data con il pattern     
        Matcher m = p.matcher(email);
 
        //Salviamo il risultato del match
        boolean matchFound = m.matches();
        StringTokenizer st = new StringTokenizer(email, ".");
        String lastToken = null;
        while (st.hasMoreTokens()) {
            lastToken = st.nextToken();
        }
 
        // validate the country code
        if (matchFound && lastToken.length() >= 2
                && email.length() - 1 != lastToken.length()) {
 
            return true;
        } else {
            return false;
        }
	}
	
	private boolean emailNonAncoraUtilizzata(String email){
		return entityManager.find(Utente.class, email)==null;
	}

}
