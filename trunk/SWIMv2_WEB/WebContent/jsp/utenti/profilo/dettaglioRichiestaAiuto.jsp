<jsp:include page="../../layoutSuperioreUp.jsp"></jsp:include>
<title>SWIM - Profilo - Richiesta d'aiuto</title>
<jsp:include page="../../layoutSuperioreDown.jsp"></jsp:include>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<h1>Dettaglio della richiesta aiuto</h1>
	<section>
	<div align="center" class="alignCenter">
		<c:if test="${!empty confermaRichiestaAiuto}">
			<div class="conferma">
				<p>
					<c:out value="${confermaRichiestaAiuto}"></c:out>
				</p>
			</div>
			<br>
		</c:if>
		<c:if test="${!empty erroreConfermaRichiestaAiuto}">
			<div class="alert">
				<p>
					<c:out value="${erroreConfermaRichiestaAiuto}"></c:out>
				</p>
			</div>
			<br>
		</c:if>
		<c:if test="${!empty erroreCollabNull}">
			<div class="alert">
				<p>
					<c:out value="${erroreCollabNull}"></c:out>
				</p>
			</div>
			<br>
		</c:if>
		
		
		<h2>Richiesta d'aiuto</h2>
		<table id="tabellaFeedback">
			<tr>
			  <th>Utente</th>
			  <th>Nome collaborazione</th>
			  <th>Descrizione</th>
			</tr>
			<tr>
				<td><c:out value="${nomeRichiedeAiuto}"></c:out>&nbsp;<c:out value="${cognomeRichiedeAiuto}"></c:out></td>
				<td><c:out value="${nomeCollaborazione}"></c:out></td>
				<td><c:out value="${descrizioneCollaborazione}"></c:out></td>
			</tr>
		<c:if test="${empty confermaRichiestaAiuto and empty erroreConfermaRichiestaAiuto}">
			<tr><td colspan="3" style="text-align: center;">
			<form id="dettaglioRichiestaAiuto" action="profilo/azioni/dettaglioRichiestaAiuto" method="POST">
				<input type="hidden" name="tipo" /> 
				<input type="hidden" name="idCollaborazione" value="${idCollaborazione}">
				<input type="button" value="conferma" id="button" onclick="dettaglioRichiestaAiuto.elements['tipo'].value='CONFERMA'; dettaglioRichiestaAiuto.submit();" /> 
				<input type="button" value="rifiuta" id="button" onclick="dettaglioRichiestaAiuto.elements['tipo'].value='RIFIUTA'; dettaglioRichiestaAiuto.submit();" />
			</form></td></tr>
		</c:if>
		</table>
		<br>
		<a href="profilo/azioni/notifiche">Torna alle notifiche</a>
	</div>
<jsp:include page="../../layoutInferiore.jsp"></jsp:include>