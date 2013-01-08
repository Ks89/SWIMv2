<jsp:include page="../../layoutSuperioreUp.jsp"></jsp:include>
<title>SWIM - Profilo</title>
<jsp:include page="../../layoutSuperioreDown.jsp"></jsp:include>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<a href="profilo/azioni/notificheDiRisposta/notificheDiRisposta?emailUtente=${sessionScope.utenteCollegato}" >Ci sono nuove risposte degli utenti</a>
	<br>
	<a href="profilo/azioni/feedbackUtente?emailUtente=${sessionScope.utenteCollegato}" >Visualizza i tuoi feedback</a>
	<%-- <br> Profilo di:
	<c:out value="${nomeUtenteCollegato}">
	</c:out>
	<c:out value="${cognomeUtenteCollegato}"></c:out>
	<br>
	<br> Foto:
	<br>--%>
	<%-- mostra immagine prelevata dalla servlet foto --%>
	<%--<img src="foto?emailUtente=${sessionScope.utenteCollegato}" /> --%>
	<br>
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

	<br>
	<br>
	<%--<a href="azioni/ricerchePerUtentiLoggati?tipoRicerca=utente">Ricerca utenti</a>
	<br>
	<br>
	<a href="azioni/ricerchePerUtentiLoggati?tipoRicerca=aiuto">Ricerca aiuto</a>
	<br>
	<br>
	<a href="azioni/collaborazioni">Visualizza collaborazioni</a>
	<br>
	<br>
	<a href="azioni/notifiche">Visualizza notifiche</a>
	<br>
	<br>
	<a href="azioni/proposteAbilita">Proponi una nuova abilita</a>
	<br>
	<br>
	<a href="azioni/modificaProfilo">Modifica il profilo</a> --%>
	<br>
	<br>
	<br>
	<br><br>
	<br>
	<br>
	<br>
	
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<%-- href fa sempre riferimento alla servlet in modo relativo, mai con link assoluti.
		Oltre a tornare indietro di un livello nella gerarchia passo il parametro esci=true per avvisare la doGet della LoginServlet che deve eseguire
		il logout invalidando la sessione --%>
	<a href="../login?esci=true">Logout</a>

	<!--  non cancellare tutta la parte che c'e' qui sotto per nessun motivo!!!!! -->
	<!--  	<option value="<c:out value="${item.nome}" />">   e' l'esempio di un menu a tendina, non rimuoverlo
				<c:out value="${item.descrizione}" /> </option>-->
<jsp:include page="../../layoutInferiore.jsp"></jsp:include>