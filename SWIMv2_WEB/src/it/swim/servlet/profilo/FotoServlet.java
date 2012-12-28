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

import sessionBeans.localInterfaces.GestioneCollaborazioniLocal;

/**
 * Servlet implementation class FotoServlet
 */
public class FotoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
			response.sendRedirect("Home");
			return;
		}
		try {
			Blob blob = gestioneCollaborazioni.getUtenteByEmail(emailUtenteCollegato).getFotoProfilo();
			byte[] foto = blob.getBytes(1, (int)blob.length());
			response.setContentType("image/jpg");
			response.getOutputStream().write(foto);
		} catch(SQLException e) {

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
