<jsp:include page="../../layoutSuperioreUp.jsp"></jsp:include>
<title>SWIM - Profilo - Proposte abilita</title>
<jsp:include page="../../layoutSuperioreDown.jsp"></jsp:include>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
	<form id="proponiAbilitaForm" action="profilo/azioni/proposteAbilita" method="POST">
		<div align="center">
			<table id="formInserimentoParametri">
				<tr>
					<td>Nome abilita proposta: </td><td><input id="nomeAbilita" type="text" name="nomeAbilita" placeholder="Nome dell'abilita"></input></td>
				</tr> 
				<tr>
					<td>Motivo: </td><td><input id="descrizioneAbilita" type="text" name="descrizioneAbilita" placeholder="Motivo della proposta"></input></td>
				<tr>
					<td colspan="2" style="text-align: center;"><input id="submit" type="submit" /></td>
				</tr>
			</table>
		</div>
	</form>

	<br>
	<%-- href fa sempre riferimento alla servlet in modo relativo, mai con link assoluti. Usando il ../ torno indietro di un livello nella
		gerarchi. Se facessi ../../ tornerei indietro di 2 livelli e cosi' via--%>
<jsp:include page="../../layoutInferiore.jsp"></jsp:include>