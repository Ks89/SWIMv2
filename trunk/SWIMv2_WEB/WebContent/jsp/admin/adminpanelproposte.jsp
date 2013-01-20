<jsp:include page="../layoutSuperioreUp.jsp"></jsp:include>
<title>SWIM - Amministrazione proposte abilita</title>
<jsp:include page="../layoutSuperioreDownAdmin.jsp"></jsp:include>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<h1>Proposte abilita</h1>
	<section>
	<div align="center" class="alignCenter">
	
	<c:if test="${!empty erroreInserimentoPropostaFallito}">
		<div class="alert">
			<p>
				Si è verificato un errore: <c:out value="${erroreInserimentoPropostaFallito}"></c:out>
			</p>
		</div>
		<br>
	</c:if>
	<c:if test="${!empty inserimentoPropostaCorretto}">
		<div class="conferma">
			<p>
				<c:out value="${inserimentoPropostaCorretto}"></c:out>
			</p>
		</div>
		<br>
	</c:if>
		<c:if test="${!empty erroreRifiutoPropostaFallito}">
		<div class="alert">
			<p>
				<c:out value="${erroreRifiutoPropostaFallito}"></c:out>
			</p>
		</div>
		<br>
	</c:if>
		<c:if test="${!empty rifiutoPropostaCorretto}">
		<div class="conferma">
			<p>
				<c:out value="${rifiutoPropostaCorretto}"></c:out>
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
	
	
	
	<h2>Proposte di aggiunta abilita, inviate dagli utenti</h2>
	<table id="tabellaRigheAlterne">
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