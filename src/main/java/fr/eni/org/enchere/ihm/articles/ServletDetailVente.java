package fr.eni.org.enchere.ihm.articles;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.org.enchere.bll.articles.ArticleManager;
import fr.eni.org.enchere.bll.categories.CategorieManager;
import fr.eni.org.enchere.bll.retraits.RetraitManager;
import fr.eni.org.enchere.bll.utilisateurs.UtilisateurManager;
import fr.eni.org.enchere.bo.Article;
import fr.eni.org.enchere.bo.Categorie;
import fr.eni.org.enchere.bo.Retrait;
import fr.eni.org.enchere.bo.Utilisateur;

/**
 * Servlet implementation class ServletDetailArticle
 */
@WebServlet("/DetailVente")
public class ServletDetailVente extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Recuperation info articles
		ArticleManager am = new ArticleManager();
		UtilisateurManager um = new UtilisateurManager();
		CategorieManager cm = new CategorieManager();
		Categorie categorie = new Categorie();
		RetraitManager rm = new RetraitManager();
		int idArticle =Integer.parseInt(request.getParameter("idArticle"));

		try {
			if(idArticle >0){
				Article article = am.selectById(idArticle);
				request.setAttribute("article", article);
				categorie = cm.selectById(article.getNoCategorie());
				request.setAttribute("categorie", categorie);
				Utilisateur utilisateur = um.selectUtilisateurById(article.getNoUtilisateur());
				request.setAttribute("utilisateur", utilisateur);
				Retrait retrait = rm.selectById(idArticle);
				request.setAttribute("retrait", retrait);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/pages/nouvelle_vente/detailVente.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// TODO Comment récupérer l'enchere ajoutée dans detailVente.jsp, je me pose la question, cela me turlupine	
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/pages/home.jsp");
		rd.forward(request, response); 
	}

}
