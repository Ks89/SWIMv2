package it.swim.servlet.profilo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sessionBeans.localInterfaces.GestioneRegistrazioneLocal;
import sessionBeans.localInterfaces.GestioneRicercheLocal;

import entityBeans.Abilita;
import exceptions.HashingException;

import lombok.extern.slf4j.Slf4j;

/**
 * Servlet implementation class RegistrazioneServlet
 */
@Slf4j
public class RegistrazioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private GestioneRegistrazioneLocal registrazione;

	@EJB
	private GestioneRicercheLocal ricerche;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegistrazioneServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("Visualizza pagina registrazione");

		// Ottengo abilita dall'insieme generale e le metto nella request
		List<Abilita> abilitaInsiemeGenerale = ricerche.insiemeAbilitaGenerali();
		request.setAttribute("abilita", abilitaInsiemeGenerale);

		log.debug("Lista abilita insieme generale : " + abilitaInsiemeGenerale.toString());
		
		getServletConfig().getServletContext().getRequestDispatcher("/jsp/registrazione.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String email = request.getParameter("emailUtente");
		String password = request.getParameter("password");
		String nome = request.getParameter("nome");
		String cognome = request.getParameter("cognome");


		log.debug("email: " + email );
		log.debug("password: " + password );
		log.debug("nome: " + nome );
		log.debug("cognome: " + cognome );

		String[] abilitaArray = request.getParameterValues("abilita");
		List<Abilita> abilitaPersonaliRegistrazione = new ArrayList<Abilita>();

		log.debug("Lista abilita passate in registrazione: " +  Arrays.toString(abilitaArray));

		
		for(String abilitaString : abilitaArray) {
			abilitaPersonaliRegistrazione.add(registrazione.getAbilitaByNome(abilitaString));
		}
				
		log.debug("Lista abilita passate in registrazione: " + abilitaPersonaliRegistrazione.toString());

		
		boolean risultatoRegistrazione = false;
		try {
			risultatoRegistrazione = registrazione.registrazioneUtente(email, password, nome, cognome, null, abilitaPersonaliRegistrazione);
		} catch (HashingException e) {
			log.error(e.getMessage(), e);
		}
		
		log.debug("risultatoRegistrazione: " + risultatoRegistrazione );

		if(risultatoRegistrazione) {
			log.debug("Registrazione avvenuta correttamente registrazione");

			request.getSession().setAttribute("utenteCollegato", email);

			response.sendRedirect("Profilo/Profilo");
		} else {
			log.debug("Errore registrazione");
			getServletConfig().getServletContext().getRequestDispatcher("/jsp/registrazione.jsp").forward(request, response);
		}

	}

}
