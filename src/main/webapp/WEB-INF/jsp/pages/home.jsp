<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>

<jsp:include page="fragments/head.jsp" />
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
	<%@ include file="./fragments/header.jsp"%>
	<div class="linkSign">
		<c:if test="${empty userIdSessionAttr}">
			<a href="${pageContext.request.contextPath}/signIn"
				class="linkSignIn">S'inscrire</a>
			<a href="${pageContext.request.contextPath}/signUp"
				class="linkSignIn">Se connecter</a>
		</c:if>
		<c:if test="${!empty userIdSessionAttr}">
		<a href="${pageContext.request.contextPath}/ProfilUtilisateur"
				class="linkSignIn">Encheres</a>
		<a href="${pageContext.request.contextPath}/NouvelleVente"
				class="linkSignIn">Vendre un article</a>
		<a href="${pageContext.request.contextPath}/ProfilUtilisateur"
				class="linkSignIn">Profil</a>
		<a href="${pageContext.request.contextPath}/signOut"
				class="linkSignIn">Déconnexion</a>
			
		</c:if>
	</div>

	<div class="filtersContainer">

		<div class="input-group">
			<input type="search" class="form-control col-md-3 "
				placeholder="Le nom de l'article contient ?" aria-label="Search"
				aria-describedby="search-addon" />
			<button type="button" class="btn btn-outline-primary">
				<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16"
					fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
 				 <path
						d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z" />
				</svg>
			</button>
		</div>

		<div class="inptSlct input-group mt-4">
			<label id="selectCat" class="form-label" for="selectCat">Catégorie
				:</label> <select class="form-control col-md-2 " name="selectCat"
				aria-label=".form-select-lg example">
				<option selected>Choisir une catégorie</option>
				<c:if test="${!empty listeCategorie}">
					<c:forEach var="lc" items="${listeCategorie}">
						<option value="${lc.noCategorie}">${lc.libelle}</option>
					</c:forEach>
				</c:if>
			</select>
		</div>
	</div>
	<c:if test="${!empty userIdSessionAttr}">
	<div class="containerFilterCo">
		<div class="chexkBoxContainer">
		
			<div class="form-check">
			
				<input class="form-check-input" type="radio" name="achat"
					id="achat"> <label class="form-check-label"
					for="achat">Achats</label>
			</div>
			<div class="subContainerCB">

				<div class="form-check">
					<input class="form-check-input" type="checkbox" value=""
						id="enchereOuvertes"> <label class="form-check-label"
						for="enchereOuvertes">Enchère ouvertes</label>
				</div>

				<div class="form-check">
					<input class="form-check-input" type="checkbox" value=""
						id="enchereEnCours" checked> <label
						class="form-check-label" for="enchereEnCours">Mes enchères en cours </label>
				</div>

				<div class="form-check">
					<input class="form-check-input" type="checkbox" value=""
						id="encheresRemportées" checked> <label
						class="form-check-label" for="encheresRemportées">Mes enchères remportées </label>
				</div>
			</div>
		</div>


		<div class="chexkBoxContainer">
			<div class="form-check">
				<input class="form-check-input" type="radio" name="mesVentes"
					id="mesVentes"> <label class="form-check-label"
					for="mesVentes">Mes ventes </label>
			</div>
			<div class="subContainerCB">

				<div class="form-check">
					<input class="form-check-input" type="checkbox" value=""
						id="mesVentesEnCours"> <label class="form-check-label"
						for="mesVentesEnCours">Mes ventes en cours</label>
				</div>

				<div class="form-check">
					<input class="form-check-input" type="checkbox" value=""
						id="mesVentesNonDebutees" checked> <label
						class="form-check-label" for="mesVentesNonDebutees">Mes vente non débutées</label>
				</div>

				<div class="form-check">
					<input class="form-check-input" type="checkbox" value=""
						id="ventesTerminees" checked> <label
						class="form-check-label" for="ventesTerminees">Ventes terminées</label>
				</div>
			</div>
		</div>
		</div>
	</c:if>

	<div class="mainHome zoomer">

		<c:if test="${!empty listEncheres}">
			<c:forEach var="enchere" items="${listEncheres}">
				<a href="${pageContext.request.contextPath}/DetailVente">
					<div class="articlesContainer">
						<div class="artContainer">
							<img class="imgArt" alt="image d'un article"
								 src="${pageContext.request.contextPath}/assets/chaise1.jpg">
							<div class="descArtContainer">
								<img class="pp" alt="photo de l'utilisateur"
									 src="${pageContext.request.contextPath}/assets/pp.jpg">
								<p class="artName">${enchere.article.nomArticle}</p>
								<p>Prix: ${enchere.montantEnchere}</p>
								<p>Fin de l'enchère : ${enchere.article.dateFinEncheres}</p>
								<p>vendeur : ${enchere.utilisateur.pseudo}</p>
							</div>
						</div>
					</div>
			</c:forEach>
		</c:if>
	</div>

</body>
</html>
