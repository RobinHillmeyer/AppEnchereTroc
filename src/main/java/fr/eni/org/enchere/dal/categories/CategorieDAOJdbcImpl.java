package fr.eni.org.enchere.dal.categories;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import fr.eni.org.enchere.BusinessException;
import fr.eni.org.enchere.bo.Categorie;
import fr.eni.org.enchere.dal.CodeResultatDAL;
import fr.eni.org.enchere.dal.ConnectionProvider;
import fr.eni.org.enchere.dal.encheres.EnchereDAOJdbcImpl;

public class CategorieDAOJdbcImpl implements CategorieDAO{

	private static final String SELECT_CATEGORIE_BY_ID = "SELECT libelle, no_categorie FROM CATEGORIES WHERE no_categorie = ?";
	private static final String SELECT_ALL_CATEGORIE = "SELECT no_categorie, libelle FROM CATEGORIES";

	private static final String INSERT = "INSERT INTO CATEGORIES (libelle)  VALUES( ?)" ;


	@Override
	public void insert(String libelle) throws BusinessException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		if (libelle == null) {
			BusinessException businessException = new BusinessException();
			businessException.addErreur(CodeResultatDAL.INSERT_OBJET_NULL);
			throw businessException;
		}

		try (Connection cnx = ConnectionProvider.getConnection()) {
			try {
				cnx.setAutoCommit(false);

				pstmt = cnx.prepareStatement(CategorieDAOJdbcImpl.INSERT);
				pstmt.setString(1, libelle);
				pstmt.executeUpdate();

				cnx.commit();
			} catch (Exception e) {
				e.printStackTrace();
				cnx.rollback();
				throw e;
			}
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.addErreur(CodeResultatDAL.INSERT_OBJET_ECHEC);
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
	public Categorie update() throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Categorie categorie) throws BusinessException {
		// TODO Auto-generated method stub

	}

	@Override
	public Categorie selectById(int id) throws BusinessException {
		Categorie categorie = new Categorie();

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try(Connection cnx = ConnectionProvider.getConnection()) {
			pstmt = cnx.prepareStatement(CategorieDAOJdbcImpl.SELECT_CATEGORIE_BY_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				System.out.println("On passe dans la DAL");
				categorie.setNoCategorie(rs.getInt("no_categorie"));
				categorie.setLibelle(rs.getString("libelle"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.addErreur(CodeResultatDAL.ECHEC_SELECT_CATEGORIE_BY_ID);
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

		return categorie;
	}

	@Override
	public List<Categorie> selectAll() throws BusinessException{
		List<Categorie> listeCategorie = new ArrayList<>();

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try(Connection cnx = ConnectionProvider.getConnection()) {
			pstmt = cnx.prepareStatement(CategorieDAOJdbcImpl.SELECT_ALL_CATEGORIE);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				listeCategorie.add(new Categorie(rs.getInt("no_categorie"), rs.getString("libelle")));
			}
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.addErreur(CodeResultatDAL.ECHEC_SELECT_ALL_CATEGORIES);
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

		return listeCategorie;
	}

}