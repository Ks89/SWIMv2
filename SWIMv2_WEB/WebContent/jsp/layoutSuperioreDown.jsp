<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
</head>
<body>
<div id="container">
	<header><h1 style="padding-top: 25px;">SWIM</h1></header>
	
	<nav>
		<ul>
			<li><a href="profilo/profilo"><span class="una">Home</span></a></li>
			<li><a href="profilo/azioni/ricerchePerUtentiLoggati?tipoRicerca=utente"><span class="una">Ricerca utenti</span></a></li>
			<li><a href="profilo/azioni/ricerchePerUtentiLoggati?tipoRicerca=aiuto"><span class="una">Ricerca aiuto</span></a></li>
			<li><a href="profilo/azioni/collaborazioni"><span>Visualizza collaborazioni</span></a></li>
			<li><a href="profilo/azioni/notifiche"><span>Visualizza notifiche</span></a></li>
			<li><a href="profilo/azioni/proposteAbilita"><span>Proponi nuove abilit&agrave;</span></a></li>
			<li><a href="profilo/azioni/modificaProfilo"><span class="una">Modifica profilo</span></a></li>
			<li><a href="profilo/azioni/feedbackUtente?emailUtente=${sessionScope.utenteCollegato}"><span>Visualizza feedback</span></a></li>
		</ul>
	</nav>
	
	<div id="space"></div>
	
	<div id="centro">
		<aside>
			<h1>Utente</h1>
			<img id="imgUtente" src="profilo/foto?emailUtente=${sessionScope.utenteCollegato}" /> <br/>
			<c:out value="${sessionScope.nomeUtenteCollegato}"/> <c:out value="${sessionScope.cognomeUtenteCollegato}"/><br/>
			<a style="text-align: center;text-decoration: none;	text-color: white;font-weight: bold;" href="login?esci=true">Logout</a>	
		</aside>
		<article>
