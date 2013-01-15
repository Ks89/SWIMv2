<jsp:include page="../../layoutSuperioreUp.jsp"></jsp:include>
<title>SWIM - Profilo - Dettaglio suggerimento</title>
<jsp:include page="../../layoutSuperioreDown.jsp"></jsp:include>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%-- if che vede se non sono vuoti dei valori passati dalla servlet per mostrare messaggi di successo e/o errore --%>
	<h1>Dettaglio suggerimento</h1>
	<section>
	<div align="center" class="alignCenter">
	Foto: 
	<img src="../../foto?emailUtente=${emailSuggerito}" />
	<br>
	<br>
	<br> Nome:
	<c:out value="${nomeSuggerito}"></c:out>
	<br> Cognome:
	<c:out value="${cognomeSuggerito}"></c:out>
	<br> Email:
	<c:out value="${emailSuggerito}"></c:out>
	<br>

	<form id="profiloSuggeritoForm" action="profiloSuggerito" method="POST">
		<input type="hidden" name="tipo" /> <input type="hidden" name="emailSuggerito" value="${emailSuggerito}"> <input type="button"
			value="conferma" onclick="profiloSuggerito.elements['tipo'].value='CONFERMA'; profiloSuggerito.submit();" /> <input type="button"
			value="rifiuta" onclick="profiloSuggerito.elements['tipo'].value='RIFIUTA'; profiloSuggerito.submit();" />
	</form>

	<br>
	<a href="profilo/azioni/notifiche">Torna alle notifiche</a>

</div>
	<br>
<jsp:include page="../../layoutInferiore.jsp"></jsp:include>