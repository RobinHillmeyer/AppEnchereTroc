package fr.eni.org.enchere.bo;

import java.util.ArrayList;
import java.util.List;

public class Categorie {

	// Attributs
	private int noCategorie;
	private String libelle;
	private List<Article> listeArticles = new ArrayList<Article>();

	// Constructeurs
	public Categorie() {

	}
	
	public Categorie(int noCategorie, String libelle) {
		this.noCategorie = noCategorie;
		this.libelle = libelle;
	}

	public Categorie(int noCategorie, String libelle, List<Article> listeArticles) {
		this.noCategorie = noCategorie;
		this.libelle = libelle;
		this.listeArticles = listeArticles;
	}

	// Methodes Getters&Setters
	public int getNoCategorie() {
		return this.noCategorie;
	}

	public void setNoCategorie(int noCategorie) {
		this.noCategorie = noCategorie;
	}

	public String getLibelle() {
		return this.libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public List<Article> getListeArticles() {
		return this.listeArticles;
	}

	public void setListeArticles(List<Article> listeArticles) {
		this.listeArticles = listeArticles;
	}

	@Override
	public String toString() {
		return "Categorie [noCategorie=" + this.noCategorie + ", libelle=" + this.libelle + ", listeArticles="
				+ this.listeArticles + "]";
	}
	
	

}
