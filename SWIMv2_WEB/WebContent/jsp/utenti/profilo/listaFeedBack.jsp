<jsp:include page="../../layoutSuperioreUp.jsp"></jsp:include>
<title>SWIM - Profilo - Collaborazioni</title>
<jsp:include page="../../layoutSuperioreDown.jsp"></jsp:include>
<!--<link rel="stylesheet" href="../../css/global.css" type="text/css"/> -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
	<div class="alert">
		<p>
			<c:out value="${nonCiSonoCollaborazioni}"></c:out>
		</p>
	</div>
	<br>
</c:if>
<c:if test="${empty nonCiSonoCollaborazioni}">
	<h1><c:out value="Collaborazioni"></c:out></h1>
	<br><br>
		<c:forEach items="${collaborazioni}" var="collaborazione">
			<table id="tabellaFeedback">
				<tr>
				  <th>Collaborazione</th>
				  <th>Collaborante</th>
				  <th>Punteggio</th>
				  <th>Commento</th>
				</tr>
				<tr>
					<td ><c:out value="${collaborazione.nome}" /></td>
					<td><c:out value="${collaborazione.utenteRichiedente.nome}" />&nbsp;<c:out value="${collaborazione.utenteRichiedente.cognome}" /></td>
					<td >
					
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
			</table>
			<br><br>
		</c:forEach>
		</c:if>
	<c:if test="${!empty altroUtente}">
		<a href="profilo/azioni/dettagliAltroUtente?utente=${email}&tipoRicerca=${tipoRicerca}">Torna al profilo dell'utente ricercato</a>
	</c:if>	
	</div>



<jsp:include page="../../layoutInferiore.jsp"></jsp:include>