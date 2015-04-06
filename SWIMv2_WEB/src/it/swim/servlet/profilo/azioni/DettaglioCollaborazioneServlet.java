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

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sessionBeans.localInterfaces.GestioneCollaborazioniLocal;

import entityBeans.Collaborazione;
import exceptions.LoginException;

/**
 * Servlet implementation class DettaglioCollaborazione
 */
public class DettaglioCollaborazioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private GestioneCollaborazioniLocal gestioneCollab;
	
	private String terminata,conFeedBack;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DettaglioCollaborazioneServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String emailUtenteCollegato = (String) UtenteCollegatoUtil.getEmailUtenteCollegato(request);
		Collaborazione collaborazione=new Collaborazione();
		Long id = Long.parseLong(request.getParameter("idCollaborazione"));
		if (emailUtenteCollegato == null) {
			response.sendRedirect("../../home");
			return;
		}
		try {
			collaborazione= gestioneCollab.getCollaborazione(id);
		} catch (LoginException e) {
			request.setAttribute("erroreRicercaCollaborazione", "Impossibile ottenere le collaborazione");
			getServletConfig().getServletContext().getRequestDispatcher("/jsp/utenti/profilo/dettaglioCollaborazione.jsp").forward(request, response);
			return;
		}
		terminata = request.getParameter("terminata");
		conFeedBack=request.getParameter("conFeedBack");
		request.setAttribute("collaborazione", collaborazione);
		if(conFeedBack!=null)
			request.setAttribute("feedback", Integer.valueOf(collaborazione.getPunteggioFeedback().intValue()));
		request.setAttribute("terminata", terminata);
		request.setAttribute("conFeedBack", conFeedBack);
		
		getServletConfig().getServletContext().getRequestDispatcher("/jsp/utenti/profilo/dettaglioCollaborazione.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
