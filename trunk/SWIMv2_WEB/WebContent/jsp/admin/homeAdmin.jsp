<jsp:include page="../layoutSuperioreUp.jsp"></jsp:include>
<title>SWIM - Amministrazione</title>
<jsp:include page="../layoutSuperioreDownAdmin.jsp"></jsp:include>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<h1>Login Amministratore</h1>
	<section>
	<div align="center" class="alignCenter">
	
	<%-- if che vede se non sono vuoti dei valori passati dalla servlet per mostrare messaggi di successo e/o errore --%>
	<c:if test="${!empty erroreLoginAdminFallito}">
		<div class="alert">
			<p>
				<c:out value="${erroreLoginAdminFallito}"></c:out>
			</p>
		</div>
		<br>
	</c:if>
	<c:if test="${!empty erroreAlcuniParamNulliOVuoti}">
		<div class="alert">
			<p>
				<c:out value="${erroreAlcuniParamNulliOVuoti}"></c:out>
			</p>
		</div>
		<br>
	</c:if>
	
	<%-- il choose e' come lo switch-case-default di java dove il case e' il when e il default e' l'otherwise --%>
	<c:choose>
		<c:when test="${sessionScope.adminCollegato != null}">
		<h2>Login effettuato correttamente</h2>
		</c:when>
		<c:otherwise>
			<form id="adminLoginForm" action="admin/adminLogin" method="POST">
				<table id="formInserimentoParametri">
					<tr>
						<td>
							Email: 
						</td>
						<td>
							<input id="emailAdmin" type="text" name="emailAdmin" maxlength="100"></input>
						</td>
					</tr>
					<tr>
						<td> 
							Password: 
						</td>
						<td>
							<input id="passwordAdmin"  type="password" name="passwordAdmin" maxlength="64"></input>
						</td>
					</tr>
					<tr>
						<td colspan="2" style="text-align: center;">  
							<input id="submit"  type="submit" /> 
						</td>
					</tr>
				</table>
			</form>
			<script>
            $('input[placeholder], textarea[placeholder]').placeholder();
      </script>
		</c:otherwise>
	</c:choose>
</div>
<jsp:include page="../layoutInferiore.jsp"></jsp:include>