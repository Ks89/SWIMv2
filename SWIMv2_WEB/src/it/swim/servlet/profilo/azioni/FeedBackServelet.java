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
		try {
			List<Collaborazione> collaborazioni = gestioneCollab.getCollaborazioniRiceventeConFeedBack(email);

			if(collaborazioni==null) { //TODO attenzione PERCHE' l'ho scritto qui????????????????????????????????????
				request.setAttribute("erroreGetCollaborazioni", "Impossibile ottenere le collaborazioni");
			}
			if(collaborazioni.size()>=1) {
				request.setAttribute("collaborazioni", collaborazioni);
			} else {
				request.setAttribute("nonCiSonoCollaborazioni", "Non è stato rilasciato nessun feedback da utenti che hai aiutato");
			}
		} catch (LoginException e) {
			log.error(e.getMessage(), e);
			request.setAttribute("erroreGetCollaborazioniDaTerminare", "Impossibile ottenere le collaborazioni");
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
