<jsp:include page="../layoutSuperioreUp.jsp"></jsp:include>
<title>SWIM - Amminsitrazione dettaglio proposta</title>
<jsp:include page="../layoutSuperioreDownAdmin.jsp"></jsp:include>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h1>Dettaglio proposta</h1>
<section>
	<div align="center" class="alignCenter">

		<c:if test="${!empty erroreInserisciNomeAbilita}">
			<div class="alert">
				<p>
					<c:out value="${erroreInserisciNomeAbilita}"></c:out>
				</p>
			</div>
			<br>
		</c:if>
		<c:if test="${!empty errorePropostaNonTrovata}">
			<div class="alert">
				<p>
					<c:out value="${errorePropostaNonTrovata}"></c:out>
				</p>
			</div>
			<br>
		</c:if>
		<c:if test="${!empty erroreSconosciutoProposta}">
			<div class="alert">
				<p>
					<c:out value="${erroreSconosciutoProposta}"></c:out>
				</p>
			</div>
			<br>
		</c:if>

		<table id="formInserimentoParametri">
			<tr>
				<td id="grassettoBlu">Email utente:</td>
				<td><c:out value="${emailProposta}"></c:out></td>
			</tr>
			<tr>
				<td id="grassettoBlu">Nome abilita proposta:</td>
				<td><c:out value="${abilitaProposta}"></c:out></td>
			</tr>
			<tr>
				<td id="grassettoBlu">Motivo della proposta:</td>
				<td><c:out value="${motivazioneProposta}"></c:out></td>
			</tr>
		</table>
		<br>
		<form id="dettaglioProposta" action="admin/dettaglioProposta" method="POST">
			<input type="hidden" name="idProposta" value="${idProposta}"></input> <input type="hidden" name="tipo" />
			<table id="formInserimentoParametri">
				<caption>Inserisci la nuova abilità</caption>
				<tr>
					<td id="grassettoBlu">Nome:</td>
					<td><input id="nuovoNomeAbilitaProposta" type="text" name="nuovoNomeAbilitaProposta" maxlength="100" style="width: 271px;"
						placeholder="Nome dell'abilita"></input></td>
				</tr>
				<tr>
					<td id="grassettoBlu">Descrizione (max: 300):</td>
					<td><textarea id="descrizioneAbilitaProposta" name="descrizioneAbilitaProposta" maxlength="300" rows="10" cols="50" style="resize: none;"
							placeholder="Descrizione dell'abilita"></textarea></td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: center;"><input type="button" id="button" value="Conferma"
						onclick="dettaglioProposta.elements['tipo'].value='AGGIUNGI'; dettaglioProposta.submit();" />
					<input type="button" id="button" value="Rifiuta"
						onclick="dettaglioProposta.elements['tipo'].value='RIFIUTA'; dettaglioProposta.submit();" /></td>
				</tr>
			</table>
		</form>
		<script>
            $('input[placeholder], textarea[placeholder]').placeholder();
      </script>
		<br> <a href="admin/adminPanel?operazione=2">Torna alla pagina delle proposte</a>
	</div>
	<jsp:include page="../layoutInferiore.jsp"></jsp:include>