package it.swim.servlet.profilo.azioni.notifiche;

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
import exceptions.CollaborazioneException;

import lombok.extern.slf4j.Slf4j;

/**
 * Servlet implementation class DettaglioRichiestaAiutoServlet
 */
@Slf4j
public class DettaglioRichiestaAiutoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	@EJB
	private GestioneCollaborazioniLocal gestioneCollab;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DettaglioRichiestaAiutoServlet() {
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

		log.debug("Entrato in dettaglio proposta abilita");

		Long idCollaborazione = Long.parseLong(request.getParameter("idCollaborazione"));

		log.debug("Entrato in dettaglio proposta abilita con id: " + idCollaborazione);

		try {
			Collaborazione collaborazione = gestioneCollab.getCollaborazione(idCollaborazione);
			if(collaborazione!=null) {
				request.setAttribute("idCollaborazione", collaborazione.getId());
				request.setAttribute("nomeRichiedeAiuto", collaborazione.getUtenteRichiedente().getNome());
				request.setAttribute("cognomeRichiedeAiuto", collaborazione.getUtenteRichiedente().getCognome());
				request.setAttribute("nomeCollaborazione", collaborazione.getNome());
				request.setAttribute("descrizioneCollaborazione", collaborazione.getDescrizione());
				getServletConfig().getServletContext().getRequestDispatcher("/jsp/utenti/profilo/dettaglioRichiestaAiuto.jsp").forward(request, response);
				return;
			} else {

			}
		} catch (CollaborazioneException e) {
			log.error(e.toString(), e);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("doPost del dettaglio richiesta aiuto");
		String emailUtenteCollegato = (String) UtenteCollegatoUtil.getEmailUtenteCollegato(request);
 
		
		if(request.getParameter("tipo").equals("CONFERMA")) {
			log.debug("accettare la collaborazione");
			this.accettaCollaborazione(request, response, Long.parseLong(request.getParameter("idCollaborazione")));
		} else {
			if(request.getParameter("tipo").equals("RIFIUTA")) {
				log.debug("rifutare la collaborazione");
				this.rifiutaCollaborazione(request, response, Long.parseLong(request.getParameter("idCollaborazione")));
			}
		}
		
		this.gestisciNotificheRichiesteAiuto(request, response, emailUtenteCollegato);
		
		getServletConfig().getServletContext().getRequestDispatcher("/jsp/utenti/profilo/notifiche.jsp").forward(request, response);
	}

	
	private void rifiutaCollaborazione(HttpServletRequest request, HttpServletResponse response, Long idCollaborazione) {
		try {
			boolean rifiutata = gestioneCollab.rifiutaCollaborazione(idCollaborazione);
			if(rifiutata) {
				request.setAttribute("okDettaglioRichiestaAiuto", "Hai rifiutato la richiesta d'aiuto");
			} else {
				request.setAttribute("erroreDettaglioRichiestaAiuto", "Errore durante in rifuto della richiesta di aiuto");
			}
		} catch (CollaborazioneException e) {
			request.setAttribute("erroreDettaglioRichiestaAiuto", "Errore durante in rifuto della richiesta di aiuto");
			log.error(e.toString(), e);
		}
	}
	
	
	private void accettaCollaborazione(HttpServletRequest request, HttpServletResponse response, Long idCollaborazione) {
		try {
			Collaborazione collaborazione = gestioneCollab.accettaCollaborazione(idCollaborazione);
			if(collaborazione!=null) {
				request.setAttribute("okDettaglioRichiestaAiuto", "Hai accettato la richiesta d'aiuto di " + collaborazione.getUtenteRichiedente().getNome() + " " + collaborazione.getUtenteRichiedente().getCognome());
			} else {
				request.setAttribute("erroreDettaglioRichiestaAiuto", "Errore nella conferma della richiesta di aiuto");
			}
		} catch (CollaborazioneException e) {
			request.setAttribute("erroreDettaglioRichiestaAiuto", "Errore nella conferma della richiesta di aiuto");
			log.error(e.toString(), e);
		}
	}
	
	
	private void gestisciNotificheRichiesteAiuto(HttpServletRequest request, HttpServletResponse response, String emailUtenteCollegato) throws ServletException, IOException {
		try {
			List<Collaborazione> collaborazioni = gestioneCollab.getNotificheRichiesteAiuto(emailUtenteCollegato);

			if(collaborazioni==null) { //TODO attenzione, PERCHE' l'ho scritto qui il TODO, cosa doveva indicare????????????????????????????????????
				request.setAttribute("erroreGetNotificheRichiesteAiuto", "Impossibile ottenere le richieste di aiuto");
				getServletConfig().getServletContext().getRequestDispatcher("/jsp/utenti/profilo/notifiche.jsp").forward(request, response);
				return;
			}
			if(collaborazioni.size()>=1) {
				request.setAttribute("richiesteAiuto", collaborazioni);
			} else {
				request.setAttribute("richiesteAiuto", collaborazioni);
				request.setAttribute("nonCiSonoRichiesteAiuto", "Non ci sono nuove richieste di aiuto");
			}
		} catch (CollaborazioneException e) {
			log.error(e.getMessage(), e);
			request.setAttribute("erroreGetNotificheRichiesteAiuto", "Impossibile ottenere le richieste di amicizia");
		}
	}
}
