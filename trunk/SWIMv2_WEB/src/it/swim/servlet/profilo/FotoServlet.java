package it.swim.servlet.profilo;

import it.swim.util.UtenteCollegatoUtil;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import exceptions.CollaborazioneException;

import sessionBeans.localInterfaces.GestioneCollaborazioniLocal;

/**
 * Servlet implementation class FotoServlet
 */
@Slf4j
public class FotoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//uso gestione collaborazione perche' tanto contiene un metodo per ottenere l'utente, data l'email
	//tale metodo e' presente anche nelle amicizie ecc... non fa differenza
	@EJB
	private GestioneCollaborazioniLocal gestioneCollaborazioni; 

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FotoServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String emailUtenteCollegato = (String) UtenteCollegatoUtil.getEmailUtenteCollegato(request);
		if (emailUtenteCollegato == null) {
			response.sendRedirect("home");
			return;
		}
		
		String emailUtente = request.getParameter("emailUtente");
		log.debug("--------------------_______________________-----------------________________-------> " + emailUtente);
		try {
			Blob blob = gestioneCollaborazioni.getUtenteByEmail(emailUtente).getFotoProfilo();
			if(blob!=null) {
				byte[] foto = blob.getBytes(1, (int)blob.length());
				response.setContentType("image/jpg");
				response.getOutputStream().write(foto);
			}
		} catch(SQLException e) {
			log.error(e.getMessage(), e);
		} catch (CollaborazioneException e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
