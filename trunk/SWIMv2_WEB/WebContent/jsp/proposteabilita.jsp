<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SWIM - Profilo - Proposte abilita</title>
</head>
<body>
	<%-- if che vede se non sono vuoti dei valori passati dalla servlet per mostrare messaggi di successo e/o errore --%>
	<c:if test="${!empty erroreInserimentoProposta}">
		<c:out value="${erroreInserimentoProposta}"></c:out>
		<br>
	</c:if>
	<c:if test="${!empty inserimentoPropostaAvvenuto}">
		<c:out value="${inserimentoPropostaAvvenuto}"></c:out>
		<br>
	</c:if>
	
	<%-- tramite post manda richiesta alla dopost della proposteAbilitaServlet e passa i valori.
		Visto che uso il type submit mando tutto il contenuto della form--%>
	<form id="proponiAbilitaForm" action="proposteAbilita" method="POST">
		Nome abilita proposta: <input id="nomeAbilita" type="text" name="nomeAbilita"></input> 
		Motivo: <input id="descrizioneAbilita" type="text" name="descrizioneAbilita"></input>
		<input id="submit" type="submit" />
	</form>

	<br>
	<%-- href fa sempre riferimento alla servlet in modo relativo, mai con link assoluti. Usando il ../ torno indietro di un livello nella
		gerarchi. Se facessi ../../ tornerei indietro di 2 livelli e cosi' via--%>
	<a href="../profilo">Torna al profilo</a>
</body>
</html>