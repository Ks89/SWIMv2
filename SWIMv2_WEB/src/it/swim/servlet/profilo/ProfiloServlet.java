package it.swim.servlet.profilo;

import it.swim.util.PresenzaNotificheDiRisposta;
import it.swim.util.UtenteCollegatoUtil;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sessionBeans.localInterfaces.GestioneAmicizieLocal;
import sessionBeans.localInterfaces.GestioneCollaborazioniLocal;
import sessionBeans.localInterfaces.GestioneRicercheLocal;

import lombok.extern.slf4j.Slf4j;
import entityBeans.Abilita;
import entityBeans.Utente;
import exceptions.LoginException;
import exceptions.RicercheException;

/**
 * Servlet implementation class ProfiloServlet
 */
@Slf4j
public class ProfiloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private GestioneAmicizieLocal amicizie;

	@EJB
	private GestioneCollaborazioniLocal gestCollaborazioni;

	@EJB
	private GestioneRicercheLocal ricerche;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProfiloServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
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

		// ottengo utente collegato con riferimento al vero oggetto Utente
		Utente utenteCollegato;
		try {
			utenteCollegato = gestCollaborazioni.getUtenteByEmail(emailUtenteCollegato);
			// mando sulla request i dati dell'utente tramite il setAttribute, e li
			// contraddistinguo dai 2 parametri passati come string
			request.setAttribute("cognomeUtenteCollegato", utenteCollegato.getCognome());
			request.setAttribute("nomeUtenteCollegato", utenteCollegato.getNome());

			// ottengo punteggio di feedback dell'utente
			Double punteggio = gestCollaborazioni.getPunteggioFeedback(emailUtenteCollegato);
			String feedback;
			if(punteggio==null) {
				feedback = new String("Non disponibile");
			} else {
				DecimalFormat df = new DecimalFormat("#.##");
				feedback = df.format(punteggio);
				request.setAttribute("feedbackStelline", punteggio.intValue() + "");
			}
			request.setAttribute("punteggioUtenteCollegato", feedback);
			log.debug("punteggioUtenteCollegato:" + feedback);

		} catch (LoginException e) {
			log.error(e.getMessage(), e);
			//se ci fosse un errore durante il calcolo del feedback lo setto come non disponibile
			//			request.setAttribute("punteggioUtenteCollegato", "Non disponibile");
		}

		List<Abilita> abilitaInsiemePersonale;
		try {
			abilitaInsiemePersonale = ricerche.insiemeAbilitaPersonaliUtente(emailUtenteCollegato);
			request.setAttribute("abilita", abilitaInsiemePersonale);
		} catch (RicercheException e) {
			log.error(e.getMessage(), e);
		}


		List<Utente> amici = amicizie.getAmici(emailUtenteCollegato);
		if(amici!=null && amici.size()>=1) {
			request.setAttribute("amici", amici);
		}


		//vedo se mostrare l'avviso che sono state accettate richieste di amicizia o di collaborazione (aiuto)
		boolean ciSonoNotificheDaMostrare = PresenzaNotificheDiRisposta.isNotificheRispostaPresenti(emailUtenteCollegato, request, 
				response, amicizie, gestCollaborazioni);
		if(ciSonoNotificheDaMostrare) {
			request.setAttribute("ciSonoNotificheDaMostrare", "true");
		}

		getServletConfig().getServletContext().getRequestDispatcher("/jsp/utenti/profilo/profilo.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}