<jsp:include page="layoutSuperioreUp.jsp"></jsp:include>
<title>SWIM - Profilo - Ricerche</title>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${tipoRicerca=='aiutoVisitatore'}">
	<jsp:include page="layoutSuperioreDownVisitatore.jsp"></jsp:include>
</c:if>
<c:if test="${tipoRicerca!='aiutoVisitatore'}">
	<jsp:include page="layoutSuperioreDown.jsp"></jsp:include>
</c:if>
<%-- Questa jsp conterra' con un choose 3 casi possibili, cioe' ricerca per visitatori, ricerca aiuto e ricerca utenti --%>
<form action="profilo/azioni/ricerchePerUtentiLoggati" method="POST" enctype="multipart/form-data">
	<h1>Ricerca</h1>
	<section>
	<div align="center" class="alignCenter">
		<c:if test="${!risultatoRicerca and ricercaGiaEffettuata}">
			<div class="alert">
				<p>
					<c:out value="La ricerca non ha ottenuto risultati"></c:out>
				</p>
			</div>
		</c:if>
		<br>
		<c:choose>
		<c:when test="${tipoRicerca=='aiuto'}">
			<h2><c:out value="Seleziona le abilità di cui sei alla ricerca"></c:out></h2>
			<table id="tabellaRigheAlterne">
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
				<td style="text-align: justify;"><c:out value="${item.descrizione}" /></td>
			</c:forEach>
			</table>
			<div id="pageNavPosition"></div>

			<script type="text/javascript">
					var pager = new Pager('tabellaRigheAlterne', 5);
					pager.init();
					pager.showPageNav('pager', 'pageNavPosition');
					pager.showPage(1);
			</script>
			 <br>
			 <br>
			 <input type="checkbox" name="soloAmici" value="Ricerca solo tra gli amici" />
			 Ricerca solo tra gli amici&nbsp;
			 <input id="Cerca" type="submit" value="Cerca"/>
			 <br><br>
			 <c:if test="${risultatoRicerca}">
			 	<table id="tabellaRigheAlterne">
					<tr>
						<th>Nome</th>
						<th>Cognome</th>
						<th>Indirizzo Email</th>
					</tr>
					<c:forEach items="${utenti}" var="utente" varStatus="num">
						<c:if test="${num.count>0}">
							<c:if test="${num.count%2!=0}">
								<tr>
							</c:if>
							<c:if test="${num.count%2==0}">
								<tr class="alt">
							</c:if>
						</c:if>
							<td><c:out value="${utente.nome}" /></td>
							<td><c:out value="${utente.cognome}" /></td>
							<td><a href="profilo/azioni/dettagliAltroUtente?utente=${utente.email}&tipoRicerca=aiuto"><c:out value="${utente.email}" /></a></td>
						</tr>
					</c:forEach>
				</table>
				<div id="pageNavPosition"></div>

				<script type="text/javascript">
					var pager = new Pager('tabellaRigheAlterne', 5);
					pager.init();
					pager.showPageNav('pager', 'pageNavPosition');
					pager.showPage(1);
				</script>
			 </c:if>
		</c:when>
		
		<c:when test="${tipoRicerca=='aiutoVisitatore'}">
			<h2><c:out value="Seleziona le abilità di cui sei alla ricerca"></c:out></h2>
			<table id="tabellaRigheAlterne">
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
				<td style="text-align: justify;"><c:out value="${item.descrizione}" /></td>
			</c:forEach>
			</table>
			
			<div id="pageNavPosition"></div>

			<script type="text/javascript">
					var pager = new Pager('tabellaRigheAlterne', 5);
					pager.init();
					pager.showPageNav('pager', 'pageNavPosition');
					pager.showPage(1);
			</script>
			<br>
			 <input id="Cerca" type="submit" value="Cerca"/>
			 <br><br>
			  <c:if test="${risultatoRicerca}">
			 	<table id="tabellaRigheAlterne">
					<tr>
						<th>Nome</th>
						<th>Cognome</th>
					</tr>
					<c:forEach items="${utenti}" var="utente" varStatus="num">
						<c:if test="${num.count>0}">
							<c:if test="${num.count%2!=0}">
								<tr>
							</c:if>
							<c:if test="${num.count%2==0}">
								<tr class="alt">
							</c:if>
						</c:if>
							<td><c:out value="${utente.nome}" /></td>
							<td><c:out value="${utente.cognome}" /></td>
						</tr>
					</c:forEach>
				</table>
			 </c:if>
		</c:when>
		
		<c:otherwise>
		<h2><c:out value="Ricerca utenti per nome"></c:out></h2>
			<input type="text" name="nomeUtente" maxlength="100" placeholder="Il nome dell'utente"></input>&nbsp;
			<input type="text" name="cognomeUtente" maxlength="100" placeholder="Il cognome dell'utente"></input>
			<input id="Cerca" type="submit" value="Cerca"/>
			<br><br>
			<c:if test="${risultatoRicerca}">
			 	<div align="center">
			 	<table id="tabellaRigheAlterne">
					<tr>
						<th>Nome</th>
						<th>Cognome</th>
						<th>Indirizzo Email</th>
					</tr>
					<c:forEach items="${utenti}" var="utente" varStatus="num">
						<c:if test="${num.count>0}">
							<c:if test="${num.count%2!=0}">
								<tr>
							</c:if>
							<c:if test="${num.count%2==0}">
								<tr class="alt">
							</c:if>
						</c:if>
							<td><c:out value="${utente.nome}" /></td>
							<td><c:out value="${utente.cognome}" /></td>
							<td><a href="profilo/azioni/dettagliAltroUtente?utente=${utente.email}&tipoRicerca=utente"><c:out value="${utente.email}" /></a></td>
						</tr>
					</c:forEach>
				</table>
				</div>
			 </c:if>
		</c:otherwise>
	</c:choose>
	</div>
</form>
<script>
            $('input[placeholder], textarea[placeholder]').placeholder();
      </script>
<jsp:include page="layoutInferiore.jsp"></jsp:include>