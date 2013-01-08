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
			<a href="profilo/azioni/ricerchePerUtentiLoggati?tipoRicerca=utente" class="item2" onclick="return false"><span>ricerca utenti</span></a>
		</li>
		<li>
			<a href="profilo/azioni/ricerchePerUtentiLoggati?tipoRicerca=aiuto" class="item3"><span>ricerca aiuto</span></a>
		</li>
		<li>
			<a href="profilo/azioni/collaborazioni" class="item4" onclick="return false"><span>visualizza collaborazioni</span></a>
		</li>
		<li>
			<a href="profilo/azioni/notifiche" class="item5" onclick="return false"><span>visualizza notifiche</span></a>
		</li>
		<li>
			<a href="profilo/azioni/proposteAbilita" class="item6" onclick="return false"><span>proponi nuove abilita</span></a>
		</li>
		<li>
			<a href="profilo/azioni/modificaProfilo" class="item7" onclick="return false"><span>modifica profilo</span></a>
		</li>
		<li>
			<a href="profilo/azioni/feedbackUtente?emailUtente=${sessionScope.utenteCollegato}" class="item8" onclick="return false"><span>visualizza feedback</span></a>
		</li>
	</ul>
</div>
</div>
<div class="content">
<div class="col1">
</div>
<div class="col2">