<jsp:include page="../../layoutSuperioreUp.jsp"></jsp:include>
<title>SWIM - Rilascio feedback</title>
<jsp:include page="../../layoutSuperioreDown.jsp"></jsp:include>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<h1>Rilascio feedback</h1>
	<section>
	<div align="center" class="alignCenter">
	<c:if test="${empty feedBackRilasciato}">
	<br>
		<form action="profilo/azioni/rilasciaFeedBack" method="POST"
			enctype="multipart/form-data">
			<c:if test="${!empty erroreNelPunteggio}">
				<div class="alert">
					<p>
						<c:out value="${erroreNelPunteggio}"></c:out>
					</p>
				</div>
				<br>
			</c:if>
			<table id="formInserimentoParametri">
				<tr><td id="grassettoBlu">Inserisci un punteggio (da 1 a 5): </td><td>
								
				<select id="punteggio" name="punteggioFeedBack">
  					<option value="1">1</option>
  					<option value="2">2</option>
  					<option value="3">3</option>
  					<option value="4">4</option>
  					<option value="5">5</option>
				</select>
				
				</td></tr>
				<tr><td id="grassettoBlu">Commento (opzionale): </td><td><input id="commento" type="text" name="commentoFeedBack" maxlength="300" placeholder="Lascia un commento"></input></td></tr>
				<tr><td colspan="2" style="text-align: center;"><input id="submit" type="submit" /></td></tr>
			</table>
		</form>
		<script>
            $('input[placeholder], textarea[placeholder]').placeholder();
      </script>
	</c:if>
	
	<c:if test="${!empty feedBackRilasciato}">
		<div class="generico">
				<p>
					<c:out value="${feedBackRilasciato}"></c:out>
				</p>
			</div>
	</c:if>
	<br>
	<a href="profilo/azioni/collaborazioni">Torna alla lista di
		collaborazioni</a>
	</div>
<jsp:include page="../../layoutInferiore.jsp"></jsp:include>