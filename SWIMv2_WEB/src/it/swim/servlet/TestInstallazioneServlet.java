package it.swim.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import entityBeans.Amministratore;
import exceptions.LoginException;

import sessionBeans.localInterfaces.GestioneLoginLocal;

/**
 * Servlet implementation class TestInstallazioneServlet
 */
@Slf4j
public class TestInstallazioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB
	private GestioneLoginLocal login;

	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestInstallazioneServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String emailAdmin = request.getParameter("emailAdmin");
		
		try {
			Amministratore admin = login.getAmministratoreByEmail(emailAdmin);
			
			log.debug("-----------------------------------TEST CONNESSIONE: " + admin);
			
			if(admin!=null) {
				request.setAttribute("testOk", "Test connessione al database: esito positivo");
			} else {
				request.setAttribute("testFallito", "Test connessione al database: esito negativo");
			}
		} catch (LoginException e) {
			request.setAttribute("testFallito", "Test connessione al database: esito negativo");
		}
		getServletConfig().getServletContext().getRequestDispatcher("/jsp/test.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
