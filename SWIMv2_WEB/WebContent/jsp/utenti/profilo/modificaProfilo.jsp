<jsp:include page="../../layoutSuperioreUp.jsp"></jsp:include>
<title>SWIM - Profilo - Modifica profilo</title>
<jsp:include page="../../layoutSuperioreDown.jsp"></jsp:include>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%-- choose che vede se non sono vuoti dei valori passati dalla servlet per mostrare messaggi di successo e/o errore --%>
	<c:choose>
		<c:when test="${!empty erroreFileTroppoGrande}"> <c:out value="${erroreFileTroppoGrande}"></c:out><br>
		</c:when>
		<c:when test="${!empty erroreNonFoto}"> <c:out value="${erroreNonFoto}"></c:out><br>
		</c:when>
		<c:when test="${!empty erroreFotoSconosciuto}"> <c:out value="${erroreFotoSconosciuto}"></c:out><br>
		</c:when>
		<c:when test="${!empty erroreFotoIrreversibile}"> <c:out value="${erroreFotoIrreversibile}"></c:out><br>
		</c:when>
		<c:when test="${!empty erroreInserimentoFoto}"> <c:out value="${erroreInserimentoFoto}"></c:out><br>
		</c:when>
		<c:when test="${!empty erroreInserimentoProposta}"> <c:out value="${erroreInserimentoProposta}"></c:out><br>
		</c:when>
		<c:when test="${!empty modificaAbilitaRiuscitaConSuccesso}"> <c:out value="${modificaAbilitaRiuscitaConSuccesso}"></c:out><br>
		</c:when>
		<c:when test="${!empty modificaFotoRiuscitaConSuccesso}"> <c:out value="${modificaFotoRiuscitaConSuccesso}"></c:out><br>
		</c:when>
		<c:when test="${!empty erroreGelListaAbilitaAggiungibili}"> <c:out value="${erroreGelListaAbilitaAggiungibili}"></c:out><br>
		</c:when>
	</c:choose>
	

	<%-- Form della modifica del profilo --%>
	<%-- Form che permette di inviare sia file allegati sia dati prelevati dalle input text.
		Essendo una post verra' intercettata dalla doPost della servlet specificata in action="modificaProfilo" --%>
	<h2>Modifica il profilo</h2>
	<br>
	<div align="center">
	<form action="profilo/azioni/modificaProfilo" method="POST" enctype="multipart/form-data">
<!-- 		Nuova password: <input id="nuovaPassword" type="password" name="nuovaPassword"></input><br> Nuovo nome: <input id="nuovoNome" type="text" -->
<!-- 			name="nuovoNome"></input><br> Nuovo cognome: <input id="nuovoCognome" type="text" name="nuovoCognome"></input> -->
		<%-- choose con dentro when che e' una delle condizioni e poi con otherwise tutte le altre condizioni con comprese nel when
			cioe' e' come uno switch con il default --%>
		<c:choose>
			<c:when test="${!empty abilita}">
			<table id="tabellaRigheAlterne">
				<caption>Aggiungi abilita'</caption>
				<tr>
				  <th></th>
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
					<td><input type="checkbox" name="abilita" value='<c:out value="${item.nome}"/>' /></td>
					<td><c:out value="${item.nome}" /></td>
					<td><c:out value="${item.descrizione}" /></td>
					</tr>
				</c:forEach>
				</table>
			</c:when>
			<c:otherwise>
				Nessuna nuova abilita' selezionabile
			</c:otherwise>
		</c:choose>
		<%-- input di tipo file per uploadare il file e poi il submit per mandare la form --%>
		<br>
		<table id="formInserimentoParametri">
		<tr><td>Cambia l'immagine del profilo: </td><td><input id="file" name="file" type="file" /></td></tr>
		<tr><td colspan="2" style="text-align: center;"><input id="submit" type="submit" /></td></tr>
		</table>
	</form>
	</div>
	<br>
<jsp:include page="../../layoutInferiore.jsp"></jsp:include>