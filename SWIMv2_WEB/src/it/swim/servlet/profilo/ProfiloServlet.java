package it.swim.servlet.profilo;

import it.swim.util.UtenteCollegatoUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sessionBeans.localInterfaces.GestioneCollaborazioniLocal;
import sessionBeans.localInterfaces.GestioneRicercheLocal;

import lombok.extern.slf4j.Slf4j;
import entityBeans.Abilita;
import entityBeans.Collaborazione;
import entityBeans.Utente;
import exceptions.LoginException;
import exceptions.RicercheException;

/**
 * Servlet implementation class ProfiloServlet
 */
@Slf4j
public class ProfiloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private GestioneCollaborazioniLocal gestCollaborazioni;

	@EJB
	private GestioneRicercheLocal ricerche;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProfiloServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ottengo l'email dell'utente collegato dalla sessione, appoggiandomi
		// ad una classe di utilita'
		String emailUtenteCollegato = (String) UtenteCollegatoUtil.getEmailUtenteCollegato(request);

		// se e' null e' perche' l'utente non e' collegato e allora devo fare il
		// redirect alla home
		if (emailUtenteCollegato == null) {
			response.sendRedirect("../home");
			return;
		}

		// ottengo utente collegato con riferimento al vero oggetto Utente
		Utente utenteCollegato;
		try {
			utenteCollegato = gestCollaborazioni.getUtenteByEmail(emailUtenteCollegato);
			// mando sulla request i dati dell'utente tramite il setAttribute, e li
			// contraddistinguo dai 2 parametri passati come string
			request.setAttribute("cognomeUtenteCollegato", utenteCollegato.getCognome());
			request.setAttribute("nomeUtenteCollegato", utenteCollegato.getNome());

			// ottengo punteggio di feedback dell'utente
			Double punteggio = gestCollaborazioni.getPunteggioFeedback(emailUtenteCollegato);
			log.debug("punteggioUtenteCollegato:" + punteggio);
			request.setAttribute("punteggioUtenteCollegato", punteggio);

			// Collaborazioni
			List<Collaborazione> collabora = gestCollaborazioni.getCollaborazioniCreate(emailUtenteCollegato);
			request.setAttribute("collabCreate", collabora);
		} catch (LoginException e) {
			log.error(e.getMessage(), e);
		}

		// ottengo le abilita che possiede l'utente
		// TODO per ora le inserisco finte poi le leggero' veramente
		//quando dovro' leggererle dal db dovro' tenere le 2 righe sequenti commentate e cancellare l'altro
//			List<Abilita> abilitaInsiemePersonale = ricerche.insiemeAbilitaPersonaliUtente(emailUtenteCollegato);
			List<Abilita> abilitaInsiemePersonale = new ArrayList<Abilita>();
			abilitaInsiemePersonale.add(new Abilita("nomeA", "desc1"));
			abilitaInsiemePersonale.add(new Abilita("nomeB", "desc2"));
			abilitaInsiemePersonale.add(new Abilita("nomeC", "desc3"));
			abilitaInsiemePersonale.add(new Abilita("nomeD", "desc4"));
			abilitaInsiemePersonale.add(new Abilita("nomeE", "desc5"));
			abilitaInsiemePersonale.add(new Abilita("nomeF", "desc6"));
			request.setAttribute("abilita", abilitaInsiemePersonale);
		
			getServletConfig().getServletContext().getRequestDispatcher("/jsp/profilo.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
