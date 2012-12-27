package entityBeans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NamedQueries({ @NamedQuery(name = "Amicizia.getUtentAmici1", query = "SELECT a.amiciziaPK.utente1 "
		+ "FROM Amicizia a "
		+ "WHERE a.amiciziaPK.utente2.email = :emailUtente"),
		@NamedQuery(name = "Amicizia.getUtentAmici2", query = "SELECT a.amiciziaPK.utente2 "
				+ "FROM Amicizia a "
				+ "WHERE a.amiciziaPK.utente1.email = :emailUtente")
})


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Amicizia")
public class Amicizia implements Serializable {

	private static final long serialVersionUID = -1033459296427544050L;

	@EmbeddedId
	private AmiciziaPK amiciziaPK;

	@Column(name = "DataAccettazione", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAccettazione;

	@Column(name = "Diretta", nullable = false)
	private boolean diretta;
}
