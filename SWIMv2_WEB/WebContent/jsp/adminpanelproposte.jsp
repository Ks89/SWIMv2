<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SWIM - Amministrazione proposte abilita</title>
</head>
<body>

	<c:if test="${!empty erroreInserimentoPropostaFallito}">
	Si è verificato un errore: <c:out value="${erroreInserimentoPropostaFallito}"></c:out>
	</c:if>

<br>
	<br> Proposte di aggiunta abilita, inviate dagli utenti:
	<table id="tabellaProposte" style="border-width: medium; border-style: solid;">
		<tr>
			<th>Utente</th>
			<th>Nome abilita</th>
		</tr>
		<c:forEach items="${proposte}" var="proposta">
			<tr>
				<td><c:out value="${proposta.utente.nome}" />&nbsp;<c:out value="${proposta.utente.cognome}" /></td>
				<td><a href="../admin/dettaglioProposta?idProposta=<c:out value="${proposta.id}" />"><c:out value="${proposta.abilitaProposta}" /></a></td>
			</tr>
		</c:forEach>
	</table>
	<div id="pageNavPosition"></div>

	<script type="text/javascript">
		var pager = new Pager('tabellaProposte', 3);
		pager.init();
		pager.showPageNav('pager', 'pageNavPosition');
		pager.showPage(1);
	</script>

</body>
</html>