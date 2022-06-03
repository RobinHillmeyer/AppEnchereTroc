package fr.eni.org.enchere.ihm.admin;

import fr.eni.org.enchere.BusinessException;
import fr.eni.org.enchere.bll.articles.ArticleManager;
import fr.eni.org.enchere.bll.categories.CategorieManager;
import fr.eni.org.enchere.bll.encheres.EnchereManager;
import fr.eni.org.enchere.bll.utilisateurs.UtilisateurManager;
import fr.eni.org.enchere.bo.Categorie;
import fr.eni.org.enchere.bo.Enchere;
import fr.eni.org.enchere.bo.Utilisateur;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/Admin")
public class Admin extends HttpServlet {
    private EnchereManager em = new EnchereManager();
    private CategorieManager cm = new CategorieManager();
    private UtilisateurManager um = new UtilisateurManager();

    private ArticleManager am = new ArticleManager();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Utilisateur> listUsers = new ArrayList<>();
        List<Categorie> listCats = new ArrayList<>();
        List<Enchere> listEncheres = new ArrayList<>();
        int idListeCourse = 0;
        List<Integer> listeCodesErreur = new ArrayList<>();

        if (listeCodesErreur.size() > 0) {
            request.setAttribute("listeCodesErreur", listeCodesErreur);
        } else {
            try {
                listUsers = this.um.selectAllUtilisateurs();
                listCats = this.cm.selectAll();
                listEncheres = this.em.selectAllEnchere();

                request.setAttribute("listUsers", listUsers);
                request.setAttribute("listCats", listCats);
                request.setAttribute("listEncheres", listEncheres);

            } catch (BusinessException e) {
                e.printStackTrace();
                request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
            }
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/admin/admin.jsp");
            rd.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        List<Integer> listeCodesErreur = new ArrayList<>();
        int idListeCourse = 0;

        try {

            if (request.getParameter("selectUserAdmin") != null && request.getParameter("newCredit") != null
            && Integer.parseInt(request.getParameter("selectUserAdmin"))  != 0 &&  Integer.parseInt(request.getParameter("newCredit")) != 0) {
                int idCompteACrediter = Integer.parseInt(request.getParameter("selectUserAdmin"));
                int newCredit = Integer.parseInt(request.getParameter("newCredit"));
                if (idCompteACrediter > 0 && newCredit > 0) {
                    int holdCredit = this.um.selectUtilisateurById(idCompteACrediter).getCredit();
                    int credit = newCredit + holdCredit;
                    this.um.updateCreditUser(idCompteACrediter, credit);
                }
            }

            if (request.getParameter("newCat") != null) {
                String newCat = request.getParameter("newCat");
                if (!newCat.isEmpty()) {
                    this.cm.ajouterCategorie(newCat);
                }
                if (request.getParameter("selectEnchere") != null) {
                    int idArticleEnchere = Integer.parseInt(request.getParameter("selectEnchere"));
                    if (idArticleEnchere > 0) {
                        this.em.deleteEnchere(idArticleEnchere);
                    }
                }

            }
        } catch (BusinessException e) {
            throw new RuntimeException(e);
        } finally {
            response.sendRedirect("Admin");
        }
    }
}
