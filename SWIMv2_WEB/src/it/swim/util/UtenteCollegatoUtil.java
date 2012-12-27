package it.swim.util;

import javax.servlet.http.HttpServletRequest;

public class UtenteCollegatoUtil {

	public static String getEmailUtenteCollegato(HttpServletRequest request) {
		return (String)request.getSession().getAttribute("utenteCollegato");
	}
}
