<jsp:include page="../../layoutSuperioreUp.jsp"></jsp:include>
<title>SWIM - Profilo - Notifiche di risposta</title>
<jsp:include page="../../layoutSuperioreDown.jsp"></jsp:include>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h1>Notifiche di risposta</h1>
	<section>
<div align="center" class="alignCenter">
<c:choose>
	<c:when test="${empty utentiAccettatiIndiretti}">
		<c:out value=""></c:out>
	</c:when>
	<c:otherwise>
		<br>
		<br> I seguenti utenti hanno accettato le tue richieste di amicizia tramite suggerimento:
		<br>
		<ul>
			<c:forEach items="${utentiAccettatiIndiretti}" var="utenteAccettatoIndiretto">
				<li><c:out value="${utenteAccettatoIndiretto.nome}"></c:out> <c:out value="${utenteAccettatoIndiretto.cognome}"></c:out></li>
			</c:forEach>
		</ul>
	</c:otherwise>
</c:choose>

<c:choose>
	<c:when test="${empty listaUtentiConSuggerimenti}">
		<div class="alert">
			<p>
				Non ci sono utenti che hanno accettato recentemente le tue richieste di amicizia
			</p>
		</div>
	</c:when>
	<c:otherwise>
		<br>
		<br>Hai stretto amicizia con: 
		<br>
		<ul>
			<c:forEach items="${listaUtentiConSuggerimenti}" var="utenteConSuggerimenti">
				<li><c:out value="${utenteConSuggerimenti.utente.nome}"></c:out> <c:out value="${utenteConSuggerimenti.utente.cognome}"></c:out></li>
			</c:forEach>
		</ul>
		<br>
		<c:if test="${empty nonCiSonoSuggerimenti}">
			<br>Suggerimenti di amiciza:
			<br><br>
			<form id="suggAlRichiedenteForm" action="profilo/azioni/notificheDiRisposta/suggAlRichiedente" method="POST">
				<table id="SuggAlRichiedenteTable" style="border-width: medium; border-style: solid;">
					<tr>
						<th></th>
						<th>Amico acquisito</th>
						<th>Utente suggerito</th>
					</tr>
					<c:forEach items="${listaUtentiConSuggerimenti}" var="utenteConSuggerimenti">
						<c:forEach items="${utenteConSuggerimenti.suggerimenti}" var="suggerimentoUtente">
							<c:if test="${!empty utenteConSuggerimenti.suggerimenti}">
								<tr>
									<td><input type="checkbox" name="suggeritoAlRichiedenteCheckbox" value="${suggerimentoUtente.email}" /></td>
									<td><c:out value="${utenteConSuggerimenti.utente.nome}"></c:out> <c:out value="${utenteConSuggerimenti.utente.cognome}"></c:out></td>
									<td><c:out value="${suggerimentoUtente.nome}" />&nbsp;<c:out value="${suggerimentoUtente.cognome}" /></td>
								</tr>
							</c:if>
						</c:forEach>
					</c:forEach>
				</table>
				<input id="submit" type="submit" />
			</form>		
		<br>
		</c:if>
		<br>Ricaricando la pagina, queste notifiche non saranno mostrate nuovamente.
	</c:otherwise>
</c:choose>
<br><br>
		<c:if test="${!empty erroreCollaborazioni}">
			<c:out value="${erroreCollaborazioni}"></c:out>
		</c:if>
		<c:if test="${empty erroreCollaborazioni}">
			<c:if test="${!empty noCollaborazioni }">
				<c:out value="Non ci sono collaborazioni da notificare"></c:out>
			</c:if>
			<c:if test="${empty noCollaborazioni }">
			Le seguenti collaborazioni richieste sono state confermate:
			<br>
			<br>
				<c:forEach items="${listaCollaborazioni}" var="collaborazione">
					<br>
					<br>
					<br>
					Nome collaborazione:
					<c:out value="${collaborazione.nome}"></c:out>
					<br>
					Collaboratore:
					<c:out value="${collaborazione.utenteRicevente.nome}" />&nbsp;<c:out value="${collaborazione.utenteRicevente.cognome}" />
				</c:forEach>
			</c:if>
		</c:if>
<br>
<br>
</div>
<jsp:include page="../../layoutInferiore.jsp"></jsp:include>