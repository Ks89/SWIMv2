package it.swim.servlet.profilo.azioni.notificheDiRisposta;

import it.swim.util.UtenteCollegatoUtil;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entityBeans.Abilita;
import entityBeans.Amicizia;
import entityBeans.Utente;
import exceptions.LoginException;
import exceptions.RicercheException;

import sessionBeans.localInterfaces.GestioneAmicizieLocal;
import sessionBeans.localInterfaces.GestioneCollaborazioniLocal;
import sessionBeans.localInterfaces.GestioneRicercheLocal;

import lombok.extern.slf4j.Slf4j;

/**
 * Servlet implementation class SuggAlRichiedenteServlet
 */
@Slf4j
public class SuggAlRichiedenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private GestioneAmicizieLocal amicizie;
	
	@EJB
	private GestioneCollaborazioniLocal gestCollaborazioni;
	
	@EJB
	private GestioneRicercheLocal ricerche;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SuggAlRichiedenteServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("Entrato nella doPost di SuggerimentiAlRichiedente");

		// ottengo l'email dell'utente collegato dalla sessione, appoggiandomi
		// ad una classe di utilita'
		String emailUtenteCollegato = (String) UtenteCollegatoUtil.getEmailUtenteCollegato(request);

		// se e' null e' perche' l'utente non e' collegato e allora devo fare il
		// redirect alla home
		if (emailUtenteCollegato == null) {
			response.sendRedirect("../../../home");
			return;
		}

		if(request.getParameter("suggeritoAlRichiedenteCheckbox")!=null) {

			List<Amicizia> amiciziaSuggeritaRichiesta  = new ArrayList<Amicizia>();
			Amicizia amiciziaRichiesta;

			for(String emailSuggerimentoAlRichiedente : request.getParameterValues("suggeritoAlRichiedenteCheckbox")) {
				log.debug("emailSuggerimentoAlRichiedente : " + emailSuggerimentoAlRichiedente);

				//se l'amicizia non e' gia' stata inoltrata in precedenza, la richiedo, altrimenti no
				if(!amicizie.amiciziaInoltrata(emailUtenteCollegato, emailSuggerimentoAlRichiedente)) {
					amiciziaRichiesta = amicizie.richiediAmicizia(emailUtenteCollegato, emailSuggerimentoAlRichiedente, false );
					if(amiciziaRichiesta!=null) {
						amiciziaSuggeritaRichiesta.add(amiciziaRichiesta);
					}
				}
			}

			if(amiciziaSuggeritaRichiesta.size()==0) {
				request.setAttribute("nessunSuggAccettato","Nessun ssuggerimento scelto. Amicizie non inoltrate");
			} else if(amiciziaSuggeritaRichiesta.size()==1) {
				request.setAttribute("suggAccettato","Hai stretto amicizia con l'utente scelto");
			} else if(amiciziaSuggeritaRichiesta.size()>1) {
				request.setAttribute("suggAccettati","Hai stretto amicizia con gli utenti scelti");
			}
		} else {
			request.setAttribute("nessunSuggAccettato","Nessun ssuggerimento scelto. Amicizie non inoltrate");
		}
		
		//per far si che la pagina profilo mostri anche il feedback, le abilita' ecc... devo rigenerare i dati e metterli nella request
		//questo lo devo fare perche' dopo vado alla profilo.jsp in modo diretto e non con una redirect, quindi non entro nella doGet della servlet.
		this.rigeneraProfiloUtenteCollegato(emailUtenteCollegato, request, response);
		
		getServletConfig().getServletContext().getRequestDispatcher("/jsp/utenti/profilo/profilo.jsp").forward(request, response);
	}

	private void rigeneraProfiloUtenteCollegato(String emailUtenteCollegato, HttpServletRequest request, HttpServletResponse response) {

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
			String feedback;
			if(punteggio==null) {
				feedback = new String("Non disponibile");
			} else {
				DecimalFormat df = new DecimalFormat("#.##");
				feedback = df.format(punteggio);
				request.setAttribute("feedback", punteggio.intValue() + "");
			}
			request.setAttribute("punteggioUtenteCollegato", feedback);
			log.debug("Sugg al richiedente: punteggioUtenteCollegato:" + feedback);

		} catch (LoginException e) {
			log.error(e.getMessage(), e);
		}

		List<Abilita> abilitaInsiemePersonale;
		try {
			abilitaInsiemePersonale = ricerche.insiemeAbilitaPersonaliUtente(emailUtenteCollegato);
			request.setAttribute("abilita", abilitaInsiemePersonale);
		} catch (RicercheException e) {
			log.error(e.getMessage(), e);
		}
	}

}
