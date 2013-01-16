<jsp:include page="../layoutSuperioreUp.jsp"></jsp:include>
<title>SWIM - Amministrazione inserimento abilita</title>
<jsp:include page="../layoutSuperioreDownAdmin.jsp"></jsp:include>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<h1>Inserisci nuove abilita</h1>
	<section>
	<div align="center" class="alignCenter">
	<%-- if che vede se non sono vuoti dei valori passati dalla servlet per mostrare messaggi di successo e/o errore --%>
	<c:if test="${!empty erroreInserimentoAbilitaFallito}">
	Si è verificato un errore: <c:out value="${erroreInserimentoAbilitaFallito}"></c:out><br>
	</c:if>
	<c:if test="${!empty inserimentoAbilitaAvvenuto}">
	<c:out value="${inserimentoAbilitaAvvenuto}"></c:out><br>
	</c:if>
	<form id="aggiuntaAbilitaForm" action="admin/aggiuntaAbilita" method="POST">
		<table id="formInserimentoParametri">
			<tr>
				<td id="grassettoBlu">
					Nome:
				</td> 
				<td>
					<input id="nuovoNomeAbilitaAggiunta" type="text" name="nuovoNomeAbilitaAggiunta" style="width: 271px;" maxlength="100" placeholder="Nome dell'abilita"></input>
				</td>
			</tr>
			<tr>
				<td id="grassettoBlu">
					Descrizione:
				</td>
				<td> 
					<textarea id="descrizioneAbilitaAggiunta" name="descrizioneAbilitaAggiunta" rows="10" cols="50" maxlength="300" style="resize:none;" placeholder="Descrizione dell'abilita"></textarea>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center;">
					<input id="submit" type="submit" />
				</td>
			</tr>
		</table>
	</form>
	
	<br><br>
	<a href="admin">Torna al pannello di amministrazione</a>
</div>
<jsp:include page="../layoutInferiore.jsp"></jsp:include>