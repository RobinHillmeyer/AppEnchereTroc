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

	public List<Categorie> ajouterCategorie(int noCategorie, String libelle) throws BusinessException {
		BusinessException businessException = new BusinessException();
		
		List<Categorie> categories = new ArrayList<>();
		
		//TODO IT2 Ajout par Admin
		if (!businessException.hasErreur()) {
			
			categories.add(new Categorie(1, "Informatique"));
			categories.add(new Categorie(2, "Ameublement"));
			categories.add(new Categorie(3, "Vetement"));
			categories.add(new Categorie(4, "Sport & Loisirs"));
			
		} else {
			throw businessException;
		}
		
		return categories;
	}
	
	public Categorie selectById(int id) throws BusinessException {
		System.out.println("On passe par le manager");
		return this.categorieDAO.selectById(id);
	}
	
	public List<Categorie> selectAll() throws BusinessException {
		return this.categorieDAO.selectAll();
	}
}
