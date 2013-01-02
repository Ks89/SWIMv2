package it.swim.servlet.profilo.azioni;

import it.swim.util.UtenteCollegatoUtil;

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

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import sessionBeans.localInterfaces.GestioneModificaProfiloLocal;
import sessionBeans.localInterfaces.GestioneRegistrazioneLocal;
import sessionBeans.localInterfaces.GestioneRicercheLocal;

import entityBeans.Abilita;
import exceptions.RicercheException;

import lombok.extern.slf4j.Slf4j;

/**
 * Servlet implementation class ModificaProfiloServlet
 */
@Slf4j
public class ModificaProfiloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	private GestioneRegistrazioneLocal registrazione;

	@EJB
	private GestioneModificaProfiloLocal modificaProfilo;

	@EJB
	private GestioneRicercheLocal ricerche;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ModificaProfiloServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ottengo l'email dell'utente collegato dalla sessione, appoggiandomi
		// ad una classe di utilita'
		String emailUtenteCollegato = (String) UtenteCollegatoUtil.getEmailUtenteCollegato(request);

		// se e' null e' perche' l'utente non e' collegato e allora devo fare il
		// redirect alla home
		if (emailUtenteCollegato == null) {
			response.sendRedirect("../../home");
			return;
		}

		try {
			request.setAttribute("abilita", this.getListaAbilitaAggiungibili(emailUtenteCollegato));
		} catch (RicercheException e) {
			log.error(e.getMessage(), e);
		}

		getServletConfig().getServletContext().getRequestDispatcher("/jsp/modificaProfilo.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ottengo l'email dell'utente collegato dalla sessione, appoggiandomi
		// ad una classe di utilita'
		String emailUtenteCollegato = (String) UtenteCollegatoUtil.getEmailUtenteCollegato(request);

		// se e' null e' perche' l'utente non e' collegato e allora devo fare il
		// redirect alla home
		if (emailUtenteCollegato == null) {
			response.sendRedirect("../../home");
			return;
		}


		List<FileItem> items;
		Blob blob = null;
		String password = new String();
		String nome = new String();
		String cognome = new String();

		List<Abilita> abilitaDaAggiungereAllePersonali = new ArrayList<Abilita>();

		try {
			items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);

			for (FileItem item : items) {
				if (item.isFormField()) {
					// Process regular form field (input
					// type="text|radio|checkbox|etc", select, etc).
					// ... (do your job here)
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
						abilitaDaAggiungereAllePersonali.add(registrazione.getAbilitaByNome(item.getString()));
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
				}
			}

			log.debug("password: " + password );
			log.debug("nome: " + nome );
			log.debug("cognome: " + cognome );
			log.debug("Lista abilita da aggiungere all'utente: " +  Arrays.toString(abilitaDaAggiungereAllePersonali.toArray()));
		} catch (FileUploadException e) {
			log.error(e.getMessage(), e);
			request.setAttribute("erroreInserimentoFoto", "Errore nel caricamento della nuova foto del profilo");
		} catch (SerialException e) {
			log.error(e.getMessage(), e);
			request.setAttribute("erroreInserimentoFoto", "Errore nel caricamento della nuova foto del profilo");
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			request.setAttribute("erroreInserimentoFoto", "Errore nel caricamento della nuova foto del profilo");
		}

		log.debug("abilitaDaAggiungereAllePersonali : " + Arrays.toString(abilitaDaAggiungereAllePersonali.toArray()));

		if(abilitaDaAggiungereAllePersonali.size()>=1) {
			boolean modificaInsiemePersonaleAbilita = modificaProfilo.modificaSetAbilita(emailUtenteCollegato, abilitaDaAggiungereAllePersonali);
			log.debug("modificaInsiemePersonaleAbilita: " + modificaInsiemePersonaleAbilita );
			if(modificaInsiemePersonaleAbilita) {
				request.setAttribute("modificaAbilitaRiuscitaConSuccesso", "Modifica eseguita correttamente");
			} else {
				log.debug("Errore inserimento abilita");
				request.setAttribute("erroreInserimentoProposta", "Errore nell'aggiunta di nuove abilita'");
			}
		}

		if(blob!=null) {
			boolean modificaFotoRiuscita = modificaProfilo.modificaFoto(emailUtenteCollegato, blob);
			log.debug("modificaFotoRiuscita: " + modificaFotoRiuscita );
			if(modificaFotoRiuscita) {
				request.setAttribute("modificaFotoRiuscitaConSuccesso", "Modifica eseguita correttamente");
			} else {
				log.debug("Errore modifica foto");
				request.setAttribute("erroreInserimentoFoto", "Errore nel caricamento della nuova foto del profilo");
			}
		}
		
		try {
			request.setAttribute("abilita", this.getListaAbilitaAggiungibili(emailUtenteCollegato));
		} catch (RicercheException e) {
			log.error(e.getMessage(), e);
		}
		
		getServletConfig().getServletContext().getRequestDispatcher("/jsp/modificaProfilo.jsp").forward(request, response);
	}

	private List<Abilita> getListaAbilitaAggiungibili(String emailUtenteCollegato) throws RicercheException {
		// Ottengo abilita dall'insieme generale, gli sottraggo quelle che l'utente possiede gia' e poi passo
		// la lista alla jsp
		List<Abilita> abilitaInsiemeGenerale = ricerche.insiemeAbilitaGenerali();
		List<Abilita> abilitaInsiemePersonale = ricerche.insiemeAbilitaPersonaliUtente(emailUtenteCollegato);
		abilitaInsiemeGenerale.removeAll(abilitaInsiemePersonale);
		return abilitaInsiemeGenerale;
	}

}