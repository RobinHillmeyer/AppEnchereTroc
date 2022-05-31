package fr.eni.org.enchere.ihm.articles;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.org.enchere.bll.articles.ArticleManager;
import fr.eni.org.enchere.bll.utilisateurs.UtilisateurManager;
import fr.eni.org.enchere.bo.Article;
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
		int idArticle = 5;
		
		try {
			Article article = am.selectById(idArticle);
			request.setAttribute("article", article);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Recuperation info vendeur
		UtilisateurManager um = new UtilisateurManager();
		int idVendeur = 2;
		
		try {
			Utilisateur utilisateur = um.selectUtilisateurById(idVendeur);
			request.setAttribute("utilisateur", utilisateur);
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
