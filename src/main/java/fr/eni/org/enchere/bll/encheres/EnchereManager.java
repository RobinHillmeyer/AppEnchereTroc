package fr.eni.org.enchere.bll.encheres;

import fr.eni.org.enchere.dal.DAOFactory;
import fr.eni.org.enchere.dal.encheres.EnchereDAO;

public class EnchereManager {
	
	private EnchereDAO enchereDAO;
	
	public EnchereManager() {
		this.enchereDAO = DAOFactory.getEnchereDAO();
	}
	
	//TODO insert & update

}
