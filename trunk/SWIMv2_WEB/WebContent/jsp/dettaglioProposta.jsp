<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SWIM - Amminsitrazione dettaglio proposta</title>
</head>
<body>

	<br> Id proposta:
	<c:out value="${idProposta}"></c:out>
	<br> Email utente:
	<c:out value="${emailProposta}"></c:out>
	<br> Nome abilita proposta:
	<c:out value="${abilitaProposta}"></c:out>
	<br> Motivo della proposta:
	<c:out value="${motivazioneProposta}"></c:out>

	<br>
	<br>Inserisci la nuova abilit�
	<br>
	<form action="dettaglioProposta" method="POST">
		<input type="hidden" name="idProposta" value="${idProposta}"></input>
		<input type="text" name="nuovoNomeAbilitaProposta"></input><br>
		<br>
		<input type="text" name="descrizioneAbilitaProposta"></input>
		<br>
		<input type="submit" /><br>
	</form>
</body>
</html>