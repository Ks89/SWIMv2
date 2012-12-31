package it.swim.servlet;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sessionBeans.localInterfaces.GestioneRicercheLocal;

import entityBeans.Abilita;

import lombok.extern.slf4j.Slf4j;

/**
 * Servlet implementation class HomeServlet
 */
@Slf4j
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private GestioneRicercheLocal ricerche;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HomeServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("Visualizza home page");

		List<Abilita> ab = ricerche.insiemeAbilitaGenerali();
		request.setAttribute("abilita", ab);

		getServletConfig().getServletContext().getRequestDispatcher("/jsp/home.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
