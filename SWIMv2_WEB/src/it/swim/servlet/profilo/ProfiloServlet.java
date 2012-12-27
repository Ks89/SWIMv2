package it.swim.servlet.profilo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sessionBeans.localInterfaces.GestioneCollaborazioniLocal;

import lombok.extern.slf4j.Slf4j;
import entityBeans.Abilita;
import entityBeans.Collaborazione;

/**
 * Servlet implementation class ProfiloServlet
 */
@Slf4j
public class ProfiloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB
	private GestioneCollaborazioniLocal collab;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfiloServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("utenteCollegato") == null) {
			response.sendRedirect("../Home");
			return;
		}
		//Punteggio
		
		Double punteggio = collab.getPunteggioFeedback((String)request.getSession().getAttribute("utenteCollegato"));
		log.debug("punteggio:" + punteggio );
		request.setAttribute("punteggio", punteggio);
		
		//Collaborazioni
		List<Collaborazione> collabora = collab.getCollaborazioniCreate((String)request.getSession().getAttribute("utenteCollegato"));
		request.setAttribute("collabCreate", collabora);
		
		//Abilita
		List<Abilita> ab = new ArrayList<Abilita>();
		ab.add(new Abilita("nomeA", "desc1"));
		ab.add(new Abilita("nomeB", "desc2"));
		ab.add(new Abilita("nomeC", "desc3"));
		request.setAttribute("abilita", ab);
		
		getServletConfig().getServletContext().getRequestDispatcher("/jsp/profilo.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
