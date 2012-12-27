package it.swim.util;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;

import entityBeans.Utente;

import sessionBeans.localInterfaces.GestioneCollaborazioniLocal;

public class UtenteCollegatoUtil {

	@EJB
	private static GestioneCollaborazioniLocal gestCollab;
	
	public static String getEmailUtenteCollegato(HttpServletRequest request) {
		return (String)request.getSession().getAttribute("utenteCollegato");
	}
	
//	public static Utente getUtenteCollegato(HttpServletRequest request) {
//		String emailUtenteCollegato = (String)request.getSession().getAttribute("utenteCollegato");
//		return gestCollab.getUtenteByEmail(emailUtenteCollegato);
//	}
}
