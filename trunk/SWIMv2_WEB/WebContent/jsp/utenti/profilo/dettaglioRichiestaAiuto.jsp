<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SWIM - Profilo - Richiesta d'aiuto</title>
</head>
<body>
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
		<form id="dettaglioRichiestaAiuto" action="dettaglioRichiestaAiuto" method="POST">
			<input type="hidden" name="tipo" /> 
			<input type="hidden" name="idCollaborazione" value="${idCollaborazione}">
			<input type="button" value="conferma" onclick="dettaglioRichiestaAiuto.elements['tipo'].value='CONFERMA'; dettaglioRichiestaAiuto.submit();" /> <input 
				type="button" value="rifiuta" onclick="dettaglioRichiestaAiuto.elements['tipo'].value='RIFIUTA'; dettaglioRichiestaAiuto.submit();" />
		</form>
	</c:if>

	<br>
	<a href="../azioni/notifiche">Torna alle notifiche</a>
</body>
</html>