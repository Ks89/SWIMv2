package it.swim.servlet.profilo.azioni;

import it.swim.util.UtenteCollegatoUtil;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entityBeans.PropostaAbilita;
import exceptions.ProposteException;

import sessionBeans.localInterfaces.GestioneProposteLocal;

import lombok.extern.slf4j.Slf4j;

/**
 * Servlet implementation class ProposteAbilitaServlet
 */
@Slf4j
public class ProposteAbilitaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private GestioneProposteLocal gestioneProposte;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProposteAbilitaServlet() {
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
		
		log.debug("Servlet proposteabilita richiesta dall'utente : " + emailUtenteCollegato);
		
		// se e' null e' perche' l'utente non e' collegato e allora devo fare il
		// redirect alla home
		if (emailUtenteCollegato == null) {
			response.sendRedirect("../home");
		}
		getServletConfig().getServletContext().getRequestDispatcher("/jsp/proposteabilita.jsp").forward(request, response);
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
			response.sendRedirect("../home");
			return;
		}
		
		String nomeAbilita = request.getParameter("nomeAbilita");
		String descrizioneAbilita = request.getParameter("descrizioneAbilita");
		log.debug(nomeAbilita + " - " + descrizioneAbilita);

		try {
			PropostaAbilita propostaAbilita = gestioneProposte.inserisciPropostaUtente(emailUtenteCollegato, nomeAbilita, descrizioneAbilita);
			if(propostaAbilita!=null) {
				request.setAttribute("inserimentoPropostaAvvenuto", "Inserimento proposta con abilita " + propostaAbilita.getAbilitaProposta() + " avvenuto con successo!");
			} else {
				request.setAttribute("erroreInserimentoProposta", "Errore inserimento proposta di abilita");
			}
		} catch (ProposteException e) {
			log.error(e.getMessage(), e);
			request.setAttribute("erroreInserimentoProposta", "Errore inserimento proposta di abilita");
		}
		getServletConfig().getServletContext().getRequestDispatcher("/jsp/proposteabilita.jsp").forward(request, response);
	}

}
