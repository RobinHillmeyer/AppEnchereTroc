package fr.eni.org.enchere.bo;

public class Retrait {

	// Attributs
	private String rue;
	private String codePostal;
	private String ville;
	private Article article;

	// Constructeur
	public Retrait() {

	}

	public Retrait(String rue, String codePostal, String ville, Article article) {
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
		this.article = article;
	}

	// Methodes Getters&Setters
	public String getRue() {
		return this.rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getCodePostal() {
		return this.codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getVille() {
		return this.ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

}
