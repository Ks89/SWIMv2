package entityBeans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of={"id","utente"})
@Entity
@Table(name = "PropostaAbilita")
public class PropostaAbilita implements Serializable {

	private static final long serialVersionUID = -5483399693458810268L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "Id", nullable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "EmailUtente",referencedColumnName = "Email", nullable = false)
	private Utente utente;

	@Column(name = "AbilitaProposta", nullable = false, length=100)
	private String abilitaProposta;

	@Column(name = "Motivazione", nullable = true, length=500)
	private String motivazione;

	@Column(name = "DataAccettazione", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAccettazione;
}
