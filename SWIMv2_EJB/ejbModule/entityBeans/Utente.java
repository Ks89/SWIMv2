/*
Copyright 2012-2015 Stefano Cappa, Jacopo Bulla, Davide Caio
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
    http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

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
