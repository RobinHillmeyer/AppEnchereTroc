<%--
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

<!--			/////////			HEADER				/////////			-->
	<%@ include file="./fragments/header.jsp"%>
<!--			/////////			END HEADER				/////////			-->

<!--			/////////			LIENS DE NAVIGATION				/////////			-->

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
				class="linkSignIn">Profile</a>
		<a href="${pageContext.request.contextPath}/signOut"
				class="linkSignIn">Déconnexion</a>
			
		</c:if>
	</div>
<!--			/////////		END	LIENS DE NAVIGATION				/////////			-->


<!--			/////////			FILTRE PAR SAISIE USER et DEBUT DU FORM FILTER				/////////			-->

		<form class="filtersContainer"  method="post"
				  action="${pageContext.request.contextPath}/home">
			<div class="input-group">
				<input type="search" class="form-control col-md-3 "
					placeholder="Le nom de l'article contient ?" aria-label="Search"
					aria-describedby="search-addon" name="filter" />
				<button type="submit" class="btn btn-outline-primary">
					<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16"
						fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
					 <path
							d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z" />
					</svg>
				</button>
			</div>
	<!--			/////////			END FILTRE PAR SAISIE USER				/////////			-->


	<!--			/////////			FILTRE PAR CATEGORIES 				/////////			-->

			<div class="inptSlct input-group mt-4">
				<label id="selectCat" class="form-label" for="selectCat">Catégorie :</label> <select class="form-control col-md-2 " name="selectCat"
					aria-label=".form-select-lg example">
					<option selected value="0">-- Choisissez une catégorie --</option>
					<c:if test="${!empty listeCategorie}">
						<c:forEach var="lc" items="${listeCategorie}">
							<option value="${lc.noCategorie}">${lc.libelle}</option>
						</c:forEach>
					</c:if>
				</select>
			</div>
	<!--			/////////			END FILTRE PAR CATEGORIES 			/////////			-->

		<c:if test="${!empty userIdSessionAttr}">

	<!--			/////////			DEBUT FORM VENTE OU ACHATS			/////////			-->


			<div class="containerFilterCo">

				<div class="chexkBoxContainer">

					<div class="form-check">
						<input class="form-check-input" type="radio" name="achat" value="achat" onClick="handleDisableVente();" id="achat">
						<label class="form-check-label" for="achat">Achats</label>
					</div>

					<div class="subContainerCB">
						<div class="form-check">
							<input class="form-check-input" type="checkbox" value="enchereOuvertes" name="enchereOuvertes"
								id="enchereOuvertes"> <label class="form-check-label"
								for="enchereOuvertes">enchère ouvertes</label>
						</div>
						<div class="form-check">
							<input class="form-check-input" type="checkbox" value="enchereEnCours" name="enchereEnCours"
								id="enchereEnCours" > <label
								class="form-check-label" for="enchereEnCours">mes enchères en cours </label>
						</div>
						<div class="form-check">
							<input class="form-check-input" type="checkbox" value="encheresRemportées" name="encheresRemportées"
								id="encheresRemportées" > <label
								class="form-check-label" for="encheresRemportées">mes enchères remportées </label>
						</div>
					</div>

				</div>

				<div class="chexkBoxContainer">
					<div class="form-check">
						<input class="form-check-input" type="radio" name="mesVentes" value="mesVentes"
							   onClick="handleDisableAchat();"
							   id="mesVentes"> <label class="form-check-label"
							   for="mesVentes">Mes ventes</label>
					</div>

					<div class="subContainerCB">
						<div class="form-check">
							<input class="form-check-input" type="checkbox" name="mesVentesEnCours" value="mesVentesEnCours"
								id="mesVentesEnCours"> <label class="form-check-label"
								for="mesVentesEnCours">Mes ventes en cours</label>
						</div>
						<div class="form-check">
							<input class="form-check-input" type="checkbox" value="mesVentesNonDebutees" name="mesVentesNonDebutees"
								id="mesVentesNonDebutees" > <label
								class="form-check-label" for="mesVentesNonDebutees">Mes ventes non débutées</label>
						</div>
						<div class="form-check">
							<input class="form-check-input" type="checkbox" value="ventesTerminees" name="ventesTerminees"
								id="ventesTerminees"> <label
								class="form-check-label" for="ventesTerminees">Ventes terminées</label>
						</div>

					</div>

				</div>

			</div>

		</form>
