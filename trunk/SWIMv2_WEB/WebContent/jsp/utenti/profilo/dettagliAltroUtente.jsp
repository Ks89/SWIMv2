<jsp:include page="../../layoutSuperioreUp.jsp"></jsp:include>
<title>Insert title here</title>
<jsp:include page="../../layoutSuperioreDown.jsp"></jsp:include>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	Foto: 
	<img src="profilo/foto?emailUtente=${utente.email}" />
	<br>
	<br /> Nome:
	<c:out value="${utente.nome}"></c:out>
	<br /> Cognome:
	<c:out value="${utente.cognome}"></c:out>
	<br /> Email:
	<c:out value="${utente.email}"></c:out>
	<br /> Punteggio feedback:
	<c:out value="${punteggioFeedback}"></c:out>
	<br />
	<%-- Lista puntata delle abilita dell'utente fatta con ul e poi li per ogni voce della lista --%>
	<br>Abilita' dell'utente:
	<c:forEach items="${listaAbilita}" var="abilita">
		<ul>
			<li><c:out value="${abilita.nome}" />
		</ul>
	</c:forEach>
	<br />
	<a href="profilo/azioni/feedbackUtente?emailUtente=${utente.email}" >Visualizza i feedback di questo utente</a>
	<br>
	<br>
	<form id="dettagliAltroUtente" action="profilo/azioni/dettagliAltroUtente" method="POST">
		<input type="hidden" name="tipo" />
		<c:if test="${amiciziaGiaInoltrata==false}">
			<input type="button" value="Richiedi amicizia" onclick="dettagliAltroUtente.elements['tipo'].value='amicizia'; dettagliAltroUtente.submit();" />
			<br />
		</c:if>
		Nome:<input type="text" name="nomeCollaborazione"></input> Descrizione:<input type="text" name="descrizioneCollaborazione"></input> <input
			type="button" value="Richiedi collaborazione" onclick="dettagliAltroUtente.elements['tipo'].value='collaborazione'; dettagliAltroUtente.submit();" />
		<br />
		<c:if test="${!empty messageCollaborazione}">${messageCollaborazione}</c:if>
	</form>
	<c:if test="${tipoRicerca=='utente'}">
		<a href="ricerchePerUtentiLoggati?tipoRicerca=utente">Torna alla ricerca utente</a>
	</c:if>
	<c:if test="${tipoRicerca=='aiuto'}">
		<a href="ricerchePerUtentiLoggati?tipoRicerca=aiuto">Torna alla ricerca aiuto</a>
	</c:if>
<jsp:include page="../../layoutInferiore.jsp"></jsp:include>