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
import fr.eni.org.enchere.bll.encheres.EnchereManager;
import fr.eni.org.enchere.bll.retraits.RetraitManager;
import fr.eni.org.enchere.bll.utilisateurs.UtilisateurManager;
import fr.eni.org.enchere.bo.Article;
import fr.eni.org.enchere.bo.Categorie;
import fr.eni.org.enchere.bo.Enchere;
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
		CategorieManager cm = new CategorieManager();
		RetraitManager rm = new RetraitManager();
		int idArticle = Integer.parseInt(request.getParameter("idArticle"));
		System.out.println("On a bien cast l'id de l'article bravo" + idArticle);
		
		try {
			Article article = am.selectById(idArticle);
			Categorie cat = cm.selectById(article.getNoCategorie());
			Retrait retrait = rm.selectById(idArticle);
			request.setAttribute("article", article);
			request.setAttribute("categorie", cat);
			request.setAttribute("retrait", retrait);
		} catch (Exception e) {
			System.out.println("Erreur sur la catégorie");
			e.printStackTrace();
		}
		
		// Recuperation info vendeur
		UtilisateurManager um = new UtilisateurManager();
		int idVendeur = 3;
		
		try {
			Utilisateur utilisateur = um.selectUtilisateurById(idVendeur);
			request.setAttribute("utilisateur", utilisateur);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Recuperation info enchere
		EnchereManager enchmng = new EnchereManager();
		
		try {
			Enchere enchere = enchmng.selectById(idArticle);
			request.setAttribute("enchere", enchere);
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
		this.doGet(request, response);
	}

}
