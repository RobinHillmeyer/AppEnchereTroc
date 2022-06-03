package fr.eni.org.enchere.bll;

public abstract class CodeResultatBLL {
	
	// Code erreur compris entre 20000 29999
	
	//Echec general quand tentative d'ajouter un objet null
	public static final int INSERT_OBJET_NULL=20000;
	
	//Echec general quand erreur non g�r�e � l'insertion 
	public static final int INSERT_OBJET_ECHEC=20001;
	
	//Echec de la lecture d'un utilisateur
	public static final int LECTURE_UTILISATEUR = 20002;
	
	//Echec de la lecture d'une liste d'utilisateurs'
	public static final int LECTURE_LISTE_UTILISATEUR_ECHEC = 20003;
	
	//Erreur sur la suppression d'un utilisateur
	public static final int SUPPRESSION_UTILISATEUR_ERREUR = 20004;
	
	//Erreur Check Password
	public static final int REGLE_UTILISATEUR_CHECK_PASSWORD_ERREUR = 20005;
	
	//Erreur de la validation du nom
	public static final int REGLE_ARTICLE_NOM_ERREUR = 20101;
	
	//Erreur dela validation de la Description
	public static final int REGLE_ARTICLE_DESCRIPTION_ERREUR = 20102;
	
	//Erreur dela validation de la Rue
	public static final int REGLE_RETRAIT_RUE_ERREUR = 20103;
	
	//Erreur dela validation du Code Postal
	public static final int REGLE_RETRAIT_CODEPOSTAL_ERREUR = 20104;
	
	//Erreur dela validation de la Ville
	public static final int REGLE_RETRAIT_VILLE_ERREUR = 20105;

	//Erreur de la validation du pseudo
	public static final int REGLE_PSEUDO_UNIQUE = 20106;
	
	//Erreur de la validation du mail
	public static final int REGLE_MAIL_UNIQUE = 20107;
	//Erreur de la validation du pseudo

	public static final int REGLE_PSEUDO_ALPHANUM = 20108;
	
	//Erreur de la validation du password
	public static final int REGLE_CHECK_PASSWORD = 20109;
	
	//Erreur de la validation du pseudo
	public static final int REGLE_CP = 20110;
	
	//Erreur de la validation du tel
	public static final int REGLE_NUMBER_PHONE = 20111;
    public static final int UPDATE_CREDIT_USER = 20112 ;
}
