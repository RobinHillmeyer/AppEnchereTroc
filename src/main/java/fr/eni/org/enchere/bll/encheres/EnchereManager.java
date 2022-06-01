package fr.eni.org.enchere.bll.encheres;

import java.util.ArrayList;
import java.util.List;

import fr.eni.org.enchere.BusinessException;
import fr.eni.org.enchere.bo.Enchere;
import fr.eni.org.enchere.dal.DAOFactory;
import fr.eni.org.enchere.dal.encheres.EnchereDAO;

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
			System.out.println(enchere.getArticle().getNomArticle()+"  depuis le manager");
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

}