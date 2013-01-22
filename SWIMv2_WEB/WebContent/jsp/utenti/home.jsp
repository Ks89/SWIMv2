<!DOCTYPE html>
<!--[if lt IE 7]> <html class="lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]> <html class="lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]> <html class="lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]><!--> <html lang="en"> <!--<![endif]-->
<jsp:include page="../layoutSuperioreUp.jsp" flush="true"></jsp:include>
<title>SWIM</title>
<meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <title>Login Form</title>
 
  <!--[if lt IE 9]><script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->

<jsp:include page="../layoutSuperioreDownVisitatore.jsp" flush="true"></jsp:include>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%-- if che vede se non sono vuoti dei valori passati dalla servlet per mostrare messaggi di successo e/o errore --%>
	
	<h1>Home</h1>
	<section>
	<div align="center">
	<br>
		<c:if test="${!empty erroreLoginFallito}">
			<div class="alert">
				<p>
					Si è verificato un errore (<c:out value="${erroreLoginFallito}"></c:out>)
				</p>
			</div>
		</c:if>
	<br>
	<br>
	<br>
	<!--  uso una choose per stabilire quale form mostrare. Se l'utente e' collegato 
	mostro le funzioni di ricerca ecc... altrimenti la form di login e registrazione -->
	<c:if test="${sessionScope.utenteCollegato == null}">
	<section class="containerLogin">
    <div class="login">
      <h1>Login to SWIMv2</h1>
      <form method="post" action="login" id="loginForm">
        <p><input type="text" id="emailUtente" name="emailUtente" maxlength="100" value="" placeholder="Email"></p>
        <p><input type="password" id="password" name="password" maxlength="100"  value="" placeholder="Password"></p>
        <p class="submit"><input id="submit" type="submit" name="commit" value="Login"></p>
      </form>
      <script>
            $('input[placeholder], textarea[placeholder]').placeholder();
      </script>
    </div>
    Non sei ancora registrato? <a href="registrazione">Registrati</a>
  	</c:if>
</div>	
<jsp:include page="../layoutInferiore.jsp" flush="true"></jsp:include>