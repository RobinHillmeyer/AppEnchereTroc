package fr.eni.org.enchere.dal;

import fr.eni.org.enchere.dal.articles.ArticleDAO;
import fr.eni.org.enchere.dal.articles.ArticleDAOJdbcImpl;
import fr.eni.org.enchere.dal.categories.CategorieDAO;
import fr.eni.org.enchere.dal.categories.CategorieDAOJdbcImpl;
import fr.eni.org.enchere.dal.encheres.EnchereDAO;
import fr.eni.org.enchere.dal.encheres.EnchereDAOJdbcImpl;
import fr.eni.org.enchere.dal.retraits.RetraitDAO;
import fr.eni.org.enchere.dal.retraits.RetraitDAOJdbcImpl;
import fr.eni.org.enchere.dal.utilisateur.UtilisateurDAO;
import fr.eni.org.enchere.dal.utilisateur.UtilisateurDAOJdbcImpl;

public abstract class DAOFactory {
	
	public static ArticleDAO getArticleDAO() {
		return new ArticleDAOJdbcImpl();
	}
	
	public static RetraitDAO getRetraitDAO() {
		return new RetraitDAOJdbcImpl();
	}
	
	public static UtilisateurDAO getUtilisateurDAO() {
		return new UtilisateurDAOJdbcImpl();
	}

	public static EnchereDAO getEnchereDAO() {
		return new EnchereDAOJdbcImpl();
	}

	public static CategorieDAO getCategorieDAO() {
		return new CategorieDAOJdbcImpl();
	}
	
	
}
