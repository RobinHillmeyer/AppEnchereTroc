package fr.eni.org.enchere;

import java.util.ArrayList;
import java.util.List;

public class BusinessException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private List<Integer> listeCodesErreur;
	
	//Constructeur
	public BusinessException() {
		super();
		this.listeCodesErreur=new ArrayList<>();
	}
	
	//Methodes
	public void addErreur(int code)
	{
		if(!this.listeCodesErreur.contains(code))
		{
			this.listeCodesErreur.add(code);
		}
	}
	
	public boolean hasErreur()
	{
		return this.listeCodesErreur.size()>0;
	}
	
	//Getter
	public List<Integer> getListeCodesErreur()
	{
		return this.listeCodesErreur;
	}



}
