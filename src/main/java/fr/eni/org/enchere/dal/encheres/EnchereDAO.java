package fr.eni.org.enchere.dal.encheres;

import fr.eni.org.enchere.BusinessException;
import fr.eni.org.enchere.bo.Enchere;

import java.util.List;

public interface EnchereDAO {
	
	public void insert(Enchere enchere) throws BusinessException;
	public Enchere update() throws BusinessException;
	public void delete (int idEnchere) throws BusinessException;

	public List<Enchere> selectAll()throws BusinessException;

	public Enchere selectById(int idArticle)throws BusinessException;

	public List<Enchere>selectEncheresByCategorie(int idCategorie)throws BusinessException;
	public List<Enchere> selectByFilter(String fitler) throws BusinessException;

	public List<Enchere> selectByEnchereOuverte() throws BusinessException;

	public List<Enchere> selectEncheresEnCours(int idUser) throws BusinessException;

	public List<Enchere> selectEncheresRemportees(int idUtilisateur) throws BusinessException ;



	}
