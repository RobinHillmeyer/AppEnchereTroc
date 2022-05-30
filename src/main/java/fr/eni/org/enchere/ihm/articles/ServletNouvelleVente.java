package fr.eni.org.enchere.ihm.articles;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.org.enchere.bll.articles.ArticleManager;
import fr.eni.org.enchere.bll.categories.CategorieManager;
import fr.eni.org.enchere.bll.retraits.RetraitManager;
import fr.eni.org.enchere.bll.utilisateurs.UtilisateurManager;
import fr.eni.org.enchere.bo.Article;
import fr.eni.org.enchere.bo.Categorie;
import fr.eni.org.enchere.bo.Retrait;

/**
 * Servlet implementation class ServletNouvelArticle
 */
@WebServlet("/NouvelleVente")
public class ServletNouvelleVente extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Afficher la liste des cat�gories
		CategorieManager cm = new CategorieManager();

		try {
			List<Categorie> listeCategorie = cm.selectAll();
			request.setAttribute("listeCategorie", listeCategorie);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Afficher l'adresse utilisateur en tant qu'adresse par défaut de retrait 
		UtilisateurManager um = new UtilisateurManager();
		
		try {
			String rue = um.selectUtilisateurById(2).getRue();
			String codePostal = um.selectUtilisateurById(2).getCodePostal();
			String ville = um.selectUtilisateurById(2).getVille();

			request.setAttribute("rue", rue);
			request.setAttribute("codePostal", codePostal);
			request.setAttribute("ville", ville);
		} catch (Exception e) {
			e.printStackTrace();
		}





		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/pages/nouvelle_vente/nouvelleVente.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nomArticle;
		String description;
		LocalDate debutEnchere;
		LocalDate finEnchere;
		int miseAPrix;
		int noCategorie;
		Article  artcl = new Article();
		String rue;
		String codePostal;
		String ville;
		int idArticle = 0;

		// r�cup�ration de l'id Utilisateur par la session .
		HttpSession session = request.getSession();
		int idUser =(int)request.getSession().getAttribute("userIdSessionAttr");
		System.out.println(idUser+" idUser depuis nouvelle vente");

		try {
			nomArticle = request.getParameter("nom_article");
			description = request.getParameter("description");
			debutEnchere = LocalDate.parse(request.getParameter("date_debut"));
			finEnchere = LocalDate.parse(request.getParameter("date_fin"));
			miseAPrix = Integer.parseInt(request.getParameter("prix"));
			noCategorie = Integer.parseInt(request.getParameter("categorie"));

			ArticleManager articleManager = new ArticleManager();
			artcl = articleManager.ajouterArticle(nomArticle, description, debutEnchere, finEnchere, miseAPrix, noCategorie,idUser);


			request.setAttribute("article", artcl);

			System.out.println("idArcticle dans servlet l79 "+artcl.getId());

			idArticle = artcl.getId();


		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			rue = request.getParameter("rue");
			codePostal = request.getParameter("code_postal");
			ville = request.getParameter("ville");

			RetraitManager retraitManager = new RetraitManager();
			Retrait retrait = retraitManager.ajouterRetrait( rue, codePostal, ville, idArticle);
			request.setAttribute("retrait", retrait);

		} catch (Exception e) {
			e.printStackTrace();
		}

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/pages/nouvelle_vente/detailVente.jsp");
		rd.forward(request, response);
	}

}