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

import entityBeans.PropostaAbilita;

/**
 * Servlet implementation class AdminPanelServlet
 */
@Slf4j
public class AdminPanelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private GestioneProposteLocal gestioneProposte;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminPanelServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		log.debug("Entrato nel pannello di amministrazione");
		
		// ottengo l'email dell'admin collegato dalla sessione, appoggiandomi
		// ad una classe di utilita'
		String emailAdminCollegato = (String) AdminCollegatoUtil.getEmailAdminCollegato(request);

		// se e' null e' perche' l'admin non e' collegato e allora devo fare il
		// redirect alla homeadmin
		if (emailAdminCollegato == null) {
			response.sendRedirect("../admin");
			return;
		}

		if ("1".equals(request.getParameter("operazione"))) {
			getServletConfig().getServletContext().getRequestDispatcher("/jsp/adminpanelinserimento.jsp").forward(request, response);
			return;
		} else {
			if ("2".equals(request.getParameter("operazione"))) {
				List<PropostaAbilita> proposteAbilita = gestioneProposte.getProposteAbilitaNonConfermate();
				request.setAttribute("proposte", proposteAbilita);
				getServletConfig().getServletContext().getRequestDispatcher("/jsp/adminpanelproposte.jsp").forward(request, response);
				return;
			} else {
				response.sendRedirect("../admin/adminPanel");
				getServletConfig().getServletContext().getRequestDispatcher("/jsp/homeadmin.jsp").forward(request, response);
				return;
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
