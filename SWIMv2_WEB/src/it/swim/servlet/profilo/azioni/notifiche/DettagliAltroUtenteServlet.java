package it.swim.servlet.profilo.azioni.notifiche;

import it.swim.util.UtenteCollegatoUtil;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import exceptions.CollaborazioneException;
import exceptions.LoginException;
import exceptions.RicercheException;

import sessionBeans.localInterfaces.GestioneAmicizieLocal;
import sessionBeans.localInterfaces.GestioneCollaborazioniLocal;
import sessionBeans.localInterfaces.GestioneRicercheLocal;

/**
 * Servlet implementation class ProfiloAltroUtenteServlet
 */
@Slf4j
public class DettagliAltroUtenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private GestioneCollaborazioniLocal gestCollaborazioni;

	@EJB
	private GestioneRicercheLocal ricerche;
	
	@EJB
	private GestioneAmicizieLocal amicizie;
	
	private String tipoRicerca,utenteRicercato;
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
		utenteRicercato=email;
		tipoRicerca=request.getParameter("tipoRicerca");
		// se e' null e' perche' l'utente non e' collegato e allora devo fare il
		// redirect alla home
		if (emailUtenteCollegato == null) {
			response.sendRedirect("../../home");
			return;
		}
		request.setAttribute("utente", ricerche.getUtenteByEmail(email));
		try {
			request.setAttribute("punteggioFeedback", gestCollaborazioni.getPunteggioFeedback(email));
			if(gestCollaborazioni.getPunteggioFeedback(email)!=null)
				request.setAttribute("feedback", Integer.valueOf(gestCollaborazioni.getPunteggioFeedback(email).intValue()));
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			request.setAttribute("listaAbilita", ricerche.insiemeAbilitaPersonaliUtente(email));
		} catch (RicercheException e) {
			// TODO Auto-generated catch block
						e.printStackTrace();
		}
		request.setAttribute("amiciziaGiaInoltrata",amicizie.amiciziaInoltrata(emailUtenteCollegato, utenteRicercato));
		request.setAttribute("tipoRicerca", tipoRicerca);
		getServletConfig().getServletContext().getRequestDispatcher("/jsp/utenti/profilo/dettagliAltroUtente.jsp").forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String nomeCollaborazione="";
		String descrizioneCollaborazione="";
		String emailUtenteCollegato = (String) UtenteCollegatoUtil.getEmailUtenteCollegato(request);
		if(request.getParameter("tipo").equals("amicizia"))
		{
			amicizie.richiediAmicizia(emailUtenteCollegato, utenteRicercato, true);
			request.setAttribute("utente", ricerche.getUtenteByEmail(utenteRicercato));
		}
		else
		{
			request.setAttribute("utente", ricerche.getUtenteByEmail(utenteRicercato));
			try {
				gestCollaborazioni.richiediAiuto(emailUtenteCollegato, utenteRicercato, request.getParameter("nomeCollaborazione"), request.getParameter("descrizioneCollaborazione"));
			} catch (CollaborazioneException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("messageCollaborazione","Devi compilare tutti i campi.");
			} catch (LoginException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			request.setAttribute("listaAbilita", ricerche.insiemeAbilitaPersonaliUtente(utenteRicercato));
		} catch (RicercheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			request.setAttribute("punteggioFeedback", gestCollaborazioni.getPunteggioFeedback(utenteRicercato));
			request.setAttribute("feedback", Integer.valueOf(gestCollaborazioni.getPunteggioFeedback(utenteRicercato).intValue()));
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("amiciziaGiaInoltrata",amicizie.amiciziaInoltrata(emailUtenteCollegato, utenteRicercato));
		log.info("<---------------------------"+amicizie.amiciziaInoltrata(emailUtenteCollegato, utenteRicercato)+"---------------------------->");
		request.setAttribute("tipoRicerca", tipoRicerca);
		getServletConfig().getServletContext().getRequestDispatcher("/jsp/utenti/profilo/dettagliAltroUtente.jsp").forward(request, response);
	}

}
