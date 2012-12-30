package it.swim.util;

import javax.servlet.http.HttpServletRequest;

public class AdminCollegatoUtil {

	public static String getEmailAdminCollegato(HttpServletRequest request) {
		return (String)request.getSession().getAttribute("adminCollegato");
	}
}
