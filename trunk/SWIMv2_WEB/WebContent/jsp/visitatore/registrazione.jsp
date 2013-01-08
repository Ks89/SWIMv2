<jsp:include page="../layoutSuperioreUp.jsp"></jsp:include>
<title>SWIM - Registrazione</title>
<jsp:include page="../layoutSuperioreDownVisitatore.jsp"></jsp:include>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	Compila i campi sottostanti per registrarti
	<br>
	<br>
	<%-- Form che permette di inviare sia file allegati sia dati prelevati dalle input text.
		Essendo una post verra' intercettata dalla doPost della servlet specificata in action="registrazione" --%>
	<form action="registrazione" method="POST" enctype="multipart/form-data">
		<div align="center">
			<table id="registrazione">
				<tr><td>Email*: </td><td><input type="text" name="emailUtente"></input><td></tr> 
				<tr><td>Password*: </td><td><input type="password" name="password"></input><td></tr> 
				<tr><td>Nome*: </td><td><input type="text" name="nome"></input><td></tr> 
				<tr><td>Cognome*: </td><td><input type="text" name="cognome"></input><td></tr> 
				<tr><td colspan="2">
				Abilita' disponibili (selezionane almeno una)*:</td>
				</tr>
			</table>
			<table id="customers" style="text-align: center;">
				<tr>
				  <th></th>
				  <th>Abilità</th>
				  <th>Descrizione</th>
				</tr>
				<c:forEach items="${abilita}" var="item"  varStatus="num">
					<c:if test="${num.count%2!=0}">
						<tr>
					</c:if>
					<c:if test="${num.count%2==0}">
						<tr class="alt">
					</c:if>
						<td><input type="checkbox" name="abilita" value='<c:out value="${item.nome}"/>' /></td>
						<td><c:out value="${item.nome}" /></td>
						<td><c:out value="${item.descrizione}" /></td>
					</tr>
				</c:forEach>
			</table>
			<table id="registrazione">
			</tr>
				<%-- input di tipo file per uploadare il file e poi il submit per mandare la form --%>
				<tr><td>
				<input id="file" name="file" type="file" /></td></tr><tr>
				<td style="text-align: center"><input id="submit" type="submit"/></td>
				</tr>
			</table>
		</div>
	</form>
	<br>
	*campi da compilare obbligatoriamente
<jsp:include page="../layoutInferiore.jsp"></jsp:include>