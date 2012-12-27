<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SWIM - Registrazione</title>
</head>
<body>

	Homepage
	<br>

	<form action="Registrazione" method="POST">
		Email: <input type="text" name="emailUtente"></input> 
		<br>
		Password: <input type="password" name="password"></input>
		<br>
		Nome: <input type="text" name="nome"></input> 
		<br>
		Cognome: <input type="text" name="cognome"></input> 
		<br>
		<br>Abilita' disponibili:
		<select name="abilita">
			<c:forEach items="${abilita}" var="item">
				<option value="<c:out value="${item.nome}" />">
					<c:out value="${item.descrizione}" />
				</option>
			</c:forEach>
		</select>
		<input type="submit" />
	</form>

</body>
</html>