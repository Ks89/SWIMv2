<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
</head>
<body>
<div id="container">
	<header><h1 style="padding-top: 25px;">SWIM</h1></header>
	
	<nav>
		<ul>
			<li><a href="admin"><span class="una">home</span></a></li>
			<li><a href="admin/adminPanel?operazione=1"><span class="una">Aggiungi abilita</span></a></li>
			<li><a href="admin/adminPanel?operazione=2"><span>Visualizza proposte abilita</span></a></li>
		</ul>
	</nav>
	
	<div id="space"></div>
	
	<div id="centro">
		<aside>
			<h1>Amministratore</h1>
			<%--<img id="imgUtente" src="profilo/foto?emailUtente=${sessionScope.utenteCollegato}" /> <br/> --%>
			<%--<c:out value="${sessionScope.nomeUtenteCollegato}"/> <c:out value="${sessionScope.cognomeUtenteCollegato}"/><br/> --%>
			<a href="admin/adminLogin?adminesci=true">Logout</a>
		</aside>
		<article>
