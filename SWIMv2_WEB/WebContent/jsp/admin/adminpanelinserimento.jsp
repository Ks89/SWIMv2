<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SWIM - Amministrazione inserimento abilita</title>
</head>
<body>
	<%-- if che vede se non sono vuoti dei valori passati dalla servlet per mostrare messaggi di successo e/o errore --%>
	<c:if test="${!empty erroreInserimentoAbilitaFallito}">
	Si è verificato un errore: <c:out value="${erroreInserimentoAbilitaFallito}"></c:out><br>
	</c:if>
	<c:if test="${!empty inserimentoAbilitaAvvenuto}">
	<c:out value="${inserimentoAbilitaAvvenuto}"></c:out><br>
	</c:if>

	Inserisci la nuova abilità
	<form id="aggiuntaAbilitaForm" action="aggiuntaAbilita" method="POST">
		<br>Nome abilità: 
		<input id="nuovoNomeAbilitaAggiunta" type="text" name="nuovoNomeAbilitaAggiunta" maxlength="100"></input>
		<br>Descrizione abilità: 
		<input id="descrizioneAbilitaAggiunta" type="text" name="descrizioneAbilitaAggiunta" maxlength="100"></input>
		<br>
		<input id="submit" type="submit" /><br>
	</form>
	
	<br><br>
	<a href="../admin">Torna al pannello di amministrazione</a>
</body>
</html>