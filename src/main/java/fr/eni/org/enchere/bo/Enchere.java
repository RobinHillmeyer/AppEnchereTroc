package fr.eni.org.enchere.bo;

import java.time.LocalDate;

public class Enchere {

	// Attributs
	private LocalDate dateEnchere;
	private int montantEnchere;
	private Article article = new Article();
	private Utilisateur utilisateur = new Utilisateur();

	// Constructeurs
	public Enchere() {

	}

	public Enchere(LocalDate dateEnchere, int montantEnchere, Article article, Utilisateur utilisateur) {
		this.dateEnchere = dateEnchere;
		this.montantEnchere = montantEnchere;
		this.article = article;
		this.utilisateur = utilisateur;
	}

	// Methodes Getters&Setters
	public LocalDate getDateEnchere() {
		return this.dateEnchere;
	}

	public void setDateEnchere(LocalDate dateEnchere) {
		this.dateEnchere = dateEnchere;
	}

	public int getMontantEnchere() {
		return this.montantEnchere;
	}

	public void setMontantEnchere(int montantEnchere) {
		this.montantEnchere = montantEnchere;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

}
