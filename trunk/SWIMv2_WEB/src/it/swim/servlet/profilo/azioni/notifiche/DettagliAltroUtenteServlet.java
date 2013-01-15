package it.swim.servlet.profilo.azioni.notifiche;

import it.swim.util.UtenteCollegatoUtil;

import java.io.IOException;
import java.text.DecimalFormat;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import entityBeans.Amicizia;
import entityBeans.Collaborazione;
import entityBeans.Utente;
import exceptions.CollaborazioneException;
import exceptions.LoginException;
import exceptions.RicercheException;

import sessionBeans.localInterfaces.GestioneAmicizieLocal;
import sessionBeans.localInterfaces.GestioneCollaborazioniLocal;
import sessionBeans.localInterfaces.GestioneRicercheLocal;

/**
 * Servlet implementation class DettagliAltroUtenteServlet
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
		String email = request.getParameter("utente");
		utenteRicercato = email;
		tipoRicerca = request.getParameter("tipoRicerca");

		// se e' null e' perche' l'utente non e' collegato e allora devo fare il
		// redirect alla home
		if (emailUtenteCollegato == null) {
			response.sendRedirect("../../../home");
			return;
		}
		request.setAttribute("utente", ricerche.getUtenteByEmail(email));

		try {
			// ottengo punteggio di feedback dell'utenteRicercato
			Double punteggio = gestCollaborazioni.getPunteggioFeedback(utenteRicercato);
			String feedback;

			if(punteggio==null) {
				feedback = new String("Non disponibile");
			} else {
				DecimalFormat df = new DecimalFormat("#.##");
				feedback = df.format(punteggio);
				request.setAttribute("feedbackUtenteRicercatoStelline", punteggio.intValue() + "");
			}
			request.setAttribute("punteggioFeedback", feedback);
			log.debug("punteggioUtenteRicercato:" + feedback);
		} catch (LoginException e) {
			log.error(e.getMessage(), e);
		}


		try {
			request.setAttribute("listaAbilita", ricerche.insiemeAbilitaPersonaliUtente(email));
		} catch (RicercheException e) {
			log.error(e.getMessage(), e);
		}

		boolean amiciziaGiaInoltrata = amicizie.amiciziaInoltrata(emailUtenteCollegato, utenteRicercato);
		request.setAttribute("amiciziaGiaInoltrata", amiciziaGiaInoltrata);

		request.setAttribute("tipoRicerca", tipoRicerca);
		getServletConfig().getServletContext().getRequestDispatcher("/jsp/utenti/profilo/dettagliAltroUtente.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String emailUtenteCollegato = (String) UtenteCollegatoUtil.getEmailUtenteCollegato(request);
		boolean amiciziaGiaInoltrata; //variabile che uso dopo

		//setto l'utente ricercato in "utente"
		Utente utente = ricerche.getUtenteByEmail(utenteRicercato);
		request.setAttribute("utente", utente);

		if(utente==null) {
			request.setAttribute("erroreGetUtente", "Errore durante la ricerca dell'utente");
			getServletConfig().getServletContext().getRequestDispatcher("/jsp/utenti/profilo/dettagliAltroUtente.jsp").forward(request, response);
		}

		//setto il tipo di ricerca
		request.setAttribute("tipoRicerca", tipoRicerca);

		if(request.getParameter("tipo").equals("amicizia")) {
			amiciziaGiaInoltrata = amicizie.amiciziaInoltrata(emailUtenteCollegato, utenteRicercato);
			if(!amiciziaGiaInoltrata) {
				Amicizia amicizia = amicizie.richiediAmicizia(emailUtenteCollegato, utenteRicercato, true);
				if(amicizia==null) {
					//non e' stato possibile stringere amicizia
					request.setAttribute("erroreAmiciziaNonStretta", "Errore, duranta la richiesta di amicizia");
				} else {
					//amicizia stretta correttamente
					request.setAttribute("amiciziaStrettaCorrettamente", "Hai stretto amicizia con " + utente.getNome() + " "  + utente.getCognome());
				}
			} else {
				//amicizia gia' inoltrata, quindi non richiedo amicizia di nuovo
				request.setAttribute("erroreAmiciziaGiaInoltrata", "Errore, amicizia gia' richiesta");
			}
		} else {
			try {
				Collaborazione collaborazione = gestCollaborazioni.richiediAiuto(emailUtenteCollegato, utenteRicercato, request.getParameter("nomeCollaborazione"), request.getParameter("descrizioneCollaborazione"));
				if(collaborazione==null) {
					//non e' stato possibile creare la collaborazione
					request.setAttribute("erroreCollaborazioneNonCreata", "Errore durante la richiesta di aiuto");
				} else {
					//collaborazione creata correttamente
					request.setAttribute("collaborazioneCreataCorrettamente", "Richiesta di aiuto inviata correttamente");
				}
			} catch (CollaborazioneException e) {
				request.setAttribute("erroreCampiVuotiCollaborazione","Devi inserire il nome della richiesta di aiuto");
			} catch (LoginException e) {
				log.error(e.getMessage(), e);
			}
		}

		//ottengo la lista delle abilita' per poterle rivisualizzare nella pagina
		try {
			request.setAttribute("listaAbilita", ricerche.insiemeAbilitaPersonaliUtente(utenteRicercato));
		} catch (RicercheException e) {
			log.error(e.getMessage(), e);
		}


		try {
			// ottengo punteggio di feedback dell'utenteRicercato
			Double punteggio = gestCollaborazioni.getPunteggioFeedback(utenteRicercato);
			String feedback;
			if(punteggio==null) {
				feedback = new String("Non disponibile");
			} else {
				DecimalFormat df = new DecimalFormat("#.##");
				feedback = df.format(punteggio);
				request.setAttribute("feedbackUtenteRicercatoStelline", punteggio.intValue() + "");
			}
			request.setAttribute("punteggioFeedback", feedback);
			log.debug("punteggioUtenteRicercato:" + feedback);
		} catch (LoginException e) {
			log.error(e.getMessage(), e);
		}

		//verifico se l'amicizia e' gia' stata inoltrata, in questo caso con la post, cioe' quando ricarico la pagina dopo aver premuto un pulsante
		//nota bene che amicizie.amiciziaInoltrata() lo chiamo anche prima per vedere se posso stringere amicizia
		//in questo punto lo chiamo per settare amiciziaGiaInoltrata, che mi fa capire se devo mostrare il pulsante per richiedere amicizia
		//al caricamento della pagina dopo la doPost.
		amiciziaGiaInoltrata = amicizie.amiciziaInoltrata(emailUtenteCollegato, utenteRicercato);
		request.setAttribute("amiciziaGiaInoltrata", amiciziaGiaInoltrata);

		getServletConfig().getServletContext().getRequestDispatcher("/jsp/utenti/profilo/dettagliAltroUtente.jsp").forward(request, response);
	}

}
