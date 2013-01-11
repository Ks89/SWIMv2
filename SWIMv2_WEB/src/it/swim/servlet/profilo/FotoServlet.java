package it.swim.servlet.profilo;

import it.swim.util.ConvertitoreFotoInBlob;
import it.swim.util.UtenteCollegatoUtil;
import it.swim.util.exceptions.FotoException;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialException;

import lombok.extern.slf4j.Slf4j;

import exceptions.LoginException;

import sessionBeans.localInterfaces.GestioneCollaborazioniLocal;

/**
 * Servlet per gestire le foto del profilo
 */
@Slf4j
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
			response.sendRedirect("home");
			return;
		}

		String emailUtente = request.getParameter("emailUtente");

		Blob blob = null;

		try {
			if(emailUtente==null || emailUtente.equals("")) {
				try {
					blob = ConvertitoreFotoInBlob.getBlobFromDefaultImage();
				} catch (SerialException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FotoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				blob = gestioneCollaborazioni.getUtenteByEmail(emailUtente).getFotoProfilo();
			}
			
			if(blob!=null) {
				byte[] foto = blob.getBytes(1, (int)blob.length());
				response.setContentType("image/jpg");
				response.getOutputStream().write(foto);
			}
		} catch(SQLException e) {
			log.error(e.getMessage(), e);
		} catch (LoginException e) {
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
