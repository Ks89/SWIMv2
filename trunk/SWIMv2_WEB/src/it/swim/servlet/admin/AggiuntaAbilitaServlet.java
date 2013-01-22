package it.swim.servlet.admin;

import it.swim.util.AdminCollegatoUtil;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import sessionBeans.localInterfaces.GestioneProposteLocal;

import entityBeans.Abilita;
import exceptions.ProposteException;

/**
 * Servlet implementation class AggiuntaAbilitaServlet
 */
@Slf4j
public class AggiuntaAbilitaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private GestioneProposteLocal gestioneProposte;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AggiuntaAbilitaServlet() {
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
		// ottengo l'email dell'admin collegato dalla sessione, appoggiandomi
		// ad una classe di utilita'
		String emailAdminCollegato = (String) AdminCollegatoUtil.getEmailAdminCollegato(request);

		// se e' null e' perche' l'admin non e' collegato e allora devo fare il
		// redirect alla homeadmin
		if (emailAdminCollegato == null) {
			response.sendRedirect("../admin");
			return;
		}

		String nuovoNomeAbilitaAggiunta = request.getParameter("nuovoNomeAbilitaAggiunta");
		String descrizioneAbilita = request.getParameter("descrizioneAbilitaAggiunta");

		Abilita giaPresente,abilitaAggiunta;
		try {

			if(nuovoNomeAbilitaAggiunta!=null && nuovoNomeAbilitaAggiunta.length()>=1) { 
				giaPresente = gestioneProposte.getAbilitaByNome(nuovoNomeAbilitaAggiunta);
				if(giaPresente==null) {
					abilitaAggiunta = gestioneProposte.inserisciAbilitaAutonomamente(emailAdminCollegato, nuovoNomeAbilitaAggiunta, descrizioneAbilita);
					if(abilitaAggiunta!=null) {
						log.debug("nuova abilita inserita correttamente: " + abilitaAggiunta.getNome());
						request.setAttribute("inserimentoAbilitaAvvenuto", "Inserimento abilita' avvenuto con successo!");
					} else {
						log.debug("erroreInserimentoAbilitaFallito : " + nuovoNomeAbilitaAggiunta);
						request.setAttribute("erroreInserimentoAbilitaFallito", "Errore aggiunta nuova abilita'");
					}
				} else {
					request.setAttribute("abilitaGiaPresenteColNomeSpecificato", "Errore! L'abilita' con il nome specificato e' gia' presente");
				}
			}
		} catch (ProposteException e) {
			log.error(e.getMessage(), e);
			request.setAttribute("erroreInserimentoAbilitaFallito", "Errore aggiunta nuova abilita con nome");
		}
		getServletConfig().getServletContext().getRequestDispatcher("/jsp/admin/adminpanelinserimento.jsp").forward(request, response);
	}
}
