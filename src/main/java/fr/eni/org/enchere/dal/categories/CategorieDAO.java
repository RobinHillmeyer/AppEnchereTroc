package fr.eni.org.enchere.dal.categories;

import java.util.List;

import fr.eni.org.enchere.BusinessException;
import fr.eni.org.enchere.bo.Categorie;

public interface CategorieDAO {
	
	public Categorie selectById(int id) throws BusinessException;
	public List<Categorie> selectAll() throws BusinessException;
}
