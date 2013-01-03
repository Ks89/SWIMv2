<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SWIM - Profilo - Ricerche</title>
</head>
<body>
<%-- Questa jsp conterra' con un choose 3 casi possibili, cioe' ricerca per visitatori, ricerca aiuto e ricerca utenti --%>
<form action="ricerchePerUtentiLoggati" method="POST" enctype="multipart/form-data">
	<c:choose>
		<c:when test="${tipoRicerca=='aiuto'}">
			<%--<c:out value="Aiuto per utenti loggati"></c:out>--%>
			<c:forEach items="${abilita}" var="item">
				<input type="checkbox" name="abilita" value='<c:out value="${item.nome}"/>' />
				<c:out value="${item.nome}" />
				<br>
			</c:forEach>
			 <input id="Cerca" type="submit" />
			 <c:if test="${risultatoRicerca}">
			 	<table id="tabellaRichiesteAiuto" style="border-width: medium; border-style: solid;">
					<tr>
						<th>Nome</th>
						<th>Cognome</th>
						<th>Indirizzo Email</th>
					</tr>
					<c:forEach items="${utenti}" var="utente">
						<tr>
							<td><c:out value="${utente.nome}" /></td>
							<td><c:out value="${utente.cognome}" /></td>
							<td><c:out value="${utente.email}" /></td>
						</tr>
					</c:forEach>
				</table>
			 </c:if>
		</c:when>
		<c:when test="${tipoRicerca=='aiutoVisitatore'}">
			<c:forEach items="${abilita}" var="item">
				<input type="checkbox" name="abilita" value='<c:out value="${item.nome}"/>' />
				<c:out value="${item.nome}" />
				<br>
			</c:forEach>
			 <input id="Cerca" type="submit" />
			  <c:if test="${risultatoRicerca}">
			 	<table id="tabellaRichiesteAiuto" style="border-width: medium; border-style: solid;">
					<tr>
						<th>Nome</th>
						<th>Cognome</th>
					</tr>
					<c:forEach items="${utenti}" var="utente">
						<tr>
							<td><c:out value="${utente.nome}" /></td>
							<td><c:out value="${utente.cognome}" /></td>
						</tr>
					</c:forEach>
				</table>
			 </c:if>
		</c:when>
		<c:otherwise>
			Nome:<input type="text" name="nomeUtente"></input>
			Cognome:<input type="text" name="cognomeUtente"></input>
			<input id="Cerca" type="submit" />
			<c:if test="${risultatoRicerca}">
			 	<table id="tabellaRichiesteAiuto" style="border-width: medium; border-style: solid;">
					<tr>
						<th>Nome</th>
						<th>Cognome</th>
						<th>Indirizzo Email</th>
					</tr>
					<c:forEach items="${utenti}" var="utente">
						<tr>
							<td><c:out value="${utente.nome}" /></td>
							<td><c:out value="${utente.cognome}" /></td>
							<td><c:out value="${utente.email}" /></td>
						</tr>
					</c:forEach>
				</table>
			 </c:if>
		</c:otherwise>
	</c:choose>
</form>
</body>
</html>