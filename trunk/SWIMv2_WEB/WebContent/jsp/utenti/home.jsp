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
        <p><input type="text" id="emailUtente" name="emailUtente" value="" placeholder="Email"></p>
        <p><input type="password" id="password" name="password" value="" placeholder="Password"></p>
        <p class="remember_me">
          <label>
            <input type="checkbox" name="remember_me" id="remember_me">
            Remember me on this computer
          </label>
        </p>
        <p class="submit"><input id="submit" type="submit" name="commit" value="Login"></p>
      </form>
    </div>
    Non sei ancora registrato? <a href="registrazione">Registrati</a>
  	</c:if>
</div>	
	<%--
	<c:if test="${sessionScope.utenteCollegato == null}">
	<form id="loginForm" action="login" method="POST">
		Email: <input id="emailUtente" type="text" name="emailUtente"></input> 
		Password: <input id="password" type="password" name="password"></input>
		<input id="submit" type="submit" /><br>
	</form>
			<%-- href fa sempre riferimento alla servlet in modo relativo, mai con link assoluti --%>
		<%--<br><a href="profilo/azioni/ricerchePerUtentiLoggati?tipoRicerca=aiuto">Ricerca utenti per visitatori</a>
	</c:if> --%>
	
	<%-- Esempio che mostra come mostrare dei pezzi di pagina quando l'utente e' connesso oppure non lo e' --%>
<%-- 	<c:choose> --%>
<%-- 		<c:when test="${sessionScope.utenteCollegato != null}"> --%>
<!-- 			<form action="ricerca" method="POST"> -->
<!-- 				Cognome: <input type="text" name="cognome" /> <br> Abilita':<br> -->
<%-- 				<c:forEach items="${abilita}" var="item"> --%>
<%-- 					<input type="checkbox" name="abilita" value='<c:out value="${item.nome}"/>' /> --%>
<%-- 					<c:out value="${item.nome}" /> --%>
<!-- 					<br> -->
<%-- 				</c:forEach> --%>
<!-- 				<br> <input type="submit" /> -->
<!-- 			</form> -->
<!-- 			<br><br> -->
<%-- 		</c:when> --%>
<%-- 		<c:otherwise> --%>
<!-- 			<form id="loginForm" action="login" method="POST"> -->
<!-- 				Email: <input id="emailUtente" type="text" name="emailUtente"></input> Password: <input id="password" type="password" name="password"></input> <input -->
<!-- 					id="submit" type="submit" /> <br> <a href="registrazione">Registrazione</a> -->
<!-- 			</form> -->	
<!-- 			<br><br> -->
<!-- 			<a href="profilo/azioni/ricerche?utenti=pervisitatori">Ricerca utenti per visitatori</a> -->
<%-- 		</c:otherwise> --%>
<%-- 	</c:choose> --%>
<jsp:include page="../layoutInferiore.jsp" flush="true"></jsp:include>