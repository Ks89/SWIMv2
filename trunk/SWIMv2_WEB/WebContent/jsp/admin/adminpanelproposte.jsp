<jsp:include page="../layoutSuperioreUp.jsp"></jsp:include>
<title>SWIM - Amministrazione proposte abilita</title>
<jsp:include page="../layoutSuperioreDownAdmin.jsp"></jsp:include>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<h1>Proposte abilita</h1>
	<section>
	<div align="center" class="alignCenter">
	<%-- if che vede se non sono vuoti dei valori passati dalla servlet per mostrare messaggi di successo e/o errore --%>
	<c:if test="${!empty erroreInserimentoPropostaFallito}">
	Si è verificato un errore: <c:out value="${erroreInserimentoPropostaFallito}"></c:out>
		<br>
	</c:if>
	<c:if test="${!empty inserimentoPropostaCorretto}">
		<c:out value="${inserimentoPropostaCorretto}"></c:out>
		<br>
	</c:if>
	<table id="tabellaRigheAlterne">
		<caption>Proposte di aggiunta abilita, inviate dagli utenti:</caption>
		<tr>
			<th>Utente</th>
			<th>Nome abilita</th>
		</tr>
		<c:forEach items="${proposte}" var="proposta" varStatus="num">
				<c:if test="${num.count>0}">
					<c:if test="${num.count%2!=0}">
						<tr>
					</c:if>
					<c:if test="${num.count%2==0}">
						<tr class="alt">
					</c:if>
				</c:if>
				<td><c:out value="${proposta.utente.nome}" />&nbsp;<c:out value="${proposta.utente.cognome}" /></td>
				<td><a href="admin/dettaglioProposta?idProposta=<c:out value="${proposta.id}" />"><c:out value="${proposta.abilitaProposta}" /></a></td>
			</tr>
		</c:forEach>
	</table>
	<div id="pageNavPosition"></div>

	<script type="text/javascript">
		var pager = new Pager('tabellaProposte', 3);
		pager.init();
		pager.showPageNav('pager', 'pageNavPosition');
		pager.showPage(1);
	</script>

	<br><br>
	<a href="admin">Torna al pannello di amministrazione</a>
</div>
<jsp:include page="../layoutInferiore.jsp"></jsp:include>