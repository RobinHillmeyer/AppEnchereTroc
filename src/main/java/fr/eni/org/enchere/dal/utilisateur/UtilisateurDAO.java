package fr.eni.org.enchere.dal.utilisateur;

import java.util.List;

import fr.eni.org.enchere.BusinessException;
import fr.eni.org.enchere.bo.Utilisateur;

public interface UtilisateurDAO {
    public void insert(Utilisateur utilisateur) throws BusinessException;
    public void update(Utilisateur utilisateur, int id) throws BusinessException;
    public void delete(int id) throws BusinessException;
    public List<Utilisateur> selectAll() throws BusinessException;
    public Utilisateur selectById(int id) throws BusinessException;
	public int checkLogin(String pseudo, String password) throws BusinessException;

    public void updateCreditUser(int idUser, int credit) throws BusinessException ;

    }
