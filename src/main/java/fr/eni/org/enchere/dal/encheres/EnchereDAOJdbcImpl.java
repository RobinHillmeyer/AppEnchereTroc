package fr.eni.org.enchere.dal.encheres;

import fr.eni.org.enchere.BusinessException;
import fr.eni.org.enchere.bo.Article;
import fr.eni.org.enchere.bo.Enchere;
import fr.eni.org.enchere.bo.Utilisateur;
import fr.eni.org.enchere.dal.CodeResultatDAL;
import fr.eni.org.enchere.dal.ConnectionProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnchereDAOJdbcImpl implements EnchereDAO {

	private static final String INSERT_ENCHERE = "INSERT into ENCHERES (no_utilisateur,no_article, date_enchere, montant_enchere) values(?,?,?,?);";

	private static final String SELECT_BY_FILTER  = "SELECT * from ENCHERES e\n"+
	"INNER JOIN ARTICLES_VENDUS a ON e.no_article = a.no_article\n"+
	"INNER JOIN UTILISATEURS u ON u.no_utilisateur = e.no_utilisateur\n"+
	"where a.nom_article LIKE = ?";
	private static final String DELETE_ENCHERE = "DELETE FROM ENCHERES where no_arcticle =?";

	private static final String SELECT_ALL =  "SELECT * from ENCHERES e\n" +
			"INNER JOIN ARTICLES_VENDUS a ON e.no_article = a.no_article\n" +
			"INNER JOIN UTILISATEURS u ON u.no_utilisateur = e.no_utilisateur";
	private static final String SELECT_BY_ID = "SELECT * from ENCHERES e\n" +
			"INNER JOIN ARTICLES_VENDUS a ON e.no_article = a.no_article\n" +
			"INNER JOIN UTILISATEURS u ON u.no_utilisateur = e.no_utilisateur\n"+
			" where e.no_article =?";
	private static final String SELECT_ENCHERES_BY_CATEGORIE = "SELECT * from ENCHERES e INNER JOIN ARTICLES_VENDUS a ON e.no_article = a.no_article INNER JOIN UTILISATEURS u ON u.no_utilisateur = e.no_utilisateur where a.no_categorie = ?";

	private static final String SELECT_ENCHERES_OUVERTES = "SELECT * from ENCHERES e\n" +
			"\t\t\tINNER JOIN ARTICLES_VENDUS a ON e.no_article = a.no_article\n" +
			"\t\t\tINNER JOIN UTILISATEURS u ON u.no_utilisateur = e.no_utilisateur\n" +
			"\t\t\twhere a.date_debut_encheres BETWEEN a.date_debut_encheres AND GETDATE()";
	private static final String SELECT_ENCHERES_EN_COURS_BY_ID_USER = "SELECT * from ENCHERES e\n" +
			"\t\t\tINNER JOIN ARTICLES_VENDUS a ON e.no_article = a.no_article\n" +
			"\t\t\tINNER JOIN UTILISATEURS u ON u.no_utilisateur = e.no_utilisateur\n" +
			"\t\t\twhere a.date_debut_encheres BETWEEN a.date_debut_encheres AND GETDATE()\n" +
			"\t\t\tAND e.no_utilisateur = ?";

	private static final String SELECT_ENCHERES_REMPORTEES = "SELECT * from ENCHERES e\n" +
			"\t\t\tINNER JOIN ARTICLES_VENDUS a ON e.no_article = a.no_article\n" +
			"\t\t\tINNER JOIN UTILISATEURS u ON u.no_utilisateur = e.no_utilisateur\n" +
			"\t\t\twhere e.no_utilisateur_win = ?";

	@Override
	public void insert(Enchere enchere) throws BusinessException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		if (enchere == null) {
			BusinessException businessException = new BusinessException();
			businessException.addErreur(CodeResultatDAL.INSERT_OBJET_NULL);
			throw businessException;
		}

		try (Connection cnx = ConnectionProvider.getConnection()) {
			try {
				cnx.setAutoCommit(false);
					pstmt = cnx.prepareStatement(EnchereDAOJdbcImpl.INSERT_ENCHERE);
					pstmt.setInt(1, enchere.getUtilisateur().getNoUtilisateur());
					pstmt.setInt(2, enchere.getArticle().getId());
					pstmt.setDate(3, Date.valueOf(enchere.getDateEnchere()));
					pstmt.setInt(4, enchere.getMontantEnchere());
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
	public Enchere update() throws BusinessException {
		return null;
	}

	@Override
	public void delete(int idArticle) throws BusinessException {
		PreparedStatement pstmt = null;

		try (Connection cnx = ConnectionProvider.getConnection()) {
			pstmt = cnx.prepareStatement(EnchereDAOJdbcImpl.DELETE_ENCHERE);
			pstmt.setInt(1, idArticle);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.addErreur(CodeResultatDAL.SUPPRESSION_UTILISATEUR_ERREUR);
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

	public List<Enchere> selectAll() throws BusinessException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Enchere> listeEncheres = new ArrayList<>();

		try (Connection cnx = ConnectionProvider.getConnection()) {

			pstmt = cnx.prepareStatement(SELECT_ALL);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Enchere enchere = new Enchere();
					enchere.setDateEnchere( rs.getDate("date_enchere").toLocalDate());
					enchere.setMontantEnchere(rs.getInt("montant_enchere"));
					if(rs.getInt("no_utilisateur_win") < 0){
						enchere.setIdUtilisateurWin(rs.getInt("no_utilisateur_win"));
					}
					if (rs.getString("nom_article") != null) {
						enchere.setArticle(new Article(
								rs.getInt("no_article"),
								rs.getString("nom_article"),
								rs.getString("description"),
								rs.getDate("date_debut_encheres").toLocalDate(),
								rs.getDate("date_fin_encheres").toLocalDate(),
								rs.getInt("prix_initial"),
								rs.getInt("prix_vente"),
								rs.getInt("no_utilisateur"),
								rs.getInt("no_categorie")
						));
					}
					if (rs.getInt("no_utilisateur") > 0 ) {
						enchere.setUtilisateur(new Utilisateur(
								rs.getInt("no_utilisateur"),
								rs.getString("pseudo"),
								rs.getString("nom"),
								rs.getString("prenom"),
								rs.getString("email"),
								rs.getString("telephone"),
								rs.getString("rue"),
								rs.getString("code_postal"),
								rs.getString("ville")
						));

				}
				listeEncheres.add(enchere);




			}

		} catch(Exception e){
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
		for (Enchere enchere1 :listeEncheres) {
		}
	return  listeEncheres;
}


	@Override
	public Enchere selectById(int idArticle) throws BusinessException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Enchere enchere = new Enchere();
		Article article = new Article();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			pstmt = cnx.prepareStatement(SELECT_BY_ID);
			pstmt.setInt(1, idArticle);

			rs = pstmt.executeQuery();
			while (rs.next()) {

				enchere.setDateEnchere(rs.getDate("date_enchere").toLocalDate());
				enchere.setMontantEnchere(rs.getInt("montant_enchere"));
				if(rs.getInt("no_utilisateur_win") < 0){
					enchere.setIdUtilisateurWin(rs.getInt("no_utilisateur_win"));
				}

				if (rs.getString("nom_article") != null) {
					enchere.setArticle(new Article(
							rs.getInt("no_article"),
							rs.getString("nom_article"),
							rs.getString("description"),
							rs.getDate("date_debut_encheres").toLocalDate(),
							rs.getDate("date_fin_encheres").toLocalDate(),
							rs.getInt("prix_initial"),
							rs.getInt("prix_vente"),
							rs.getInt("no_utilisateur"),
							rs.getInt("no_categorie")
					));
				}
				if (rs.getInt("no_utilisateur") > 0) {
					enchere.setUtilisateur(new Utilisateur(
							rs.getInt("no_utilisateur"),
							rs.getString("pseudo"),
							rs.getString("nom"),
							rs.getString("prenom"),
							rs.getString("email"),
							rs.getString("telephone"),
							rs.getString("rue"),
							rs.getString("code_postal"),
							rs.getString("ville")
					));
				}
			}

		} catch(Exception e){
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
		return enchere;
	}
	public List<Enchere> selectEncheresByCategorie(int idCategorie) throws BusinessException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Enchere> listeEncheres = new ArrayList<>();
		Article article = new Article();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			pstmt = cnx.prepareStatement(SELECT_ENCHERES_BY_CATEGORIE);
			pstmt.setInt(1, idCategorie);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				Enchere enchere = new Enchere();

				enchere.setDateEnchere(rs.getDate("date_enchere").toLocalDate());
				enchere.setMontantEnchere(rs.getInt("montant_enchere"));
				if(rs.getInt("no_utilisateur_win") < 0){
					enchere.setIdUtilisateurWin(rs.getInt("no_utilisateur_win"));
				}

			if (rs.getString("nom_article") != null) {
				enchere.setArticle(new Article(
						rs.getInt("no_article"),
						rs.getString("nom_article"),
						rs.getString("description"),
						rs.getDate("date_debut_encheres").toLocalDate(),
						rs.getDate("date_fin_encheres").toLocalDate(),
						rs.getInt("prix_initial"),
						rs.getInt("prix_vente"),
						rs.getInt("no_utilisateur"),
						rs.getInt("no_categorie")
				));
			}
			if (rs.getInt("no_utilisateur") > 0) {
				enchere.setUtilisateur(new Utilisateur(
						rs.getInt("no_utilisateur"),
						rs.getString("pseudo"),
						rs.getString("nom"),
						rs.getString("prenom"),
						rs.getString("email"),
						rs.getString("telephone"),
						rs.getString("rue"),
						rs.getString("code_postal"),
						rs.getString("ville")
				));
			}
				listeEncheres.add(enchere);
		}

		} catch(Exception e){
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
		return listeEncheres;
	}

	public List<Enchere> selectByFilter(String fitler) throws BusinessException{

		List<Enchere> listeEncheres = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Article article = new Article();
		String flt = "'%"+fitler+"%'";
		try (Connection cnx = ConnectionProvider.getConnection()) {
			pstmt = cnx.prepareStatement(SELECT_BY_FILTER);
			pstmt.setString(1, flt);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				Enchere enchere = new Enchere();

				enchere.setDateEnchere(rs.getDate("date_enchere").toLocalDate());
				enchere.setMontantEnchere(rs.getInt("montant_enchere"));
				if(rs.getInt("no_utilisateur_win") < 0){
					enchere.setIdUtilisateurWin(rs.getInt("no_utilisateur_win"));
				}

				if (rs.getString("nom_article") != null) {
					enchere.setArticle(new Article(
							rs.getInt("no_article"),
							rs.getString("nom_article"),
							rs.getString("description"),
							rs.getDate("date_debut_encheres").toLocalDate(),
							rs.getDate("date_fin_encheres").toLocalDate(),
							rs.getInt("prix_initial"),
							rs.getInt("prix_vente"),
							rs.getInt("no_utilisateur"),
							rs.getInt("no_categorie")
					));
				}
				if (rs.getInt("no_utilisateur") > 0) {
					enchere.setUtilisateur(new Utilisateur(
							rs.getInt("no_utilisateur"),
							rs.getString("pseudo"),
							rs.getString("nom"),
							rs.getString("prenom"),
							rs.getString("email"),
							rs.getString("telephone"),
							rs.getString("rue"),
							rs.getString("code_postal"),
							rs.getString("ville")
					));
				}
				listeEncheres.add(enchere);
			}

		} catch(Exception e){
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
		return  listeEncheres;

	}

	public List<Enchere> selectByEnchereOuverte() throws BusinessException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Enchere> listeEncheres = new ArrayList<>();
		Article article = new Article();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			pstmt = cnx.prepareStatement(SELECT_ENCHERES_OUVERTES);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				Enchere enchere = new Enchere();

				enchere.setDateEnchere(rs.getDate("date_enchere").toLocalDate());
				enchere.setMontantEnchere(rs.getInt("montant_enchere"));
				if(rs.getInt("no_utilisateur_win") < 0){
					enchere.setIdUtilisateurWin(rs.getInt("no_utilisateur_win"));
				}

				if (rs.getString("nom_article") != null) {
					enchere.setArticle(new Article(
							rs.getInt("no_article"),
							rs.getString("nom_article"),
							rs.getString("description"),
							rs.getDate("date_debut_encheres").toLocalDate(),
							rs.getDate("date_fin_encheres").toLocalDate(),
							rs.getInt("prix_initial"),
							rs.getInt("prix_vente"),
							rs.getInt("no_utilisateur"),
							rs.getInt("no_categorie")
					));
				}
				if (rs.getInt("no_utilisateur") > 0) {
					enchere.setUtilisateur(new Utilisateur(
							rs.getInt("no_utilisateur"),
							rs.getString("pseudo"),
							rs.getString("nom"),
							rs.getString("prenom"),
							rs.getString("email"),
							rs.getString("telephone"),
							rs.getString("rue"),
							rs.getString("code_postal"),
							rs.getString("ville")
					));
				}
				listeEncheres.add(enchere);
			}

		} catch(Exception e){
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
		return listeEncheres;
	}


	public List<Enchere> selectEncheresEnCours(int idUser) throws BusinessException{

		List<Enchere> listeEncheres = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Article article = new Article();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			pstmt = cnx.prepareStatement(SELECT_ENCHERES_EN_COURS_BY_ID_USER);
			pstmt.setInt(1, idUser);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Enchere enchere = new Enchere();

				enchere.setDateEnchere(rs.getDate("date_enchere").toLocalDate());
				enchere.setMontantEnchere(rs.getInt("montant_enchere"));
				if(rs.getInt("no_utilisateur_win") < 0){
					enchere.setIdUtilisateurWin(rs.getInt("no_utilisateur_win"));
				}

				if (rs.getString("nom_article") != null) {
					enchere.setArticle(new Article(
							rs.getInt("no_article"),
							rs.getString("nom_article"),
							rs.getString("description"),
							rs.getDate("date_debut_encheres").toLocalDate(),
							rs.getDate("date_fin_encheres").toLocalDate(),
							rs.getInt("prix_initial"),
							rs.getInt("prix_vente"),
							rs.getInt("no_utilisateur"),
							rs.getInt("no_categorie")
					));
				}
				if (rs.getInt("no_utilisateur") > 0) {
					enchere.setUtilisateur(new Utilisateur(
							rs.getInt("no_utilisateur"),
							rs.getString("pseudo"),
							rs.getString("nom"),
							rs.getString("prenom"),
							rs.getString("email"),
							rs.getString("telephone"),
							rs.getString("rue"),
							rs.getString("code_postal"),
							rs.getString("ville")
					));
				}
				listeEncheres.add(enchere);
			}

		} catch(Exception e){
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
		return  listeEncheres;

	}

	public List<Enchere> selectEncheresRemportees(int idUtilisateur) throws BusinessException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Enchere> listeEncheres = new ArrayList<>();
		Article article = new Article();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			pstmt = cnx.prepareStatement(SELECT_ENCHERES_REMPORTEES);
			pstmt.setInt(1, idUtilisateur);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				Enchere enchere = new Enchere();

				enchere.setDateEnchere(rs.getDate("date_enchere").toLocalDate());
				enchere.setMontantEnchere(rs.getInt("montant_enchere"));
				if(rs.getInt("no_utilisateur_win") < 0){
					enchere.setIdUtilisateurWin(rs.getInt("no_utilisateur_win"));
				}

				if (rs.getString("nom_article") != null) {
					enchere.setArticle(new Article(
							rs.getInt("no_article"),
							rs.getString("nom_article"),
							rs.getString("description"),
							rs.getDate("date_debut_encheres").toLocalDate(),
							rs.getDate("date_fin_encheres").toLocalDate(),
							rs.getInt("prix_initial"),
							rs.getInt("prix_vente"),
							rs.getInt("no_utilisateur"),
							rs.getInt("no_categorie")
					));
				}
				if (rs.getInt("no_utilisateur") > 0) {
					enchere.setUtilisateur(new Utilisateur(
							rs.getInt("no_utilisateur"),
							rs.getString("pseudo"),
							rs.getString("nom"),
							rs.getString("prenom"),
							rs.getString("email"),
							rs.getString("telephone"),
							rs.getString("rue"),
							rs.getString("code_postal"),
							rs.getString("ville")
					));
				}
				listeEncheres.add(enchere);
			}

		} catch(Exception e){
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
		return listeEncheres;
	}
}
