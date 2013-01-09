<jsp:include page="../../layoutSuperioreUp.jsp"></jsp:include>
<title>Insert title here</title>
<jsp:include page="../../layoutSuperioreDown.jsp"></jsp:include>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<div align="center">
		<img src="profilo/foto?emailUtente=${utente.email}" />
		<table id="formInserimentoParametri">
			<tr>
				 <td>Nome: </td>
				 <td><c:out value="${utente.nome}"></c:out></td>
			</tr>
			<tr>
				<td>Cognome: </td>
				<td><c:out value="${utente.cognome}"></c:out></td>
			</tr>
			<tr>
				<td>Email: </td>
				<td><c:out value="${utente.email}"></c:out></td>
			</tr>
			<tr>
				<td>Punteggio feedback: </td>
				<td><c:out value="${punteggioFeedback}"></c:out></td>
			</tr>
		</table>
		<br>
		<table id="tabellaRigheAlterne">
			<caption>Abilita' dell'utente:</caption>
			<tr> 
		  		<th>Abilità</th>
				<th>Descrizione</th>
			</tr>
			<c:forEach items="${listaAbilita}" var="abilita" varStatus="num">
				<c:if test="${num.count>0}">
					<c:if test="${num.count%2!=0}">
						<tr>
					</c:if>
					<c:if test="${num.count%2==0}">
						<tr class="alt">
					</c:if>
				</c:if>
				<td><c:out value="${abilita.nome}" /></td>
				<td><c:out value="${abilita.descrizione}" /></td>
				</tr>
			</c:forEach>
		</table>
		<br>
		<a href="profilo/azioni/feedbackUtente?emailUtente=${utente.email}" >Visualizza i feedback di questo utente</a>
		<br>
		<br>
		<form id="dettagliAltroUtente" action="profilo/azioni/dettagliAltroUtente" method="POST">
			<input type="hidden" name="tipo" />
			<c:if test="${amiciziaGiaInoltrata==false}">
				<input type="button" value="Richiedi amicizia" onclick="dettagliAltroUtente.elements['tipo'].value='amicizia'; dettagliAltroUtente.submit();" />
				<br />
			</c:if>
			Nome:<input type="text" name="nomeCollaborazione"></input> 
			Descrizione:<input type="text" name="descrizioneCollaborazione"></input><br><br>
			<input type="button" id="button" value="Richiedi collaborazione" onclick="dettagliAltroUtente.elements['tipo'].value='collaborazione'; dettagliAltroUtente.submit();" />
			<br>
			<c:if test="${!empty messageCollaborazione}">${messageCollaborazione}</c:if>
		</form>
			<br>
			<c:if test="${tipoRicerca=='utente'}">
				<a href="profilo/azioni/ricerchePerUtentiLoggati?tipoRicerca=utente">Torna alla ricerca utente</a>
			</c:if>
			<c:if test="${tipoRicerca=='aiuto'}">
				<a href="profilo/azioni/ricerchePerUtentiLoggati?tipoRicerca=aiuto">Torna alla ricerca aiuto</a>
			</c:if>
	</div>
<jsp:include page="../../layoutInferiore.jsp"></jsp:include>