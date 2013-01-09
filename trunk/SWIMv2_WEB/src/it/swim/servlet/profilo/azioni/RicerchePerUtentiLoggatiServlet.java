package it.swim.servlet.profilo.azioni;

import it.swim.servlet.RicercaPerVisitatoriServlet;
import it.swim.util.UtenteCollegatoUtil;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
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

import entityBeans.Abilita;
import entityBeans.Utente;
import exceptions.RicercheException;

import sessionBeans.localInterfaces.GestioneAmicizieLocal;
import sessionBeans.localInterfaces.GestioneCollaborazioniLocal;
import sessionBeans.localInterfaces.GestioneRegistrazioneLocal;
import sessionBeans.localInterfaces.GestioneRicercheLocal;

import lombok.extern.slf4j.Slf4j;

/**
 * Servlet implementation class RicerchePerUtentiLoggatiServlet
 */
@Slf4j
public class RicerchePerUtentiLoggatiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	private GestioneRegistrazioneLocal registrazione;
	
	@EJB
	private GestioneRicercheLocal gestioneRicerche;
	
	@EJB
	private GestioneAmicizieLocal gestioneAmicizie;
	
	@EJB
	private GestioneCollaborazioniLocal gestioneCollaborazioni;
	
	private String tipoRicerca;
	//questa variabile mi serve per sapere se la servlet è stata richiamata da un pulsante submit o no
	private boolean ricerca=false;
	private boolean ricercaGiaEffettuata=false;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RicerchePerUtentiLoggatiServlet() {
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
		this.tipoRicerca=request.getParameter("tipoRicerca");
		// se e' null e' perche' l'utente non e' collegato e allora devo fare il
		// redirect alla home
		/*if (emailUtenteCollegato == null) {
			response.sendRedirect("../home");
			return;
		}*/
		//i visitatori non possono fare la ricerca utenti
		if (emailUtenteCollegato == null && tipoRicerca.equals("utente")) {
			response.sendRedirect("../../home");
			return;
		}
		//ricerca aiuto per visitatori
		else if (emailUtenteCollegato == null && tipoRicerca.equals("aiuto")) {
			tipoRicerca="aiutoVisitatore";
			request.setAttribute("abilita", gestioneRicerche.insiemeAbilitaGenerali());
			request.setAttribute("tipoRicerca", tipoRicerca);
			request.setAttribute("risultatoRicerca", ricerca);
		}
		//ricerca aiuto
		else if(emailUtenteCollegato != null && tipoRicerca.equals("aiuto"))
		{
			request.setAttribute("abilita", gestioneRicerche.insiemeAbilitaGenerali());
			request.setAttribute("tipoRicerca", tipoRicerca);
			request.setAttribute("risultatoRicerca", ricerca);
		}
		//ricerca utenti per utenti loggati
		else
		{
			request.setAttribute("tipoRicerca", tipoRicerca);
			request.setAttribute("risultatoRicerca", ricerca);
		}	
			
		getServletConfig().getServletContext().getRequestDispatcher("/jsp/ricerche.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<FileItem> items;
		String nome = new String();
		String cognome = new String();
		String emailUtenteCollegato = (String) UtenteCollegatoUtil.getEmailUtenteCollegato(request);
		List<Abilita> abilitaRicercate = new ArrayList<Abilita>();
		List<Utente> risultatoRicerca = new ArrayList<Utente>();
		boolean soloAmici=false;
		ricerca=false;
			try {
				items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
				for (FileItem item : items) {
					if (item.isFormField()) {
						// Process regular form field (input
						// type="text|radio|checkbox|etc", select, etc).
						// ... (do your job here)
						if(item.getFieldName().equals("nomeUtente")){
							nome=item.getString().trim();
						}
						if(item.getFieldName().equals("cognomeUtente")){
							cognome=item.getString().trim();
						}
						if(item.getFieldName().equals("abilita")){
							abilitaRicercate.add(registrazione.getAbilitaByNome(item.getString()));
						}
						if(item.getFieldName().equals("soloAmici")){
							soloAmici=true;
						}
					}
				}
			} 
			catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(tipoRicerca.equals("aiuto"))
			{
				try {
					log.info("<---------------------------"+(String) UtenteCollegatoUtil.getEmailUtenteCollegato(request)+"----------------------------->");
					risultatoRicerca=gestioneRicerche.ricercaAiuto(abilitaRicercate,(String) UtenteCollegatoUtil.getEmailUtenteCollegato(request));
					if(soloAmici==true){
						List<Utente> amiciUtente = gestioneAmicizie.getAmici(emailUtenteCollegato);
						for(int i=0; i<risultatoRicerca.size(); i++){
							if(!(amiciUtente.contains(risultatoRicerca.get(i)))){
								risultatoRicerca.remove(i);
								i--;
							}
						}
						
					}
				} catch (RicercheException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ricercaGiaEffettuata=true;
				request.setAttribute("abilita", gestioneRicerche.insiemeAbilitaGenerali());
				request.setAttribute("utenti", risultatoRicerca);
				if(risultatoRicerca.size()>0)
					ricerca=true;
				request.setAttribute("risultatoRicerca", ricerca);
				request.setAttribute("ricercaGiaEffettuata", ricercaGiaEffettuata);
				request.setAttribute("tipoRicerca", tipoRicerca);
			}
			else if(tipoRicerca.equals("aiutoVisitatore"))
			{
				try {
					risultatoRicerca=gestioneRicerche.ricercaAiutoVisitatore(abilitaRicercate);
				} catch (RicercheException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ricercaGiaEffettuata=true;
				request.setAttribute("abilita", gestioneRicerche.insiemeAbilitaGenerali());
				request.setAttribute("utenti", risultatoRicerca);
				if(risultatoRicerca.size()>0)
					ricerca=true;
				request.setAttribute("risultatoRicerca", ricerca);
				request.setAttribute("ricercaGiaEffettuata", ricercaGiaEffettuata);
				request.setAttribute("tipoRicerca", tipoRicerca);
			}
			else
			{
				try {
					risultatoRicerca=gestioneRicerche.ricercaUtenti(nome, cognome,(String) UtenteCollegatoUtil.getEmailUtenteCollegato(request));
				} catch (RicercheException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ricercaGiaEffettuata=true;
				request.setAttribute("utenti", risultatoRicerca);
				if(risultatoRicerca.size()>0)
					ricerca=true;
				request.setAttribute("risultatoRicerca", ricerca);
				request.setAttribute("tipoRicerca", tipoRicerca);
				request.setAttribute("ricercaGiaEffettuata", ricercaGiaEffettuata);
			}
			getServletConfig().getServletContext().getRequestDispatcher("/jsp/ricerche.jsp").forward(request, response);
	}
}
