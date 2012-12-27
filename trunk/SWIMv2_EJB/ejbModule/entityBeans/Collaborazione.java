package entityBeans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.validator.Max;
import org.hibernate.validator.Min;
import org.hibernate.validator.Pattern;

@NamedQueries({
		@NamedQuery(name = "Collaborazione.getCollaborazioniCreateByEmail", query = "SELECT c " + "FROM Collaborazione c " + "WHERE c.utenteRichiedente.email = :emailRichiedente"),

		@NamedQuery(name = "Collaborazione.getCollaborazioniAccettateByEmail", query = "SELECT c " + "FROM Collaborazione c "
				+ "WHERE c.utenteRicevente.email = :emailRicevente AND c.dataStipula IS NOT NULL"),

		@NamedQuery(name = "Collaborazione.getCollaborazioniCreateFeedbackNonRilasciatoByEmail", query = "SELECT c " + "FROM Collaborazione c "
				+ "WHERE c.utenteRichiedente.email = :emailRichiedente AND c.dataTermine IS NOT NULL AND c.punteggioFeedback IS NULL"),

		@NamedQuery(name = "Collaborazione.getPunteggioFeedbackByEmail", query = "SELECT avg(c.punteggioFeedback) " + "FROM Collaborazione c "
				+ "WHERE c.utenteRicevente.email = :emailRicevente AND c.punteggioFeedback IS NOT NULL"), })
@Data
@EqualsAndHashCode(of = { "id", "utenteRicevente", "utenteRichiedente" })
@Entity
@Table(name = "Collaborazione")
public class Collaborazione implements Serializable {

	private static final long serialVersionUID = 7778335853396334887L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "EmailRicevente", referencedColumnName = "Email", nullable = false)
	private Utente utenteRicevente;

	@ManyToOne
	@JoinColumn(name = "EmailRichiedente", referencedColumnName = "Email", nullable = false)
	private Utente utenteRichiedente;

	@Column(name = "Nome", nullable = false)
	@Pattern(regex = "[a-zA-z0-9 ]*", message = "Il nome contiene caratteri non validi")
	private String nome;

	@Column(name = "DataStipula", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataStipula;

	@Column(name = "DataTermine", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataTermine;

	@Column(name = "Descrizione", nullable = true, length = 500)
	@Pattern(regex = "[a-zA-z0-9 ]*", message = "Il nome contiene caratteri non validi")
	private String descrizione;

	@Column(name = "PunteggioFeedback", nullable = true)
	@Min(value = 1, message = "Il punteggio deve essere superiore a 0")
	@Max(value = 5, message = "Il punteggio deve essere inferiore a 6")
	private Integer punteggioFeedback;

	@Column(name = "CommentoFeedback", nullable = true, length = 250)
	private String commentoFeedback;
}