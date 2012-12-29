package entityBeans;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
