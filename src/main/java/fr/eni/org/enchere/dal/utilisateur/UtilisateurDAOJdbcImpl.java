package fr.eni.org.enchere.dal.utilisateur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.org.enchere.BusinessException;
import fr.eni.org.enchere.bo.Article;
import fr.eni.org.enchere.bo.Utilisateur;
import fr.eni.org.enchere.dal.CodeResultatDAL;
import fr.eni.org.enchere.dal.ConnectionProvider;

public class UtilisateurDAOJdbcImpl implements UtilisateurDAO {

	private static final String SELECT_ALL = "SELECT no_utilisateur, pseudo,  nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur FROM UTILISATEURS";
	private static final String SELECT_BY_ID = "SELECT u.no_utilisateur as no_utilisateur ,u.pseudo as pseudo,u.nom as nom,u.prenom as prenom,u.email as email,u.telephone as telephone,\r\n"
			+ "u.rue as rue,u.code_postal as code_postal,u.ville as ville,u.mot_de_passe as mot_de_passe,u.credit as credit,u.administrateur as administrateur,a.no_article as no_article,\r\n"
			+ "a.nom_article as nom_article,a.description as description,a.date_debut_encheres as date_debut_encheres,a.date_fin_encheres as date_fin_encheres,a.prix_initial as prix_initial,\r\n"
			+ "a.prix_vente as prix_vente,a.no_categorie as no_categorie FROM UTILISATEURS u LEFT JOIN ARTICLES_VENDUS a ON u.no_utilisateur = a.no_utilisateur where u.no_utilisateur =?";

	private static final String INSERT_UTILISATEUR = "INSERT into UTILISATEURS (pseudo,nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) values(?,?,?,?,?,?,?,?,?,?,?);";
	private static final String DELETE_UTILISATEUR = "DELETE FROM UTILISATEURS where no_utilisateur=?";
	private static final String SELECT_BY_PSEUDO = "SELECT no_utilisateur, pseudo,  mot_de_passe, administrateur FROM UTILISATEURS where pseudo=?";
	private static final String SELECT_BY_MAIL = "SELECT no_utilisateur, pseudo,  mot_de_passe, administrateur FROM UTILISATEURS where email=?";
	private static final String UPDATE_USER = "UPDATE UTILISATEURS SET pseudo = ?, nom = ?, prenom = ?, email = ?, telephone = ?, rue = ?, code_postal = ?, ville = ?,mot_de_passe= ?  WHERE no_utilisateur = ?";

	@Override
	public void insert(Utilisateur utilisateur) throws BusinessException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		if (utilisateur == null) {
			BusinessException businessException = new BusinessException();
			businessException.addErreur(CodeResultatDAL.INSERT_OBJET_NULL);
			throw businessException;
		}

