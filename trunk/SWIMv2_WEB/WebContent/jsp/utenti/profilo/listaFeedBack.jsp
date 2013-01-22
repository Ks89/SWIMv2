<jsp:include page="../../layoutSuperioreUp.jsp"></jsp:include>
<title>SWIM - Profilo - Collaborazioni</title>
<jsp:include page="../../layoutSuperioreDown.jsp"></jsp:include>
<!--<link rel="stylesheet" href="../../css/global.css" type="text/css"/> -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h1>Lista dei feedback</h1>
	<section>
<div align="center" class="alignCenter">
<c:if test="${!empty erroreGetCollaborazioni}">
	<div class="alert">
		<p>
			<c:out value="${erroreGetCollaborazioni}"></c:out>
		</p>
	</div>
	<br>
</c:if>
<c:if test="${!empty nonCiSonoCollaborazioni}">
	<div class="generico">
		<p>
			<c:out value="${nonCiSonoCollaborazioni}"></c:out>
		</p>
	</div>
	<br>
</c:if>
<c:if test="${empty nonCiSonoCollaborazioni}">
	<h1><c:out value="Collaborazioni"></c:out></h1>
	<br><br>
			<table id="tabellaRigheAlterne1" name="tabellaRigheAlterne1" class="tabellaRigheAlterne">
				<tr>
				  <th>Collaborazione</th>
				  <th>Collaborante</th>
				  <th>Punteggio</th>
				  <th>Commento</th>
				</tr>
				<c:forEach items="${collaborazioni}" var="collaborazione" varStatus="num">
				<c:if test="${num.count>0}">
					<c:if test="${num.count%2!=0}">
						<tr>
					</c:if>
					<c:if test="${num.count%2==0}">
						<tr class="alt">
					</c:if>
				</c:if>
					<td ><c:out value="${collaborazione.nome}" /></td>
					<td><c:out value="${collaborazione.utenteRichiedente.nome}" />&nbsp;<c:out value="${collaborazione.utenteRichiedente.cognome}" /></td>
					<td width="95px;">
					
						<div id="STAR_RATING" align="center">
							<ul>
								<c:forEach begin="1" end="5" varStatus="num">
									<c:if test="${num.count <= collaborazione.punteggioFeedback}">
										<li id="star_${num.count}" class="on"></li>
									</c:if>
									<c:if test="${num.count > collaborazione.punteggioFeedback}">
										<li id="star_${num.count}"></li>
									</c:if>
								</c:forEach>
								<%--<li style="background-image: none;">&nbsp;<c:out value="${collaborazione.punteggioFeedback}" /></li>--%>
							</ul>
						</div>
				
					
					</td>
					<td style="text-align: justify;"><c:out value="${collaborazione.commentoFeedback}" /></td>
				</tr>
			</c:forEach>
			</table>
			<div id="pageNavPosition2"></div>

			<script type="text/javascript">
				var pagerSecond = new Pager('tabellaRigheAlterne1', 5);
				pagerSecond.init();
				pagerSecond.showPageNav('pagerSecond', 'pageNavPosition2');
				pagerSecond.showPage(1);
			</script>
			<br><br>
		</c:if>
	<c:if test="${!empty altroUtente}">
		<a href="profilo/azioni/dettagliAltroUtente?utente=${email}&tipoRicerca=${tipoRicerca}">Torna al profilo dell'utente ricercato</a>
	</c:if>	
	</div>



<jsp:include page="../../layoutInferiore.jsp"></jsp:include>