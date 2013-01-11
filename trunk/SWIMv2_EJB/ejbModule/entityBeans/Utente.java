package entityBeans;

import java.io.Serializable;
import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.hibernate.validator.Pattern;
@NamedQueries({
	@NamedQuery(name = "Utente.getUtentiByNomeCognome", 
			query = "SELECT u " +
					"FROM Utente u " +
					"WHERE u.nome = :nomeUtente AND u.cognome = :cognomeUtente AND u.email!=:emailUtente"),
	@NamedQuery(name = "Utente.getUtentiByNome", 
			query = "SELECT u " +
					"FROM Utente u " +
					"WHERE u.nome = :nomeUtente AND u.email!=:emailUtente"),
	@NamedQuery(name = "Utente.getUtentiByCognome", 
			query = "SELECT u " +
					"FROM Utente u " +
					"WHERE u.cognome = :cognomeUtente AND u.email!=:emailUtente")
	})
@Data
@EqualsAndHashCode(of={"email"})
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Utente")
public class Utente implements Serializable  {

	private static final long serialVersionUID = -6701906697816419986L;

	@Id
	@Column(name = "Email", nullable = false, length=100)
	@Pattern(regex=".+@.+\\.[a-z]+")
	private String email;

	@Column(name = "Password", nullable = false, length=64) //solo 64 perche' lo sha256 fa solo a 64 caratteri
	private String password;

	@Column(name = "Cognome", nullable = false, length=100)
	private String cognome;

	@Column(name = "Nome", nullable = false, length=100)
	private String nome;

	@Column(name = "FotoProfilo", nullable = true)
	@Lob
	private Blob fotoProfilo;		
}
