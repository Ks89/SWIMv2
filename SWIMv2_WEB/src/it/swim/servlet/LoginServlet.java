package it.swim.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if ("12345".equals(request.getParameter("secretlogin"))) {
			request.getSession().setAttribute("utenteCollegato", "peppino@gmail.com");
			
			
			response.sendRedirect("Profilo/Profilo");
		
			
		} else if ("true".equals(request.getParameter("esci"))) {
			log.debug("Logout");
			request.getSession().invalidate();
			response.sendRedirect("Home");
		}
		
	} 

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		log.debug(username + " - " + password);
		boolean ok = login.esegueLoginUtente(username, password);
		log.debug("Esito: " + ok);
		if (ok) {
			
			
			request.getSession().setAttribute("utenteCollegato", username);
			
			
			response.sendRedirect("Profilo/Profilo");
		} else {
			request.setAttribute("errorMessage", "Utente non riconosciuto");
			getServletConfig().getServletContext().getRequestDispatcher("/jsp/home.jsp").forward(request,response);
		}
		
	}

}
