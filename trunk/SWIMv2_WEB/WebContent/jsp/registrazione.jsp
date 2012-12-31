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

	<form action="registrazione" method="POST" enctype="multipart/form-data">
		Email: <input type="text" name="emailUtente"></input> <br> Password: <input type="password" name="password"></input> <br> Nome: <input
			type="text" name="nome"></input> <br> Cognome: <input type="text" name="cognome"></input> <br> 
		<br>
		Abilita' disponibili:
		<br>
		<c:forEach items="${abilita}" var="item">
			<input type="checkbox" name="abilita" value='<c:out value="${item.nome}"/>' />
			<c:out value="${item.nome}" />
			<br>
		</c:forEach>
		
		<!-- 		<select name="abilita"> -->
		<%-- 			<c:forEach items="${abilita}" var="item"> --%>
		<%-- 				<option value="<c:out value="${item.nome}" />"> --%>
		<%-- 					<c:out value="${item.nome}" /> --%>
		<!-- 				</option> -->
		<%-- 			</c:forEach> --%>
		<!-- 		</select> -->
		
		<input id="file" name="file" type="file" />
<!-- 		<input type="hidden" name="MAX_FILE_SIZE" value="500" /> -->
		<br>
		
		<input id="submit" type="submit" />
	</form>
	
<!-- 	<form action="Upload" method="POST" enctype="multipart/form-data"> -->
<!-- 			<input name="file" type="file" /> <br> <input type="submit" /> -->
<!-- 	</form> -->

</body>
</html>