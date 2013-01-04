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
	<%-- Richieste aiuto --%>
	<c:if test="${!empty erroreGetNotificheRichiesteAiuto}">
		<c:out value="${erroreGetNotificheRichiesteAiuto}"></c:out>
		<br>
	</c:if>
	<c:if test="${!empty okDettaglioRichiestaAiuto}">
		<c:out value="${okDettaglioRichiestaAiuto}"></c:out>
		<br>
	</c:if>
	<c:if test="${!empty erroreDettaglioRichiestaAiuto}">
		<c:out value="${erroreDettaglioRichiestaAiuto}"></c:out>
		<br>
	</c:if>
	<c:if test="${!empty nonCiSonoRichiesteAiuto}">
		<c:out value="${nonCiSonoRichiesteAiuto}"></c:out>
		<br>
	</c:if>
	<%-- Richieste amicizia --%>
	<c:if test="${!empty erroreGetNotificheRichiesteAmicizia}">
		<c:out value="${erroreGetNotificheRichiesteAmicizia}"></c:out>
		<br>
	</c:if>
	<c:if test="${!empty okProfiloAltroUtente}">
		<c:out value="${okProfiloAltroUtente}"></c:out>
		<br>
	</c:if>
	<c:if test="${!empty erroreProfiloAltroUtente}">
		<c:out value="${erroreProfiloAltroUtente}"></c:out>
		<br>
	</c:if>
	<c:if test="${!empty nonCiSonoRichiesteAmicizia}">
		<c:out value="${nonCiSonoRichiesteAmicizia}"></c:out>
		<br>
	</c:if>



	<c:if test="${empty nonCiSonoRichiesteAiuto}">
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
					<td><a href="../azioni/dettaglioRichiestaAiuto?idCollaborazione=<c:out value="${richiestaAiuto.id}" />"><c:out
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
	</c:if>

	<br>
	
	<c:if test="${empty nonCiSonoRichiesteAmicizia}">
		<br>
		<br> Richieste di amicizia:
	<table id="tabellaRichiesteAmicizia" style="border-width: medium; border-style: solid;">
			<tr>
				<th>Utente richiedente</th>
			</tr>
			<c:forEach items="${utentiCheRichidonoAmicizia}" var="utenteCheRichiedeAmicizia">
				<tr>
					<td><a href="../azioni/profiloAltroUtente?emailUtenteRichiedente=<c:out value="${utenteCheRichiedeAmicizia.email}" />"> <c:out
								value="${utenteCheRichiedeAmicizia.nome}" />&nbsp;<c:out value="${utenteCheRichiedeAmicizia.cognome}" /></a></td>
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
	</c:if>

	<br>
	<a href="../profilo">Torna al profilo</a>
</body>
</html>