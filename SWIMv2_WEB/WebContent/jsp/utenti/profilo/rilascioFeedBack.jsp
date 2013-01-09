<jsp:include page="../../layoutSuperioreUp.jsp"></jsp:include>
<title>Insert title here</title>
<jsp:include page="../../layoutSuperioreDown.jsp"></jsp:include>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<c:if test="${empty feedBackRilasciato}">
	Compila i campi per rilasciare il FeedBack!
	<br>
		<form action="profilo/azioni/rilasciaFeedBack" method="POST"
			enctype="multipart/form-data">
			<c:if test="${!empty erroreNelPunteggio}">
				<c:out value="${erroreNelPunteggio}"></c:out>
				<br>
			</c:if>
			Inserisci un punteggio (da 1 a 5): <input id="punteggio" type="text"
				name="punteggioFeedBack"></input><br> Commento (opzionale): <input
				id="commento" type="text" name="commentoFeedBack"></input><br>
			<input id="submit" type="submit" />
		</form>
	</c:if>
	<c:if test="${!empty feedBackRilasciato}">
		<c:out value="${feedBackRilasciato}"></c:out>
	</c:if>
	<br>
	<a href="profilo/azioni/collaborazioni">Torna alla lista di
		collaborazioni</a>
<jsp:include page="../../layoutInferiore.jsp"></jsp:include>