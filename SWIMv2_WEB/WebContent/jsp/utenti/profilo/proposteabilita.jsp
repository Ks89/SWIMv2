<jsp:include page="../../layoutSuperioreUp.jsp"></jsp:include>
<title>SWIM - Profilo - Proposte abilita</title>
<jsp:include page="../../layoutSuperioreDown.jsp"></jsp:include>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%-- if che vede se non sono vuoti dei valori passati dalla servlet per mostrare messaggi di successo e/o errore --%>
	<h1>Proposte abilita</h1>
	<section>
	<div align="center" class="alignCenter">
	<c:if test="${!empty erroreInserimentoProposta}">
		<div class="alert">
			<p>
				<c:out value="${erroreInserimentoProposta}"></c:out>
			</p>
		</div>
		<br>
	</c:if>
	<c:if test="${!empty inserimentoPropostaAvvenuto}">
		<div class="conferma">
			<p>
				<c:out value="${inserimentoPropostaAvvenuto}"></c:out>
			</p>
		</div>
		<br>
	</c:if>
	
	<%-- tramite post manda richiesta alla dopost della proposteAbilitaServlet e passa i valori.
		Visto che uso il type submit mando tutto il contenuto della form--%>
	<form id="proponiAbilitaForm" action="profilo/azioni/proposteAbilita" method="POST">
		<div align="center">
			<table id="formInserimentoParametri">
				<tr>
					<td id="grassettoBlu">Nome abilita proposta: </td><td><input id="nomeAbilita" type="text" name="nomeAbilita" maxlength="100" style="width: 271px;" placeholder="Nome dell'abilita"></input></td>
				</tr> 
				<tr>
					<td id="grassettoBlu">Motivo: </td><td><textarea name="descrizioneAbilita" rows="10" cols="50" maxlength="500" style="resize:none;" placeholder="Motivo della proposta" ></textarea></td>
				<tr>
					<td colspan="2" style="text-align: center;"><input id="submit" type="submit" /></td>
				</tr>
			</table>
		</div>
	</form>
</div>
	<br>
	<%-- href fa sempre riferimento alla servlet in modo relativo, mai con link assoluti. Usando il ../ torno indietro di un livello nella
		gerarchi. Se facessi ../../ tornerei indietro di 2 livelli e cosi' via--%>
<jsp:include page="../../layoutInferiore.jsp"></jsp:include>