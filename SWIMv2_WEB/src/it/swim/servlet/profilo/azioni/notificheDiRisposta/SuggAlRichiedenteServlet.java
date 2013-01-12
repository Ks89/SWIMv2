package it.swim.servlet.profilo.azioni.notificheDiRisposta;

import it.swim.util.UtenteCollegatoUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entityBeans.Amicizia;

import sessionBeans.localInterfaces.GestioneAmicizieLocal;

import lombok.extern.slf4j.Slf4j;

/**
 * Servlet implementation class SuggAlRichiedenteServlet
 */
@Slf4j
public class SuggAlRichiedenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private GestioneAmicizieLocal amicizie;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SuggAlRichiedenteServlet() {
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
		log.debug("Entrato nella doPost di SuggerimentiAlRichiedente");
		
		// ottengo l'email dell'utente collegato dalla sessione, appoggiandomi
		// ad una classe di utilita'
		String emailUtenteCollegato = (String) UtenteCollegatoUtil.getEmailUtenteCollegato(request);

		// se e' null e' perche' l'utente non e' collegato e allora devo fare il
		// redirect alla home
		if (emailUtenteCollegato == null) {
			response.sendRedirect("../../../home");
			return;
		}

		if(request.getParameter("suggeritoAlRichiedenteCheckbox")!=null) {

			List<Amicizia> amiciziaSuggeritaRichiesta  = new ArrayList<Amicizia>();
			Amicizia amiciziaRichiesta;

			for(String emailSuggerimentoAlRichiedente : request.getParameterValues("suggeritoAlRichiedenteCheckbox")) {
				log.debug("emailSuggerimentoAlRichiedente : " + emailSuggerimentoAlRichiedente);

				//se l'amicizia non e' gia' stata inoltrata in precedenza, la richiedo, altrimenti no
				if(!amicizie.amiciziaInoltrata(emailUtenteCollegato, emailSuggerimentoAlRichiedente)) {
					amiciziaRichiesta = amicizie.richiediAmicizia(emailUtenteCollegato, emailSuggerimentoAlRichiedente, false );
					if(amiciziaRichiesta!=null) {
						amiciziaSuggeritaRichiesta.add(amiciziaRichiesta);
					}
				}
			}

			if(amiciziaSuggeritaRichiesta.size()==0) {
				request.setAttribute("nessunSuggAccettato","Nessun ssuggerimento scelto. Amicizie non inoltrate");
			} else if(amiciziaSuggeritaRichiesta.size()==1) {
				request.setAttribute("suggAccettato","Hai stretto amicizia con l'utente scelto");
			} else if(amiciziaSuggeritaRichiesta.size()>1) {
				request.setAttribute("suggAccettati","Hai stretto amicizia con gli utenti scelti");
			}
		} else {
			request.setAttribute("nessunSuggAccettato","Nessun ssuggerimento scelto. Amicizie non inoltrate");
		}
		getServletConfig().getServletContext().getRequestDispatcher("/jsp/utenti/profilo/profilo.jsp").forward(request, response);
	}
}
