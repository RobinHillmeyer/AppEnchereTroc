<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>

<jsp:include page="../fragments/head.jsp" />

<body>

	<%@ include file="../fragments/header.jsp"%>

	<c:if test="${!empty listeCodesErreur}">
		<div class="alert alert-danger" role="alert">
			<strong>Erreur</strong>
			<ul>
				<c:forEach var="code" items="${listeCodesErreur}">
					<li><p>${LecteurMessage.getMessageErreur(code)}
						<p></li>
				</c:forEach>
			</ul>
		</div>
	</c:if>

	<main class="mainUp slideLeft">

		<form class="formUp" method="post" action="${pageContext.request.contextPath}/signIn">
		
			<div class="divContForm">
			
				<div class="subDivContForm">
					<label for="pseudo">Pseudo</label> <input type="text"
						class="inptForm form-control" id="pseudo" name="pseudo"
						placeholder="ex : Marcel16" required="required">
				</div>
				
				<div class="subDivContForm">
					<label for="nom">Nom</label> <input type="text"
						class="inptForm form-control" id="nom" name="nom"
						placeholder="ex : Marcel" required="required">
				</div>
				
			</div>

			<div class="divContForm">

				<div class="subDivContForm">
					<label for="prenom">Prénom</label> <input type="text"
						class="inptForm form-control" id="prenom" name="prenom"
						placeholder="ex : Lechat" required="required">
				</div>
				
				<div class="subDivContForm">
					<label for="email">Email</label> <input type="email"
						class="inptForm form-control" id="email" name="email"
						placeholder="ex toto@gmail.com" required="required">
				</div>

			</div>

			<div class="divContForm">

				<div class="subDivContForm">
					<label for="telephone">Téléphone</label> <input type="text"
						class="inptForm form-control" id="telephone" name="telephone"
						placeholder="ex :06 44 00 00 00" required="required">
				</div>
				
				<div class="subDivContForm">
					<label for="rue">Rue</label> <input type="text"
						class="inptForm form-control" id="rue" name="rue"
						placeholder="24 rue du grand puit" required="required">
				</div>

			</div>

			<div class="divContForm">

				<div class="subDivContForm">
					<label for="inpputCity">Code postal</label> <input type="text"
						class="inptForm form-control" id="cp" name="cp"
						placeholder="ex : 64600">
				</div>
				
				<div class="subDivContForm">
					<label for="ville">Ville</label> <input type="text"
						class="inptForm form-control" id="ville" name="ville"
						placeholder="Anglet" required="required">
				</div>

			</div>

			<div class="divContForm">

				<div class="subDivContForm">
					<label for="mdp">Mot de passe</label> <input type="password"
						class="inptForm form-control" id="mdp" name="mdp"
						required="required">
				</div>
				
				<div class="subDivContForm">
					<label for="confMdp">Confirmation</label> <input type="password"
						class="inptForm form-control" id="confMdp" name="confMdp"
						required="required">
				</div>

			</div>
			
			<div class="btnFormSignIn">
				<button type="submit" class="btn btn-primary col-md-3">Créer</button>
				<a class="btn btn-danger col-md-3"
					href="${pageContext.request.contextPath}/home">Annuler</a>
			</div>
		</form>
	</main>

</body>

</html>

