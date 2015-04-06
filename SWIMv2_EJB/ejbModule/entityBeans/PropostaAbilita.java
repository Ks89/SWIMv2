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
import java.util.Date;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NamedQueries({
	@NamedQuery(name = "PropostaAbilita.getTutteProposteAbilitaNonConfermate", 
			query = "SELECT p " +
					"FROM PropostaAbilita p " +
					"WHERE p.dataAccettazione IS NULL"),
			
	@NamedQuery(name = "PropostaAbilita.getTutteProposteAbilitaConfermate", 
			query = "SELECT p " +
					"FROM PropostaAbilita p " +
					"WHERE p.dataAccettazione IS NOT NULL"),
})

@Data
@EqualsAndHashCode(of={"id","utente"})
@AllArgsConstructor
@NoArgsConstructor
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
