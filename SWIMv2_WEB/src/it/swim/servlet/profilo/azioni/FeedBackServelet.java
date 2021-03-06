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

import entityBeans.Collaborazione;
import exceptions.LoginException;

import sessionBeans.localInterfaces.GestioneCollaborazioniLocal;

import lombok.extern.slf4j.Slf4j;

/**
 * Servlet implementation class FeedBackServelet
 */
@Slf4j
public class FeedBackServelet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	@EJB
	private GestioneCollaborazioniLocal gestioneCollab;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FeedBackServelet() {
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
			response.sendRedirect("../../home");
			return;
		}
		
		String email= request.getParameter("emailUtente");
		request.setAttribute("email",email);
		request.setAttribute("tipoRicerca", request.getParameter("tipoRicerca"));
		try {
			List<Collaborazione> collaborazioni = gestioneCollab.getCollaborazioniRiceventeConFeedBack(email);

			if(collaborazioni==null) { //TODO attenzione PERCHE' l'ho scritto qui????????????????????????????????????
				request.setAttribute("erroreGetCollaborazioni", "Impossibile ottenere le collaborazioni");
			}
			if(collaborazioni.size()>=1) {
				request.setAttribute("collaborazioni", collaborazioni);
			} else {
				request.setAttribute("nonCiSonoCollaborazioni", "Non e' stato rilasciato nessun feedback da utenti che hai aiutato");
			}
		} catch (LoginException e) {
			log.error(e.getMessage(), e);
			request.setAttribute("erroreGetCollaborazioniDaTerminare", "Impossibile ottenere le collaborazioni");
		}
		if(!(emailUtenteCollegato.equals(email))){
			request.setAttribute("altroUtente",email);
		}
		
		getServletConfig().getServletContext().getRequestDispatcher("/jsp/utenti/profilo/listaFeedBack.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
