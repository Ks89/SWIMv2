<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
</HEAD>
<BODY>
<div class="main">
<div><%-- 
<div id="logo">
	<img src="logo.png" width="256" height="91"/>
</div>--%>
<div id="header" class="header">
	<ul>
		<li>
			<a href="profilo/profilo" class="item1"><span>home</span></a>
		</li>
		<li>
			<a href="profilo/azioni/ricerchePerUtentiLoggati?tipoRicerca=utente" class="item2"><span>ricerca utenti</span></a>
		</li>
		<li>
			<a href="profilo/azioni/ricerchePerUtentiLoggati?tipoRicerca=aiuto" class="item3"><span>ricerca aiuto</span></a>
		</li>
		<li>
			<a href="profilo/azioni/collaborazioni" class="item4"><span>visualizza collaborazioni</span></a>
		</li>
		<li>
			<a href="profilo/azioni/notifiche" class="item5"><span>visualizza notifiche</span></a>
		</li>
		<li>
			<a href="profilo/azioni/proposteAbilita" class="item6"><span>proponi nuove abilita</span></a>
		</li>
		<li>
			<a href="profilo/azioni/modificaProfilo" class="item7"><span>modifica profilo</span></a>
		</li>
		<li>
			<a href="profilo/azioni/feedbackUtente?emailUtente=${sessionScope.utenteCollegato}" class="item8"><span>visualizza feedback</span></a>
		</li>
	</ul>
</div>
</div>
<div class="content">
<div class="col1"><img id="imgUtente" src="profilo/foto?emailUtente=${sessionScope.utenteCollegato}" /> <br/>
<c:out value="${sessionScope.nomeUtenteCollegato}"/> <c:out value="${sessionScope.cognomeUtenteCollegato}"/><br/>
<a  style="text-align: center;" href="login?esci=true" style="float: right;">Logout</a>
</div>
<div class="col2">