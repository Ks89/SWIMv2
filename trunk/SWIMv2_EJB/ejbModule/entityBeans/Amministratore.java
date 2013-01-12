package entityBeans;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.validator.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(of={"email"})
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Amministratore")
public class Amministratore implements Serializable {

	private static final long serialVersionUID = -3872222525432211531L;

	//la lunghezza e' elevata per garantire che si possa registrare anche con mail lunghissime
	@Id
	@Column(name = "Email", unique = true, nullable = false, length=100) 
	@Pattern(regex=".+@.+\\.[a-z]+")
	private String email;

	@Column(name = "Password", nullable = false, length=64) //perche' 64 e' la lunghezza massima di una password hashata con SHA256
	private String password;
}
