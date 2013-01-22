<jsp:include page="../../layoutSuperioreUp.jsp"></jsp:include>
<title>SWIM - Profilo - Notifiche di risposta</title>
<jsp:include page="../../layoutSuperioreDown.jsp"></jsp:include>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h1>Notifiche di risposta</h1>
<section>
	<div align="center" class="alignCenter">

		<c:if test="${!empty noAmicizie}">
			<div class="generico">
				<p>Non ci sono utenti che hanno accettato recentemente le tue richieste di amicizia</p>
			</div>
		</c:if>
		<br>
		<c:if test="${!empty erroreCollaborazioni}">
			<div class="alert">
				<c:out value="${erroreCollaborazioni}"></c:out>
			</div>
		</c:if>
		<br>
		<c:if test="${!empty noCollaborazioniRespinte }">
			<div class="generico">
				<p>Non ci sono utenti che hanno respinto recentemente le tue richieste di aiuto</p>
			</div>
		</c:if>
		<br>
		<c:if test="${!empty noCollaborazioni }">
			<div class="generico">
				<p>Non ci sono utenti che hanno accettato recentemente le tue richieste di aiuto</p>
			</div>
		</c:if>

		<br>
		<c:choose>
			<c:when test="${empty utentiAccettatiIndiretti}">
			</c:when>
			<c:otherwise>
				<h2>Amicizia accettate tramite suggerimento</h2>
				<table id="tabellaRigheAlterne">
					<tr>
						<th>Utente</th>
					</tr>
					<c:forEach items="${utentiAccettatiIndiretti}" var="utenteAccettatoIndiretto" varStatus="num">
						<c:if test="${num.count>0}">
							<c:if test="${num.count%2!=0}">
								<tr>
							</c:if>
							<c:if test="${num.count%2==0}">
								<tr class="alt">
							</c:if>
						</c:if>
						<td><c:out value="${utenteAccettatoIndiretto.nome}"></c:out> <c:out value="${utenteAccettatoIndiretto.cognome}"></c:out></td>
						</tr>
					</c:forEach>
				</table>
			</c:otherwise>
		</c:choose>

		<c:choose>
			<c:when test="${empty listaUtentiConSuggerimenti}">
			</c:when>
			<c:otherwise>
				<br>
				<h2>Amicizie accettate</h2>
				<table id="tabellaRigheAlterne">
					<tr>
						<th>Utente</th>
					</tr>
					<c:forEach items="${listaUtentiConSuggerimenti}" var="utenteConSuggerimenti" varStatus="num">
						<c:if test="${num.count>0}">
							<c:if test="${num.count%2!=0}">
								<tr>
							</c:if>
							<c:if test="${num.count%2==0}">
								<tr class="alt">
							</c:if>
						</c:if>
						<td><c:out value="${utenteConSuggerimenti.utente.nome}"></c:out> <c:out value="${utenteConSuggerimenti.utente.cognome}"></c:out></td>
						</tr>
					</c:forEach>
				</table>
				<br>
				<c:if test="${empty nonCiSonoSuggerimenti}">
					<br>
					<h2>Suggerimenti di amiciza</h2>
					<form id="suggAlRichiedenteForm" action="profilo/azioni/notificheDiRisposta/suggAlRichiedente" method="POST">
						<table id="tabellaRigheAlterne">
							<tr>
								<th></th>
								<th>Amico acquisito</th>
								<th>Utente suggerito</th>
							</tr>
							<c:forEach items="${listaUtentiConSuggerimenti}" var="utenteConSuggerimenti" varStatus="num">
								<c:forEach items="${utenteConSuggerimenti.suggerimenti}" var="suggerimentoUtente">
									<c:if test="${!empty utenteConSuggerimenti.suggerimenti}">
										<c:if test="${num.count>0}">
											<c:if test="${num.count%2!=0}">
												<tr>
											</c:if>
											<c:if test="${num.count%2==0}">
												<tr class="alt">
											</c:if>
										</c:if>
										<td><input type="checkbox" name="suggeritoAlRichiedenteCheckbox" value="${suggerimentoUtente.email}" /></td>
										<td><c:out value="${utenteConSuggerimenti.utente.nome}"></c:out> <c:out value="${utenteConSuggerimenti.utente.cognome}"></c:out></td>
										<td><c:out value="${suggerimentoUtente.nome}" />&nbsp;<c:out value="${suggerimentoUtente.cognome}" /></td>
										</tr>
									</c:if>
								</c:forEach>
							</c:forEach>
						</table>
						<input id="submit" type="submit" value="Richiedi amicizia" />
					</form>
					<script>
            			$('input[placeholder], textarea[placeholder]').placeholder();
     				 </script>
					<br>
				</c:if>
			</c:otherwise>
		</c:choose>
		<br> <br>

		<c:if test="${empty erroreCollaborazioni}">

			<c:if test="${empty noCollaborazioni }">
				<h2>Collaborazioni richieste confermate</h2>
				<table id="tabellaRigheAlterne">
					<tr>
						<th>Nome collaborazione</th>
						<th>Collaboratore</th>
					</tr>
					<c:forEach items="${listaCollaborazioni}" var="collaborazione" varStatus="num">
						<c:if test="${num.count>0}">
							<c:if test="${num.count%2!=0}">
								<tr>
							</c:if>
							<c:if test="${num.count%2==0}">
								<tr class="alt">
							</c:if>
						</c:if>
						<td><c:out value="${collaborazione.nome}"></c:out></td>
						<td><c:out value="${collaborazione.utenteRicevente.nome}" />&nbsp;<c:out value="${collaborazione.utenteRicevente.cognome}" /></td>
						</tr>
					</c:forEach>
				</table>
			</c:if>
		</c:if>
		<br>
		<c:if test="${empty erroreCollaborazioni}">
			<c:if test="${empty noCollaborazioniRespinte}">
				<h2>Collaborazioni richieste respinte</h2>
				<table id="tabellaRigheAlterne">
					<tr>
						<th>Nome collaborazione</th>
						<th>Collaboratore</th>
					</tr>
					<c:forEach items="${listaCollaborazioniRespinte}" var="collaborazione" varStatus="num">
						<c:if test="${num.count>0}">
							<c:if test="${num.count%2!=0}">
								<tr>
							</c:if>
							<c:if test="${num.count%2==0}">
								<tr class="alt">
							</c:if>
						</c:if>
						<td><c:out value="${collaborazione.nome}"></c:out></td>
						<td><c:out value="${collaborazione.utenteRicevente.nome}" />&nbsp;<c:out value="${collaborazione.utenteRicevente.cognome}" /></td>
						</tr>
					</c:forEach>
				</table>
			</c:if>
		</c:if>
		<br>
		<br> Ricaricando la pagina, queste notifiche non saranno mostrate nuovamente.
	</div>
	<jsp:include page="../../layoutInferiore.jsp"></jsp:include>