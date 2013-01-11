<jsp:include page="../../layoutSuperioreUp.jsp"></jsp:include>
<title>SWIM - Profilo - Notifiche di risposta</title>
<jsp:include page="../../layoutSuperioreDown.jsp"></jsp:include>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
		<br>Non ci sono utenti che hanno accettato recentemte le tue richieste di amicizia
	</c:when>
	<c:otherwise>
		I seguenti utenti hanno accettato le tue richieste di amicizia e per ognuno di essi sono mostrati i suggerimenti di amiciza: 
		<br>
		<br>
 <%-- 						<c:if test="${!empty utenteConSuggerimenti.suggerimenti}"> --%>
 
		<form id="suggAlRichiedenteForm" action="suggAlRichiedente" method="POST">
			<table id="SuggAlRichiedenteTable" style="border-width: medium; border-style: solid;">
				<tr>
					<th></th>
					<th>Utente che suggerisce</th>
					<th>Utente suggerito</th>
				</tr>
				<c:forEach items="${listaUtentiConSuggerimenti}" var="utenteConSuggerimenti">
					<c:forEach items="${utenteConSuggerimenti.suggerimenti}" var="suggerimentoUtente">
							<tr>
								<td><input type="checkbox" name="amicoSuggeritoAlRichiedente" value="${suggerimentoUtente.email}" /></td>
								<td><c:out value="${utenteConSuggerimenti.utente.nome}"></c:out> <c:out value="${utenteConSuggerimenti.utente.cognome}"></c:out></td>
								<td><c:out value="${suggerimentoUtente.nome}" />&nbsp;<c:out value="${suggerimentoUtente.cognome}" /></td>
							</tr>
					</c:forEach>
				</c:forEach>
			</table>
			<input id="submit" type="submit" />
		</form>
<%-- 						</c:if> --%>
	</c:otherwise>
</c:choose>
<br>
<br>
<jsp:include page="../../layoutInferiore.jsp"></jsp:include>