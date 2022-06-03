package fr.eni.org.enchere.bll.encheres;

import fr.eni.org.enchere.BusinessException;
import fr.eni.org.enchere.bo.Enchere;
import fr.eni.org.enchere.bo.Utilisateur;
import fr.eni.org.enchere.dal.DAOFactory;
import fr.eni.org.enchere.dal.encheres.EnchereDAO;

import java.util.ArrayList;
import java.util.List;

public class EnchereManager {
	
	private EnchereDAO enchereDAO;
	
	public EnchereManager() {
		this.enchereDAO = DAOFactory.getEnchereDAO();
	}

	public List<Enchere> selectAllEnchere() throws BusinessException {
		BusinessException businessException = new BusinessException();
		List<Enchere> listEnchere = new ArrayList<>();
		if (!businessException.hasErreur()) {
			listEnchere = this.enchereDAO.selectAll();
		} else {
			throw businessException;
		}
		for (Enchere enchere: listEnchere) {
		}
		return listEnchere;
	}

	public Enchere selectById(int idArticle) throws BusinessException {
		BusinessException businessException = new BusinessException();
		Enchere enchere = new Enchere();
		if (!businessException.hasErreur()) {
			enchere = this.enchereDAO.selectById(idArticle);
		} else {
			throw businessException;
		}
		return enchere;
	}

	public void deleteEnchere(int idArticle) throws BusinessException{
		BusinessException businessException = new BusinessException();
		if (!businessException.hasErreur()) {
			this.enchereDAO.delete(idArticle);
		} else {
			throw businessException;
		}
	}

	public void insertEnchere(Enchere enchere) throws BusinessException{
		BusinessException businessException = new BusinessException();
		if (!businessException.hasErreur()) {
			this.enchereDAO.insert(enchere);
		} else {
			throw businessException;
		}

	}
	public List<Enchere> selectByCategories(int idCategorie) throws BusinessException {
		BusinessException businessException = new BusinessException();
		List<Enchere> listEnchere = new ArrayList<>();

		if (!businessException.hasErreur()) {
			listEnchere = this.enchereDAO.selectEncheresByCategorie(idCategorie);
		} else {
			throw businessException;
		}
		return listEnchere;

	}
	public List<Enchere> selectByFilter(String fitler) throws BusinessException {
		BusinessException businessException = new BusinessException();
		List<Enchere> listEnchere = new ArrayList<>();

		if (!businessException.hasErreur()) {
			listEnchere = this.enchereDAO.selectByFilter(fitler);
		} else {
			throw businessException;
		}
		return listEnchere;

	}

	public List<Enchere> selectEnchereOuverte() throws BusinessException {
		BusinessException businessException = new BusinessException();
		List<Enchere> listEnchere = new ArrayList<>();
		if (!businessException.hasErreur()) {
			listEnchere = this.enchereDAO.selectByEnchereOuverte();
		} else {
			throw businessException;
		}
		for (Enchere enchere: listEnchere) {
		}
		return listEnchere;
	}
	public List<Enchere> selectEncheresEnCours(int idUser) throws BusinessException {
		BusinessException businessException = new BusinessException();
		List<Enchere> listEnchere = new ArrayList<>();
		if (!businessException.hasErreur()) {
			listEnchere = this.enchereDAO.selectEncheresEnCours(idUser);
		} else {
			throw businessException;
		}
		for (Enchere enchere: listEnchere) {
		}
		return listEnchere;
	}

	public List<Enchere> selectEncheresRemportees(int idUser) throws BusinessException {
		BusinessException businessException = new BusinessException();
		List<Enchere> listEnchere = new ArrayList<>();
		if (!businessException.hasErreur()) {
			listEnchere = this.enchereDAO.selectEncheresRemportees(idUser);
		} else {
			throw businessException;
		}
		for (Enchere enchere: listEnchere) {
		}
		return listEnchere;
	}

}