<!--			/////////			END FORM VENTE OU ACHATS et FIN FORM FILTER			/////////			-->

	</c:if>

<!--			/////////			CARDS ENCHERE			/////////			-->
	<div class="mainHome zoomer">
		<c:if test="${!empty listEncheres}">
			<c:forEach var="enchere" items="${listEncheres}">
				<a class="cardLink" href="${pageContext.request.contextPath}/DetailVente">
					<div class="articlesContainer">
						<div class="artContainer">
							<img class="imgArt" alt="image d'un article"
								src="${pageContext.request.contextPath}/assets/chaise1.jpg">
							<div class="descArtContainer">
								<img class="pp" alt="photo de l'utilisateur"
									src="${pageContext.request.contextPath}/assets/pp.jpg">
								<p class="artName">${enchere.article.nomArticle}</p>
								<p>Prix: ${enchere.montantEnchere}</p>
								<p>fin de l'enchère : ${enchere.dateEnchere}</p>
								<p>vendeur : ${enchere.utilisateur.pseudo}</p>
							</div>
						</div>
					</div>
				</a>
			</c:forEach>
		</c:if>
	</div>
<!--			/////////			END CARDS ENCHERE			/////////			-->






</body>
</html>
--%>


<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

<!--			/////////			HEADER				/////////			-->
<%@ include file="./fragments/header.jsp"%>
<!--			/////////			END HEADER				/////////			-->

<!--			/////////			LIENS DE NAVIGATION				/////////			-->

<div class="linkSign">
	<c:if test="${empty userIdSessionAttr}">
		<a href="${pageContext.request.contextPath}/signIn"
		   class="linkSignIn">S'inscrire</a>
		<a href="${pageContext.request.contextPath}/signUp"
		   class="linkSignIn">Se connecter</a>
	</c:if>
	<c:if test="${!empty userIdSessionAttr}">
		<a href="${pageContext.request.contextPath}/ProfilUtilisateur"
		   class="linkSignIn">Enchères</a>
		<a href="${pageContext.request.contextPath}/NouvelleVente"
		   class="linkSignIn">Vendre un article</a>
		<a href="${pageContext.request.contextPath}/ProfilUtilisateur"
		   class="linkSignIn">Profil</a>
		<a href="${pageContext.request.contextPath}/signOut"
		   class="linkSignIn">Déconnexion</a>

	</c:if>
</div>
<!--			/////////		END	LIENS DE NAVIGATION				/////////			-->


<!--			/////////			FILTRE PAR SAISIE USER et DEBUT DU FORM FILTER				/////////			-->

