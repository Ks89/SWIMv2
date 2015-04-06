/*
Copyright 2012-2015 Stefano Cappa, Jacopo Bulla, Davide Caio
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
    http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

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
import sessionBeans.localInterfaces.GestioneRicercheLocal;

import lombok.extern.slf4j.Slf4j;

/**
 * Servlet implementation class LoginServlet
 */
@Slf4j
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private GestioneLoginLocal login;

	@EJB
	private GestioneRicercheLocal ricerche;
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

		// se il parametro che ricevo tramite get e' esci e vale = true eseguo
		// il logout
		// questa condizione if sara' da tenere anche dopo perche' e' quella che
		// permette il logout, sia di un utente vero che con login automatico
		if ("true".equals(request.getParameter("esci"))) {
			log.debug("logout");
			request.getSession().invalidate();
			response.sendRedirect("home");
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
				request.getSession().setAttribute("nomeUtenteCollegato", ricerche.getUtenteByEmail(emailUtente).getNome());
				request.getSession().setAttribute("cognomeUtenteCollegato", ricerche.getUtenteByEmail(emailUtente).getCognome());
				// processata la request, uso la response per fare il redirect alla pagina del profilo dell'utente
				response.sendRedirect("profilo/profilo");
			} else {
				//se il login fallisce, metto le request un messaggio di errore sotto il nome di erroreLoginFallito
				request.setAttribute("erroreLoginFallito", "Email o password non riconosciute");
				getServletConfig().getServletContext().getRequestDispatcher("/jsp/utenti/home.jsp").forward(request, response);
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
			getServletConfig().getServletContext().getRequestDispatcher("/jsp/utenti/home.jsp").forward(request, response);
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
			getServletConfig().getServletContext().getRequestDispatcher("/jsp/utenti/home.jsp").forward(request, response);
		}
	}

}
