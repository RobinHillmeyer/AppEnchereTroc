package fr.eni.org.enchere.dal;

public abstract class CodeResultatDAL {
	
	// Code d'erreur entre 10000 et 19999
	
	// Code erreur pour ARTICLES
	public static final int INSERT_ARTICLE_NULL = 10001;
	public static final int ECHEC_INSERT_ARTICLE = 10002;
	public static final int ECHEC_DELETE_ARTICLE = 10003;
	public static final int ECHEC_SELECT_ALL = 10004;
	public static final int ECHEC_SELECT_BY_ID = 10005;
	
	// Code erreur pour UTILISATEURS
	public static final int INSERT_OBJET_NULL = 10006;
	public static final int INSERT_OBJET_ECHEC = 10007;
	public static final int SUPPRESSION_UTILISATEUR_ERREUR = 10008;
	public static final int LECTURE_LISTE_UTILISATEUR_ECHEC = 10009;
	public static final int LECTURE_UTILISATEUR = 10010;
	public static final int LECTURE_UTILISATEURS = 10015;

	
	// Code erreur pour RETRAIT
	public static final int INSERT_RETRAIT_NULL = 10011;
	public static final int ECHEC_INSERT_RETRAIT = 10012;
	public static final int ECHEC_SELECT_RETRAIT_BY_ARTICLE_ID = 10013;
	
	// Code erreur pour CATEGORIES
	public static final int ECHEC_SELECT_CATEGORIE_BY_ID = 10014;
	public static final int ECHEC_SELECT_ALL_CATEGORIES = 10015;
	

}
