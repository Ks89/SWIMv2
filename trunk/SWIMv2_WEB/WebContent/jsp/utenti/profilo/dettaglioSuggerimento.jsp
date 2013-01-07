<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	Foto: 
	<img src="foto" />
	<br>
	<br>
	<br> Nome:
	<c:out value="${nomeSuggerito}"></c:out>
	<br> Cognome:
	<c:out value="${cognomeSuggerito}"></c:out>
	<br> Email:
	<c:out value="${emailSuggerito}"></c:out>
	<br>

	<form id="profiloSuggeritoForm" action="profiloSuggerito" method="POST">
		<input type="hidden" name="tipo" /> <input type="hidden" name="emailSuggerito" value="${emailSuggerito}"> <input type="button"
			value="conferma" onclick="profiloSuggerito.elements['tipo'].value='CONFERMA'; profiloSuggerito.submit();" /> <input type="button"
			value="rifiuta" onclick="profiloSuggerito.elements['tipo'].value='RIFIUTA'; profiloSuggerito.submit();" />
	</form>

	<br>
	<a href="../notifiche">Torna alle notifiche</a>

</body>
</html>