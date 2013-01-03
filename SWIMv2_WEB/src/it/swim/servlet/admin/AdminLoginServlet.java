package it.swim.servlet.admin;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sessionBeans.localInterfaces.GestioneLoginLocal;

import exceptions.HashingException;
import exceptions.LoginException;

import lombok.extern.slf4j.Slf4j;

/**
 * Servlet implementation class AdminLoginServlet
 */
@Slf4j
public class AdminLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private GestioneLoginLocal login;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminLoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if ("true".equals(request.getParameter("adminesci"))) {
			log.debug("Logout admin");
			request.getSession().invalidate();
			response.sendRedirect("../admin");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("Entrato in Adminlogin");
		// Ricevo la form AdminLogin dopo aver cliccato su submit in admin.jsp
		// Estraggo i parametri passati tramite post dalla form AdminLogin
		String emailAdmin = request.getParameter("emailAdmin");
		String password = request.getParameter("passwordAdmin");

		// esego il login e vedo il risultato
		boolean logineEseguitoCorrettamente = false;
		try {
			logineEseguitoCorrettamente = login.eseguiLoginAmministratore(emailAdmin, password);
			log.debug("Esito: " + logineEseguitoCorrettamente);

			if (logineEseguitoCorrettamente) {
				// se il login riesce setto nella sessione l'email dell'amministratore
				// che si e' loggato
				request.getSession().setAttribute("adminCollegato", emailAdmin);
				// processata la request, uso la response per fare il redirect
				// alla pagina dell'amministratore
				response.sendRedirect("../admin");
			} else {
				// se il login fallisce, metto le request un messaggio di errore
				// sotto il nome di erroreLoginFallito
				request.setAttribute("erroreLoginAdminFallito", "Email o password non riconosciute");
				getServletConfig().getServletContext().getRequestDispatcher("/jsp/admin/homeAdmin.jsp").forward(request, response);
			}

		} catch (LoginException e) {
			log.error(e.getMessage(), e);
			if (e.getCausa() == LoginException.Causa.ALCUNIPARAMETRINULLIOVUOTI) {
				request.setAttribute("erroreLoginAdminFallito", "LoginException: Email o password non riconosciute");
			} else {
				if (e.getCausa() == LoginException.Causa.ERRORESCONOSCIUTO) {
					request.setAttribute("erroreLoginAdminFallito", "LoginException: Errore durante il login - causa sconosciuta");
				} else {
					request.setAttribute("erroreLoginAdminFallito", "LoginException: Errore durante il login - causa sconosciuta");
				}
			}
			getServletConfig().getServletContext().getRequestDispatcher("/jsp/admin/homeAdmin.jsp").forward(request, response);
		} catch (HashingException e) {
			log.error(e.getMessage(), e);
			if (e.getCausa() == HashingException.Causa.ALCUNIPARAMETRINULLIOVUOTI) {
				request.setAttribute("erroreLoginAdminFallito", "HashingException: Errore passaggio parametri");
			} else {
				if (e.getCausa() == HashingException.Causa.ERRORESCONOSCIUTO) {
					request.setAttribute("erroreLoginAdminFallito", "HashingException: causa sconosciuta");
				} else {
					request.setAttribute("erroreLoginAdminFallito", "HashingException: ausa sconosciuta");
				}
			}
			getServletConfig().getServletContext().getRequestDispatcher("/jsp/admin/homeAdmin.jsp").forward(request, response);
		}
	}

}