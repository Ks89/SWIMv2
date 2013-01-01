package it.swim.servlet.profilo;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import sessionBeans.localInterfaces.GestioneRegistrazioneLocal;
import sessionBeans.localInterfaces.GestioneRicercheLocal;
import entityBeans.Abilita;
import entityBeans.Utente;
import exceptions.HashingException;
import exceptions.RegistrazioneException;

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

		getServletConfig().getServletContext().getRequestDispatcher("/jsp/registrazione.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		List<FileItem> items;
		Blob blob = null;
		String email = new String();
		String password = new String();
		String nome = new String();
		String cognome = new String();

		List<Abilita> abilitaPersonaliRegistrazione = new ArrayList<Abilita>();

		try {
			items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);

			for (FileItem item : items) {
				if (item.isFormField()) {
					// Process regular form field (input
					// type="text|radio|checkbox|etc", select, etc).
					// ... (do your job here)
					if(item.getFieldName().equals("emailUtente")){
						//ottengo il valore del form field
						email=item.getString(); 
					}
					if(item.getFieldName().equals("password")){
						password=item.getString();
					}
					if(item.getFieldName().equals("nome")){
						nome=item.getString();
					}
					if(item.getFieldName().equals("cognome")){
						cognome=item.getString();
					}
					if(item.getFieldName().equals("abilita")){
						abilitaPersonaliRegistrazione.add(registrazione.getAbilitaByNome(item.getString()));
					}
				} else {
					// Process form file field (input type="file").
					// String fieldname = item.getFieldName();
					// String filename = item.getName();
					InputStream filecontent = item.getInputStream();
					byte[] b = new byte[filecontent.available()];
					log.debug("inputstream blob: " + filecontent.available());
					if(filecontent.available()>0) {
						filecontent.read(b);
						blob = new SerialBlob(b);
					}
					filecontent.close();
//					blob = ConvertitoreFotoInBlob.getBlobFromFileItem(item, LUNGHEZZA, ALTEZZA);
				}
			}

			log.debug("email: " + email );
			log.debug("password: " + password );
			log.debug("nome: " + nome );
			log.debug("cognome: " + cognome );
			log.debug("Lista abilita passate in registrazione: " +  Arrays.toString(abilitaPersonaliRegistrazione.toArray()));

		} catch (FileUploadException e) {
			log.error(e.getMessage(), e);
		} catch (SerialException e) {
			log.error(e.getMessage(), e);
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		}


		Utente utenteRegistrato;
		try {
			utenteRegistrato = registrazione.registrazioneUtente(email, password, nome, cognome, blob, abilitaPersonaliRegistrazione);

			log.debug("utenteRegistrato: " + utenteRegistrato );

			if(utenteRegistrato!=null) {
				log.debug("Registrazione avvenuta correttamente registrazione");

				request.getSession().setAttribute("utenteCollegato", email);

				response.sendRedirect("profilo/profilo");
			} else {
				log.debug("Errore registrazione");
				getServletConfig().getServletContext().getRequestDispatcher("/jsp/registrazione.jsp").forward(request, response);
			}
		} catch (HashingException e) {
			log.error(e.getMessage(), e);
		} catch (RegistrazioneException e) {
			log.error(e.getMessage(), e);
		}
	}
}
