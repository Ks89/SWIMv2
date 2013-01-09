package it.swim.servlet.profilo.azioni.notificheDiRisposta;

import it.swim.util.UtenteCollegatoUtil;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entityBeans.Amicizia;

import sessionBeans.localInterfaces.GestioneAmicizieLocal;

import lombok.extern.slf4j.Slf4j;

/**
 * Servlet implementation class SuggerimentiAlRichiedenteServlet
 */
@Slf4j
public class SuggerimentiAlRichiedenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	@EJB
	private GestioneAmicizieLocal amicizie;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SuggerimentiAlRichiedenteServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ottengo l'email dell'utente collegato dalla sessione, appoggiandomi
		// ad una classe di utilita'
		String emailUtenteCollegato = (String) UtenteCollegatoUtil.getEmailUtenteCollegato(request);

		// se e' null e' perche' l'utente non e' collegato e allora devo fare il
		// redirect alla home
		if (emailUtenteCollegato == null) {
			response.sendRedirect("../../home");
			return;
		}
		
		String emailSuggerimento = request.getParameter("emailSuggAccettato");
		
		Amicizia richiesta = amicizie.richiediAmicizia(emailUtenteCollegato, emailSuggerimento, false);
		
		if(richiesta!=null) {
			
		} else {
			
		}
		response.sendRedirect("../../profilo");
	}

}
