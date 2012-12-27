package it.swim.servlet.profilo;

import it.swim.servlet.HomeServlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sessionBeans.localInterfaces.GestioneLoginLocal;
import sessionBeans.localInterfaces.GestioneRegistrazioneLocal;

import entityBeans.Abilita;

import lombok.extern.slf4j.Slf4j;

/**
 * Servlet implementation class RegistrazioneServlet
 */
@Slf4j
public class RegistrazioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private GestioneRegistrazioneLocal registrazione;

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

		// Abilita
		List<Abilita> ab = new ArrayList<Abilita>();
		ab.add(new Abilita("nomeA", "desc1"));
		ab.add(new Abilita("nomeB", "desc2"));
		ab.add(new Abilita("nomeC", "desc3"));
		request.setAttribute("abilita", ab);

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

		//		String[] abilitaArray = request.getParameterValues("abilita");
		//		List<String> abilitaLista = Arrays.asList(abilitaArray);
		//		
		List<Abilita> ab = new ArrayList<Abilita>();
		ab.add(new Abilita("nomeA", "desc1"));
		ab.add(new Abilita("nomeB", "desc2"));
		ab.add(new Abilita("nomeC", "desc3"));

		
		//TODO APPENA FUNZIONA LA REGISTRAZIONE MI BASTERA' SCOMMENTARE LA RIGA SOTTO E CANCELLARE LA SECONDA, ILREDIRECT ECC.. FUNZIONA
//		boolean risultatoRegistrazione = registrazione.registrazioneUtente(email, password, nome, cognome, null, ab);
		boolean risultatoRegistrazione = true;
		
		log.debug("risultatoRegistrazione: " + risultatoRegistrazione );

		if(risultatoRegistrazione) {
			log.debug("Registrazione avvenuta correttamente registrazione");

			request.getSession().setAttribute("utenteCollegato", email);

			request.setAttribute("registrazioneSuccesso", "esegui il login inserendo email e password!");
			getServletConfig().getServletContext().getRequestDispatcher("/jsp/home.jsp").forward(request, response);
		} else {
			log.debug("Errore registrazione");
			getServletConfig().getServletContext().getRequestDispatcher("/jsp/registrazione.jsp").forward(request, response);
		}

	}

}
