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

package it.swim.servlet.profilo.azioni;

import it.swim.util.UtenteCollegatoUtil;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sessionBeans.localInterfaces.GestioneAmicizieLocal;
import sessionBeans.localInterfaces.GestioneCollaborazioniLocal;

import entityBeans.Collaborazione;
import entityBeans.Utente;
import exceptions.LoginException;

import lombok.extern.slf4j.Slf4j;

/**
 * Servlet implementation class NotificheServlet
 */
@Slf4j
public class NotificheServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB 
	private GestioneCollaborazioniLocal gestioneCollab;

	@EJB
	private GestioneAmicizieLocal gestioneAmicizie;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NotificheServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("Entrato nella NotificheServlet");

		// ottengo l'email dell'utente collegato dalla sessione, appoggiandomi
		// ad una classe di utilita'
		String emailUtenteCollegato = (String) UtenteCollegatoUtil.getEmailUtenteCollegato(request);

		// se e' null e' perche' l'utente non e' collegato e allora devo fare il
		// redirect alla home
		if (emailUtenteCollegato == null) {
			response.sendRedirect("../../home");
			return;
		}


		this.gestisciNotificheRichiesteAiuto(request, response, emailUtenteCollegato);

		this.gestisciNotificheRichiesteAmicizia(request, response, emailUtenteCollegato);

		getServletConfig().getServletContext().getRequestDispatcher("/jsp/utenti/profilo/notifiche.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}


	private void gestisciNotificheRichiesteAiuto(HttpServletRequest request, HttpServletResponse response, String emailUtenteCollegato) throws ServletException, IOException {
		try {
			List<Collaborazione> collaborazioni = gestioneCollab.getNotificheRichiesteAiuto(emailUtenteCollegato);

			if(collaborazioni==null) {
				request.setAttribute("erroreGetNotificheRichiesteAiuto", "Impossibile ottenere le richieste di aiuto");
				getServletConfig().getServletContext().getRequestDispatcher("/jsp/utenti/profilo/notifiche.jsp").forward(request, response);
				return;
			}
			if(collaborazioni.size()>=1) {
				request.setAttribute("richiesteAiuto", collaborazioni);
			} else {
				request.setAttribute("richiesteAiuto", collaborazioni);
				request.setAttribute("nonCiSonoRichiesteAiuto", "Non ci sono nuove richieste di aiuto");
			}
		} catch (LoginException e) {
			log.error(e.getMessage(), e);
			request.setAttribute("erroreGetNotificheRichiesteAiuto", "Impossibile ottenere le richieste di aiuto");
		}
	}


	private void gestisciNotificheRichiesteAmicizia (HttpServletRequest request, HttpServletResponse response, String emailUtenteCollegato) throws ServletException, IOException {
		List<Utente> utentiCheRichiedonoAmicizia = gestioneAmicizie.getUtentiCheVoglionoAmicizia(emailUtenteCollegato);

		//impossibile ottenere le richieste di amicizia
		if(utentiCheRichiedonoAmicizia==null) {
			request.setAttribute("erroreGetNotificheRichiesteAmicizia", "Impossibile ottenere le richieste di amicizia");
		} else {
			//se ha richieste di amicizia
			if(utentiCheRichiedonoAmicizia.size()>=1) {
				request.setAttribute("utentiCheRichidonoAmicizia", utentiCheRichiedonoAmicizia);
			} else {
				//se non ha richieste di amicizia
				request.setAttribute("utentiCheRichidonoAmicizia", utentiCheRichiedonoAmicizia);
				request.setAttribute("nonCiSonoRichiesteAmicizia", "Non ci sono nuove richieste di amicizia");
			}
		}
	}
}
