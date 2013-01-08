package it.swim.servlet.profilo.azioni.notificheDiRisposta;

import it.swim.util.UtenteCollegatoUtil;
import it.swim.util.UtenteConSuggerimenti;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import entityBeans.Collaborazione;
import entityBeans.Utente;
import exceptions.CollaborazioneException;

import sessionBeans.localInterfaces.GestioneAmicizieLocal;
import sessionBeans.localInterfaces.GestioneCollaborazioniLocal;

/**
 * Servlet implementation class NotificheDiRispostaServlet
 */
@Slf4j
public class NotificheDiRispostaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private GestioneAmicizieLocal amicizie;
	
	@EJB
	private GestioneCollaborazioniLocal collaborazioni;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NotificheDiRispostaServlet() {
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
			response.sendRedirect("../../..home");
			return;
		}

		//ottengo utenti che hanno accettato le mie richieste, separandoli in diretti e indiretti (ottenuti con suggerimento)
		List<Utente> utentiAccettatiDiretti = amicizie.getUtentiCheHannoAccettatoLaRichiestaDiretti(emailUtenteCollegato);
		List<Utente> utentiAccettatiIndiretti = amicizie.getUtentiCheHannoAccettatoLaRichiestaIndiretti(emailUtenteCollegato);

		List<UtenteConSuggerimenti> listaUtentiConSugg = new ArrayList<UtenteConSuggerimenti>();
		
		//metto come attributo quelli indiretti, tanto non devo lavorarci sopra, mi basta leggere la lista e stamparli nella jsp
		request.setAttribute("utentiAccettatiIndiretti", utentiAccettatiIndiretti);

		//segno come viste le notifiche di accettazione delle richieste dell'utente con emailUtenteCollegato,
		//o meglio metto l'attributo notificaAlRichiedente=true perche' appunto, in questo momento il richiedente
		//riceve le notifiche di accettazione delle sue richieste fatte ad altri utenti che le hanno accettate
		for(Utente utente: utentiAccettatiDiretti) {
			//uso una classe di appoggio per inserire utente e la lista dei suggerimenti estratti da quell'utente
			UtenteConSuggerimenti utenteConSugg = new UtenteConSuggerimenti();
			utenteConSugg.setUtente(utente);
			utenteConSugg.setSuggerimenti(amicizie.getSuggerimenti(utente.getEmail(), emailUtenteCollegato));
			listaUtentiConSugg.add(utenteConSugg);
			log.debug("utentediretto: " + utente);
			amicizie.setAmiciziaNotificata(emailUtenteCollegato, utente.getEmail());
		}
		//setto come visti anche quelli degli utenti indiretti
		for(Utente utente: utentiAccettatiIndiretti) {
			log.debug("utenteindiretto: " + utente);
			amicizie.setAmiciziaNotificata(emailUtenteCollegato, utente.getEmail());
		}
		
		//quindi ora ho negli attributi utentiAccettatiIndiretti gli utenti che hanno accettato le mie richieste,
		//ma non ho ancora i suggerimenti presi da quelli utentiAccettatiDiretti, degli indiretti non mi interessano, perche' da loro non devo
		//ricevere suggerimenti
		//a questo punto setto come attributo la lista degli utenti con suggerimenti, cioe' passo la lista di classe UtenteConSuggerimenti
		request.setAttribute("listaUtentiConSuggerimenti", listaUtentiConSugg);

		
		getServletConfig().getServletContext().getRequestDispatcher("/jsp/utenti/profilo/notificheDiRisposta.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
}
