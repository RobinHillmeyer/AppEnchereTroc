package fr.eni.org.enchere.bll.categories;

import java.util.ArrayList;
import java.util.List;

import fr.eni.org.enchere.BusinessException;
import fr.eni.org.enchere.bo.Categorie;
import fr.eni.org.enchere.dal.DAOFactory;
import fr.eni.org.enchere.dal.categories.CategorieDAO;

public class CategorieManager {
	
	private CategorieDAO categorieDAO;
	
	public CategorieManager() {
		this.categorieDAO = DAOFactory.getCategorieDAO();
	}

	public List<Categorie> ajouterCategorie( String libelle) throws BusinessException {
		BusinessException businessException = new BusinessException();
		
		List<Categorie> categories = new ArrayList<>();

		if (!businessException.hasErreur()) {
			this.categorieDAO.insert(libelle);
		} else {
			throw businessException;
		}
		
		return categories;
	}
	
	public Categorie selectById(int id) throws BusinessException {
		return this.categorieDAO.selectById(id);
	}
	
	public List<Categorie> selectAll() throws BusinessException {
		return this.categorieDAO.selectAll();
	}
}
