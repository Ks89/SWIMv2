package entityBeans;

import java.io.Serializable;
import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
					"WHERE u.nome = :nomeUtente and u.cognome = :cognomeUtente")
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
	@Pattern(regex=".+@.+\\..+", message="Inserisci un indirizzo email corretto")
	private String email;

	@Column(name = "Password", nullable = false, length=64) //solo 64 perche' lo sha256 fa solo a 64 caratteri
	private String password;

	@Column(name = "Cognome", nullable = false, length=100)
	@Pattern(regex="[a-zA-z ]*", message="Il cognome contiene caratteri non validi")
	private String cognome;

	@Column(name = "Nome", nullable = false, length=100)
	@Pattern(regex="[a-zA-z ]*", message="Il nome contiene caratteri non validi")
	private String nome;

	@Column(name = "FofoProfilo", nullable = true)
	private Blob fotoProfilo;		
}
