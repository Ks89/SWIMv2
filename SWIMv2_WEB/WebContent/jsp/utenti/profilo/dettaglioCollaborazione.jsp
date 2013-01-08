<jsp:include page="../../layoutSuperioreUp.jsp"></jsp:include>
<title>SWIM - Profilo - Collaborazioni - Collaborazione	Dettagliata</title>
<jsp:include page="../../layoutSuperioreDown.jsp"></jsp:include>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
	<c:out value="${collaborazione.dataStipula}"></c:out>
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
	<br>
	<c:out value="${collaborazione.commentoFeedback}"></c:out>
	<br />
	</c:if>
	<a href="profilo/azioni/collaborazioni">Torna alla lista di collaborazioni</a>
<jsp:include page="../../layoutInferiore.jsp"></jsp:include>