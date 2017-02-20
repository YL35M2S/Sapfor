<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="main.css" rel="stylesheet" media="all" type="text/css"> 
<title>Creation d'un agent</title>
</head>
<form method="post" action="creation">
            <fieldset>
                <legend>Inscription</legend>
                <p>Vous pouvez ajouter un Agent via ce formulaire</p>
                <br />
				
                <label for="id">ID</label>
                <input type="text" id="id" name="id" value=""" size="20" maxlength="20" />
                <br />
                
                <label for="nom">Nom</label>
                <input type="text" id="nom" name="nom" value=""" size="20" maxlength="20" />
                <br />
                
                <label for="motdepasse">Mot de passe</label>
                <input type="password" id="motdepasse" name="motdepasse" value="" size="20" maxlength="20" />
                <br />
                
                <label for="matricule">Matricule</label>
                <input type="text" id="matricule" name="matricule" value="" size="20" maxlength="20" />
                <br />
                
                <label for="gestionnaire">Est-il gestionnaire ?</label>
                <br />
                <label for="gestionnaire">OUI</label>
                <input type=checkbox id="gestionnaireOUI" name="gestionnaire" value="1" size="20" maxlength="20" />
                <br />
                <label for="gestionnaire">NON</label>
                <input type=checkbox id="gestionnaireNON" name="gestionnaire" value="0" size="20" maxlength="20" />
                <br />
                <br />
				<br />
                <input type="submit" value="creation" class="sansLabel" />
                <br />
                
                <p class="${empty form.erreurs ? 'succes' : 'erreur'}">${form.resultat}</p>
            </fieldset>
        </form>
    </body>
</html>