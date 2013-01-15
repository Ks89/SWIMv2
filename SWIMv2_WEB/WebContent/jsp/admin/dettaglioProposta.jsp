<jsp:include page="../layoutSuperioreUp.jsp"></jsp:include>
<title>SWIM - Amminsitrazione dettaglio proposta</title>
<jsp:include page="../layoutSuperioreDownAdmin.jsp"></jsp:include>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<h1>Dettaglio proposta</h1>
	<section>
	<div align="center" class="alignCenter">
		<table id="formInserimentoParametri">
			<tr>
				<td>
					Id proposta:
				</td>
				<td>
					<c:out value="${idProposta}"></c:out>
				</td>
			</tr>
			<tr>
				<td>
					Email utente:
				</td>
				<td>
					<c:out value="${emailProposta}"></c:out>
				</td>
			</tr>
			<tr>
				<td>
					Nome abilita proposta:
				</td>
				<td>
					<c:out value="${abilitaProposta}"></c:out>
				</td>
			</tr>
			<tr>
				<td>
					Motivo della proposta:
				</td>
				<td>
					<c:out value="${motivazioneProposta}"></c:out>
					
				</td>
			</tr>
		</table>
	<br>
	<form id="dettaglioPropostaForm" action="admin/dettaglioProposta" method="POST">
		<input type="hidden" name="idProposta" value="${idProposta}"></input>
		<table id="formInserimentoParametri">
			<caption>Inserisci la nuova abilità</caption>
			<tr>
				<td> Nome:</td>
				<td><input id="nuovoNomeAbilitaProposta" type="text" name="nuovoNomeAbilitaProposta" maxlength="100" placeholder="Nome dell'abilita"></input></td>
			</tr>
			<tr>
				<td>Descrizione:</td>
				<td><input id="descrizioneAbilitaProposta" type="text" name="descrizioneAbilitaProposta" maxlength="300" placeholder="Descrizione dell'abilita"></input></td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center;"><input id="submit" type="submit" /><br></td>
			</tr>
		</table>
	</form>
</div>
<jsp:include page="../layoutInferiore.jsp"></jsp:include>