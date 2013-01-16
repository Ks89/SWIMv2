package it.swim.servlet.profilo.azioni.notifiche;

import it.swim.util.UtenteCollegatoUtil;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import sessionBeans.localInterfaces.GestioneAmicizieLocal;
import sessionBeans.localInterfaces.GestioneCollaborazioniLocal;

import entityBeans.Amicizia;
import entityBeans.Utente;
import exceptions.AmiciziaException;
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
			response.sendRedirect("../../../home");
			return;
		}

		String emailUtenteRichiedente = (String) request.getParameter("emailUtenteRichiedente");

		// ottengo utente collegato con riferimento al vero oggetto Utente
		Utente utenteRichiedente;
		try {
			utenteRichiedente = gestioneAmicizie.getUtenteByEmail(emailUtenteRichiedente);

			if(utenteRichiedente!=null) {
				// mando sulla request i dati dell'utente tramite il setAttribute, e li
				// contraddistinguo dai 2 parametri passati come string
				request.setAttribute("cognomeRichiedente", utenteRichiedente.getCognome());
				request.setAttribute("nomeRichiedente", utenteRichiedente.getNome());
				request.setAttribute("emailRichiedente", utenteRichiedente.getEmail());
			} else {
				//se non riesco a ottenere l'Utente dalla mail della persona che mi ha richiesto amicizia
				//devo segnalare un errore
				request.setAttribute("erroreIndividuazUtenteRichiedente", "Impossibile identificare l'utente scelto");
			}


			// ottengo punteggio di feedback dell'utente richiedente
			Double punteggio = gestioneCollab.getPunteggioFeedback(emailUtenteRichiedente);
			String feedback;
			if(punteggio==null) {
				feedback = new String("Non disponibile");
			} else {
				DecimalFormat df = new DecimalFormat("#.##");
				feedback = df.format(punteggio);
				request.setAttribute("feedbackStellineRichiedente", punteggio.intValue() + "");
			}
			request.setAttribute("punteggioFeedbackRichiedente", feedback);
			log.debug("punteggioFeedbackRichiedente:" + feedback);
			
		} catch(LoginException e) {
			log.error(e.getMessage(), e);
			//se ci fosse un errore durante il calcolo del feedback lo setto come non disponibile
			request.setAttribute("punteggioFeedbackRichiedente", "Non disponibile");
		}
		getServletConfig().getServletContext().getRequestDispatcher("/jsp/utenti/profilo/profiloAltroUtente.jsp").forward(request, response);
	}

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("doPost del profilo altro altro utente, cioe' della richiesta amicizia");
		String emailUtenteCollegato = (String) UtenteCollegatoUtil.getEmailUtenteCollegato(request);

		String emailRichiedente = request.getParameter("emailRichiedente");

		
		//entro qua solo se accetto richieste di suggerimenti, cioe' dalla form di profiloAltroUtente.jsp nell'otherwise della choose
		//amiciSuggeriti non e' altro che il nome delle checkbox
		if(request.getParameter("amiciSuggeriti")!=null) {

			List<Amicizia> amiciziaSuggeriteDaRichiedere  = new ArrayList<Amicizia>();
			Amicizia amiciziaRichiesta;

			for(String emailSuggerimento : request.getParameterValues("amiciSuggeriti")) {
				//se l'amicizia non e' gia' stata inoltrata in precedenza, la richiedo, altrimenti no
				if(!gestioneAmicizie.amiciziaInoltrata(emailUtenteCollegato, emailSuggerimento)) {
					amiciziaRichiesta = gestioneAmicizie.richiediAmicizia(emailUtenteCollegato, emailSuggerimento, false );
					if(amiciziaRichiesta!=null) {
						amiciziaSuggeriteDaRichiedere.add(amiciziaRichiesta);
					}
				}
			}

			//se hai stretto amicizia con quelli selezionati
			if(amiciziaSuggeriteDaRichiedere.size()>=1) {
				request.setAttribute("suggAccettati","Hai stretto amicizia con gli utenti suggeriti");
			} else {
				request.setAttribute("nonSonoStatiAccettatiSugg","Hai stretto amicizia e nessun suggerimento accettato");
			}

			this.gestisciNotificheRichiesteAmicizia(request, response, emailUtenteCollegato);
			getServletConfig().getServletContext().getRequestDispatcher("/jsp/utenti/profilo/notifiche.jsp").forward(request, response);
			return;
		}

		
		//se clicco su conferma per accettare la richiesta di amicizia
		if((request.getParameter("tipo"))!=null && request.getParameter("tipo").equals("CONFERMA")) {
			log.debug("accettata la richiesta");

			try {
				Amicizia amiciziaAccettata = gestioneAmicizie.accettaAmicizia(emailRichiedente, emailUtenteCollegato);


				// se l'amicizia accettata e' indiretta perche' nata da un suggerimento da parte dell'utente richiedente (emailutente1), allora
				// non deve mostrare suggerimenti ma tornare subito alla pagina delle notifiche.
				if(!amiciziaAccettata.isDiretta()) {
					request.setAttribute("amiciziaIndirettaStretta", "Hai stretto amicizia con un utente suggerito dal sistema!");
					this.gestisciNotificheRichiesteAmicizia(request, response, emailUtenteCollegato);
					getServletConfig().getServletContext().getRequestDispatcher("/jsp/utenti/profilo/notifiche.jsp").forward(request, response);
					return;
//					response.sendRedirect("notifiche");
//					return;
				}

				//---se e' diretta faccio cio' che segue
				//Visto che ho accettato la richiesta di amicizia visualizzo i suggerimenti di amicizia
				List<Utente> suggeriti = gestioneAmicizie.getSuggerimenti(emailRichiedente, emailUtenteCollegato);


				if(suggeriti==null) {
					request.setAttribute("noSuggDisponibili", "Amicizia stretta! Non ci sono suggerimenti d'amicizia");
					this.gestisciNotificheRichiesteAmicizia(request, response, emailUtenteCollegato);
					getServletConfig().getServletContext().getRequestDispatcher("/jsp/utenti/profilo/notifiche.jsp").forward(request, response);
					return;
				} else {
					this.gestisciNotificheRichiesteAmicizia(request, response, emailUtenteCollegato);
					if(suggeriti.size()>=1) {
						request.setAttribute("amiciSuggeriti", suggeriti);
					} else {
						//se non ci sono suggerimenti disponibili setto un messaggio d'avvio
						request.setAttribute("noSuggDisponibili", "Amicizia stretta! Non ci sono suggerimenti d'amicizia disponibili");
						getServletConfig().getServletContext().getRequestDispatcher("/jsp/utenti/profilo/notifiche.jsp").forward(request, response);
						return;
					}
				}
				
				getServletConfig().getServletContext().getRequestDispatcher("/jsp/utenti/profilo/profiloAltroUtente.jsp").forward(request, response);
				return;
			} catch (AmiciziaException e) {
				//gestire l'eccezione come si deve
				log.error(e.getMessage(), e);
				request.setAttribute("erroreProfiloAltroUtente", "Errore nella conferma della richiesta di amicizia");
			}
		} else {
			//se ho premuto su RIFIUTA
			if((request.getParameter("tipo"))!=null && request.getParameter("tipo").equals("RIFIUTA")) {
				log.debug("rifutare la richiesta");
				this.rifiutaAmicizia(request, response, emailUtenteCollegato, emailRichiedente);
			}
		}

		//in ogni caso, se arrivo qui, per sicurezza rigenero la lista delle richiste di amicizia della pagina notifiche
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
			log.debug("gestisciNotificheRichiesteAmicizia() : utentiCheRichiedonoAmicizia==null");
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
