<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SWIM</title>
</head>
<body>
	<!-- Ricevo l'attributo erroreLoginFallito dalla LoginServlet, se empty non c'e' errorem altrimenti lo mostro-->

	<c:if test="${!empty erroreLoginFallito}">
	Si è verificato un errore: <c:out value="${erroreLoginFallito}"></c:out>
	</c:if>

	<br> Homepage utente
	<br>

	<!--  uso una choose per stabilire quale form mostrare. Se l'utente e' collegato 
	mostro le funzioni di ricerca ecc... altrimenti la form di login e registrazione -->
	<c:choose>
		<c:when test="${sessionScope.utenteCollegato != null}">
			<form action="ricerca" method="POST">
				Cognome: <input type="text" name="cognome" /> <br> Abilita':<br>
				<c:forEach items="${abilita}" var="item">
					<input type="checkbox" name="abilita" value='<c:out value="${item.nome}"/>' />
					<c:out value="${item.nome}" />
					<br>
				</c:forEach>
				<br> <input type="submit" />
			</form>
			<br><br>
			<a href="profilo/azioni/ricerche?utenti=perregistrati">Ricerca utenti</a>
			<br><br>
			<a href="profilo/azioni/ricerche?aiuto=true">Ricerca aiuto</a>
			<br><br>
			<a href="profilo/azioni/collaborazioni">Visualizza collaborazioni</a>
			<br><br>
			<a href="profilo/azioni/notifiche">Visualizza notifiche</a>
			<br><br>
			<a href="profilo/azioni/proposteAbilita">Proponi una nuova abilita</a>
			<br><br>
			<a href="profilo/profilo">Torna al profilo</a>
			<br><br>
			<a href="login?esci=true">Logout</a>
		</c:when>
		<c:otherwise>
			<form id="loginForm" action="login" method="POST">
				Email: <input id="emailUtente" type="text" name="emailUtente"></input> Password: <input id="password" type="password" name="password"></input> <input
					id="submit" type="submit" /> <br> <a href="registrazione">Registrazione</a>
			</form>
			
			<br><br>
			<a href="profilo/azioni/ricerche?utenti=pervisitatori">Ricerca utenti per visitatori</a>
		</c:otherwise>
	</c:choose>
</body>
</html>