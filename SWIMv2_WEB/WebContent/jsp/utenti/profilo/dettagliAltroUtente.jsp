<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
Foto: ?<br/>
Nome:<c:out value="${utente.nome}"></c:out><br/>
Cognome:<c:out value="${utente.cognome}"></c:out><br/>
Email:<c:out value="${utente.email}"></c:out><br/>
Punteggio feedback:<c:out value="${punteggioFeedback}"></c:out><br/>
</body>
</html>