<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SWIM - Profilo - Notifiche</title>
</head>
<body>
	<%-- if che vede se non sono vuoti dei valori passati dalla servlet per mostrare messaggi di successo e/o errore --%>
	<c:if test="${!empty erroreGetNotificheRichiesteAiuto}">
		<c:out value="${erroreGetNotificheRichiesteAiuto}"></c:out>
		<br>
	</c:if>
	<c:if test="${!empty nonCiSonoRichiesteAiuto}">
		<c:out value="${nonCiSonoRichiesteAiuto}"></c:out>
		<br>
	</c:if>

	<br>
	<br> Richieste di aiuto, inviate dagli utenti:
	<table id="tabellaRichiesteAiuto" style="border-width: medium; border-style: solid;">
		<tr>
			<th>Utente richiedente</th>
			<th>Nome richiesta aiuto</th>
		</tr>
		<c:forEach items="${richiesteAiuto}" var="richiestaAiuto">
			<tr>
				<td><c:out value="${richiestaAiuto.utenteRichiedente.nome}" />&nbsp;<c:out value="${richiestaAiuto.utenteRichiedente.cognome}" /></td>
				<td><a href="../profilo/azioni/dettaglioNotifica?idCollaborazione=<c:out value="${richiestaAiuto.id}" />"><c:out
							value="${richiestaAiuto.nome}" /></a></td>
			</tr>
		</c:forEach>
	</table>
	<div id="pageNavPosition1"></div>

	<script type="text/javascript">
		var pager = new Pager('tabellaRichiesteAiuto', 5);
		pager.init();
		pager.showPageNav('pager', 'pageNavPosition1');
		pager.showPage(1);
	</script>
	
	<br><br>
	<br> Richieste di amicizia:
	<table id="tabellaRichiesteAmicizia" style="border-width: medium; border-style: solid;">
		<tr>
			<th>Utente richiedente</th>
		</tr>
		<c:forEach items="${richiesteAmicizia}" var="richiesteAmicizia">
			<tr>
				<td><a href="../profilo/azioni/profiloAltroUtente?iemailUtenteRichiedente=<c:out value="${richiesteAmicizia.email}" />">
				<c:out value="${richiesteAmicizia.nome}" />&nbsp;<c:out value="${richiesteAmicizia.cognome}" /></a></td>
			</tr>
		</c:forEach>
	</table>
	<div id="pageNavPosition2"></div>

	<script type="text/javascript">
		var pager = new Pager('tabellaRichiesteAmicizia', 5);
		pager.init();
		pager.showPageNav('pager', 'pageNavPosition2');
		pager.showPage(1);
	</script>

	<br>
	<a href="../profilo">Torna al profilo</a>
</body>
</html>