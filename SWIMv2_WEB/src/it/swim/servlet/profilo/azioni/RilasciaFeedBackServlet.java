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

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import exceptions.LoginException;

import sessionBeans.localInterfaces.GestioneCollaborazioniLocal;

/**
 * Servlet implementation class RilasciaFeedBackServlet
 */
public class RilasciaFeedBackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private GestioneCollaborazioniLocal collab;
	private Long idCollaborazione;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RilasciaFeedBackServlet() {

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// ottengo l'email dell'utente collegato dalla sessione, appoggiandomi
		// ad una classe di utilita'
		String emailUtenteCollegato = (String) UtenteCollegatoUtil
				.getEmailUtenteCollegato(request);
		idCollaborazione=Long.parseLong(request.getParameter("idCollaborazione"));
		// se e' null e' perche' l'utente non e' collegato e allora devo fare il
		// redirect alla home
		if (emailUtenteCollegato == null) {
			response.sendRedirect("../../home");
			return;
		}
		getServletConfig()
				.getServletContext()
				.getRequestDispatcher(
						"/jsp/utenti/profilo/rilascioFeedBack.jsp")
				.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// ottengo l'email dell'utente collegato dalla sessione, appoggiandomi
		// ad una classe di utilita'
		String emailUtenteCollegato = (String) UtenteCollegatoUtil
				.getEmailUtenteCollegato(request);
		List<FileItem> items;
		int punteggioFeedBack = 0;
		String commento="";
		// se e' null e' perche' l'utente non e' collegato e allora devo fare il
		// redirect alla home
		
		if (emailUtenteCollegato == null) {
			response.sendRedirect("../../home");
			return;
		}

		try {
			items = new ServletFileUpload(new DiskFileItemFactory())
					.parseRequest(request);
			for (FileItem item : items) {
				if (item.isFormField()) {
					// Process regular form field (input
					// type="text|radio|checkbox|etc", select, etc).
					// ... (do your job here)
					if (item.getFieldName().equals("punteggioFeedBack")) {
						punteggioFeedBack = Integer.parseInt(item.getString()
								.trim());
					}
					if (item.getFieldName().equals("commentoFeedBack")) {
						commento = item.getString().trim();
					}
				}
			}
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(punteggioFeedBack<1||punteggioFeedBack>5){
			request.setAttribute("erroreNelPunteggio", "Il punteggio deve essere compreso tra 1 e 5");
			getServletConfig().getServletContext().getRequestDispatcher("/jsp/utenti/profilo/rilascioFeedBack.jsp").forward(request, response);
			return;
		}
		if(commento.isEmpty()){
			commento="Non ci sono commenti rilasciati";
		}
		try {
			collab.rilasciaFeedback(idCollaborazione, punteggioFeedBack, commento);
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			request.setAttribute("erroreNelPunteggio", "Collaborazione a cui aggiungere il feedBack non trovata");
			getServletConfig().getServletContext().getRequestDispatcher("/jsp/utenti/profilo/rilascioFeedBack.jsp").forward(request, response);
			return;
		}
		request.setAttribute("feedBackRilasciato", "Feedback rilasciato con successo");
		getServletConfig().getServletContext().getRequestDispatcher("/jsp/utenti/profilo/rilascioFeedBack.jsp").forward(request, response);
		

	}

}
