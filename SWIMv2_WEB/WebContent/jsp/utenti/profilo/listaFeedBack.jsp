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
	<h1><c:out value="Collaborazioni"></c:out></h1>
	<br><br>
	<div align="center">
		<c:forEach items="${collaborazioni}" var="collaborazione">
			<table id="tabellaFeedback">
				<tr>
				  <th>Nome</th>
				  <th>Chi ha rilasciato il feedback</th>
				  <th>Punteggio</th>
				  <th>Commento</th>
				</tr>
				<tr>
					<td ><c:out value="${collaborazione.nome}" /></td>
					<td><c:out value="${collaborazione.utenteRichiedente.nome}" />&nbsp;<c:out value="${collaborazione.utenteRichiedente.cognome}" /></td>
					<td ><c:out value="${collaborazione.punteggioFeedback}" /></td>
					<td style="text-align: justify;"><c:out value="${collaborazione.commentoFeedback}" /></td>
				</tr>
			</table>
			<br><br>
		</c:forEach>
		<c:if test="${!empty altroUtente}">
			<a href="profilo/azioni/dettagliAltroUtente?utente=${collaborazioni}&tipoRicerca=${tipoRicerca}">Torna al profilo dell'utente ricercato</a>
		</c:if>
	</div>
</c:if>


<jsp:include page="../../layoutInferiore.jsp"></jsp:include>