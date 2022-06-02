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
        <h2>${article.nomArticle}</h2>
        <p>Description : ${article.description}</p>
        <p>Catégorie : ${categorie.libelle}</p>
        <p>Meilleure offre : </p>
        <p>Mise a prix : ${article.miseAPrix}</p>
        <p>Fin de l'enchère : ${article.dateFinEncheres}</p>
        <p>Retrait : ${retrait.rue} ${retrait.codePostal} ${retrait.ville}</p>
        <p>Vendeur : ${utilisateur.pseudo}</p>
    </c:if>
    <form action="${pageContext.request.contextPath}/DetailVente" method="post">
        <p>
            <label for="proposition">Ma proposition : </label>
            <input type="number" name="proposition" id="proposition" min="0" class="inptForm form-control">
        </p>
        <input type="submit" value="Enchérir" class="btn btn-success col-md-3">
    </form>
</main>
</body>
</html>