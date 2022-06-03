<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>

<jsp:include page="../pages/fragments/head.jsp" />

<body class="bodyAdmin">
<%@ include file="../admin/fragmentAdmin/headerAdmin.jsp"%>
<div class="linkSignAdmin"></div>

<%--
<input type="hidden" name="userId" value="${user.noUtilisateur}" />
<button type="submit" class="btn btn-outline-primary" name="addMoney" value="addMoney">Recharger un compte client</button>
<button type="submit" class="btn btn-outline-primary" name="addCat" value="addCat">Ajouter une catégorie </button>
<button type="submit" class="btn btn-outline-danger" name="delUser" value="delUser">Supprimer un utilisateur</button>
<button type="submit" class="btn btn-outline-danger" name="delAuction" value="delAuction">Supprimer une enchère </button>
--%>
<div class="mainAdmin ">

    <form class="formAdmin slideUp" method="post" action="${pageContext.request.contextPath}/Admin">
        <h2 class="txtAdm">Recharger le crédit d'un compte utilisateur</h2>

        <div class="actionContainer">
            <div class="inptSlctAdm input-group mt-4">
                <label id="selectUserAdmin" class="form-label" for="selectUserAdmin">Recharger le credit du compte :</label>
                <select class="form-control col-md-2 " name="selectUserAdmin" aria-label=".form-select-lg example">
                    <option selected value="0">-- Selectionnez un Utilisateur --</option>
                    <c:if test="${!empty listUsers}">
                        <c:forEach var="lu" items="${listUsers}">
                            <option value="${lu.noUtilisateur}">${lu.email}</option>
                        </c:forEach>
                    </c:if>
                </select>
                <label id="selectCatAdmin" class="form-label" for="selectCatAdmin">
                    Crédit :</label>
                <input type="text" class="form-control col-md-3 "
                       value="0"
                       placeholder="ex : 1600" aria-label="Search"
                       aria-describedby="search-addon" name="newCredit" />
                <button type="submit" class="btn btn-outline-primary">créditer</button>
            </div>
        </div>

         <h2  class="txtAdm">Créer une nouvelle Catégorie</h2>
         <div class="actionContainer">

            <div class="inptSlctAdm input-group mt-4">
                  <label id="newCat" class="form-label" for="newCat">
               nom de la nouvelle catégorie :</label>
                 <input type="text" class="form-control col-md-3 "
                   placeholder="ex : 1600" aria-label="Search"
                   aria-describedby="search-addon" name="newCat"
                 value=""/>
               <button type="submit" class="btn btn-outline-primary">ajouter</button>
             </div>
        </div>


        <h2  class="txtAdm">Supprimer une Enchere</h2>
        <div class="actionContainer">
        <div class="inptSlctAdm input-group mt-4">
                <label id="selectEnchere" class="form-label" for="selectCatAdmin">
                    Encheres :</label>
                <select class="form-control col-md-2 " name="selectEnchere" aria-label=".form-select-lg example">
                <option selected value="0">-- Selectionner une enchere --</option>
                    <c:if test="${!empty listEncheres}">
                        <c:forEach var="liE" items="${listEncheres}">
                            <option value="${liE.article.id}">${liE.article.nomArticle}</option>
                        </c:forEach>
                    </c:if>
             </select>
            <button type="submit" class="btn btn-outline-danger" name="delAuction" value="delAuction">Supprimer une enchère</button>

        </div>

     </div>

    </form>

</div>


</body>