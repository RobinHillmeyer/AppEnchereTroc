package fr.eni.org.enchere.ihm.articles;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.org.enchere.BusinessException;
import fr.eni.org.enchere.bll.articles.ArticleManager;
import fr.eni.org.enchere.bll.categories.CategorieManager;
import fr.eni.org.enchere.bll.encheres.EnchereManager;
import fr.eni.org.enchere.bll.utilisateurs.UtilisateurManager;
import fr.eni.org.enchere.bo.Article;
import fr.eni.org.enchere.bo.Categorie;
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
		request.setCharacterEncoding("UTF-8");

		// Recuperation info articles
		ArticleManager am = new ArticleManager();
		UtilisateurManager um = new UtilisateurManager();
		CategorieManager cm = new CategorieManager();
		Categorie categorie = new Categorie();
		int idArticle =Integer.parseInt(request.getParameter("idArticle"));

		try {
			if(idArticle >0){
				Article article = am.selectById(idArticle);
				request.setAttribute("article", article);
				categorie = cm.selectById(article.getNoCategorie());
				request.setAttribute("categorie", categorie);
				Utilisateur utilisateur = um.selectUtilisateurById(article.getNoUtilisateur());
				request.setAttribute("utilisateur", utilisateur);
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
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		EnchereManager em = new EnchereManager();

		try {
			int idArticle = Integer.parseInt(request.getParameter("idArticle"));
			int montantEnchere = Integer.parseInt(request.getParameter("proposition"));
			int idUser = (int) session.getAttribute("userIdSessionAttr");
			int userCredit = (int) session.getAttribute("userCredit");

			// on met à jour le crédit de l'utilisateur.
			int newUserCredit = userCredit - montantEnchere;
			int idUserOldWin = em.selectById(idArticle).getIdUtilisateurWin();
		  	session.setAttribute("userCredit",newUserCredit);
			UtilisateurManager um = new UtilisateurManager();
			um.updateCreditUser(idUser,newUserCredit);


			em.update(montantEnchere,idUser,idArticle);
			System.out.println("On à passé l'update dans la servlet" );
		} catch (Exception e) {
			request.setAttribute("listeCodesErreur", e);
			e.printStackTrace();
		}finally {
			response.sendRedirect("home");
		}
	}

}
