<jsp:include page="../../layoutSuperioreUp.jsp"></jsp:include>
<title>SWIM - Profilo - Profilo altro utente</title>
<jsp:include page="../../layoutSuperioreDown.jsp"></jsp:include>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<h1>Profilo altro utente</h1>
	<section>
	<div align="center" class="alignCenter">
	
	<%-- Mostra il profilo dell'utente che ha richiesto l'amicizia. Accedo a questa pagina
			dal link della tabella in notifiche. 
			Faccio il controllo su amiciSuggeriti perche' per ora e' per forza empty --%>
	<c:choose>
		<c:when test="${empty amiciSuggeriti}">
			<img src="profilo/foto?emailUtente=${emailRichiedente}" />
			<br>
			<table id="formInserimentoParametri">
			<tr>
				<td id="grassettoBlu">Nome:</td>
				<td><c:out value="${nomeRichiedente}"></c:out></td>
			</tr> 
			<tr>
				<td id="grassettoBlu">Cognome:</td>
				<td><c:out value="${cognomeRichiedente}"></c:out></td>
			</tr>
			<tr>
				<td id="grassettoBlu">Email:</td>
				<td><c:out value="${emailRichiedente}"></c:out></td>
			</tr>
			<tr>
				<td id="grassettoBlu">Punteggio feedback:</td>
				<td>
				<c:choose>
						<c:when test="${!empty feedbackStellineRichiedente}">
							<div id="STAR_RATING" align="center">
								<ul>
									<c:forEach begin="1" end="5" varStatus="num">
										<c:if test="${num.count <= feedbackStellineRichiedente}">
											<li id="star_${num.count}" class="on"></li>
										</c:if>
										<c:if test="${num.count > feedbackStellineRichiedente}">
											<li id="star_${num.count}"></li>
										</c:if>
									</c:forEach>
									<li style="background-image: none;">&nbsp;<c:out value="${punteggioFeedbackRichiedente}" /></li>
								</ul>
							</div>
						</c:when>
						<c:otherwise>
							<c:out value="${punteggioFeedbackRichiedente}" />
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			
			
			<%-- Ma a cosa serve sto if??????? --%>
			<c:if test="${empty confermaRichiestaAiuto and empty erroreConfermaRichiestaAiuto}">
				<form id="profiloAltroUtente" action="profilo/azioni/profiloAltroUtente" method="POST">
					<input type="hidden" name="tipo" /> <input type="hidden" name="emailRichiedente" value="${emailRichiedente}"> 
					<tr>
						<td><input type="button" id="button" value="conferma" onclick="profiloAltroUtente.elements['tipo'].value='CONFERMA'; profiloAltroUtente.submit();" /></td> 
						<td><input type="button" id="button" value="rifiuta" onclick="profiloAltroUtente.elements['tipo'].value='RIFIUTA'; profiloAltroUtente.submit();" /></td>
					</tr>
				
				</form>
				<script>
           			 $('input[placeholder], textarea[placeholder]').placeholder();
     			 </script>			
			</c:if>
			</table>
			<br> 
			<a href="profilo/azioni/notifiche">Torna alle notifiche</a>
		</c:when>
		
		<%-- Entro nell otherwise se ho i suggerimenti, cioe' se ho accettato la richiesta di amicizia del profilo
			che questa jsp mostra nel when di questa choose. Ovviamente se ho lista vuota ecc... se ne occupa la ProfiloAltroUtenteServlet --%>
		<c:otherwise>
			<br>
			<br> Amici suggeriti:
			<form action="profilo/azioni/profiloAltroUtente" method="POST">
				<table id="tabellaSuggerimenti" style="border-width: medium; border-style: solid;">
					<tr>
						<th></th>
						<th>Nome e cognome</th>
					</tr>
					<c:forEach items="${amiciSuggeriti}" var="utenteSuggerito">
						<tr>
							<td><input type="checkbox" name="amiciSuggeriti" value="${utenteSuggerito.email}" /></td>
							<td><c:out value="${utenteSuggerito.nome}" />&nbsp;<c:out value="${utenteSuggerito.cognome}" /></td>
						</tr>
					</c:forEach>
				</table>

				<input id="submit" type="submit" value="Accetta suggerimenti" />
			</form>
			<a href="profilo/azioni/notifiche">Rifiuta suggerimenti e torna alle notifiche</a>
		</c:otherwise>
	</c:choose>
</div>
	<br>
<jsp:include page="../../layoutInferiore.jsp"></jsp:include>