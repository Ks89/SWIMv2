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
		<c:out value="Collaborazioni in corso:"></c:out>
		<br>
		<br>
		<form id="collaborazioniInCorso" action="collaborazioni"
			method="POST">
			<input type="hidden" name="tipo" />
			<table id="tabellaCollaborazioniInCorso"
				style="border-width: medium; border-style: solid;">
				<tr>
					<th>Utente Collaborante</th>
					<th>Nome Collaborazione</th>
					<th>Azione</th>
				</tr>
				<c:forEach items="${collaborazioniDaTerminare}" var="collaborazione">
					<tr>
						<td><c:out value="${collaborazione.utenteRicevente.nome}" />&nbsp;<c:out
								value="${collaborazione.utenteRicevente.cognome}" /></td>
						<td><a
							href="../azioni/CollaborazioneDettagliata?idCollaborazione=<c:out value="${collaborazione.id}" />"><c:out
									value="${collaborazione.nome}" /></a></td>
						<td><input type="button" value="Termina"
							onclick="collaborazioniInCorso.elements['tipo'].value=${collaborazione.id}; collaborazioniInCorso.submit();" />
					</tr>
				</c:forEach>
			</table>
		</form>
		<c:if test="${!empty erroreGetCollaborazioniSenzaFeedback}">
			<c:out value="${erroreGetCollaborazioniSenzaFeedback}"></c:out>
			<br>
		</c:if>
		<c:if test="${!empty nonCiSonoCollaborazioniSenzaFeedback}">
			<c:out value="${nonCiSonoCollaborazioniDaTerminare}"></c:out>
			<br>
		</c:if>
	</c:if>
	<c:if test="${empty nonCiSonoCollaborazioniSenzaFeedback}">
		<c:out value="Collaborazioni senza feedback:"></c:out>
		<br>
		<br>
		<table id="tabellaCollaborazioniSenzaFeedBack"
			style="border-width: medium; border-style: solid;">
			<tr>
				<th>Utente Collaborante</th>
				<th>Nome Collaborazione</th>
				<th>Azione</th>
			</tr>
			<c:forEach items="${collaborazioniDaRilasciareFeedBack}"
				var="collaborazioneFeedBack">
				<tr>
					<td><c:out
							value="${collaborazioneFeedBack.utenteRicevente.nome}" />&nbsp;<c:out
							value="${collaborazioneFeedBack.utenteRicevente.cognome}" /></td>
					<td><a
						href="../azioni/dettaglioCollaborazione?idCollaborazione=<c:out value="${collaborazioneFeedBack.id}&terminata=si" />"><c:out
								value="${collaborazioneFeedBack.nome}" /></a></td>
					<td><a
						href="../azioni/rilasciaFeedBack?idCollaborazione=<c:out value="${collaborazioneFeedBack.id}" />"><c:out
								value="Aggiungi Feedback" /></a></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	
	<c:if test="${!empty erroreGetCollaborazioniTerminateConFeedBack}">
			<c:out value="${erroreGetCollaborazioniTerminateConFeedBack}"></c:out>
			<br>
		</c:if>
		<c:if test="${!empty nonCiSonoCollaborazioniTerminateConFeedBack}">
			<c:out value="${nonCiSonoCollaborazioniTerminateConFeedBack}"></c:out>
			<br>
		</c:if>
	<c:if test="${empty nonCiSonoCollaborazioniTerminateConFeedBack}">
		<c:out value="Storico collaborazioni:"></c:out>
		<br>
		<br>
		<table id="tabellaStoricoCollaborazioni"
			style="border-width: medium; border-style: solid;">
			<tr>
				<th>Utente Collaborante</th>
				<th>Nome Collaborazione</th>
			</tr>
			<c:forEach items="${collaborazioniTerminateConFeedBack}"
				var="collaborazioneTerminateConFeedBack">
				<tr>
					<td><c:out
							value="${collaborazioneTerminateConFeedBack.utenteRicevente.nome}" />&nbsp;<c:out
							value="${collaborazioneTerminateConFeedBack.utenteRicevente.cognome}" /></td>
					<td><a
						href="../azioni/dettaglioCollaborazione?idCollaborazione=<c:out value="${collaborazioneTerminateConFeedBack.id}&terminata=si&conFeedBack=si" />"><c:out
								value="${collaborazioneTerminateConFeedBack.nome}" /></a></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	<a href="../profilo">Torna al profilo</a>

</body>
</html>