package fr.eni.org.enchere.dal.retraits;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.eni.org.enchere.BusinessException;
import fr.eni.org.enchere.bo.Retrait;
import fr.eni.org.enchere.dal.CodeResultatDAL;
import fr.eni.org.enchere.dal.ConnectionProvider;

public class RetraitDAOJdbcImpl implements RetraitDAO {

	private static final String INSERT_RETRAIT = "INSERT INTO RETRAITS (no_article, rue, code_postal, ville) VALUES (?, ?, ?, ?);";
	private static final String SELECT_BY_ARTICLE_ID = "SELECT no_article, rue, code_postal, ville FROM RETRAITS WHERE no_article = ?;";

	@Override
	public void insert(Retrait retrait, int idArticle) throws BusinessException {

		if (retrait == null) {
			System.out.println("on passe dans le if retrat null ");

			BusinessException businessException = new BusinessException();
			businessException.addErreur(CodeResultatDAL.INSERT_RETRAIT_NULL);
			throw businessException;
		}

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try(Connection cnx = ConnectionProvider.getConnection()) {	

			System.out.println("on passe dans insert avec l'id article :  "+idArticle);

			pstmt = cnx.prepareStatement(RetraitDAOJdbcImpl.INSERT_RETRAIT);
			pstmt.setInt(1, idArticle); 
			pstmt.setString(2, retrait.getRue());
			pstmt.setString(3, retrait.getCodePostal());
			pstmt.setString(4, retrait.getVille());

			pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("on passe pas dans insert ");

			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.addErreur(CodeResultatDAL.ECHEC_INSERT_RETRAIT);
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
	public Retrait selectByArticleId(int idArticle) throws BusinessException {
		Retrait retrait = new Retrait();

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try(Connection cnx = ConnectionProvider.getConnection()) {
			pstmt = cnx.prepareStatement(RetraitDAOJdbcImpl.SELECT_BY_ARTICLE_ID);
			pstmt.setInt(1, idArticle);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				System.out.println("On passe dans la DAL");
				retrait.setRue(rs.getString("rue"));
				retrait.setCodePostal(rs.getString("code_postal"));
				retrait.setVille(rs.getString("ville"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.addErreur(CodeResultatDAL.ECHEC_SELECT_RETRAIT_BY_ARTICLE_ID);
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

		return retrait;
	}

}
