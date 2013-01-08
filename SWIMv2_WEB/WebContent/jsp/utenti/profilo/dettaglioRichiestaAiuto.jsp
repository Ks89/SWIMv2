<jsp:include page="../../layoutSuperioreUp.jsp"></jsp:include>
<title>SWIM - Profilo - Richiesta d'aiuto</title>
<jsp:include page="../../layoutSuperioreDown.jsp"></jsp:include>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<c:if test="${!empty confermaRichiestaAiuto}">
		<c:out value="${confermaRichiestaAiuto}"></c:out>
		<br>
	</c:if>
	<c:if test="${!empty erroreConfermaRichiestaAiuto}">
		<c:out value="${erroreConfermaRichiestaAiuto}"></c:out>
		<br>
	</c:if>

	<br>Richiesta d'aiuto: 
	<br> Utente: 
	<c:out value="${nomeRichiedeAiuto}"></c:out>
	<c:out value="${cognomeRichiedeAiuto}"></c:out>
	<br> Nome collaborazione:
	<c:out value="${nomeCollaborazione}"></c:out>
	<br> Descrizione:
	<c:out value="${descrizioneCollaborazione}"></c:out>

	<c:if test="${empty confermaRichiestaAiuto and empty erroreConfermaRichiestaAiuto}">
		<form id="dettaglioRichiestaAiuto" action="profilo/azioni/notifiche/dettaglioRichiestaAiuto" method="POST">
			<input type="hidden" name="tipo" /> 
			<input type="hidden" name="idCollaborazione" value="${idCollaborazione}">
			<input type="button" value="conferma" onclick="dettaglioRichiestaAiuto.elements['tipo'].value='CONFERMA'; dettaglioRichiestaAiuto.submit();" /> 
			<input type="button" value="rifiuta" onclick="dettaglioRichiestaAiuto.elements['tipo'].value='RIFIUTA'; dettaglioRichiestaAiuto.submit();" />
		</form>
	</c:if>

	<br>
	<a href="profilo/azioni/notifiche">Torna alle notifiche</a>
<jsp:include page="../../layoutInferiore.jsp"></jsp:include>