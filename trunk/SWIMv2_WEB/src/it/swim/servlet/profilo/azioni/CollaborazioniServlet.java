package it.swim.servlet.profilo.azioni;

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
 * Servlet implementation class CollaborazioniServlet
 */
@Slf4j
public class CollaborazioniServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private GestioneCollaborazioniLocal gestioneCollab;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CollaborazioniServlet() {
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
			response.sendRedirect("../home");
			return;
		}
		
		try {
			List<Collaborazione> collaborazioniDaTerminare = gestioneCollab.getCollaborazioniDaTerminare(emailUtenteCollegato);

			if(collaborazioniDaTerminare==null) { //TODO attenzione PERCHE' l'ho scritto qui????????????????????????????????????
				request.setAttribute("erroreGetCollaborazioniDaTerminare", "Impossibile ottenere le collaborazioni");
			}
			if(collaborazioniDaTerminare.size()>=1) {
				request.setAttribute("collaborazioniDaTerminare", collaborazioniDaTerminare);
			} else {
				request.setAttribute("nonCiSonoCollaborazioniDaTerminare", "Non ci sono collaborazioni in corso");
			}
		} catch (LoginException e) {
			log.error(e.getMessage(), e);
			request.setAttribute("erroreGetCollaborazioniDaTerminare", "Impossibile ottenere le collaborazioni in corso");
		}
		
		try {
			List<Collaborazione> collaborazioniDaRilasciareFeedBack = gestioneCollab.getCollaborazioniCreateFeedbackNonRilasciato(emailUtenteCollegato);

			if(collaborazioniDaRilasciareFeedBack==null) { //TODO attenzione PERCHE' l'ho scritto qui????????????????????????????????????
				request.setAttribute("erroreGetCollaborazioniSenzaFeedback", "Impossibile ottenere le collaborazioni senza feedback");
				getServletConfig().getServletContext().getRequestDispatcher("/jsp/utenti/profilo/collaborazioni.jsp").forward(request, response);
				return;
			}
			if(collaborazioniDaRilasciareFeedBack.size()>=1) {
				request.setAttribute("collaborazioniDaRilasciareFeedBack", collaborazioniDaRilasciareFeedBack);
			} else {
				request.setAttribute("collaborazioniDaRilasciareFeedBack", collaborazioniDaRilasciareFeedBack);
				request.setAttribute("nonCiSonoCollaborazioniSenzaFeedback", "Non ci sono collaborazioni senza feedback");
			}
		} catch (LoginException e) {
			log.error(e.getMessage(), e);
			request.setAttribute("erroreGetCollaborazioniSenzaFeedback", "Impossibile ottenere le collaborazioni senza feedback");
		}
		
		try {
			List<Collaborazione> collaborazioniTerminateConFeedBack = gestioneCollab.getCollaborazioniTerminateConFeedBack(emailUtenteCollegato);

			if(collaborazioniTerminateConFeedBack==null) { //TODO attenzione PERCHE' l'ho scritto qui????????????????????????????????????
				request.setAttribute("erroreGetCollaborazioniTerminateConFeedBack", "Impossibile ottenere le collaborazioni terminate con feedback");
				getServletConfig().getServletContext().getRequestDispatcher("/jsp/utenti/profilo/collaborazioni.jsp").forward(request, response);
				return;
			}
			if(collaborazioniTerminateConFeedBack.size()>=1) {
				request.setAttribute("collaborazioniTerminateConFeedBack", collaborazioniTerminateConFeedBack);
			} else {
				request.setAttribute("collaborazioniTerminateConFeedBack", collaborazioniTerminateConFeedBack);
				request.setAttribute("nonCiSonoCollaborazioniTerminateConFeedBack", "Non ci sono collaborazioni terminate con feedback");
			}
		} catch (LoginException e) {
			log.error(e.getMessage(), e);
			request.setAttribute("erroreGetCollaborazioniSenzaFeedback", "Impossibile ottenere le collaborazioni terminate con feedback");
		}
	
		getServletConfig().getServletContext().getRequestDispatcher("/jsp/utenti/profilo/collaborazioni.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			gestioneCollab.terminaCollaborazione(Long.valueOf(request.getParameter("tipo")));
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String emailUtenteCollegato = (String) UtenteCollegatoUtil.getEmailUtenteCollegato(request);

		// se e' null e' perche' l'utente non e' collegato e allora devo fare il
		// redirect alla home
		if (emailUtenteCollegato == null) {
			response.sendRedirect("../home");
			return;
		}
		
		try {
			List<Collaborazione> collaborazioniDaTerminare = gestioneCollab.getCollaborazioniDaTerminare(emailUtenteCollegato);

			if(collaborazioniDaTerminare==null) { //TODO attenzione PERCHE' l'ho scritto qui????????????????????????????????????
				request.setAttribute("erroreGetCollaborazioniDaTerminare", "Impossibile ottenere le collaborazioni");
			}
			if(collaborazioniDaTerminare.size()>=1) {
				request.setAttribute("collaborazioniDaTerminare", collaborazioniDaTerminare);
			} else {
				request.setAttribute("nonCiSonoCollaborazioniDaTerminare", "Non ci sono collaborazioni in corso");
			}
		} catch (LoginException e) {
			log.error(e.getMessage(), e);
			request.setAttribute("erroreGetCollaborazioniDaTerminare", "Impossibile ottenere le collaborazioni in corso");
		}
		
		try {
			List<Collaborazione> collaborazioniDaRilasciareFeedBack = gestioneCollab.getCollaborazioniCreateFeedbackNonRilasciato(emailUtenteCollegato);

			if(collaborazioniDaRilasciareFeedBack==null) { //TODO attenzione PERCHE' l'ho scritto qui????????????????????????????????????
				request.setAttribute("erroreGetCollaborazioniSenzaFeedback", "Impossibile ottenere le collaborazioni senza feedback");
				getServletConfig().getServletContext().getRequestDispatcher("/jsp/utenti/profilo/collaborazioni.jsp").forward(request, response);
				return;
			}
			if(collaborazioniDaRilasciareFeedBack.size()>=1) {
				request.setAttribute("collaborazioniDaRilasciareFeedBack", collaborazioniDaRilasciareFeedBack);
			} else {
				request.setAttribute("collaborazioniDaRilasciareFeedBack", collaborazioniDaRilasciareFeedBack);
				request.setAttribute("nonCiSonoCollaborazioniSenzaFeedback", "Non ci sono collaborazioni senza feedback");
			}
		} catch (LoginException e) {
			log.error(e.getMessage(), e);
			request.setAttribute("erroreGetCollaborazioniSenzaFeedback", "Impossibile ottenere le collaborazioni senza feedback");
		}
	
		getServletConfig().getServletContext().getRequestDispatcher("/jsp/utenti/profilo/collaborazioni.jsp").forward(request, response);
	
	}

}
