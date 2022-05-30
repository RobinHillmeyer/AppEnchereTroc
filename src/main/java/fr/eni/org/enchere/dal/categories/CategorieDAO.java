package fr.eni.org.enchere.dal.categories;

import java.util.List;

import fr.eni.org.enchere.BusinessException;
import fr.eni.org.enchere.bo.Categorie;

public interface CategorieDAO {
	
	//ITERATION 2
	public void insert(Categorie categorie) throws BusinessException;
	public Categorie update() throws BusinessException;
	public void delete(Categorie categorie) throws BusinessException;
	public Categorie selectById(int id) throws BusinessException;
	public List<Categorie> selectAll() throws BusinessException;
}
