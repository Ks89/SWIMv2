<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
</head>
<body>
<div id="container">
	<header><h1 style="padding-top: 25px;">SWIM</h1></header>
	
	<nav>
		<ul>
			<li><a href="profilo/profilo"><span class="una">home</span></a></li>
			<li><a href="profilo/azioni/ricerchePerUtentiLoggati?tipoRicerca=utente" onclick="return false"><span class="una">ricerca utenti</span></a></li>
			<li><a href="profilo/azioni/ricerchePerUtentiLoggati?tipoRicerca=aiuto"><span class="una">ricerca aiuto</span></a></li>
			<li><a href="profilo/azioni/collaborazioni" onclick="return false"><span>visualizza collaborazioni</span></a></li>
			<li><a href="profilo/azioni/notifiche" onclick="return false"><span>visualizza notifiche</span></a></li>
			<li><a href="profilo/azioni/proposteAbilita" onclick="return false"><span>proponi nuove abilit&agrave;</span></a></li>
			<li><a href="profilo/azioni/modificaProfilo" onclick="return false"><span class="una">modifica profilo</span></a></li>
			<li><a href="profilo/azioni/feedbackUtente?emailUtente=${sessionScope.utenteCollegato}" onclick="return false"><span>visualizza feedback</span></a></li>
		</ul>
	</nav>
	
	<div id="space"></div>
	
	<div id="centro">
		<aside>
		</aside>
		<article>
