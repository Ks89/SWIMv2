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
	
	<%-- Form che permette di inviare sia file allegati sia dati prelevati dalle input text.
		Essendo una post verra' intercettata dalla doPost della servlet specificata in action="registrazione" --%>
	<form action="registrazione" method="POST" enctype="multipart/form-data">
		Email: <input type="text" name="emailUtente"></input> <br> Password: <input type="password" name="password"></input> <br> Nome: <input
			type="text" name="nome"></input> <br> Cognome: <input type="text" name="cognome"></input> <br> 
		<br>
		Abilita' disponibili:
		<%-- for che cicla sulle lista di abilita prelevata da ${abilita} e assegna ogni singolo elemeno a item ad ogni giro
		quindi uso quella varibile per prelevare il nome dell'abilita e metterlo come nome della checkbox --%>
		<br>
		<c:forEach items="${abilita}" var="item">
			<input type="checkbox" name="abilita" value='<c:out value="${item.nome}"/>' />
			<c:out value="${item.nome}" />
			<br>
		</c:forEach>
		<%-- input di tipo file per uploadare il file e poi il submit per mandare la form --%>
		<input id="file" name="file" type="file" /><br>
		<input id="submit" type="submit" />
	</form>
</body>
</html>