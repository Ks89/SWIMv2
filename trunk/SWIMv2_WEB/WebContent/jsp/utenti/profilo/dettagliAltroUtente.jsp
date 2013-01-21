<jsp:include page="../../layoutSuperioreUp.jsp"></jsp:include>
<title>SWIM - Profilo - DettaglioRicerca</title>
<jsp:include page="../../layoutSuperioreDown.jsp"></jsp:include>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h1>Dettagli altro utente</h1>
<section>
	<div align="center" class="alignCenter">
		
		<%-- Messaggi di errore --%>
		<c:if test="${!empty erroreCampiVuotiCollaborazione}"> 
		<div class="alert">
			<p>
				<c:out value="${erroreCampiVuotiCollaborazione}"></c:out><br>
			</p>
		</div>
		</c:if>
		<c:if test="${!empty erroreGetUtente}"> 
		<div class="alert">
			<p>
				<c:out value="${erroreGetUtente}"></c:out><br>
			</p>
		</div>
		</c:if>
		<c:if test="${!empty erroreAmiciziaNonStretta}"> 
		<div class="alert">
			<p>
				<c:out value="${erroreAmiciziaNonStretta}"></c:out><br>
			</p>
		</div>
		</c:if>
		<c:if test="${!empty erroreCollaborazioneNonCreata}"> 
		<div class="alert">
			<p>
				<c:out value="${erroreCollaborazioneNonCreata}"></c:out><br>
			</p>
		</div>
		</c:if>
		<c:if test="${!empty erroreAmiciziaGiaInoltrata}"> 
		<div class="alert">
			<p>
				<c:out value="${erroreAmiciziaGiaInoltrata}"></c:out><br>
			</p>
		</div>
		</c:if>	
		
		
		<%-- Messaggi di conferma --%>
		<c:if test="${!empty richiestaAmiciziaInviata}"> 
		<div class="conferma">
			<p> 
				<c:out value="${richiestaAmiciziaInviata}"></c:out><br>
			</p>
		</div>
		</c:if>	
		<c:if test="${!empty collaborazioneCreataCorrettamente}"> 
		<div class="conferma">
			<p>
				<c:out value="${collaborazioneCreataCorrettamente}"></c:out><br>
			</p>
		</div>
		</c:if>			
		
		
		<img src="profilo/foto?emailUtente=${utente.email}" />
		<table id="formInserimentoParametri">
			<tr>
				<td id="grassettoBlu">Nome:</td>
				<td><c:out value="${utente.nome}"></c:out></td>
			</tr>
			<tr>
				<td id="grassettoBlu">Cognome:</td>
				<td><c:out value="${utente.cognome}"></c:out></td>
			</tr>
			<tr>
				<td id="grassettoBlu">Email:</td>
				<td><c:out value="${utente.email}"></c:out></td>
			</tr>
			<tr>
				<td id="grassettoBlu">Punteggio feedback:</td>
				<td><c:choose>
						<c:when test="${!empty feedbackUtenteRicercatoStelline}">
							<div id="STAR_RATING" align="center">
								<ul>
									<c:forEach begin="1" end="5" varStatus="num">
										<c:if test="${num.count <= feedbackUtenteRicercatoStelline}">
											<li id="star_${num.count}" class="on"></li>
										</c:if>
										<c:if test="${num.count > feedbackUtenteRicercatoStelline}">
											<li id="star_${num.count}"></li>
										</c:if>
									</c:forEach>
									<li style="background-image: none;">&nbsp;<c:out value="${punteggioFeedback}" /></li>
								</ul>
							</div>
						</c:when>
						<c:otherwise>
							<c:out value="${punteggioFeedback}" />
						</c:otherwise>
					</c:choose></td>
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
		
		<br> <a href="profilo/azioni/feedbackUtente?emailUtente=${utente.email}&tipoRicerca=${tipoRicerca}">Visualizza i feedback di questo utente</a>
		<br> <br>
		
		<form id="dettagliAltroUtente" action="profilo/azioni/dettagliAltroUtente" method="POST">
			<input type="hidden" name="tipo" />
			<c:if test="${amiciziaGiaInoltrata==false}">
				<input type="button" id="button" value="Richiedi amicizia"
					onclick="dettagliAltroUtente.elements['tipo'].value='amicizia'; dettagliAltroUtente.submit(); " />
				<br>
			</c:if>
			<br>
			<table id="formInserimentoParametri">
				<caption>Compila i campi sottostanti per richiedere una collaborazione</caption>
				<tr>
					<td id="grassettoBlu">Nome:</td><td><input type="text" name="nomeCollaborazione" maxlength="100" style="width: 271px;" placeholder="Nome della collaborazione"></input></td>
				</tr> 
				<tr>
					<td id="grassettoBlu">Descrizione:</td><td><textarea name="descrizioneCollaborazione" rows="10" cols="50" maxlength="500" style="resize:none;" placeholder="Descrizione della collaborazione"></textarea></td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: center;"><input type="button" id="button" value="Richiedi collaborazione" onclick="dettagliAltroUtente.elements['tipo'].value='collaborazione'; dettagliAltroUtente.submit();" /></td>
				</tr>
			</table>

		</form>
		<script>
            $('input[placeholder], textarea[placeholder]').placeholder();
      </script>
		<br>
		<c:if test="${!empty messageCollaborazione}">
				<div class="alert" >
					<p>
						${messageCollaborazione}
					</p>
				</div>
			</c:if>
			<br>
			<c:if test="${tipoRicerca=='utente'}">
				<a href="profilo/azioni/ricerchePerUtentiLoggati?tipoRicerca=utente">Torna alla ricerca utente</a>
			</c:if>
			<c:if test="${tipoRicerca=='aiuto'}">
				<a href="profilo/azioni/ricerchePerUtentiLoggati?tipoRicerca=aiuto">Torna alla ricerca aiuto</a>
			</c:if>
	</div>
	<jsp:include page="../../layoutInferiore.jsp"></jsp:include>