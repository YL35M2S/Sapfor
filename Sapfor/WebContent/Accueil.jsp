<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="main.css" rel="stylesheet" media="all" type="text/css"> 
<title>Accueil</title>
</head>
<body>
<p>
  <a href="http://localhost:8080/Sapfor/rest/agent/agents?Agent=1" class="bouton">Agent 1</a>
</p>
<p>
  <a href="http://localhost:8080/Sapfor/rest/agent/agents?Agent=2" class="bouton">Agent 2</a>
</p>
<p>
  <a href="http://localhost:8080/Sapfor/rest/agent/agents?Agent=3" class="bouton">Agent 3</a>
</p>
<p>
  <a href="http://localhost:8080/Sapfor/rest/session/listeOuvertes" class="bouton">Session ouvertes</a>
</p>
<p>
  <a href="http://localhost:8080/Sapfor/rest/session/19041975/fermerCandidature?Session=1" class="bouton">Fermer Candidature sur session 1</a>
</p>
<p>
  <a href="http://localhost:8080/Sapfor/rest/session/listeCandidature?Session=1" class="bouton">Voir candidature session 1</a>
</p>
<p>
  <a href="http://localhost:8080/Sapfor/rest/session/sessions?Session=1" class="bouton">Voir session 1</a>
</p>
<p>
  <a href="http://localhost:8080/Sapfor/rest/uv/uvs?Uv=1" class="bouton">Voir UV 1</a>
</p>
<p>
  <a href="http://localhost:8080/Sapfor/rest/session/19041975/accessible" class="bouton">Voir sessions accessibles pour l'agent de matricule 19041975</a>
</p>
<p>
  <a href="http://localhost:8080/Sapfor/rest/session/06091991/accessible" class="bouton">Voir sessions accessibles pour l'agent de matricule 06091991</a>
</p>
<p>
  <a href="http://localhost:8080/Sapfor/rest/session/19041975/candidater?Session=1&Formateur=false" class="bouton">Candidature de l'agent de matricule 19041975 en tant que stagiaire</a>
</p>
</body>
</html>