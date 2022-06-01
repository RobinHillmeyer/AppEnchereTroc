package fr.eni.org.enchere.ihm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.org.enchere.BusinessException;
import fr.eni.org.enchere.bll.articles.ArticleManager;
import fr.eni.org.enchere.bll.categories.CategorieManager;
import fr.eni.org.enchere.bll.encheres.EnchereManager;
import fr.eni.org.enchere.bo.Article;
import fr.eni.org.enchere.bo.Categorie;
import fr.eni.org.enchere.bo.Enchere;


@WebServlet("/home")
public class ServletHome extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Version Quentin
		EnchereManager enchmng = new EnchereManager();
		List<Enchere> listEncheres = new ArrayList<>();

		try {
			listEncheres = enchmng.selectAllEnchere();
			request.setAttribute("listEncheres",listEncheres);
			for (Enchere enchere : listEncheres) {
				System.out.println(enchere.getArticle().getNomArticle());
			}
		} catch (BusinessException e) {
			throw new RuntimeException(e);
		}
		
		
		//Version Lucas&Robin
		CategorieManager cm = new CategorieManager();

		try {
			List<Categorie> listeCategorie = cm.selectAll();
			request.setAttribute("listeCategorie", listeCategorie);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ArticleManager am = new ArticleManager();

		try {
			List<Article> listeArticle = am.selectAll();
			request.setAttribute("listeArticle", listeArticle);
		} catch (Exception e) {
			e.printStackTrace();
		}


		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/pages/home.jsp");
		rd.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}
