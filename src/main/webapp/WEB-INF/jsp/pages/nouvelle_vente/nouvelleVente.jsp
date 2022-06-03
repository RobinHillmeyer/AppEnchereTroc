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

	    <h1>Nouvelle vente</h1>
	    
	    <hr>
	    
	    <div class="formUp">
	        <form action="<%=request.getContextPath()%>/NouvelleVente" method="post" class="form_article">
	        	<div class="divSellContForm">
		            <label for="article">Article : </label>
		            <input type="text" class="inptForm form-control" name="nom_article" id="article" required="required">
				
	            </div>
	            
	            <div class="divSellContForm">
		            <label for="description">Description : </label>
		            <textarea name="description" class="inptForm form-control" id="description" cols="20" rows="5" required="required"></textarea>
	            </div>
	            
	            <div class="divSellContForm">
		            <label for="categorie">Catégorie : </label>
		            <select name="categorie" class="inptForm form-control" id="categorie" required="required">
		                <option value="">-- Choisissez la catégorie de l'article --</option>
		                                      	
			          	<c:if test="${!empty listeCategorie}">
			          		<c:forEach var="lc" items="${listeCategorie}">
			          			<option value="${lc.noCategorie}">${lc.libelle}</option>
			          		</c:forEach>
			          	</c:if>
		            </select>
	            </div>
	            
	            <div class="divSellContForm">
		            <label for="photo">Photo de l'article : </label>
		            <input type="image">
	            </div>
	            
	            <div class="divSellContForm">
		            <label for="prix">Mise à prix : </label>
		            <input type="number" class="inptForm form-control" name="prix" id="prix" min="0" required="required">
	            </div>
	            
	            <div class="divSellContForm">
		            <label for="date_debut">Début de l'enchère : </label>
		            <input type="date" class="inptForm form-control" name="date_debut" id="date_debut" required="required"> <!--Choisir la date du jour en date min et date par défaut-->
	            </div>
	            
	            <div class="divSellContForm">
		            <label for="date_fin">Fin de l'enchère : </label>
		            <input type="date" class="inptForm form-control" name="date_fin" id="date_fin" required="required"> <!--Choisir la date du début en date min-->
	            </div>
	            
	            <fieldset>
	                <legend class="retrait">Retrait</legend>
					
					<div class="divSellContForm">
	                   <label for="rue">Rue : </label> 
	                   <input type="text" class="inptForm form-control" name="rue" id="rue" value="${rue}" onfocus="this.value='';" required="required">
	                </div>
	                
	                <div class="divSellContForm">
	                    <label for="code_postal">Code postal : </label>
	                    <input type="text" class="inptForm form-control" name="code_postal" id="code_postal" maxlength="5" value="${codePostal}" onfocus="this.value='';" required="required">
	                </div>
	                
	                <div class="divSellContForm">
	                    <label for="ville">Ville : </label>
	                    <input type="text" class="inptForm form-control" name="ville" id="ville" value="${ville}" onfocus="this.value='';" required="required">
	                </div>
	                
	            </fieldset>
	            
	            <div class="btnFormSell">
		            <input type="submit" class="btn btn-primary col-md-3" name="enregister" value="Enregistrer">
		            <input type="reset" class="btn btn-secondary col-md-4" name="annuler" value="Annuler la vente">
		            <a href="${pageContext.request.contextPath}/home" class="btn btn-danger col-md-3">Annuler</a>
	            </div>
	        </form>
	    </div>
    </main>
</body>
</html>