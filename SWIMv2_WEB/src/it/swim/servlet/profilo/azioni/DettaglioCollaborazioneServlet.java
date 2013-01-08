package it.swim.servlet.profilo.azioni;

import it.swim.servlet.profilo.azioni.notifiche.DettagliAltroUtenteServlet;
import it.swim.util.UtenteCollegatoUtil;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sessionBeans.localInterfaces.GestioneCollaborazioniLocal;

import entityBeans.Collaborazione;
import exceptions.LoginException;

import lombok.extern.slf4j.Slf4j;


/**
 * Servlet implementation class DettaglioCollaborazione
 */
@Slf4j
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
