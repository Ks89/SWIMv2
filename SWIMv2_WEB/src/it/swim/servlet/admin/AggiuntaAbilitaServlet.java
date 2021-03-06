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

package it.swim.servlet.admin;

import it.swim.util.AdminCollegatoUtil;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import sessionBeans.localInterfaces.GestioneProposteLocal;

import entityBeans.Abilita;
import exceptions.ProposteException;

/**
 * Servlet implementation class AggiuntaAbilitaServlet
 */
@Slf4j
public class AggiuntaAbilitaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private GestioneProposteLocal gestioneProposte;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AggiuntaAbilitaServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ottengo l'email dell'admin collegato dalla sessione, appoggiandomi
		// ad una classe di utilita'
		String emailAdminCollegato = (String) AdminCollegatoUtil.getEmailAdminCollegato(request);

		// se e' null e' perche' l'admin non e' collegato e allora devo fare il
		// redirect alla homeadmin
		if (emailAdminCollegato == null) {
			response.sendRedirect("../admin");
			return;
		}

		String confermaHiddenInput = request.getParameter("conferma");

		if(confermaHiddenInput!=null && confermaHiddenInput.equals("CONFERMA")) {

			String nuovoNomeAbilitaAggiunta = request.getParameter("nuovoNomeAbilitaAggiunta");
			String descrizioneAbilita = request.getParameter("descrizioneAbilitaAggiunta");

			Abilita giaPresente,abilitaAggiunta;
			try {

				if(nuovoNomeAbilitaAggiunta!=null && nuovoNomeAbilitaAggiunta.length()>=1) { 
					giaPresente = gestioneProposte.getAbilitaByNome(nuovoNomeAbilitaAggiunta);
					if(giaPresente==null) {
						abilitaAggiunta = gestioneProposte.inserisciAbilitaAutonomamente(emailAdminCollegato, nuovoNomeAbilitaAggiunta, descrizioneAbilita);
						if(abilitaAggiunta!=null) {
							log.debug("nuova abilita inserita correttamente: " + abilitaAggiunta.getNome());
							request.setAttribute("inserimentoAbilitaAvvenuto", "Inserimento abilita' avvenuto con successo!");
						} else {
							log.debug("erroreInserimentoAbilitaFallito : " + nuovoNomeAbilitaAggiunta);
							request.setAttribute("erroreInserimentoAbilitaFallito", "Errore aggiunta nuova abilita'");
						}
					} else {
						request.setAttribute("abilitaGiaPresenteColNomeSpecificato", "Errore! L'abilita' con il nome specificato e' gia' presente");
					}
				} else {
					request.setAttribute("erroreNomeAbilitaVuoto", "Inserisci il nome dell'abilita'");
				}
			} catch (ProposteException e) {
				log.error(e.getMessage(), e);
				request.setAttribute("erroreInserimentoAbilitaFallito", "Errore aggiunta nuova abilita con nome");
			}
		} else {
			request.setAttribute("nonHaiConfermatoInvioForm", "Hai interrotto la procedura. Nessun dato e' stato inviato");
		}
		getServletConfig().getServletContext().getRequestDispatcher("/jsp/admin/adminpanelinserimento.jsp").forward(request, response);
	}
}
