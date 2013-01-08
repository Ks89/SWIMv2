<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SWIM - Profilo - Notifiche di risposta</title>
</head>
<body>
	<br> I seguenti utenti hanno accettato le tue richieste di amicizia tramite suggerimento:
	<br>
	<ul>
		<c:forEach items="${utentiAccettatiIndiretti}" var="utenteAccettatoIndiretto">
			<li><c:out value="${utenteAccettatoIndiretto.nome}"></c:out> <c:out value="${utenteAccettatoIndiretto.cognome}"></c:out></li>
		</c:forEach>
	</ul>

	<br>
	<br> I seguenti utenti hanno accettato le tue richieste di amicizia e per ognuno di essi sono mostrati i suggerimenti di amiciza:
	<br>
	<br>
	<c:forEach items="${listaUtentiConSuggerimenti}" var="utenteConSuggerimenti">
		<c:out value="${utenteConSuggerimenti.utente.nome}"></c:out>
		<c:out value="${utenteConSuggerimenti.utente.cognome}"></c:out>

		<br>
		<table id="${utenteConSuggerimenti.utente.email}" style="border-width: medium; border-style: solid;">
			<tr>
				<th>Utente che ha accettato</th>
				<th>Accetta suggerimento</th>
			</tr>
			<c:forEach items="${utenteConSuggerimenti.suggerimenti}" var="suggerimentoUtente">
				<tr>
					<td><c:out value="${suggerimentoUtente.nome}" />&nbsp;<c:out value="${suggerimentoUtente.cognome}" /></td>
					<td><form action="suggerimentiAlRichiedente" method="POST">
							<input type="hidden" name="emailSuggAccettato" value="${suggerimentoUtente.email}"></input> <input id="submit" type="submit" value="Accetta"/>
						</form></td>
				</tr>
			</c:forEach>
		</table>
		<br>
	</c:forEach>
	<br> E' possibile accettare un solo suggerimenti presente in questa pagina
	<br>
	<a href="../../profilo">Torna indietro</a>
</body>
</html>