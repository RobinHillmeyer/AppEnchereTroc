package fr.eni.org.enchere.ihm;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
import fr.eni.org.enchere.bo.Enchere;
import fr.eni.org.enchere.bo.Utilisateur;


@WebServlet( "/home")
public class ServletHome extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EnchereManager  enchmng = new EnchereManager();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		List<Enchere> listEncheres = new ArrayList<>();
		CategorieManager cm = new CategorieManager();

		try {
			List<Categorie> listeCategorie = cm.selectAll();
			request.setAttribute("listeCategorie", listeCategorie);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			listEncheres = enchmng.selectAllEnchere();
			createNewEnchere(listEncheres);

			request.setAttribute("listEncheres",listEncheres);

		} catch (BusinessException e) {
			System.out.println("ERREUR DANS LE GET"+e );
			throw new RuntimeException(e);
		}


		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/pages/home.jsp");
		rd.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BusinessException businessException =new BusinessException();
		HttpSession session = request.getSession();
		List<Integer> listeCodesErreur = new ArrayList<>();
		List<Enchere> encheres = new ArrayList<>();
		List<Enchere> listEncheres = new ArrayList<>();
		CategorieManager cm = new CategorieManager();

		String idCat = request.getParameter("selectCat");
		String txtFilter    = request.getParameter("filter");
		String paramAchat = request.getParameter("achat");
		String paramMesVente = request.getParameter("mesVentes");
		System.out.println("paramAchat  " +paramAchat);
		System.out.println("paramMesVente  " +paramMesVente);

		try {

			List<Categorie> listeCategorie = cm.selectAll();
			request.setAttribute("listeCategorie", listeCategorie);

			encheres = enchmng.selectAllEnchere();
			int idCategorie = Integer.parseInt(idCat);

			if (idCategorie > 0){

				encheres = enchmng.selectByCategories(idCategorie);
			}
			for (Enchere enchere: encheres) {
				if(enchere.getArticle().getNomArticle().toLowerCase().contains(txtFilter.toLowerCase().trim()) || enchere.getArticle().getDescription().toLowerCase().contains(txtFilter.toLowerCase().trim()) ){
					listEncheres.add(enchere);
				}
			}
			if (session.getAttribute("userIdSessionAttr") != null) {
				int idUser = (int) session.getAttribute("userIdSessionAttr");
				if(paramAchat!=null){
					String isEnchereOuverte    = request.getParameter("enchereOuvertes");
					String isEncheresEnCours    = request.getParameter("enchereEnCours");
					String isEncheresRemportees    = request.getParameter("encheresRemportées");
					if (isEnchereOuverte !=null){
						listEncheres =  enchmng.selectEnchereOuverte();
					} else if (isEncheresEnCours != null) {
						listEncheres = enchmng.selectEncheresEnCours(idUser);
					}
					else if(isEncheresRemportees != null){
						listEncheres = enchmng.selectEncheresRemportees(idUser);
					}
				} else if (paramMesVente != null) {
					String isEnchereOuverte    = request.getParameter("mesVentesEnCours");
					String isEncheresEnCours    = request.getParameter("mesVentesNonDebutees");
					String isEncheresRemportees    = request.getParameter("ventesTerminees");
				}
			}

		} catch (BusinessException e) {
			System.out.println("ERREUR HAHA : "+e);
			e.printStackTrace();
		}
		request.setAttribute("listEncheres",listEncheres);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/pages/home.jsp");
		rd.forward(request, response);

	}

	public void createNewEnchere(List<Enchere> listeEnchere) throws BusinessException {

		BusinessException businessException = new BusinessException();
		List<Article> listeArticle = new ArrayList<>();
		ArticleManager articleManager = new ArticleManager();
		EnchereManager enchereManager = new EnchereManager();
		UtilisateurManager utilisateurManager = new UtilisateurManager();
		Enchere enchere = new Enchere();

		try {
			listeArticle = articleManager.selectAll();
			// on boucle sur tout les articles
			for (Article art: listeArticle) {
				// on test si un article est à la date du jour
				if (Objects.equals(art.getDateDebutEncheres(), LocalDate.now())){

					// Si l'article est à la date on test que l'enchere ne soit pas déja crée
					enchere = enchereManager.selectById(art.getId());

					if (enchere.getArticle().getId() == 0){

						Utilisateur user = new Utilisateur();
						try {
							user = utilisateurManager.selectUtilisateurById(art.getNoUtilisateur());
							enchere.setDateEnchere(art.getDateFinEncheres());
							enchere.setMontantEnchere(art.getMiseAPrix());
							enchere.setUtilisateur(user);
							enchere.setArticle(art);

							enchereManager.insertEnchere(enchere);
						}catch (BusinessException e){
							System.out.println("ERREUR HAHA  dans creer une enchere: "+e);

							throw businessException;
						}
					}

				}else{
				}

			}
		}catch (BusinessException e) {
			throw businessException;
		}
	}
}
