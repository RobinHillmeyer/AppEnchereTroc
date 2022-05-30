package fr.eni.org.enchere.dal.encheres;

import fr.eni.org.enchere.BusinessException;
import fr.eni.org.enchere.bo.Enchere;

public interface EnchereDAO {
	
	public void insert(Enchere enchere) throws BusinessException;
	public Enchere update() throws BusinessException;

}
