package it.swim.servlet.admin;

import it.swim.util.AdminCollegatoUtil;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import sessionBeans.localInterfaces.GestioneProposteLocal;

import entityBeans.Abilita;
import entityBeans.PropostaAbilita;

/**
 * Servlet implementation class DettaglioPropostaServlet
 */
@Slf4j
public class DettaglioPropostaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private GestioneProposteLocal gestioneProposte;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DettaglioPropostaServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		log.debug("Entrato in dettaglio proposta abilita");

		Long idProposta = Long.parseLong(request.getParameter("idProposta"));
		
		log.debug("Entrato in dettaglio proposta abilita con id: " + idProposta);

		// ottengo l'email dell'admin collegato dalla sessione, appoggiandomi
		// ad una classe di utilita'
		String emailAdminCollegato = (String) AdminCollegatoUtil.getEmailAdminCollegato(request);

		// se e' null e' perche' l'admin non e' collegato e allora devo fare il
		// redirect alla homeadmin
		if (emailAdminCollegato == null) {
			response.sendRedirect("../admin");
			return;
		}

		PropostaAbilita propostaAbilita = gestioneProposte.getPropostaAbilitaById(idProposta);

		if(propostaAbilita!=null) {
			request.setAttribute("idProposta", propostaAbilita.getId());
			request.setAttribute("emailProposta", propostaAbilita.getUtente().getEmail());
			request.setAttribute("abilitaProposta", propostaAbilita.getAbilitaProposta());
			request.setAttribute("motivazioneProposta", propostaAbilita.getMotivazione());
			getServletConfig().getServletContext().getRequestDispatcher("/jsp/dettaglioProposta.jsp").forward(request, response);
			return;
		} else {

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		log.debug("doPost del dettaglio proposta");
		
		Long idPropostaAbilita = Long.parseLong(request.getParameter("idProposta"));
		String nuovoNomeAbilitaProposta = request.getParameter("nuovoNomeAbilitaProposta");
		String descrizioneAbilita = request.getParameter("descrizioneAbilitaProposta");

		Abilita abilitaInserita = gestioneProposte.confermaPropostaAbilitaSpecificandoAttributi(idPropostaAbilita, nuovoNomeAbilitaProposta, descrizioneAbilita);

		if(abilitaInserita!=null) {
			log.debug("nuova abilita inserita correttamente: " + abilitaInserita.getNome());
		} else {
			request.setAttribute("erroreInserimentoPropostaFallito", "Errore inserimento nuova abilita con nome: " + nuovoNomeAbilitaProposta);
		}
		
		List<PropostaAbilita> proposteAbilita = gestioneProposte.getProposteAbilitaNonConfermate();
		request.setAttribute("proposte", proposteAbilita);
		getServletConfig().getServletContext().getRequestDispatcher("/jsp/adminpanelproposte.jsp").forward(request, response);
	}
}
