<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SWIM - Profilo</title>
<style type="text/css">
.pg-normal {
	color: black;
	font-weight: normal;
	text-decoration: none;
	cursor: pointer;
}

.pg-selected {
	color: black;
	font-weight: bold;
	text-decoration: underline;
	cursor: pointer;
}
</style>

<script type="text/javascript" src="../js/paging.js"></script>

</head>
<body>

	<!-- Si usa sessionScope solo per ottenere l'email dell'utente collegato. 
		Tutto il resto lo si ottiene semplicemente col nome dell'attributo settato nella servlet  -->

	<br> Profilo di:
	<c:out value="${nomeUtenteCollegato}">
	</c:out>
	<c:out value="${cognomeUtenteCollegato}"></c:out>
	<br>
	<br> Foto:
	<br>
	<img src="Foto" />
	<br>
	<br> Email:
	<c:out value="${sessionScope.utenteCollegato}"></c:out>
	<br> Nome:
	<c:out value="${nomeUtenteCollegato}"></c:out>
	<br> Cognome:
	<c:out value="${cognomeUtenteCollegato}"></c:out>
	<br> Punteggio feedback:
	<c:out value="${punteggioUtenteCollegato}" />

	<br>Abilita' dell'utente:
	<c:forEach items="${abilita}" var="item">
		<ul>
			<li><c:out value="${item.nome}" />
		</ul>
		<!--  	<option value="<c:out value="${item.nome}" />">
				<c:out value="${item.descrizione}" />
			</option>-->
	</c:forEach>


	<a href="../Home">Torna alla Homepage</a>
	<br>

	<br>
	<br> Collaborazioni create da me:
	<table id="resultsAbilit" style="border-width: medium; border-style: solid;">
		<tr>
			<th>utenteRicevente</th>
			<th>nome</th>
			<th>punteggioFeedback</th>
		</tr>
		<c:forEach items="${collabCreate}" var="item">
			<tr>
				<td><c:out value="${item.utenteRicevente.cognome}" />&nbsp;<c:out value="${item.utenteRicevente.nome}" /></td>
				<td><a href="../Collaborazione/DettaglioCollaborazione?id=<c:out value="${item.id}" />"><c:out value="${item.nome}" /></a></td>
				<td><c:out value="${item.punteggioFeedback}" /></td>
			</tr>
		</c:forEach>
	</table>
	<div id="pageNavPosition"></div>

	<script type="text/javascript">
		var pager = new Pager('resultsAbilit', 3);
		pager.init();
		pager.showPageNav('pager', 'pageNavPosition');
		pager.showPage(1);
	</script>

	<br>
	<br>Abilita' disponibili:
	<c:forEach items="${abilita}" var="item">
		<input type="checkbox" name="abilita" value='<c:out value="${item.nome}"/>' />
		<c:out value="${item.nome}" />
		<br>
	</c:forEach>
				
	<form action="Upload" method="POST" enctype="multipart/form-data">
		<input name="file" type="file" /> <br> <input type="submit" />
	</form>

	<a href="../Login?esci=true">Logout</a>
	<!-- Sarà una servlet dedicata -->
</body>
</html>