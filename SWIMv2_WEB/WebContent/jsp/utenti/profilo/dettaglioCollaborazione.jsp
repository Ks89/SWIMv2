<jsp:include page="../../layoutSuperioreUp.jsp"></jsp:include>
<title>SWIM - Profilo - Collaborazioni - Collaborazione	Dettagliata</title>
<jsp:include page="../../layoutSuperioreDown.jsp"></jsp:include>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div align="center">
	<h2>Dettagglio della collaborazione</h2>
	<c:if test="${!empty erroreRicercaCollaborazione}">
		<c:out value="${erroreRicercaCollaborazione}"></c:out>
		<br>
	</c:if>
	<table id="tabellaFeedback">
		<tr>
		  <th>Nome</th>
		  <th>Utente richiesto</th>
		  <th>Data Inizio</th>
		  <th>Data Termine</th>
		</tr>
		<tr>
			<c:if test="${empty erroreRicercaCollaborazione}">
				<td><c:out value="${collaborazione.nome}"></c:out></td>
				<td><c:out value="${collaborazione.utenteRicevente.nome}" />&nbsp;<c:out value="${collaborazione.utenteRicevente.cognome}" /></td>
				<td><c:out value="${collaborazione.dataStipula}"></c:out></td>
				</c:if>
				<c:if test="${!empty terminata}">
				<td><c:out value="${collaborazione.dataTermine}"></c:out></td>
			</c:if>
		</tr>
	</table>
	<br>
	<table id="tabellaFeedback">
		<tr>
		  <th>Punteggio feedback</th>
		  <th>Commenti sul collaboratore:</th>
		</tr>
		<tr>
			<c:if test="${!empty conFeedBack}">
			<td>
			
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
							<li style="background-image: none;">&nbsp;<c:out value="${collaborazione.punteggioFeedback}"></c:out></li>
						</ul>
					</div>
			
			</td>
		<td><c:out value="${collaborazione.commentoFeedback}"></c:out></td>
		</c:if>
		</tr>
		
	</table><br>
	<a href="profilo/azioni/collaborazioni">Torna alla lista di collaborazioni</a>
</div>
<jsp:include page="../../layoutInferiore.jsp"></jsp:include>