<jsp:include page="layoutSuperioreUp.jsp"></jsp:include>
<title>SWIM - Profilo - Ricerche</title>
<jsp:include page="layoutSuperioreDown.jsp"></jsp:include>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- Questa jsp conterra' con un choose 3 casi possibili, cioe' ricerca per visitatori, ricerca aiuto e ricerca utenti --%>
<form action="profilo/azioni/ricerchePerUtentiLoggati" method="POST" enctype="multipart/form-data">
	<c:choose>
		<c:when test="${tipoRicerca=='aiuto'}">
			<%--<c:out value="Aiuto per utenti loggati"></c:out>--%>
			<c:forEach items="${abilita}" var="item">
				<input type="checkbox" name="abilita" value='<c:out value="${item.nome}"/>' />
				<c:out value="${item.nome}" />
				<br>
			</c:forEach>
			 <input id="Cerca" type="submit" value="Cerca"/>
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
							<td><a href="profilo/azioni/dettagliAltroUtente?utente=${utente.email}&tipoRicerca=aiuto"><c:out value="${utente.email}" /></a></td>
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
			 <input id="Cerca" type="submit" value="Cerca"/>
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
			 <a href="../../home">Torna alla home</a>
		</c:when>
		<c:otherwise>
			Nome:<input type="text" name="nomeUtente"></input>
			Cognome:<input type="text" name="cognomeUtente"></input>
			<input id="Cerca" type="submit" value="Cerca"/>
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
							<td><a href="profilo/azioni/dettagliAltroUtente?utente=${utente.email}&tipoRicerca=utente"><c:out value="${utente.email}" /></a></td>
						</tr>
					</c:forEach>
				</table>
			 </c:if>
		</c:otherwise>
	</c:choose>
</form>
<a href="../profilo">Torna al profilo</a>
<jsp:include page="layoutInferiore.jsp"></jsp:include>