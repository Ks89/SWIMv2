<jsp:include page="../../layoutSuperioreUp.jsp" flush="true"></jsp:include>
<title>SWIM - Profilo</title>
<jsp:include page="../../layoutSuperioreDown.jsp" flush="true"></jsp:include>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<h1>Dati personali</h1>
	<section>
		<div align="center" class="alignCenter">
	<c:choose>
		<%-- Per gestire i vari errori durante l'upload della foto del profilo --%>
		<c:when test="${!empty erroreFileTroppoGrande}"> 
		<div class="alert">
			<p>
				<c:out value="${erroreFileTroppoGrande}"></c:out><br>
			</p>
		</div>
		</c:when>
		<c:when test="${!empty erroreNonFoto}"> 
		<div class="alert">
			<p>
				<c:out value="${erroreNonFoto}"></c:out><br>
			</p>
		</div>
		</c:when>
		<c:when test="${!empty erroreFotoSconosciuto}"> 
		<div class="alert">
			<p>
				<c:out value="${erroreFotoSconosciuto}"></c:out><br>
			</p>
		</div>
		</c:when>
		<c:when test="${!empty erroreFotoIrreversibile}"> 
		<div class="alert">
			<p>
				<c:out value="${erroreFotoIrreversibile}"></c:out><br>
			</p>
		</div>
		</c:when>
		
		<%-- Messaggi della servlet SuggAlRichiedente --%>
		<c:when test="${!empty nessunSuggAccettato}"> 
		<div class="alert">
			<p>
				<c:out value="${nessunSuggAccettato}"></c:out><br>
			</p>
		</div>
		</c:when>
		<c:when test="${!empty suggAccettato}"> 
		<div class="alert">
			<p>
				<c:out value="${suggAccettato}"></c:out><br>
			</p>
		</div>
		</c:when>
		<c:when test="${!empty suggAccettati}">
		<div class="alert">
			<p>
				<c:out value="${suggAccettati}"></c:out><br>
			</p>
		</div>
		</c:when>
	</c:choose>
	<table id="formInserimentoParametri">
	<tr><td>Email:</td><td><c:out value="${sessionScope.utenteCollegato}"></c:out></td>
	<tr><td>Punteggio feedback:</td>
	<td>
	<c:choose>
		<c:when test="${!empty feedbackStelline}">
			<div id="STAR_RATING" align="center">
				<ul>
					<c:forEach begin="1" end="5" varStatus="num">
						<c:if test="${num.count <= feedbackStelline}">
							<li id="star_${num.count}" class="on"></li>
						</c:if>
						<c:if test="${num.count > feedbackStelline}">
							<li id="star_${num.count}"></li>
						</c:if>
					</c:forEach>
					<li style="background-image: none;">&nbsp;<c:out value="${punteggioUtenteCollegato}"/></li>
				</ul>
			</div>
		</c:when>
		<c:otherwise>
			<c:out value="${punteggioUtenteCollegato}"/>
		</c:otherwise>
	</c:choose>
	</td>
	</tr>
	</table>
	<br>
	<%-- Lista puntata delle abilita dell'utente fatta con ul e poi li per ogni voce della lista --%>
	<table id="tabellaRigheAlterne">
	<caption>Abilita' dell'utente:</caption>
	<tr>
	  <th>Abilità</th>
	  <th>Descrizione</th>
	</tr>
	<c:forEach items="${abilita}" var="item" varStatus="num">
		<c:if test="${num.count>0}">
			<c:if test="${num.count%2!=0}">
				<tr>
			</c:if>
			<c:if test="${num.count%2==0}">
				<tr class="alt">
			</c:if>
		</c:if>
			<td><c:out value="${item.nome}" /></td>
			<td><c:out value="${item.descrizione}" /></td>
		</tr>
	</c:forEach>
	</table>
	<br>
	
	<c:if test="${!empty ciSonoNotificheDaMostrare}">
		<div class="alertNotification">
			<p>
				<a href="profilo/azioni/notificheDiRisposta/notificheDiRisposta?emailUtente=${sessionScope.utenteCollegato}" >Ci sono nuove risposte degli utenti</a>
			</p>
		</div>
	</c:if>	

</div>
<jsp:include page="../../layoutInferiore.jsp" flush="true"></jsp:include>