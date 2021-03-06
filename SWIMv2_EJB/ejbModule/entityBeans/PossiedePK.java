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

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(of={"utente","abilita"})
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PossiedePK  implements Serializable {

	private static final long serialVersionUID = 6055787595255942281L;

	@ManyToOne
	@JoinColumn(name = "EmailUtente", referencedColumnName = "Email", nullable = false)
	private Utente utente;

	@ManyToOne
	@JoinColumn(name = "NomeAbilita", referencedColumnName = "Nome", nullable = false)
	private Abilita abilita;
}
