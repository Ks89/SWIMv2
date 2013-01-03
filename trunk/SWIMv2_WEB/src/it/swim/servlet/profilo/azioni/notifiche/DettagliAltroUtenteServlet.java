package it.swim.servlet.profilo.azioni.notifiche;

import it.swim.util.UtenteCollegatoUtil;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exceptions.LoginException;

import sessionBeans.localInterfaces.GestioneCollaborazioniLocal;
import sessionBeans.localInterfaces.GestioneRicercheLocal;

/**
 * Servlet implementation class ProfiloAltroUtenteServlet
 */
public class DettagliAltroUtenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private GestioneCollaborazioniLocal gestCollaborazioni;

	@EJB
	private GestioneRicercheLocal ricerche;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DettagliAltroUtenteServlet() {
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
		String email=request.getParameter("utente");
		// se e' null e' perche' l'utente non e' collegato e allora devo fare il
		// redirect alla home
		if (emailUtenteCollegato == null) {
			response.sendRedirect("../home");
			return;
		}
		request.setAttribute("utente", ricerche.getUtenteByEmail(email));
		try {
			request.setAttribute("punteggioFeedback", gestCollaborazioni.getPunteggioFeedback(email));
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getServletConfig().getServletContext().getRequestDispatcher("/jsp/utenti/profilo/dettagliAltroUtente.jsp").forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
