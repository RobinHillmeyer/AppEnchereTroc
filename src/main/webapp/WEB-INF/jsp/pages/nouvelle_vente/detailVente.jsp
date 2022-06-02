<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">

<jsp:include page="../fragments/head.jsp" />
<c:if test="${!empty listeCodesErreur}">
    <div class="alert alert-danger" role="alert">
        <strong>Erreur</strong>
        <ul>
            <c:forEach var="code" items="${listeCodesErreur}">
                <li>${LecteurMessage.getMessageErreur(code)}</li>
            </c:forEach>
        </ul>
    </div>
</c:if>

<body>

<%@ include file="../fragments/header.jsp"%>

<main class="mainUp slideLeft">
    <c:if test="${!empty article}">
    	
        <h1>Détail vente</h1>
        
        <hr>

        <div class="subDivContForm">
        	<h2>${article.nomArticle}</h2>
        </div>
        
        <div class="subDivContForm">
        	<p>Description : ${article.description}</p>
        </div>
        
        <div class="subDivContForm">
        	<p>Catégorie : ${categorie.libelle}</p>
        </div>
        
        <div class="subDivContForm">
        	<p>Meilleure offre : </p>
        </div>
        
        <div class="subDivContForm">
        	<p>Mise a prix : ${article.miseAPrix}</p>
        </div>
        
        <div class="subDivContForm">
        	<p>Fin de l'enchère : ${article.dateFinEncheres}</p>
        </div>
        
        <div class="subDivContForm">
        	<p>Retrait : ${retrait.rue} ${retrait.codePostal} ${retrait.ville}</p>
        </div>
        
        <div class="subDivContForm">
        	<p>Vendeur : ${utilisateur.pseudo}</p>
        </div>
        
    </c:if>
    
    <form action="${pageContext.request.contextPath}/DetailVente" method="post">
    
        <div  class="subDivContDetail">
            <label for="proposition">Ma proposition : </label>
            <input type="number" name="proposition" id="proposition" min="0" class="inptForm form-control inptFormNumber">
        </div>
        
        <input type="submit" value="Enchérir" class="btn btn-success col-md-3">
        
    </form>
    
</main>
</body>
</html>