package entityBeans;

import java.io.Serializable;

import javax.persistence.*;

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

	@Id
	@Column(name = "Email", unique = true, nullable = false, length=100)
	private String email;

	@Column(name = "Password", nullable = false, length=100)
	private String password;
}