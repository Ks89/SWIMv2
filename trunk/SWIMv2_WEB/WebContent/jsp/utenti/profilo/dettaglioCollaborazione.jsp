<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SWIM - Profilo - Collaborazioni - Collaborazione
	Dettagliata</title>
</head>
<body>
	<c:out value="aiuto"></c:out>
	<c:if test="${!empty erroreRicercaCollaborazione}">
		<c:out value="${erroreRicercaCollaborazione}"></c:out>
		<br>
	</c:if>
	<c:if test="${empty erroreRicercaCollaborazione}">
		<br /> Nome:
	<c:out value="${collaborazione.nome}"></c:out>
	<br /> Utente richiesto:
	<c:out value="${collaborazione.utenteRicevente.nome}" />&nbsp;<c:out
								value="${collaborazione.utenteRicevente.cognome}" />
	<br /> Data di inizio collaborazione:
	<c:out value="${collaborazione.dataAccettazione}"></c:out>
	</c:if>
	<c:if test="${!empty terminata}">
	<br /> Data di fine collaborazione:
	<c:out value="${collaborazione.dataTermine}"></c:out>
	<br />
	</c:if>
	<c:if test="${!empty conFeedBack}">
	<br /> Punteggio del feedback rilasciato:
	<c:out value="${collaborazione.punteggioFeedback}"></c:out>
	<br />
	<br /> Commenti sul collaboratore:
	<c:out value="${collaborazione.commentoFeedback}"></c:out>
	<br />
	</c:if>
	
</body>
</html>