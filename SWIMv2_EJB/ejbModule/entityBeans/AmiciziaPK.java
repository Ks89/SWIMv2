package entityBeans;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of={"utente1","utente2"})
@Embeddable
public class AmiciziaPK implements Serializable {

	private static final long serialVersionUID = -3827525328260056880L;

	@ManyToOne
	@JoinColumn(name = "EmailUtente1", referencedColumnName = "Email", nullable = false)
	private Utente utente1;

	@ManyToOne
	@JoinColumn(name = "EmailUtente2", referencedColumnName = "Email", nullable = false)
	private Utente utente2;
}
