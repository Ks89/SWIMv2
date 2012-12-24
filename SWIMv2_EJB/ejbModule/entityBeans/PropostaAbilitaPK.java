package entityBeans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of={"id","utente"})
public class PropostaAbilitaPK implements Serializable {

	private static final long serialVersionUID = -3239817666310977083L;

	@Column(name = "Id", nullable = false)
	private int id;

	@ManyToOne
	@JoinColumn(name = "EmailUtente",referencedColumnName = "Email", nullable = false)
	private Utente utente;
}