<form class="filtersContainer"  method="post"
	  action="${pageContext.request.contextPath}/home">
	<div class="input-group">
		<input type="search" class="form-control col-md-3 "
			   placeholder="Le nom de l'article contient ?" aria-label="Search"
			   aria-describedby="search-addon" name="filter" />
		<button type="submit" class="btn btn-outline-primary">
			<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16"
				 fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
				<path
						d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z" />
			</svg>
		</button>
	</div>
	<!--			/////////			END FILTRE PAR SAISIE USER				/////////			-->


	<!--			/////////			FILTRE PAR CATEGORIES 				/////////			-->

	<div class="inptSlct input-group mt-4">
		<label id="selectCat" class="form-label" for="selectCat">Catégorie :</label> <select class="form-control col-md-2 " name="selectCat"
																							 aria-label=".form-select-lg example">
		<option selected value="0">-- Choisissez une catégorie --</option>
		<c:if test="${!empty listeCategorie}">
			<c:forEach var="lc" items="${listeCategorie}">
				<option value="${lc.noCategorie}">${lc.libelle}</option>
			</c:forEach>
		</c:if>
	</select>
	</div>
	<!--			/////////			END FILTRE PAR CATEGORIES 			/////////			-->

	<c:if test="${!empty userIdSessionAttr}">

	<!--			/////////			DEBUT FORM VENTE OU ACHATS			/////////			-->


	<div class="containerFilterCo">

		<div class="chexkBoxContainer">

			<div class="form-check">
				<input class="form-check-input" type="radio" name="achatVente" value="achat" onClick="handleDisableVente();" id="achat">
				<label class="form-check-label" for="achat">Achats</label>
			</div>

			<div class="subContainerCB">
				<div class="form-check">
					<input class="form-check-input" type="checkbox" value="enchereOuvertes" name="enchereOuvertes"
						   id="enchereOuvertes"> <label class="form-check-label"
														for="enchereOuvertes">Enchères ouvertes</label>
				</div>
				<div class="form-check">
					<input class="form-check-input" type="checkbox" value="enchereEnCours" name="enchereEnCours"
						   id="enchereEnCours" > <label
						class="form-check-label" for="enchereEnCours">Mes enchères en cours </label>
				</div>
				<div class="form-check">
					<input class="form-check-input" type="checkbox" value="encheresRemportées" name="encheresRemportées"
						   id="encheresRemportées" > <label
						class="form-check-label" for="encheresRemportées">Mes enchères remportées </label>
				</div>
			</div>

		</div>

		<div class="chexkBoxContainer">
			<div class="form-check">
				<input class="form-check-input" type="radio" name="achatVente" value="mesVentes"
					   onClick="handleDisableAchat();"
					   id="mesVentes"> <label class="form-check-label"
											  for="mesVentes">Mes ventes</label>
			</div>

			<div class="subContainerCB">
				<div class="form-check">
					<input class="form-check-input" type="checkbox" name="mesVentesEnCours" value="mesVentesEnCours"
						   id="mesVentesEnCours"> <label class="form-check-label"
														 for="mesVentesEnCours">Mes ventes en cours</label>
				</div>
				<div class="form-check">
					<input class="form-check-input" type="checkbox" value="mesVentesNonDebutees" name="mesVentesNonDebutees"
						   id="mesVentesNonDebutees" > <label
						class="form-check-label" for="mesVentesNonDebutees">Mes ventes non débutées</label>
				</div>
				<div class="form-check">
					<input class="form-check-input" type="checkbox" value="ventesTerminees" name="ventesTerminees"
						   id="ventesTerminees"> <label
						class="form-check-label" for="ventesTerminees">Ventes terminées</label>
				</div>

			</div>

		</div>

	</div>

</form>
<!--			/////////			END FORM VENTE OU ACHATS et FIN FORM FILTER			/////////			-->

</c:if>

<!--			/////////			CARDS ENCHERE			/////////			-->
<div class="mainHome zoomer">
	<c:if test="${!empty listEncheres}">
		<c:forEach var="enchere" items="${listEncheres}">
			<a class="cardLink" href="${pageContext.request.contextPath}/DetailVente?idArticle=${enchere.article.id}"> 
				<div class="articlesContainer">
					<div class="artContainer">
						<img class="imgArt" alt="image d'un article"
							 src="${pageContext.request.contextPath}/assets/chaise1.jpg">
						<div class="descArtContainer">
							<img class="pp" alt="photo de l'utilisateur"
								 src="${pageContext.request.contextPath}/assets/pp.jpg">
							<p class="artName">${enchere.article.nomArticle}</p>
							<p>Prix: ${enchere.montantEnchere}</p>
							<p>Fin de l'enchère : ${enchere.dateEnchere}</p>
							<p>Vendeur : ${enchere.utilisateur.pseudo}</p>
						</div>
					</div>
				</div>
			</a>
		</c:forEach>
	</c:if>
</div>
<!--			/////////			END CARDS ENCHERE			/////////			-->






</body>
</html>