<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<c:if test="${empty nonCiSonoRichiesteAmicizia}">
		<br>
		<br> Amici suggeriti:
	<table id="tabellaSuggerimenti" style="border-width: medium; border-style: solid;">
			<tr>
				<th>Nome e cognome</th>
			</tr>
			<c:forEach items="${amiciSuggeriti}" var="utenteSuggerito">
				<tr>
					<td><a href="../azioni/dettaglioSuggerimento?emailUtenteSuggerito=<c:out value="${utenteSuggerito.email}" />"> <c:out
								value="${utenteSuggerito.nome}" />&nbsp;<c:out value="${utenteSuggerito.cognome}" /></a></td>
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
	</c:if>

	<br>
	<a href="../profilo/notifiche">Torna alle notifiche</a>

</body>
</html>