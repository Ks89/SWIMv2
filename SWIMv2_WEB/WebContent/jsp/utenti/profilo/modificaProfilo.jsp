<jsp:include page="../../layoutSuperioreUp.jsp"></jsp:include>
<title>SWIM - Profilo - Modifica profilo</title>
<jsp:include page="../../layoutSuperioreDown.jsp"></jsp:include>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%-- if che vede se non sono vuoti dei valori passati dalla servlet per mostrare messaggi di successo e/o errore --%>
	<c:if test="${!empty erroreInserimentoFoto}">
		<c:out value="${erroreInserimentoFoto}"></c:out>
		<br>
	</c:if>
	<c:if test="${!empty erroreInserimentoProposta}">
		<c:out value="${erroreInserimentoProposta}"></c:out>
		<br>
	</c:if>
	<c:if test="${!empty modificaAbilitaRiuscitaConSuccesso}">
		<c:out value="${modificaAbilitaRiuscitaConSuccesso}"></c:out>
		<br>
	</c:if>
	<c:if test="${!empty modificaFotoRiuscitaConSuccesso}">
		<c:out value="${modificaFotoRiuscitaConSuccesso}"></c:out>
		<br>
	</c:if>
	
	<%-- Form della modifica del profilo --%>
	<%-- Form che permette di inviare sia file allegati sia dati prelevati dalle input text.
		Essendo una post verra' intercettata dalla doPost della servlet specificata in action="modificaProfilo" --%>
	Modifica profilo utente
	<br>
	<form action="profilo/azioni/modificaProfilo" method="POST" enctype="multipart/form-data">
<!-- 		Nuova password: <input id="nuovaPassword" type="password" name="nuovaPassword"></input><br> Nuovo nome: <input id="nuovoNome" type="text" -->
<!-- 			name="nuovoNome"></input><br> Nuovo cognome: <input id="nuovoCognome" type="text" name="nuovoCognome"></input> -->
		<br> <br> Aggiungi abilita': <br>
		<%-- choose con dentro when che e' una delle condizioni e poi con otherwise tutte le altre condizioni con comprese nel when
			cioe' e' come uno switch con il default --%>
		<c:choose>
			<c:when test="${!empty abilita}">
				<c:forEach items="${abilita}" var="item">
					<input type="checkbox" name="abilita" value='<c:out value="${item.nome}"/>' />
					<c:out value="${item.nome}" />
					<br>
				</c:forEach>
			</c:when>
			<c:otherwise>
				Nessuna nuova abilita' selezionabile
			</c:otherwise>
		</c:choose>
		<%-- input di tipo file per uploadare il file e poi il submit per mandare la form --%>
		<br>Cambia l'immagine del profilo: <input id="file" name="file" type="file" /><br> <input id="submit" type="submit" />
	</form>
	
	<br>
	<a href="../profilo">Torna al profilo</a>
<jsp:include page="../../layoutInferiore.jsp"></jsp:include>