		try (Connection cnx = ConnectionProvider.getConnection()) {
			try {
				cnx.setAutoCommit(false);
				if (utilisateur.getNoUtilisateur() == 0) {
					pstmt = cnx.prepareStatement(UtilisateurDAOJdbcImpl.INSERT_UTILISATEUR,
							Statement.RETURN_GENERATED_KEYS);
					pstmt.setString(1, utilisateur.getPseudo());
					pstmt.setString(2, utilisateur.getNom());
					pstmt.setString(3, utilisateur.getPrenom());
					pstmt.setString(4, utilisateur.getEmail());
					pstmt.setString(5, utilisateur.getTelephone());
					pstmt.setString(6, utilisateur.getRue());
					pstmt.setString(7, utilisateur.getCodePostal());
					pstmt.setString(8, utilisateur.getVille());
					pstmt.setString(9, utilisateur.getMotDePasse());
					pstmt.setInt(10, utilisateur.getCredit());
					pstmt.setByte(11, utilisateur.getAdministrateur());

					pstmt.executeUpdate();
					rs = pstmt.getGeneratedKeys();

					if (rs.next()) {
						utilisateur.setNoUtilisateur(rs.getInt(1));
					}
				}
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
	public void update(Utilisateur utilisateur, int id) throws BusinessException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		if (utilisateur == null) {
			BusinessException businessException = new BusinessException();
			businessException.addErreur(CodeResultatDAL.INSERT_OBJET_NULL);
			throw businessException;
		}

		try (Connection cnx = ConnectionProvider.getConnection()) {
			try {
				cnx.setAutoCommit(false);
				if (utilisateur.getNoUtilisateur() == 0) {
					pstmt = cnx.prepareStatement(UtilisateurDAOJdbcImpl.UPDATE_USER);

					pstmt.setString(1, utilisateur.getPseudo());
					pstmt.setString(2, utilisateur.getNom());
					pstmt.setString(3, utilisateur.getPrenom());
					pstmt.setString(4, utilisateur.getEmail());
					pstmt.setString(5, utilisateur.getTelephone());
					pstmt.setString(6, utilisateur.getRue());
					pstmt.setString(7, utilisateur.getCodePostal());
					pstmt.setString(8, utilisateur.getVille());
					pstmt.setString(9, utilisateur.getMotDePasse());
					pstmt.setInt(10, id);

					pstmt.executeUpdate();

				}
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
	public void delete(int id) throws BusinessException {
		PreparedStatement pstmt = null;

		try (Connection cnx = ConnectionProvider.getConnection()) {
			pstmt = cnx.prepareStatement(UtilisateurDAOJdbcImpl.DELETE_UTILISATEUR);
			pstmt.setInt(1, id);
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

	@Override
	public List<Utilisateur> selectAll() throws BusinessException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List<Utilisateur> listeUtilisateurs = new ArrayList<Utilisateur>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			pstmt = cnx.prepareStatement(UtilisateurDAOJdbcImpl.SELECT_ALL);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				listeUtilisateurs.add(new Utilisateur(rs.getInt("no_utilisateur"), rs.getString("pseudo"),
						rs.getString("nom"), rs.getString("prenom"), rs.getString("email"), rs.getString("telephone"),
						rs.getString("rue"), rs.getString("code_postal"), rs.getString("ville"),
						rs.getString("mot_de_passe"), rs.getInt("credit"), rs.getByte("administrateur")));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.addErreur(CodeResultatDAL.LECTURE_UTILISATEURS);
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
		return listeUtilisateurs;
	}

	@Override
	public Utilisateur selectById(int id) throws BusinessException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		Utilisateur utilisateur = new Utilisateur();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			pstmt = cnx.prepareStatement(UtilisateurDAOJdbcImpl.SELECT_BY_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				utilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
				utilisateur.setPseudo(rs.getString("pseudo"));
				utilisateur.setNom(rs.getString("nom"));
				utilisateur.setPrenom(rs.getString("prenom"));
				utilisateur.setEmail(rs.getString("email"));
				utilisateur.setTelephone(rs.getString("telephone"));
				utilisateur.setRue(rs.getString("rue"));
				utilisateur.setCodePostal(rs.getString("code_postal"));
				utilisateur.setVille(rs.getString("ville"));
				utilisateur.setMotDePasse(rs.getString("mot_de_passe"));
				utilisateur.setCredit(rs.getInt("credit"));
				utilisateur.setAdministrateur(rs.getByte("administrateur"));

				if (rs.getString("nom_article") != null) {
					utilisateur.getListeArticles()
							.add(new Article(rs.getInt("no_article"), rs.getString("nom_article"),
									rs.getString("description"), rs.getDate("date_debut_encheres").toLocalDate(),
									rs.getDate("date_fin_encheres").toLocalDate(), rs.getInt("prix_initial"),
									rs.getInt("prix_vente"), rs.getInt("no_utilisateur"), rs.getInt("no_categorie")
							));
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.addErreur(CodeResultatDAL.LECTURE_UTILISATEUR);
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
		return utilisateur;
	}

	@Override
	public int checkLogin(String pseudo, String password) throws BusinessException {
		final String MAIL_REGEX = "([_A-Za-z0-9-]+)(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})";
		int utilisateurId = 0;
		Utilisateur utilisateur = new Utilisateur();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try (Connection cnx = ConnectionProvider.getConnection()) {

			if (pseudo.matches(MAIL_REGEX)) {
				pstmt = cnx.prepareStatement(UtilisateurDAOJdbcImpl.SELECT_BY_MAIL);
			} else {
				pstmt = cnx.prepareStatement(UtilisateurDAOJdbcImpl.SELECT_BY_PSEUDO);
			}
			pstmt.setString(1, pseudo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				utilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
				utilisateur.setMotDePasse(rs.getString("mot_de_passe"));
			}
			
			if (utilisateur.getMotDePasse().equals(password)) {
				utilisateurId = utilisateur.getNoUtilisateur();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.addErreur(CodeResultatDAL.LECTURE_UTILISATEUR);
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
		return utilisateurId;
	}
}
