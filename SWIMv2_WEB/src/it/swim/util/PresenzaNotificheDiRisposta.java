package it.swim.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import sessionBeans.localInterfaces.GestioneAmicizieLocal;
import sessionBeans.localInterfaces.GestioneCollaborazioniLocal;

import entityBeans.Collaborazione;
import entityBeans.Utente;
import exceptions.LoginException;

@Slf4j
public class PresenzaNotificheDiRisposta {

	/**
	 * Metodo che dice se ci sono notifiche di risposta da mostrare all'utente.
	 * @param emailUtenteCollegato : String che rappresenta l'email dell'utente collegato (utente richiedente delle collaborazione o utente1 delle amicizie)
	 * @param request : HttpServletRequest della servlet
	 * @param response : HttpServletResponse della servlet
	 * @param amicizie : GestioneAmicizieLocal, cioe' il riferimento all'interfaccia locale dell'entitybean GestioneAmicizie
	 * @param collab : GestioneCollaborazioniLocal, cioe' il riferimento all'interfaccia locale dell'entitybean GestioneCollaborazioni
	 * @return <b>true</b> se ci sono notifiche da visualizzare, <b>false</b> altrimenti.
	 */
	public static boolean isNotificheRispostaPresenti(String emailUtenteCollegato, HttpServletRequest request, HttpServletResponse response, GestioneAmicizieLocal amicizie, GestioneCollaborazioniLocal collab) {
		
		//ottengo utenti che hanno accettato le mie richieste, separandoli in diretti e indiretti (ottenuti con suggerimento)
		List<Utente> utentiAccettatiDiretti = amicizie.getUtentiCheHannoAccettatoLaRichiestaDiretti(emailUtenteCollegato);
		List<Utente> utentiAccettatiIndiretti = amicizie.getUtentiCheHannoAccettatoLaRichiestaIndiretti(emailUtenteCollegato);
		
		
		//ottengo tutte le collaborazioni da me create,che sono state accettate.
		//e dopo ottengo le collaborazioni da me create che pero' sono stante respinte
		List<Collaborazione> collaborazioniAccettate=new ArrayList<Collaborazione>();
		List<Collaborazione> collaborazioniRespinte=new ArrayList<Collaborazione>();
		try {
			collaborazioniAccettate = collab.getCollaborazioniDaNotificare(emailUtenteCollegato);
			collaborazioniRespinte = collab.getCollaborazioniRifiutate(emailUtenteCollegato);
		} catch (LoginException e) {
			log.error(e.getMessage(), e);
		}
		

		//ritorno true se c'e' qualche cosa da notificare, altrimenti false
		return ((utentiAccettatiIndiretti!=null && utentiAccettatiIndiretti.size()>=1) || 
				(utentiAccettatiDiretti!=null && utentiAccettatiDiretti.size()>=1) || collaborazioniAccettate.size()>=1
				|| collaborazioniRespinte.size()>=1);
	}
}

