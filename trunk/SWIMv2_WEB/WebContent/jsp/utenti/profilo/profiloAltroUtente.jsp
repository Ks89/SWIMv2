<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<c:choose>
		<c:when test="${empty amiciSuggeriti}">
			<img src="../foto?emailUtente=${emailRichiedente}" />
			<br>
			<br>
			<br> Nome:
			<c:out value="${nomeRichiedente}"></c:out>
			<br> Cognome:
			<c:out value="${cognomeRichiedente}"></c:out>
			<br> Email:
			<c:out value="${emailRichiedente}"></c:out>
			<br> Punteggio feedback:
			<c:out value="${punteggioFeedback}"></c:out>
			<br>

			<c:if test="${empty confermaRichiestaAiuto and empty erroreConfermaRichiestaAiuto}">
				<form id="profiloAltroUtente" action="profiloAltroUtente" method="POST">
					<input type="hidden" name="tipo" /> <input type="hidden" name="emailRichiedente" value="${emailRichiedente}"> <input type="button"
						value="conferma" onclick="profiloAltroUtente.elements['tipo'].value='CONFERMA'; profiloAltroUtente.submit();" /> <input type="button"
						value="rifiuta" onclick="profiloAltroUtente.elements['tipo'].value='RIFIUTA'; profiloAltroUtente.submit();" />
				</form>
			</c:if>
			<br>
			<a href="../azioni/notifiche">Torna alle notifiche</a>
		</c:when>
		<c:otherwise>
			<br>
			<br> Amici suggeriti:
			<form action="profiloAltroUtente" method="POST">
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

				<div id="pageNavPosition2"></div>

				<script type="text/javascript">
 				var pager = new Pager('tabellaSuggerimenti', 5);
 				pager.init();
 				pager.showPageNav('pager', 'pageNavPosition2');
 				pager.showPage(1);
			</script>

				<input id="submit" type="submit" value="Accetta suggerimenti" />
			</form>
		</c:otherwise>
	</c:choose>
</body>
</html>