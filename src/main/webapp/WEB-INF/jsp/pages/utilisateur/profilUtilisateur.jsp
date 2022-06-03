<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>

<jsp:include page="../fragments/head.jsp" />

<body>

	<%@ include file="../fragments/header.jsp"%>
	<c:if test="${!empty listeCodesErreur}">
		<div class="alert alert-danger" role="alert">
			<strong>Erreur!</strong>
			<ul>
				<c:forEach var="code" items="${listeCodesErreur}">
					<li>${LecteurMessage.getMessageErreur(code)}</li>
				</c:forEach>
			</ul>
		</div>
	</c:if>

	<main class="mainUp slideLeft">

		<!--  buttons logs container with condition check if user is empty or not -->
		<c:if test="${!empty user}">
			<img class="ppPr" alt="" src="${pageContext.request.contextPath}/assets/pp.jpg">

			<form class="formUp" method="post"
				action="${pageContext.request.contextPath}/ProfilUtilisateur">
				<input type="hidden" name="userId" value="${user.noUtilisateur}" />

				<div class="divContForm">
					<div class="subDivContForm">
						<label for="pseudo">Pseudo</label> <input type="text"
							class="inptForm form-control" id="pseudo"
							placeholder="${user.pseudo}" value="${user.pseudo }"
							name="pseudo">
					</div>
				
					<div class="subDivContForm">
						<label for="nom">Nom</label> <input type="text"
							class="inptForm form-control" id="nom" name="nom"
							placeholder="${user.nom}" value="${user.nom}">
					</div>
				</div>

				<div class="divContForm">

					<div class="subDivContForm">
						<label for="prenom">Prénom</label> <input type="text"
							class="inptForm form-control" id="prenom" name="prenom"
							placeholder="${user.prenom}" value="${user.prenom}">
					</div>
					<div class="subDivContForm">
						<label for="email">Email</label> <input type="email"
							class="inptForm form-control" id="email" name="email"
							placeholder="${user.email}" value="${user.email}">
					</div>

				</div>

				<div class="divContForm">

					<div class="subDivContForm">
						<label for="telephone">Téléphone</label> <input type="number"
							class="inptForm form-control" id="telephone" name="telephone"
							placeholder="${user.telephone}" value="${user.telephone}">
					</div>
					<div class="subDivContForm">
						<label for="rue">rue</label> <input type="text"
							class="inptForm form-control" id="rue" name="rue"
							placeholder="${user.rue}" value="${user.rue}">
					</div>

				</div>

				<div class="divContForm">

					<div class="subDivContForm">
						<label for="cp">Code postal</label> <input type="text"
							class="inptForm form-control" id="cp" name="cp"
							placeholder="${user.codePostal}" value="${user.codePostal}">
					</div>
					<div class="subDivContForm">
						<label for="ville">Ville</label> <input type="text"
							class="inptForm form-control" id="ville" name="ville"
							placeholder="${user.ville}" value="${user.ville}">
					</div>

				</div>

				<div class="divContForm">
					<div class="subDivContForm">
						<label for="mdp">Mot de passe actuel</label> <input
							type="password" class="inptForm form-control" id="holdmdp"
							name="holdmdp">
					</div>
					<div class="subDivContForm">
						<label for="mdp">Nouveau mot de passe</label> <input
							type="password" class="inptForm form-control" id="mdp" name="mdp"
							value="${user.motDePasse }">
					</div>

				</div>
				<div class="btnConf">
					<label for="confMdp">Confirmation</label> <input type="password"
						class="inptForm form-control" id="confMdp" name="confMdp">
				</div>
				<div class="btnConf">
					<p>Crédit :
					<p>
					<p class="creditTxt">${user.credit}</p>
				</div>
				<div class="btnFormSignIn">

					<a href="#"
						onclick="document.getElementById('modalUpdate').style.display='block'"
						class="btn btn-primary col-md-3" style="width: auto;">Modifier</a>

					<div id="modalUpdate" class="modal slideDown">
						<div class=divCOntModal>
							<p class="txtModal">Vous êtes sur le point de modifier votre
								profil</p>
							<div class="btnModalCont">
								<span
									onclick="document.getElementById('modalUpdate').style.display='none'"
									class="btn btn-primary col-md-3" title="Close Modal">Annuler</span>
								<button type="submit" class="btn btn-danger">Modifier</button>
							</div>
						</div>
					</div>
				</div>
			</form>

			<a href="#"
				onclick="document.getElementById('modalDelete').style.display='block'"
				class="btn btn-danger" style="width: auto;">Supprimer votre
				compte</a>

			<div id="modalDelete" class="modal slideDown">
				<div class=divCOntModal>


					<p>Vous êtes sur le point de supprimer votre profil</p>

					<div class="btnModalCont">
						<span
							onclick="document.getElementById('modalDelete').style.display='none'"
							class="btn btn-primary col-md-3" title="Close Modal">Annuler</span>

						<form method="post"
							action="${pageContext.request.contextPath}/SupprimerUtilisateur">
							<button type="submit" class="btn btn-danger">Supprimer</button>
							<input type="hidden" name="userId" value="${user.noUtilisateur}" />
						</form>
					</div>
				</div>

			</div>




		</c:if>
		<c:if test="${empty user}">
			<h3>Une erreur est survenue</h3>
		</c:if>


	</main>


</body>

</html>

