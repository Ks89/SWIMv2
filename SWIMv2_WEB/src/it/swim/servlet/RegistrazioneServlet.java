package it.swim.servlet;

import it.swim.util.ConvertitoreFotoInBlob;
import it.swim.util.exceptions.FotoException;

import java.io.IOException;
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

	//--------------------------------IMPORTANTE--------------------------------
	//--------------scegliere qui la dimensione della foto profilo--------------
	private static final int LUNGHEZZA = 186, ALTEZZA = 249; 

	//dimensione massima in MB del file uploadato
	private static final int DIMMB = 6;

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

		getServletConfig().getServletContext().getRequestDispatcher("/jsp/visitatore/registrazione.jsp").forward(request, response);
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

		//nel caso ci siano errori e devo tornare alla stessa jsp, preparo subito la lista delle abilita' da rivisualizzare
		// Ottengo abilita dall'insieme generale e le metto nella request
		List<Abilita> abilitaInsiemeGenerale = ricerche.insiemeAbilitaGenerali();
		request.setAttribute("abilita", abilitaInsiemeGenerale);

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
					//non cancellare questi commenti, potranno tornare utili
					// Process form file field (input type="file").
					// String fieldname = item.getFieldName();
					// String filename = item.getName();
					// InputStream filecontent = item.getInputStream();
					try {
						blob = ConvertitoreFotoInBlob.getBlobFromFileItem(item, LUNGHEZZA, ALTEZZA, DIMMB);
					} catch (FotoException e) {
						try {
							if(e.getCausa().equals(FotoException.Causa.FILETROPPOGRANDE)) {
								blob = ConvertitoreFotoInBlob.getBlobFromDefaultImage();
								request.setAttribute("erroreFileTroppoGrande", "Errore, file troppo grande! E' stata impostata la foto di profilo predefinita");
							} else {
								if(e.getCausa().equals(FotoException.Causa.NONRICONOSCIUTACOMEFOTO)) {
									blob = ConvertitoreFotoInBlob.getBlobFromDefaultImage();
									request.setAttribute("erroreNonFoto", "Errore, foto non riconosciuta! E' stata impostata la foto di profilo predefinita");
								}
							}
							//in questo caso uploada una foto predefinita
							blob = ConvertitoreFotoInBlob.getBlobFromDefaultImage();
							request.setAttribute("erroreFotoSconosciuto", "Errore durante il caricamento della foto! E' stata impostata la foto di profilo predefinita");
						} catch (FotoException e1) {
							request.setAttribute("erroreFotoSconosciuto", "Errore durante il caricamento della foto! E' stata impostata la foto di profilo predefinita");
						}
					}
				}
			}

			log.debug("email: " + email );
			log.debug("password: " + password );
			log.debug("nome: " + nome );
			log.debug("cognome: " + cognome );
			log.debug("Lista abilita passate in registrazione: " +  Arrays.toString(abilitaPersonaliRegistrazione.toArray()));

			if(blob==null) {
				try {
					blob = ConvertitoreFotoInBlob.getBlobFromDefaultImage();
				} catch (FotoException e) {
					request.setAttribute("erroreFotoPredefinita", "Errore durante il caricamento della foto predefinita. Nessun file caricato!");
				}
			}
			
		} catch (FileUploadException e) {
			log.error(e.getMessage(), e);
			request.setAttribute("erroreFotoIrreversibile", "Errore durante il caricamento della foto! Non e' stata impostata nessuna foto di profilo");
		} catch (SerialException e) {
			log.error(e.getMessage(), e);
			request.setAttribute("erroreFotoIrreversibile", "Errore durante il caricamento della foto! Non e' stata impostata nessuna foto di profilo");
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			request.setAttribute("erroreFotoIrreversibile", "Errore durante il caricamento della foto! Non e' stata impostata nessuna foto di profilo");
		}


		Utente utenteRegistrato;
		try {
			utenteRegistrato = registrazione.registrazioneUtente(email, password, nome, cognome, blob, abilitaPersonaliRegistrazione);

			log.debug("utenteRegistrato: " + utenteRegistrato );

			if(utenteRegistrato!=null) {
				log.debug("Registrazione avvenuta correttamente registrazione");

				request.getSession().setAttribute("utenteCollegato", email);
				request.setAttribute("abilita", abilitaPersonaliRegistrazione );
				request.setAttribute("punteggioUtenteCollegato", "Non disponibile");
				getServletConfig().getServletContext().getRequestDispatcher("/jsp/utenti/profilo/profilo.jsp").forward(request, response);

			} else {
				log.debug("Errore registrazione");
				request.setAttribute("erroreRegistrazione", "Errore durante la registrazione");
				getServletConfig().getServletContext().getRequestDispatcher("/jsp/visitatore/registrazione.jsp").forward(request, response);
			}
		} catch (HashingException e) {
			log.error(e.getMessage(), e);
			request.setAttribute("erroreHashing", "Errore hashing durante la registrazione");
			getServletConfig().getServletContext().getRequestDispatcher("/jsp/visitatore/registrazione.jsp").forward(request, response);
		} catch (RegistrazioneException e) {
			log.error(e.getMessage(), e);
			request.setAttribute("erroreSconosciutoRegistrazione", "Errore sconosciuto durante la registrazione");
			getServletConfig().getServletContext().getRequestDispatcher("/jsp/visitatore/registrazione.jsp").forward(request, response);
		}
	}
}
