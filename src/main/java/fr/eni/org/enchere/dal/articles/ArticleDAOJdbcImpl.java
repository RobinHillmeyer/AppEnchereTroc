package fr.eni.org.enchere.dal.articles;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.org.enchere.BusinessException;
import fr.eni.org.enchere.bo.Article;
import fr.eni.org.enchere.dal.CodeResultatDAL;
import fr.eni.org.enchere.dal.ConnectionProvider;

public class ArticleDAOJdbcImpl implements ArticleDAO {

	private static final String INSERT_ARTICLE = "INSERT INTO ARTICLES_VENDUS (nom_article, description, "
			+ "date_debut_encheres, date_fin_encheres, prix_initial, no_utilisateur, no_categorie) VALUES "
			+ "(?, ?, ?, ?, ?, ?, ?);";
	private static final String DELETE_ARTICLE = "DELETE FROM ARTICLES_VENDUS WHERE no_article = ?;";
	private static final String SELECT_ALL_ARTICLES = "SELECT no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie FROM ARTICLES_VENDUS;";
	private static final String SELECT_ARTICLE_BY_ID = "SELECT nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie FROM ARTICLES_VENDUS WHERE no_article = ?;";

	@Override
	public void insert(Article article, int idUser) throws BusinessException {
		if (article == null) {
			BusinessException businessException = new BusinessException();
			businessException.addErreur(CodeResultatDAL.INSERT_ARTICLE_NULL);
			throw businessException;
		}

		// Creation du Prepare Statment
		PreparedStatement pstmt = null;
		// Creation du ResultSet
		ResultSet rs = null;

		try(Connection cnx = ConnectionProvider.getConnection()) {
			pstmt = cnx.prepareStatement(ArticleDAOJdbcImpl.INSERT_ARTICLE, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, article.getNomArticle());
			pstmt.setString(2, article.getDescription());
			pstmt.setDate(3, Date.valueOf(article.getDateDebutEncheres()));
			pstmt.setDate(4, Date.valueOf(article.getDateFinEncheres()));
			pstmt.setInt(5, article.getMiseAPrix());
			pstmt.setInt(6, idUser);
			pstmt.setInt(7, article.getNoCategorie());

			pstmt.executeUpdate();

			rs = pstmt.getGeneratedKeys();

			if (rs.next()) {
				article.setId(rs.getInt(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.addErreur(CodeResultatDAL.ECHEC_INSERT_ARTICLE);
			throw businessException;

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}

	}

	@Override
	public void delete(Article article) throws BusinessException {
		PreparedStatement pstmt = null;

		try(Connection cnx = ConnectionProvider.getConnection()) {
			pstmt = cnx.prepareStatement(ArticleDAOJdbcImpl.DELETE_ARTICLE);
			pstmt.setInt(1, article.getId());

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.addErreur(CodeResultatDAL.ECHEC_DELETE_ARTICLE);
			throw businessException;
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public List<Article> selectAll() throws BusinessException {
		List<Article> listeArticles = new ArrayList<>();

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try(Connection cnx = ConnectionProvider.getConnection()) {
			pstmt = cnx.prepareStatement(ArticleDAOJdbcImpl.SELECT_ALL_ARTICLES);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				listeArticles.add(new Article(
						rs.getInt("no_article"), 
						rs.getString("nom_article"), 
						rs.getString("description"), 
						rs.getDate("date_debut_encheres").toLocalDate(),
						rs.getDate("date_fin_encheres").toLocalDate(), 
						rs.getInt("prix_initial"), 
						rs.getInt("prix_vente"), 
						rs.getInt("no_utilisateur"), 
						rs.getInt("no_categorie")));
			}

		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.addErreur(CodeResultatDAL.ECHEC_SELECT_ALL);
			throw businessException;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
		return listeArticles;
	}

	@Override
	public Article selectById(int id) throws BusinessException {
		Article article = new Article();

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try(Connection cnx = ConnectionProvider.getConnection()) {
			pstmt = cnx.prepareStatement(ArticleDAOJdbcImpl.SELECT_ARTICLE_BY_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				article.setId(rs.getInt("no_article"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.addErreur(CodeResultatDAL.ECHEC_SELECT_BY_ID);
			throw businessException;

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
		return article;
	}

}