package fr.eni.org.enchere.bll.utilisateurs;

import java.util.ArrayList;
import java.util.List;

import fr.eni.org.enchere.BusinessException;
import fr.eni.org.enchere.bll.CodeResultatBLL;
import fr.eni.org.enchere.bo.Utilisateur;
import fr.eni.org.enchere.dal.DAOFactory;
import fr.eni.org.enchere.dal.utilisateur.UtilisateurDAO;

public class UtilisateurManager {

	private UtilisateurDAO utilisateurDAO;

	public UtilisateurManager() {
		this.utilisateurDAO = DAOFactory.getUtilisateurDAO();
	}

	public List<Utilisateur> selectAllUtilisateurs() throws BusinessException {
		BusinessException businessException = new BusinessException();
		List<Utilisateur> ListeUsers = new ArrayList<>();
		
		if (!businessException.hasErreur()) {
			ListeUsers = this.utilisateurDAO.selectAll();
		} else {
			throw businessException;
		}
		
		return ListeUsers;
	}

	public int selectUtilisateurAuth(String pseudo, String Password) throws BusinessException {
		BusinessException businessException = new BusinessException();
		int idUser = 0;
		
		if (!businessException.hasErreur()) {
			idUser = this.utilisateurDAO.checkLogin(pseudo, Password);
		} else {
			throw businessException;
		}
		
		return idUser;
	}

	public Utilisateur selectUtilisateurById(int id) throws BusinessException {
		BusinessException businessException = new BusinessException();
		Utilisateur user = new Utilisateur();

		if (!businessException.hasErreur()) {
			user = this.utilisateurDAO.selectById(id);
		} else {
			throw businessException;
		}
		
		return user;
	}

	public void addUser(Utilisateur utilisateur, String comfirPass) throws BusinessException {
		BusinessException businessException = new BusinessException();

		this.isUniqueMail(utilisateur.getEmail(), businessException);
		this.isUniquePseudo(utilisateur.getPseudo(), businessException);
		this.isValidPseudo(utilisateur.getPseudo(), businessException);
		this.isSamePasswords(utilisateur, comfirPass, businessException);
		this.isValidCp(utilisateur.getCodePostal(), businessException);
		this.isValidNum(utilisateur.getTelephone(), businessException);

		if (!businessException.hasErreur()) {
			this.utilisateurDAO.insert(utilisateur);
		} else {
			throw businessException;
		}
	}

	public void updateUser(Utilisateur utilisateur, int id, String comfirPass,Utilisateur userBeforUpdate) throws BusinessException {
		BusinessException businessException = new BusinessException();

		this.isUniquePseudoUpdate(utilisateur.getPseudo(), userBeforUpdate, businessException);
		this.isUniqueMailUpdate(utilisateur.getEmail(),userBeforUpdate, businessException);
		this.isValidCp(utilisateur.getCodePostal(), businessException);
		this.isValidNum(utilisateur.getTelephone(), businessException);
		this.isValidPseudo(utilisateur.getPseudo(), businessException);
		
		if(utilisateur.getMotDePasse()!=userBeforUpdate.getMotDePasse()) {
			this.isSamePasswords(utilisateur, comfirPass, businessException);
		}
	
		if (!businessException.hasErreur()) {
			this.utilisateurDAO.update(utilisateur, id);			
		} else {
			throw businessException;
		}
	}

	public void deleteUser(int id) throws BusinessException {
		this.utilisateurDAO.delete(id);
	}

	private void isSamePasswords(Utilisateur user, String comfirPass, BusinessException businessException) {
		if (user.getMotDePasse() == null || user.getMotDePasse().trim().length() < 4
				|| !user.getMotDePasse().equals(comfirPass)) {
			
			businessException.addErreur(0);
			businessException.addErreur(CodeResultatBLL.REGLE_CHECK_PASSWORD);
		}
	}

	public void isUniquePseudo(String pseudo, BusinessException businessException) {
		List<Utilisateur> users = new ArrayList<>();
		
		try {
			users = this.selectAllUtilisateurs();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		
		if (users != null) {
			for (Utilisateur user : users) {
				if (user.getPseudo().equals(pseudo.trim())) {

					businessException.addErreur(CodeResultatBLL.REGLE_PSEUDO_UNIQUE);
				}
			}
		}
	}

	public void isUniqueMail(String mail, BusinessException businessException) {
		List<Utilisateur> users = new ArrayList<>();
		
		try {
			users = this.selectAllUtilisateurs();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		
		if (users != null) {
			for (Utilisateur user : users) {
				if (user.getEmail().equals(mail.trim())) {
					businessException.addErreur(CodeResultatBLL.REGLE_MAIL_UNIQUE);
					System.out.println("err email unique");
				}
			}
		}
	}
	
	public void isUniquePseudoUpdate(String pseudo,Utilisateur userBeforeUp, BusinessException businessException) {
		List<Utilisateur> users = new ArrayList<>();
		
		try {
			users = this.selectAllUtilisateurs();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		
		if (users != null) {
			for (Utilisateur user : users) {
				if (user.getPseudo().equals(pseudo.trim()) && !pseudo.equals(userBeforeUp.getPseudo())  ) {
						businessException.addErreur(CodeResultatBLL.REGLE_PSEUDO_UNIQUE);
				}
			}
		}
	}

	public void isUniqueMailUpdate(String mail,Utilisateur userBeforUpdate, BusinessException businessException) {
		// on cr�er une liste qui va recevoir la liste de tout les utilisateurs
		List<Utilisateur> users = new ArrayList<>();
		
		try {
			//on essai de r�cup�rer tout les utilisateurs en les setant dans la liste
			users = this.selectAllUtilisateurs();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		// si la liste n'est pas vide 
		if (users != null) {
			for (Utilisateur user : users) {
				//on test que le mail de chaque users est bien diff�rent du nouveau mail
				if (user.getEmail().equals(mail.trim())&& !mail.equals(userBeforUpdate.getEmail())) {	
						businessException.addErreur(CodeResultatBLL.REGLE_MAIL_UNIQUE);
				}
			}
		}
	}

	public void isValidPseudo(String pseudo, BusinessException businessException) {
		final String REGEX = "^[a-zA-Z0-9]+$";

		if (!pseudo.matches(REGEX)) {
			businessException.addErreur(CodeResultatBLL.REGLE_PSEUDO_ALPHANUM);
			System.out.println("ALPHA num err");
		}
	}

	public void isValidCp(String cp, BusinessException businessException) {
		final String REGEX = "^[0-9]+$";

		if (!cp.matches(REGEX)) {
			businessException.addErreur(CodeResultatBLL.REGLE_CP);
			System.out.println("code Postal incorrect");
		}
	}

	public void isValidNum(String number, BusinessException businessException) {
		final String REGEX = "^[0-9]+$";

		if (!number.matches(REGEX) && number.length() != 10) {
			businessException.addErreur(CodeResultatBLL.REGLE_NUMBER_PHONE);
			System.out.println("num�ro de t�l�phone incorrect");
		}
	}
}
