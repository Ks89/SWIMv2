package entityBeans;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "Possiede")
public class Possiede implements Serializable {

	private static final long serialVersionUID = -6119129567539254690L;

	@EmbeddedId
	private PossiedePK possiedePK;
}
