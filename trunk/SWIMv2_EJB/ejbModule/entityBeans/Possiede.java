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
			@NamedQuery(name = "Possiede.getUtenteByAbilita",
					query = "SELECT DISTINCT p.possiedePK.utente FROM Possiede p WHERE p.possiedePK.abilita IN (:insiemeAbilita) AND " +
							"(SELECT COUNT(pk.possiedePK.utente) FROM Possiede pk " +
							"WHERE pk.possiedePK.abilita IN (:insiemeAbilita) AND pk.possiedePK.utente=p.possiedePK.utente) = :numAbilita AND p.possiedePK.utente.email != :emailUtente"),

			@NamedQuery(name = "Possiede.getUtenteByAbilitaVisitatore",
							query = "SELECT DISTINCT p.possiedePK.utente FROM Possiede p WHERE p.possiedePK.abilita IN (:insiemeAbilita) AND " +
									"(SELECT COUNT(pk.possiedePK.utente) FROM Possiede pk " +
									"WHERE pk.possiedePK.abilita IN (:insiemeAbilita) AND pk.possiedePK.utente=p.possiedePK.utente) = :numAbilita"),
									
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
