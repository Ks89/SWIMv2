<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SWIM - Profilo - Collaborazioni</title>
</head>
<body>
	<c:if test="${!empty erroreGetCollaborazioniDaTerminare}">
		<c:out value="${erroreGetCollaborazioniDaTerminare}"></c:out>
		<br>
	</c:if>
	<c:if test="${!empty nonCiSonoCollaborazioniDaTerminare}">
		<c:out value="${nonCiSonoCollaborazioniDaTerminare}"></c:out>
		<br>
	</c:if>
	<c:if test="${empty nonCiSonoCollaborazioniDaTerminare}">
		<c:out value="Collaborazioni da terminare:"></c:out>
		<br>
		<br> 
		<table id="tabellaCollaborazioniDaTerminare" style="border-width: medium; border-style: solid;">
			<tr>
				<th>Utente Collaborante</th>
				<th>Nome Collaborazione</th>
				<th>Azione</th>
			</tr>
			<c:forEach items="${collaborazioniDaTerminare}" var="collaborazione">
				<tr>
					<td><c:out value="${collaborazione.utenteRicevente.nome}" />&nbsp;<c:out value="${collaborazione.utenteRicevente.cognome}" /></td>
					<td><a href="../azioni/CollaborazioneDettagliata?idCollaborazione=<c:out value="${collaborazione.id}" />"><c:out
								value="${collaborazione.nome}" /></a></td>
					<td><c:out value="Termina Collaborazione"></c:out>
				</tr>
			</c:forEach>
		</table>
		<div id="pageNavPosition1"></div>

		<%-- <script type="text/javascript">
			var pager = new Pager('tabellaCollaborazioniDaTerminare', 5);
			pager.init();
			pager.showPageNav('pager', 'pageNavPosition1');
			pager.showPage(1);
		</script>--%>
	</c:if>
	<c:if test="${empty nonCiSonoCollaborazioniSenzaFeedback}">
		<c:out value="Collaborazioni senza feedback:"></c:out>
		<br>
		<br> 
		<table id="tabellaCollaborazioniSenzaFeedBack" style="border-width: medium; border-style: solid;">
			<tr>
				<th>Utente Collaborante</th>
				<th>Nome Collaborazione</th>
				<th>Azione</th>
			</tr>
	<c:forEach items="${collaborazioniDaRilasciareFeedBack}" var="collaborazioneFeedBack">
				<tr>
					<td><c:out value="${collaborazioneFeedBack.utenteRicevente.nome}" />&nbsp;<c:out value="${collaborazioneFeedBack.utenteRicevente.cognome}" /></td>
					<td><a href="../azioni/CollaborazioneDettagliata?idCollaborazione=<c:out value="${collaborazioneFeedBack.id}" />"><c:out
								value="${collaborazioneFeedBack.nome}" /></a></td>
					<td><c:out value="Aggiungi FeedBack"></c:out>
				</tr>
			</c:forEach>
		</table>
		<div id="pageNavPosition1"></div>

		<%-- <script type="text/javascript">
			var pager = new Pager('tabellaCollaborazioniSenzaFeedBack', 5);
			pager.init();
			pager.showPageNav('pager', 'pageNavPosition1');
			pager.showPage(1);
		</script>--%>
	</c:if>
	<a href="../profilo">Torna al profilo</a>

</body>
</html>