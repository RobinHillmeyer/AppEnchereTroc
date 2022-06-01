<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<jsp:include page="../fragments/head.jsp" />
<body>
	<%@ include file="../fragments/header.jsp"%>

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

	<main class="mainUp slideRight">

		<form form method="post"
			action="${pageContext.request.contextPath}/signUp" class="formSignUp">
			<div class="form-group row">
				<label for="identifiant" class="col-sm-2 col-form-label">Identifiant</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="identifiant"
						name="identifiant" placeholder="ex : Marcel" required="required">
				</div>
			</div>
			<div class="form-group row">
				<label for="mdp" class="col-sm-2 col-form-label">Mot de
					passe</label>
				<div class="col-sm-10">
					<input type="password" class="form-control" id="mdp" name="mdp"
						placeholder="entrez votre mot de passe" required="required">
				</div>
			</div>

			<div class="form-row sbContBtn">
				<button type="submit" class="btn btn-primary">Connexion</button>

				<div class="form-group">
					<div class="form-check">
						<input type="checkbox" class="form-check-input" id="rememberMe">
						<label class="form-check-label" for="rememberMe" name="rememberMe">Se
							souvenir de moi</label>
					</div>
					<a href="">Mot de passe oublié ?</a>
				</div>

			</div>

		</form>
		<a href="${pageContext.request.contextPath}/signIn"
			class="btnCNC btn-primary ">Créer un compte</a>
	</main>
	<%@ include file="../fragments/script.html"%>

</body>
</html>