<jsp:include page="../../layoutSuperioreUp.jsp"></jsp:include>
<title>SWIM - Profilo - Notifiche</title>
<jsp:include page="../../layoutSuperioreDown.jsp"></jsp:include>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%-- Richieste aiuto --%>
	<h1>Notifiche</h1>
	<section>
	<div align="center" class="alignCenter">
	
	<%-- Richieste aiuto --%>
	<c:if test="${!empty erroreGetNotificheRichiesteAiuto}">
		<div class="alert">
			<p>
				<c:out value="${erroreGetNotificheRichiesteAiuto}"></c:out>
			</p>
		</div>
		<br>
	</c:if>
	<c:if test="${!empty okDettaglioRichiestaAiuto}">
		<div class="conferma">
			<p>
				<c:out value="${okDettaglioRichiestaAiuto}"></c:out>
			</p>
		</div>
		<br>
	</c:if>
	<c:if test="${!empty erroreDettaglioRichiestaAiuto}">
		<div class="alert">
			<p>
				<c:out value="${erroreDettaglioRichiestaAiuto}"></c:out>
			</p>
		</div>
		<br>
	</c:if>
	<c:if test="${!empty nonCiSonoRichiesteAiuto}">
		<div class="generico">
			<p>
				<c:out value="${nonCiSonoRichiesteAiuto}"></c:out>
			</p>
		</div>
		<br>
	</c:if>
	
	
	<%-- Richieste amicizia --%>
	<c:if test="${!empty erroreGetNotificheRichiesteAmicizia}">
		<div class="alert">
			<p>
				<c:out value="${erroreGetNotificheRichiesteAmicizia}"></c:out>
			</p>
		</div>
		<br>
	</c:if>
	<c:if test="${!empty okProfiloAltroUtente}">
		<div class="conferma">
			<p>
				<c:out value="${okProfiloAltroUtente}"></c:out>
			</p>
		</div>
		<br>
	</c:if>
	<c:if test="${!empty erroreProfiloAltroUtente}">
		<div class="alert">
			<p>
				<c:out value="${erroreProfiloAltroUtente}"></c:out>
			</p>
		</div>
		<br>
	</c:if>
	<c:if test="${!empty nonCiSonoRichiesteAmicizia}">
		<div class="generico">
			<p>
				<c:out value="${nonCiSonoRichiesteAmicizia}"></c:out>
			</p>
		</div>
		<br>
	</c:if>
	<c:if test="${!empty suggAccettati}">
		<div class="conferma">
			<p>
				<c:out value="${suggAccettati}"></c:out>
			</p>
		</div>
		<br>
	</c:if>
	<c:if test="${!empty nonSonoStatiAccettatiSugg}">
		<div class="conferma">
			<p>
				<c:out value="${nonSonoStatiAccettatiSugg}"></c:out>
			</p>
		</div>
		<br>
	</c:if>
	<c:if test="${!empty noSuggDisponibili}">
		<div class="conferma">
			<p>
				<c:out value="${noSuggDisponibili}"></c:out>
			</p>
		</div>
		<br>
	</c:if>
	<c:if test="${!empty amiciziaIndirettaStretta}">
		<div class="conferma">
			<p>
				<c:out value="${amiciziaIndirettaStretta}"></c:out>
			</p>
		</div>
		<br>
	</c:if>


	<%-- Visualizzazione richieste di aiuto --%>
	<c:if test="${empty nonCiSonoRichiesteAiuto}">
		<br>
		<h2> Richieste di aiuto, inviate dagli utenti</h2>
		<table id="tabellaRigheAlterne1" name="tabellaRigheAlterne1" class="tabellaRigheAlterne">
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
			var pagerFirst = new Pager('tabellaRigheAlterne1', 5);
			pagerFirst.init();
			pagerFirst.showPageNav('pagerFirst', 'pageNavPosition1');
			pagerFirst.showPage(1);
		</script>
	</c:if>

	<br>
	
	
	
	<c:if test="${empty nonCiSonoRichiesteAmicizia}">
		<h2>Richieste di amicizia</h2>
		<table id="tabellaRigheAlterne2" name="tabellaRigheAlterne2" class="tabellaRigheAlterne">
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
			var pagerSecond = new Pager('tabellaRigheAlterne2', 5);
			pagerSecond.init();
			pagerSecond.showPageNav('pagerSecond', 'pageNavPosition2');
			pagerSecond.showPage(1);
		</script>
	</c:if>
</div>
	<br>
<jsp:include page="../../layoutInferiore.jsp"></jsp:include>