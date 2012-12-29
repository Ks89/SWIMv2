package entityBeans;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NamedQueries({
	/*@NamedQuery(name = "PossiedePK.getUtenteByAbilita", query = "SELECT u FROM Utente u WHERE u.email IN " +
	"(SELECT DISTINCT p.utente " +
	"FROM PossiedePK p " +
	"WHERE p.abilita IN :insiemeAbilita and (SELECT COUNT(pk) " +
											"FROM PossiedePK pk " +
											"WHERE pk.abilita IN :insiemeAbilita and pk.utente=p.utente) = :numAbilita )"),*/
			@NamedQuery(name = "Possiede.getUtenteByAbilita",
					query = "SELECT p.possiedePK.utente FROM Possiede p WHERE p.possiedePK.abilita IN (:insiemeAbilita)"),

			@NamedQuery(name = "Possiede.getAbilitaByUtente",
					query = "SELECT p.possiedePK.abilita FROM Possiede p WHERE p.possiedePK.utente.email = :emailUtente")
})

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Possiede")
public class Possiede implements Serializable {

	private static final long serialVersionUID = -6119129567539254690L;

	@EmbeddedId
	private PossiedePK possiedePK;
}
