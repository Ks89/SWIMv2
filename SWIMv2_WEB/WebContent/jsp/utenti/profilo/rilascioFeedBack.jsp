<jsp:include page="../../layoutSuperioreUp.jsp"></jsp:include>
<title>Insert title here</title>
<jsp:include page="../../layoutSuperioreDown.jsp"></jsp:include>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<c:if test="${empty feedBackRilasciato}">
	<h2>Compila i campi per rilasciare il FeedBack!</h2>
	<br>
	<div align="center">
		<form action="profilo/azioni/rilasciaFeedBack" method="POST"
			enctype="multipart/form-data">
			<c:if test="${!empty erroreNelPunteggio}">
				<c:out value="${erroreNelPunteggio}"></c:out>
				<br>
			</c:if>
			<table id="formInserimentoParametri">
				<tr><td>Inserisci un punteggio (da 1 a 5): </td><td><input id="punteggio" type="text" name="punteggioFeedBack"></input></td></tr>
				<tr><td>Commento (opzionale): </td><td><input id="commento" type="text" name="commentoFeedBack"></input></td></tr>
				<tr><td colspan="2" style="text-align: center;"><input id="submit" type="submit" /></td></tr>
			</table>
		</form>
	</c:if>
	
	<c:if test="${!empty feedBackRilasciato}">
		<c:out value="${feedBackRilasciato}"></c:out>
	</c:if>
	<br>
	<a href="profilo/azioni/collaborazioni">Torna alla lista di
		collaborazioni</a>
	</div>
<jsp:include page="../../layoutInferiore.jsp"></jsp:include>