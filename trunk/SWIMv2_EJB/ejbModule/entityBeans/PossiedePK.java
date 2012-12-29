package entityBeans;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@NamedQueries({
	@NamedQuery(name = "PossiedePK.getUtenteByAbilita", query = "SELECT u FROM Utente u WHERE u.email IN " +
			"(SELECT DISTINCT p.utente " +
			"FROM PossiedePK p " +
			"WHERE p.abilita IN :insiemeAbilita and (SELECT COUNT(pk) " +
													"FROM PossiedePK pk " +
													"WHERE pk.abilita IN :insiemeAbilita and pk.utente=p.utente) = :numAbilita )"),
													
	@NamedQuery(name = "PossiedePK.getAbilitaByUtente", query = "SELECT p.abilita FROM PossiedePK p WHERE p.utente.email = :emailUtente"),
})
    
@Data
@EqualsAndHashCode(of={"utente","abilita"})
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PossiedePK  implements Serializable {

	private static final long serialVersionUID = 6055787595255942281L;

	@ManyToOne
	@JoinColumn(name = "EmailUtente", referencedColumnName = "Email", nullable = false)
	private Utente utente;

	@ManyToOne
	@JoinColumn(name = "NomeAbilita", referencedColumnName = "Nome", nullable = false)
	private Abilita abilita;
}
