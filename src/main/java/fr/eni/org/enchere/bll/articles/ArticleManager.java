package fr.eni.org.enchere.bll.articles;

import java.time.LocalDate;
import java.util.List;

import fr.eni.org.enchere.BusinessException;
import fr.eni.org.enchere.bll.CodeResultatBLL;
import fr.eni.org.enchere.bo.Article;
import fr.eni.org.enchere.dal.DAOFactory;
import fr.eni.org.enchere.dal.articles.ArticleDAO;

public class ArticleManager {
	
	private ArticleDAO articleDAO;
	
	public ArticleManager() {
		this.articleDAO = DAOFactory.getArticleDAO();
	}
	
	public Article ajouterArticle(String nomArticle, String description, LocalDate dateDebutEnchere, LocalDate dateFinEnchere, int miseAPrix, int noCategorie,int idUser) throws BusinessException {
		BusinessException businessException = new BusinessException();
		
		Article article = null;
		
		this.validerNomArticle(nomArticle, businessException);
		this.validerDescription(description, businessException);
		
		if (!businessException.hasErreur()) {
			article = new Article();
			article.setNomArticle(nomArticle);
			article.setDescription(description);
			article.setMiseAPrix(miseAPrix);
			article.setDateDebutEncheres(dateDebutEnchere);
			article.setDateFinEncheres(dateFinEnchere);
			article.setNoCategorie(noCategorie);
			
			this.articleDAO.insert(article, idUser);
		} else {
			throw businessException;
		}
		
		return article;
	}	

	public void supprimerArticle(Article article) throws BusinessException {
		this.articleDAO.delete(article);
	}
	
	public List<Article> selectAll() throws BusinessException {
		return this.articleDAO.selectAll();
	}
	
	public Article selectById(int id) throws BusinessException {
		return this.articleDAO.selectById(id);
	}
	
	private void validerNomArticle(String nomArticle, BusinessException businessException) {
		if (nomArticle == null || nomArticle.trim().length() > 30) {
			businessException.addErreur(CodeResultatBLL.REGLE_ARTICLE_NOM_ERREUR);
		}
	}
	
	private void validerDescription(String description, BusinessException businessException) {
		if (description == null || description.trim().length() > 300) {
			businessException.addErreur(CodeResultatBLL.REGLE_ARTICLE_DESCRIPTION_ERREUR);
		}
	}
}
