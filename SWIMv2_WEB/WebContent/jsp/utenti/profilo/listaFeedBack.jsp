<jsp:include page="../../layoutSuperioreUp.jsp"></jsp:include>
<title>SWIM - Profilo - Collaborazioni</title>
<jsp:include page="../../layoutSuperioreDown.jsp"></jsp:include>
<!--<link rel="stylesheet" href="../../css/global.css" type="text/css"/> -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${!empty erroreGetCollaborazioni}">
	<c:out value="${erroreGetCollaborazioni}"></c:out>
	<br>
</c:if>
<c:if test="${!empty nonCiSonoCollaborazioni}">
	<c:out value="${nonCiSonoCollaborazioni}"></c:out>
	<br>
</c:if>
<c:if test="${empty nonCiSonoCollaborazioni}">
	<c:out value="Collaborazioni: "></c:out>
	<br>
	<c:forEach items="${collaborazioni}" var="collaborazione">
		<br>
		<br>
		<c:out value="Nome collaborazione: "></c:out>
		<c:out value="${collaborazione.nome}" />
		<br>
		<c:out value="Nome utente che ha rilasciato il feedback: "></c:out>
		<c:out value="${collaborazione.utenteRichiedente.nome}" />&nbsp;<c:out
			value="${collaborazione.utenteRichiedente.cognome}" />
		<br>
		<c:out value="Punteggio ottenuto: "></c:out>
		<c:out value="${collaborazione.punteggioFeedback}" />
		<br>
		<c:out value="Commento: "></c:out>
		<br>
		<c:out value="${collaborazione.commentoFeedback}" />
	</c:forEach>

</c:if>


<jsp:include page="../../layoutInferiore.jsp"></jsp:include>