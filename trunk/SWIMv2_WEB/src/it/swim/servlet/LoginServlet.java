package it.swim.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exceptions.HashingException;
import exceptions.LoginException;

import sessionBeans.localInterfaces.GestioneLoginLocal;

import lombok.extern.slf4j.Slf4j;

/**
 * Servlet implementation class LoginServlet
 */
@Slf4j
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private GestioneLoginLocal login;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// metodo che crea un login fasullo per un accesso rapido al sito
		// durante i test (in fase di sviluppo)
		// se il parametro che ricevo tramite get e' secretlogin=12345 permetto
		// il login autoamtico
		if ("12345".equals(request.getParameter("secretlogin"))) {
			request.getSession().setAttribute("utenteCollegato", "peppino@gmail.com");
			response.sendRedirect("Profilo/Profilo");
		}
		// se il parametro che ricevo tramite get e' esci e vale = true eseguo
		// il logout
		// questa condizione if sara' da tenere anche dopo perche' e' quella che
		// permette il logout, sia di un utente vero che con login automatico
		else if ("true".equals(request.getParameter("esci"))) {
			log.debug("Logout");
			request.getSession().invalidate();
			response.sendRedirect("Home");
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Ricevo la form Login dopo aver cliccato su submit in home.jsp
		// Estraggo i parametri passati tramite post dalla form Login
		String emailUtente = request.getParameter("emailUtente");
		String password = request.getParameter("password");
		log.debug(emailUtente + " - " + password);

		// esego il login e vedo il risultato
		boolean logineEseguitoCorrettamente = false;
		try {
			logineEseguitoCorrettamente = login.esegueLoginUtente(emailUtente, password);
			log.debug("Esito: " + logineEseguitoCorrettamente);

			if (logineEseguitoCorrettamente) {
				// se il login riesce setto nella sessione l'email dell'utente che
				// si e' loggato
				request.getSession().setAttribute("utenteCollegato", emailUtente);
				// processata la request, uso la response per fare il redirect alla pagina del profilo dell'utente
				response.sendRedirect("Profilo/Profilo");
			} else {
				//se il login fallisce, metto le request un messaggio di errore sotto il nome di erroreLoginFallito
				request.setAttribute("erroreLoginFallito", "Email o password non riconosciute");
				getServletConfig().getServletContext().getRequestDispatcher("/jsp/home.jsp").forward(request, response);
			}

		} catch (LoginException e) {
			log.error(e.getMessage(), e);
			if(e.getCausa()==LoginException.Causa.ALCUNIPARAMETRINULLIOVUOTI) {
				request.setAttribute("erroreLoginFallito", "LoginException: Email o password non riconosciute");
			} else {
				if(e.getCausa()==LoginException.Causa.ERRORESCONOSCIUTO) {
					request.setAttribute("erroreLoginFallito", "LoginException: Errore durante il login - causa sconosciuta");
				} else {
					request.setAttribute("erroreLoginFallito", "LoginException: Errore durante il login - causa sconosciuta");
				}
			}
			getServletConfig().getServletContext().getRequestDispatcher("/jsp/home.jsp").forward(request, response);
		} catch (HashingException e) {
			log.error(e.getMessage(), e);
			if(e.getCausa()==HashingException.Causa.ALCUNIPARAMETRINULLIOVUOTI) {
				request.setAttribute("erroreLoginFallito", "HashingException: Errore passaggio parametri");
			} else {
				if(e.getCausa()==HashingException.Causa.ERRORESCONOSCIUTO) {
					request.setAttribute("erroreLoginFallito", "HashingException: causa sconosciuta");
				} else {
					request.setAttribute("erroreLoginFallito", "HashingException: ausa sconosciuta");
				}
			}
			getServletConfig().getServletContext().getRequestDispatcher("/jsp/home.jsp").forward(request, response);
		}
	}

}
