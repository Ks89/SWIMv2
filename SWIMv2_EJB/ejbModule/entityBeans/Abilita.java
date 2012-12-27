package entityBeans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of={"nome"})
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Abilita")
public class Abilita implements Serializable {

	private static final long serialVersionUID = 9179746129392087937L;

	@Id
	@Column(name = "Nome", unique = true, nullable = false, length=100)
	private String nome;
	
	@Column(name = "Descrizione", unique = false, nullable = true, length=500)
	private String descrizione;
}
