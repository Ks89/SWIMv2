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

package it.swim.servlet.profilo.azioni.notifiche;

import it.swim.util.UtenteCollegatoUtil;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import sessionBeans.localInterfaces.GestioneAmicizieLocal;

import entityBeans.Utente;


/**
 * Servlet implementation class DettaglioServlet
 */
@Slf4j
public class DettaglioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private GestioneAmicizieLocal gestioneAmicizie;


	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DettaglioServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ottengo l'email dell'utente collegato dalla sessione, appoggiandomi
		// ad una classe di utilita'
		String emailUtenteCollegato = (String) UtenteCollegatoUtil.getEmailUtenteCollegato(request);


		// se e' null e' perche' l'utente non e' collegato e allora devo fare il
		// redirect alla home
		if (emailUtenteCollegato == null) {
			response.sendRedirect("../../../home");
			return;
		}

		String emailUtenteSuggerito = request.getParameter("emailUtenteSuggerito");

		Utente utenteSuggerito;
		utenteSuggerito = gestioneAmicizie.getUtenteByEmail(emailUtenteSuggerito);
		log.debug(" ---------------------utente suggerito--------------------->   " + utenteSuggerito);


		// mando sulla request i dati dell'utente tramite il setAttribute, e li
		// contraddistinguo dai 2 parametri passati come string
		request.setAttribute("cognomeSuggerito", utenteSuggerito.getCognome());
		request.setAttribute("nomeSuggerito", utenteSuggerito.getNome());
		request.setAttribute("emailSuggerito", utenteSuggerito.getEmail());
		getServletConfig().getServletContext().getRequestDispatcher("/jsp/utenti/profilo/suggerimentoDettaglio.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
