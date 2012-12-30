<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SWIM - Amministrazione</title>
</head>
<body>
	<c:if test="${!empty erroreLoginAdminFallito}">
	Si � verificato un errore: <c:out value="${erroreLoginAdminFallito}"></c:out>
	</c:if>
	
	<c:choose>
		<c:when test="${sessionScope.adminCollegato != null}">
			<!-- Link per accedere alle varie funzionalita' di amministrazione -->
			<br>Pagina di amministrazione
			<br><br>
			<a href="admin/adminPanel?operazione=1">Aggiungi abilita</a>
			<br><br>
			<a href="admin/adminPanel?operazione=2">Visualizza proposte abilita</a>
			<br><br>
			<a href="admin/adminLogin?adminesci=true">Logout</a>
		</c:when>
		<c:otherwise>
			<br>Pagina di login per l'amministratore<br>
			<form action="admin/adminLogin" method="POST">
				Email: <input type="text" name="emailAdmin"></input> Password: <input type="password" name="passwordAdmin"></input> <input type="submit" /> <br>
			</form>
		</c:otherwise>
	</c:choose>
	
</body>
</html>