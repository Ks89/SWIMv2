<jsp:include page="../layoutSuperioreUp.jsp"></jsp:include>
<title>SWIM</title>
<jsp:include page="../layoutSuperioreDownVisitatore.jsp"></jsp:include>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%-- if che vede se non sono vuoti dei valori passati dalla servlet per mostrare messaggi di successo e/o errore --%>
	<c:if test="${!empty erroreLoginFallito}">
	Si è verificato un errore: <c:out value="${erroreLoginFallito}"></c:out><br>
	</c:if>

	Homepage utente
	<br>

	<!--  uso una choose per stabilire quale form mostrare. Se l'utente e' collegato 
	mostro le funzioni di ricerca ecc... altrimenti la form di login e registrazione -->
	<c:if test="${sessionScope.utenteCollegato == null}">
	<form id="loginForm" action="login" method="POST">
				Email: <input id="emailUtente" type="text" name="emailUtente"></input> Password: <input id="password" type="password" name="password"></input> <input
					id="submit" type="submit" /> <br> <a href="registrazione">Registrazione</a>
			</form>
			
			<br><br>
			<%-- href fa sempre riferimento alla servlet in modo relativo, mai con link assoluti --%>
			<a href="profilo/azioni/ricerchePerUtentiLoggati?tipoRicerca=aiuto">Ricerca utenti per visitatori</a>
	</c:if>
	
	<%-- Esempio che mostra come mostrare dei pezzi di pagina quando l'utente e' connesso oppure non lo e' --%>
<%-- 	<c:choose> --%>
<%-- 		<c:when test="${sessionScope.utenteCollegato != null}"> --%>
<!-- 			<form action="ricerca" method="POST"> -->
<!-- 				Cognome: <input type="text" name="cognome" /> <br> Abilita':<br> -->
<%-- 				<c:forEach items="${abilita}" var="item"> --%>
<%-- 					<input type="checkbox" name="abilita" value='<c:out value="${item.nome}"/>' /> --%>
<%-- 					<c:out value="${item.nome}" /> --%>
<!-- 					<br> -->
<%-- 				</c:forEach> --%>
<!-- 				<br> <input type="submit" /> -->
<!-- 			</form> -->
<!-- 			<br><br> -->
<%-- 		</c:when> --%>
<%-- 		<c:otherwise> --%>
<!-- 			<form id="loginForm" action="login" method="POST"> -->
<!-- 				Email: <input id="emailUtente" type="text" name="emailUtente"></input> Password: <input id="password" type="password" name="password"></input> <input -->
<!-- 					id="submit" type="submit" /> <br> <a href="registrazione">Registrazione</a> -->
<!-- 			</form> -->	
<!-- 			<br><br> -->
<!-- 			<a href="profilo/azioni/ricerche?utenti=pervisitatori">Ricerca utenti per visitatori</a> -->
<%-- 		</c:otherwise> --%>
<%-- 	</c:choose> --%>
<jsp:include page="../layoutInferiore.jsp"></jsp:include>