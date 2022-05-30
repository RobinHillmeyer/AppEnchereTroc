<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nouvelle vente</title>
</head>
<body>


    <h1>Nouvelle vente</h1>
    
    <div class="form_nouvelle_vente">
        <form action="<%=request.getContextPath()%>/NouvelleVente" method="post" class="form_article">
            <p>
                <label for="article">Article : </label>
                <input type="text" name="nom_article" id="article" required="required">
                <br><br>
            </p>
            <p>
                <label for="description">Description : </label>
                <textarea name="description" id="description" cols="30" rows="10" required="required"></textarea>
                <br><br>
            </p>
            <p>
                <label for="categorie">Catégorie</label>
                <select name="categorie" id="categorie" required="required">
                    <option value="">-- Choisissez la catégorie de l'article --</option>
                   
                   	
	             	<c:if test="${!empty listeCategorie}">
	             		<c:forEach var="lc" items="${listeCategorie}">
	             			<option value="${lc.noCategorie}">${lc.libelle}</option>
	             		</c:forEach>
	             	</c:if>
	             	
	           <p> ${userId}</p>
	                    
                    
                  
                </select>
            </p>
            <p>
                <label for="photo">Photo de l'article</label>
                <input type="image">
            </p>
            <p>
                <label for="prix">Mise à prix</label>
                <input type="number" name="prix" id="prix" min="0" required="required">
            </p>
            <p>
                <label for="date_debut">Début de l'enchère</label>
                <input type="date" name="date_debut" id="date_debut" required="required"> <!--Choisir la date du jour en date min et date par défaut-->
            </p>
            <p>
                <label for="date_fin">Fin de l'enchère</label>
                <input type="date" name="date_fin" id="date_fin" required="required"> <!--Choisir la date du début en date min-->
            </p>
            <fieldset>
                <legend>Retrait</legend>

                <p>
                   <label for="rue">Rue : </label> 
                   <input type="text" name="rue" id="rue" value="${rue}" onfocus="this.value='';" required="required">
                </p>
                <p>
                    <label for="code_postal">Code postal : </label>
                    <input type="text" name="code_postal" id="code_postal" maxlength="5" value="${codePostal}" onfocus="this.value='';" required="required">
                </p>
                <p>
                    <label for="ville">Ville : </label>
                    <input type="text" name="ville" id="ville" value="${ville}" onfocus="this.value='';" required="required">
                </p>
            </fieldset>
            <input type="submit" name="enregister" value="Enregistrer">
            <input type="reset" name="annuler" value="Annuler">
        </form>
    </div>
</body>
</html>