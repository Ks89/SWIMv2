<jsp:include page="../../layoutSuperioreUp.jsp"></jsp:include>
<title>SWIM - Profilo - Profilo altro utente</title>
<jsp:include page="../../layoutSuperioreDown.jsp"></jsp:include>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<h1>Profilo altro utente</h1>
	<section>
	<div align="center" class="alignCenter">
	<c:choose>
		<c:when test="${empty amiciSuggeriti}">
			<img src="profilo/foto?emailUtente=${emailRichiedente}" />
			<br>
			<table id="formInserimentoParametri">
			<tr>
				<td>Nome:</td>
				<td><c:out value="${nomeRichiedente}"></c:out></td>
			</tr> 
			<tr>
				<td>Cognome:</td>
				<td><c:out value="${cognomeRichiedente}"></c:out></td>
			</tr>
			<tr>
				<td>Email:</td>
				<td><c:out value="${emailRichiedente}"></c:out></td>
			</tr>
			<tr>
				<td>Punteggio feedback:<td>
				<td>
				<c:if test="${!empty punteggioFeedback}">
						<div id="STAR_RATING" align="center">	
							<ul>
								<c:forEach begin="1" end="5" varStatus="num">
									<c:if test="${num.count <= feedback}">
										<li id="star_${num.count}" class="on"></li>
									</c:if>
									<c:if test="${num.count > feedback}">
										<li id="star_${num.count}"></li>
									</c:if>
								</c:forEach>
								<%--<li style="background-image: none;">&nbsp;<c:out value="${punteggioFeedback}"/></li> --%>
							</ul>
						</div>
					</c:if>
				</td>
			</tr>
			
			<c:if test="${empty confermaRichiestaAiuto and empty erroreConfermaRichiestaAiuto}">
				<form id="profiloAltroUtente" action="profilo/azioni/profiloAltroUtente" method="POST">
					<input type="hidden" name="tipo" /> <input type="hidden" name="emailRichiedente" value="${emailRichiedente}"> 
					<tr>
						<td><input type="button" id="button" value="conferma" onclick="profiloAltroUtente.elements['tipo'].value='CONFERMA'; profiloAltroUtente.submit();" /></td> 
						<td><input type="button" id="button" value="rifiuta" onclick="profiloAltroUtente.elements['tipo'].value='RIFIUTA'; profiloAltroUtente.submit();" /></td>
					</tr>
				
				</form>
							
			</c:if>
			</table>
			<br> 
			<a href="profilo/azioni/notifiche">Torna alle notifiche</a>
		</c:when>
		<c:otherwise>
			<br>
			<br> Amici suggeriti:
			<form action="profilo/azioni/profiloAltroUtente" method="POST">
				<table id="tabellaSuggerimenti" style="border-width: medium; border-style: solid;">
					<tr>
						<th></th>
						<th>Nome e cognome</th>
					</tr>
					<c:forEach items="${amiciSuggeriti}" var="utenteSuggerito">
						<tr>
							<td><input type="checkbox" name="amiciSuggeriti" value="${utenteSuggerito.email}" /></td>
							<td><c:out value="${utenteSuggerito.nome}" />&nbsp;<c:out value="${utenteSuggerito.cognome}" /></td>
						</tr>
					</c:forEach>
				</table>

				<div id="pageNavPosition2"></div>

				<script type="text/javascrip	t">
 				var pager = new Pager('tabellaSuggerimenti', 5);
 				pager.init();
 				pager.showPageNav('pager', 'pageNavPosition2');
 				pager.showPage(1);
			</script>

				<input id="submit" type="submit" value="Accetta suggerimenti" />
			</form>
		</c:otherwise>
	</c:choose>
</div>
	<br>
<jsp:include page="../../layoutInferiore.jsp"></jsp:include>