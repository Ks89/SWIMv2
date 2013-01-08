<jsp:include page="../../layoutSuperioreUp.jsp"></jsp:include>
<title>SWIM - Profilo</title>
<jsp:include page="../../layoutSuperioreDown.jsp"></jsp:include>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<a href="profilo/azioni/notificheDiRisposta/notificheDiRisposta?emailUtente=${sessionScope.utenteCollegato}" >Ci sono nuove risposte degli utenti</a>
	<br> Email:
	<c:out value="${sessionScope.utenteCollegato}"></c:out>
	<%--<br> Nome:
	<c:out value="${nomeUtenteCollegato}"></c:out>
	<br> Cognome:
	<c:out value="${cognomeUtenteCollegato}"></c:out> --%>
	<br> Punteggio feedback:
	<c:out value="${punteggioUtenteCollegato}" />

	<%-- Lista puntata delle abilita dell'utente fatta con ul e poi li per ogni voce della lista --%>
	<br>Abilita' dell'utente:
	<c:forEach items="${abilita}" var="item">
		<ul>
			<li><c:out value="${item.nome}" />
		</ul>
	</c:forEach>

	<!--  non cancellare tutta la parte che c'e' qui sotto per nessun motivo!!!!! -->
	<!--  	<option value="<c:out value="${item.nome}" />">   e' l'esempio di un menu a tendina, non rimuoverlo
				<c:out value="${item.descrizione}" /> </option>-->
<jsp:include page="../../layoutInferiore.jsp"></jsp:include>