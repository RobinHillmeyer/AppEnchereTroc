package fr.eni.org.enchere.dal.articles;

import java.util.List;

import fr.eni.org.enchere.BusinessException;
import fr.eni.org.enchere.bo.Article;

public interface ArticleDAO {

	void insert(Article article, int idUser) throws BusinessException;
	public void delete(Article article) throws BusinessException;
	public List<Article> selectAll() throws BusinessException;
	public Article selectById(int id) throws BusinessException;

}
