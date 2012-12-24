package entityBeans;


import java.io.Serializable;
import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.validator.Pattern;

@NamedQueries({
	@NamedQuery(name = "Utente.getUtenteByEmail", 
			query = "SELECT u " +
					"FROM Utente u " +
			"WHERE u.email= :emailUtente")
})

@Data
@EqualsAndHashCode(of={"email"})
@Entity
@Table(name = "Utente")
public class Utente implements Serializable  {

	private static final long serialVersionUID = -6701906697816419986L;

	@Id
	@Column(name = "Email", nullable = false, length=100)
	@Pattern(regex=".+@.+\\..+", message="Inserisci un indirizzo email corretto")
	private String email;

	@Column(name = "Password", nullable = false, length=100)
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
