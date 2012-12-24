package entityBeans;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of={"utente","abilita"})
@Entity
@IdClass(value = PossiedePK.class)
public class Possiede implements Serializable {

	private static final long serialVersionUID = -6119129567539254690L;

	@Id
	@ManyToOne
	@JoinColumn(name = "EmailUtente", referencedColumnName = "Email", nullable = false)
	private Utente utente;

	@Id
	@ManyToOne
	@JoinColumn(name = "NomeAbilita", referencedColumnName = "Nome", nullable = false)
	private Abilita abilita;
}
