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
