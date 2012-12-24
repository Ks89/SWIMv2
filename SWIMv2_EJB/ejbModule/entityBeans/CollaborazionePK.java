package entityBeans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of={"id","utenteRicevente","utenteRichiedente"})
public class CollaborazionePK implements Serializable {

	private static final long serialVersionUID = 3155900159305609222L;

	@Column(name = "Id", nullable = false)
	private int id;

	@ManyToOne
	@JoinColumn(name = "EmailRicevente",referencedColumnName = "Email", nullable = false)
	private Utente utenteRicevente;

	@ManyToOne
	@JoinColumn(name = "EmailRichiedente",referencedColumnName = "Email", nullable = false)
	private Utente utenteRichiedente;
}
