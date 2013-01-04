package it.swim.servlet.profilo.azioni.notifiche;

import it.swim.util.UtenteCollegatoUtil;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import sessionBeans.localInterfaces.GestioneAmicizieLocal;
import sessionBeans.localInterfaces.GestioneCollaborazioniLocal;

import entityBeans.Utente;
import exceptions.LoginException;

/**
 * Servlet implementation class ProfiloAltroUtenteServlet
 */
@Slf4j
public class ProfiloAltroUtenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private GestioneCollaborazioniLocal gestioneCollab;

	@EJB
	private GestioneAmicizieLocal gestioneAmicizie;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProfiloAltroUtenteServlet() {
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

		String emailUtenteRichiedente = (String) request.getParameter("emailUtenteRichiedente");

		log.debug(" ------------------------------------------------------->   " + emailUtenteRichiedente);

		// ottengo utente collegato con riferimento al vero oggetto Utente
		Utente utenteRichiedente;
		try {
			utenteRichiedente = gestioneAmicizie.getUtenteByEmail(emailUtenteRichiedente);
			log.debug(" ------------------------------------------------------->   " + utenteRichiedente);


			// mando sulla request i dati dell'utente tramite il setAttribute, e li
			// contraddistinguo dai 2 parametri passati come string
			request.setAttribute("cognomeRichiedente", utenteRichiedente.getCognome());
			request.setAttribute("nomeRichiedente", utenteRichiedente.getNome());
			request.setAttribute("emailRichiedente", utenteRichiedente.getEmail());

			// ottengo punteggio di feedback dell'utente
			Double punteggio = gestioneCollab.getPunteggioFeedback(emailUtenteRichiedente);
			String feedback;
			if(punteggio==null) {
				feedback = new String("Non disponibile");
			} else {
				feedback = Double.toString(punteggio);
			}
			log.debug("punteggioUtenteCollegato:" + feedback);
			request.setAttribute("punteggioFeedback", feedback);
		} catch (LoginException e) {
			log.error(e.getMessage(), e);
		}

		getServletConfig().getServletContext().getRequestDispatcher("/jsp/utenti/profilo/profiloAltroUtente.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("doPost del dettaglio richiesta amicizia");
		String emailUtenteCollegato = (String) UtenteCollegatoUtil.getEmailUtenteCollegato(request);

		String emailRichiedente = request.getParameter("emailRichiedente");

		if(request.getParameter("tipo").equals("CONFERMA")) {
			log.debug("accettata la richiesta");

			boolean accettata = gestioneAmicizie.accettaAmicizia(emailRichiedente, emailUtenteCollegato);
			if(accettata) {
				//				request.setAttribute("okProfiloAltroUtente", "Hai accettato la richiesta d'aiuto di amicizia");

				//qui dovrei chiamare un metodo per vedere se la richiesta di amicizia era diretta o indiretta
				//se era indiretta vuol dire che non devo fare le righe sotto, altrimenti lo devo fare e mandare a suggerimenti.jsp
				
				//---se e' diretta faccio cio' che segue
				//Visto che ho accettato la richiesta di amicizia visualizzo i suggerimenti di amicizia
				List<Utente> suggeriti = gestioneAmicizie.getSuggerimenti(emailRichiedente, emailUtenteCollegato);

				log.debug("----------:::::::;;;;:_:;_;_:;_:;:_;_:;_:;_:;:_:_;_:;_:;:_;:_;:_;_;:*********;;;");
				for(Utente u : suggeriti) {
					log.debug("----------:::::::;;;;:_:;_;_:;_:;:_;_:;_:;_:;:_:_;_:;_:;:_;:_;:_;_;:*********;;;:  " + u);
				}
				
				if(suggeriti.size()>=1) {
					request.setAttribute("amiciSuggeriti", suggeriti);
					
//					this.gestisciNotificheRichiesteAmicizia(request, response, emailUtenteCollegato);
				} else {
					request.setAttribute("noSuggerimentiDisponibili", "Non ci sono suggerimenti disponibili");
				}
				
				getServletConfig().getServletContext().getRequestDispatcher("/jsp/utenti/profilo/suggerimento.jsp").forward(request, response);
				return;
			} else {
				request.setAttribute("erroreProfiloAltroUtente", "Errore nella conferma della richiesta di amicizia");
			}
		} else {
			if(request.getParameter("tipo").equals("RIFIUTA")) {
				log.debug("rifutare la richiesta");
				this.rifiutaAmicizia(request, response, emailUtenteCollegato, emailRichiedente);
			}
		}
		
		this.gestisciNotificheRichiesteAmicizia(request, response, emailUtenteCollegato);
		getServletConfig().getServletContext().getRequestDispatcher("/jsp/utenti/profilo/notifiche.jsp").forward(request, response);
	}

	private void rifiutaAmicizia(HttpServletRequest request, HttpServletResponse response, String emailUtenteCollegato , String emailRichiedente) {
		boolean rifiutata = gestioneAmicizie.rifiutaAmicizia(emailRichiedente, emailUtenteCollegato);
		if(rifiutata) {
			request.setAttribute("okProfiloAltroUtente", "Hai rifiutato la richiesta d'amicizia");
		} else {
			request.setAttribute("erroreProfiloAltroUtente", "Errore durante il rifuto della richiesta di amicizia");
		}
	}


	private void gestisciNotificheRichiesteAmicizia (HttpServletRequest request, HttpServletResponse response, String emailUtenteCollegato) throws ServletException, IOException {
		List<Utente> utentiCheRichiedonoAmicizia = gestioneAmicizie.getUtentiCheVoglionoAmicizia(emailUtenteCollegato);
		if(utentiCheRichiedonoAmicizia==null) {
			request.setAttribute("erroreGetNotificheRichiesteAmicizia", "Impossibile ottenere le richieste di amicizia");
			getServletConfig().getServletContext().getRequestDispatcher("/jsp/utenti/profilo/notifiche.jsp").forward(request, response);
			return;
		}
		if(utentiCheRichiedonoAmicizia.size()>=1) {
			request.setAttribute("utentiCheRichidonoAmicizia", utentiCheRichiedonoAmicizia);
		} else {
			request.setAttribute("utentiCheRichidonoAmicizia", utentiCheRichiedonoAmicizia);
			request.setAttribute("nonCiSonoRichiesteAmicizia", "Non ci sono nuove richieste di amicizia");
		}
	}
}
