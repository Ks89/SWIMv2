<jsp:include page="../../layoutSuperioreUp.jsp"></jsp:include>
<title>SWIM - Profilo - Collaborazioni</title>
<jsp:include page="../../layoutSuperioreDown.jsp"></jsp:include>
<!--<link rel="stylesheet" href="../../css/global.css" type="text/css"/> -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h1>Collaborazioni</h1>
<section>
	<div align="center" class="alignCenter">
		<c:if test="${!empty erroreGetCollaborazioniDaTerminare}">
			<div class="alert">
				<p>
					<c:out value="${erroreGetCollaborazioniDaTerminare}"></c:out>
				</p>
			</div>
		</c:if>
		<c:if test="${!empty nonCiSonoCollaborazioniDaTerminare}">
			<div class="generico">
				<p>
					<c:out value="${nonCiSonoCollaborazioniDaTerminare}"></c:out>
				</p>
			</div>
		</c:if>
		<c:if test="${!empty erroreGetCollaborazioniSenzaFeedback}">
			<div class="alert">
				<p>
					<c:out value="${erroreGetCollaborazioniSenzaFeedback}"></c:out>
				</p>
			</div>
		</c:if>
		<c:if test="${empty nonCiSonoCollaborazioniDaTerminare}">
			<h2>
				<c:out value="Collaborazioni in corso:"></c:out>
			</h2>
			<form id="collaborazioniInCorso"
				action="profilo/azioni/collaborazioni" method="POST">
				<input type="hidden" name="tipo" />
				<table id="tabellaRigheAlterne">
					<tr>
						<th>Utente Collaborante</th>
						<th>Nome Collaborazione</th>
						<th>Azione</th>
					</tr>
					<c:forEach items="${collaborazioniDaTerminare}"
						var="collaborazione" varStatus="num">
						<c:if test="${num.count>0}">
							<c:if test="${num.count%2!=0}">
								<tr>
							</c:if>
							<c:if test="${num.count%2==0}">
								<tr class="alt">
							</c:if>
						</c:if>
						<td><c:out value="${collaborazione.utenteRicevente.nome}" />&nbsp;<c:out
								value="${collaborazione.utenteRicevente.cognome}" /></td>
						<td><a
							href="profilo/azioni/dettaglioCollaborazione?idCollaborazione=<c:out value="${collaborazione.id}" />"><c:out
									value="${collaborazione.nome}" /></a></td>
						<td><input id="button" type="button" value="Termina"
							onclick="collaborazioniInCorso.elements['tipo'].value=${collaborazione.id}; collaborazioniInCorso.submit();" />
						</tr>
					</c:forEach>
				</table>
			</form>
			<br>
		</c:if>
		<c:if test="${!empty nonCiSonoCollaborazioniSenzaFeedback}">
			<div class="generico">
				<p>
					<c:out value="${nonCiSonoCollaborazioniSenzaFeedback}"></c:out>
				</p>
			</div>
		</c:if>
		<c:if test="${empty nonCiSonoCollaborazioniSenzaFeedback}">
			<h2>
				<c:out value="Collaborazioni senza feedback:"></c:out>
			</h2>
			<table id="tabellaRigheAlterne">
				<tr>
					<th>Utente Collaborante</th>
					<th>Nome Collaborazione</th>
					<th>Azione</th>
				</tr>
				<c:forEach items="${collaborazioniDaRilasciareFeedBack}"
					var="collaborazioneFeedBack" varStatus="num">
					<c:if test="${num.count>0}">
						<c:if test="${num.count%2!=0}">
							<tr>
						</c:if>
						<c:if test="${num.count%2==0}">
							<tr class="alt">
						</c:if>
					</c:if>
					<td><c:out
							value="${collaborazioneFeedBack.utenteRicevente.nome}" />&nbsp;<c:out
							value="${collaborazioneFeedBack.utenteRicevente.cognome}" /></td>
					<td><a
						href="profilo/azioni/dettaglioCollaborazione?idCollaborazione=<c:out value="${collaborazioneFeedBack.id}&terminata=si" />"><c:out
								value="${collaborazioneFeedBack.nome}" /></a></td>
					<td><a
						href="profilo/azioni/rilasciaFeedBack?idCollaborazione=<c:out value="${collaborazioneFeedBack.id}" />"><c:out
								value="Aggiungi Feedback" /></a></td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
		<%-- <br>--%>
		<c:if test="${!empty erroreGetCollaborazioniTerminateConFeedBack}">
			<div class="alert">
				<p>
					<c:out value="${erroreGetCollaborazioniTerminateConFeedBack}"></c:out>
				</p>
			</div>
		</c:if>
		<c:if test="${!empty nonCiSonoCollaborazioniTerminateConFeedBack}">
			<div class="generico">
				<p>
					<c:out value="${nonCiSonoCollaborazioniTerminateConFeedBack}"></c:out>
				</p>
			</div>
		</c:if>

		<c:if test="${empty nonCiSonoCollaborazioniTerminateConFeedBack}">
			<h2>
				<c:out value="Storico collaborazioni:"></c:out>
			</h2>
			<table id="tabellaRigheAlterne">
				<tr>
					<th>Utente Collaborante</th>
					<th>Nome Collaborazione</th>
				</tr>
				<c:forEach items="${collaborazioniTerminateConFeedBack}"
					var="collaborazioneTerminateConFeedBack" varStatus="num">
					<c:if test="${num.count>0}">
						<c:if test="${num.count%2!=0}">
							<tr>
						</c:if>
						<c:if test="${num.count%2==0}">
							<tr class="alt">
						</c:if>
					</c:if>
					<td><c:out
							value="${collaborazioneTerminateConFeedBack.utenteRicevente.nome}" />&nbsp;<c:out
							value="${collaborazioneTerminateConFeedBack.utenteRicevente.cognome}" /></td>
					<td><a
						href="profilo/azioni/dettaglioCollaborazione?idCollaborazione=<c:out value="${collaborazioneTerminateConFeedBack.id}&terminata=si&conFeedBack=si" />"><c:out
								value="${collaborazioneTerminateConFeedBack.nome}" /></a></td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
	</div>
	<jsp:include page="../../layoutInferiore.jsp"></jsp:include>