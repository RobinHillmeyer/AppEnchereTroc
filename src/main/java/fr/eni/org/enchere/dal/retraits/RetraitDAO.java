package fr.eni.org.enchere.dal.retraits;

import fr.eni.org.enchere.BusinessException;
import fr.eni.org.enchere.bo.Retrait;

public interface RetraitDAO {
	
	public void insert(Retrait retrait,int idArticle) throws BusinessException;


}
