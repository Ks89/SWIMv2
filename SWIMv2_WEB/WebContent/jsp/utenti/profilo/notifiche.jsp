<jsp:include page="../../layoutSuperioreUp.jsp"></jsp:include>
<title>SWIM - Profilo - Notifiche</title>
<jsp:include page="../../layoutSuperioreDown.jsp"></jsp:include>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%-- Richieste aiuto --%>
	<c:if test="${!empty erroreGetNotificheRichiesteAiuto}">
		<c:out value="${erroreGetNotificheRichiesteAiuto}"></c:out>
		<br>
	</c:if>
	<c:if test="${!empty okDettaglioRichiestaAiuto}">
		<c:out value="${okDettaglioRichiestaAiuto}"></c:out>
		<br>
	</c:if>
	<c:if test="${!empty erroreDettaglioRichiestaAiuto}">
		<c:out value="${erroreDettaglioRichiestaAiuto}"></c:out>
		<br>
	</c:if>
	<c:if test="${!empty nonCiSonoRichiesteAiuto}">
		<c:out value="${nonCiSonoRichiesteAiuto}"></c:out>
		<br>
	</c:if>
	
	<%-- Richieste amicizia --%>
	<c:if test="${!empty erroreGetNotificheRichiesteAmicizia}">
		<c:out value="${erroreGetNotificheRichiesteAmicizia}"></c:out>
		<br>
	</c:if>
	<c:if test="${!empty okProfiloAltroUtente}">
		<c:out value="${okProfiloAltroUtente}"></c:out>
		<br>
	</c:if>
	<c:if test="${!empty erroreProfiloAltroUtente}">
		<c:out value="${erroreProfiloAltroUtente}"></c:out>
		<br>
	</c:if>
	<c:if test="${!empty nonCiSonoRichiesteAmicizia}">
		<c:out value="${nonCiSonoRichiesteAmicizia}"></c:out>
		<br>
	</c:if>
	<c:if test="${!empty suggAccettati}">
		<c:out value="${suggAccettati}"></c:out>
		<br>
	</c:if>
	<c:if test="${!empty noSuggDisponibili}">
		<c:out value="${noSuggDisponibili}"></c:out>
		<br>
	</c:if>


	<div align="center">
	<c:if test="${empty nonCiSonoRichiesteAiuto}">
		<br>
		<h2> Richieste di aiuto, inviate dagli utenti:</h2>
		<table id="tabellaRigheAlterne">
			<tr>
				<th>Utente richiedente</th>
				<th>Nome richiesta aiuto</th>
			</tr>
			<c:forEach items="${richiesteAiuto}" var="richiestaAiuto"  varStatus="num">
				<c:if test="${num.count>0}">
						<c:if test="${num.count%2!=0}">
							<tr>
						</c:if>
						<c:if test="${num.count%2==0}">
							<tr class="alt">
						</c:if>
				</c:if>
					<td><c:out value="${richiestaAiuto.utenteRichiedente.nome}" />&nbsp;<c:out value="${richiestaAiuto.utenteRichiedente.cognome}" /></td>
					<td><a href="profilo/azioni/dettaglioRichiestaAiuto?idCollaborazione=<c:out value="${richiestaAiuto.id}" />"><c:out
								value="${richiestaAiuto.nome}" /></a></td>
				</tr>
			</c:forEach>
		</table>
		<div id="pageNavPosition1"></div>

		<script type="text/javascript">
			var pager = new Pager('tabellaRichiesteAiuto', 5);
			pager.init();
			pager.showPageNav('pager', 'pageNavPosition1');
			pager.showPage(1);
		</script>
	</c:if>

	<br>
	
	<c:if test="${empty nonCiSonoRichiesteAmicizia}">
		<br>
		<h2>Richieste di amicizia:</h2>
	<table id="tabellaRigheAlterne">
			<tr>
				<th>Utente richiedente</th>
			</tr>
			<c:forEach items="${utentiCheRichidonoAmicizia}" var="utenteCheRichiedeAmicizia"  varStatus="num">
				<c:if test="${num.count>0}">
						<c:if test="${num.count%2!=0}">
							<tr>
						</c:if>
						<c:if test="${num.count%2==0}">
							<tr class="alt">
						</c:if>
				</c:if>
					<td><a href="profilo/azioni/profiloAltroUtente?emailUtenteRichiedente=<c:out value="${utenteCheRichiedeAmicizia.email}" />"> 
					<c:out value="${utenteCheRichiedeAmicizia.nome}" />&nbsp;<c:out value="${utenteCheRichiedeAmicizia.cognome}" /></a></td>
				</tr>
			</c:forEach>
		</table>
		<div id="pageNavPosition2"></div>

		<script type="text/javascript">
			var pager = new Pager('tabellaRichiesteAmicizia', 5);
			pager.init();
			pager.showPageNav('pager', 'pageNavPosition2');
			pager.showPage(1);
		</script>
	</c:if>
</div>
	<br>
<jsp:include page="../../layoutInferiore.jsp"></jsp:include>