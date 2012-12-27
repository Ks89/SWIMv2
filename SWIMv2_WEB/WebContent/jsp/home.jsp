<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SWIM</title>
</head>
<body>
	<c:if test="${!empty errorMessage}">
	Si è verificato un errore: <c:out value="${errorMessage}"></c:out>
	</c:if>

	Questa è la home
	<br>

	<c:choose>
		<c:when test="${sessionScope.utenteCollegato != null}">

			<form action="Ricerca" method="POST">
				Cognome: <input type="text" name="cognome" /> <br> Abilita':<br>
				<c:forEach items="${abilita}" var="item">
					<input type="checkbox" name="abilita" value='<c:out value="${item.nome}"/>' />
					<c:out value="${item.nome}" />
					<br>
				</c:forEach>
				<br> <input type="submit" />
			</form>

		</c:when>
		<c:otherwise>
			<form action="Login" method="POST">
				Username: <input type="text" name="username"></input> Password: <input type="password" name="password"></input> <input type="submit" /> <br>
				<a href="Registrazione">Registrazione</a>
				<!-- Sarà una servlet dedicata -->
			</form>
		</c:otherwise>
	</c:choose>



</body>
</html>