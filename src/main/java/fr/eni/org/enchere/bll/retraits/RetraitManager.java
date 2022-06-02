package fr.eni.org.enchere.bll.retraits;

import fr.eni.org.enchere.BusinessException;
import fr.eni.org.enchere.bll.CodeResultatBLL;
import fr.eni.org.enchere.bll.articles.ArticleManager;
import fr.eni.org.enchere.bo.Article;
import fr.eni.org.enchere.bo.Retrait;
import fr.eni.org.enchere.dal.DAOFactory;
import fr.eni.org.enchere.dal.retraits.RetraitDAO;

public class RetraitManager {
	
	private RetraitDAO retraitDAO;
	
	public RetraitManager() {
		this.retraitDAO = DAOFactory.getRetraitDAO();
	}
	
	public Retrait ajouterRetrait(String rue, String codePostal, String ville, int idArticle) throws BusinessException {
		BusinessException businessException = new BusinessException();
		Retrait retrait = null;
		
		this.validerRue(rue, businessException);
		this.validerCodePostal(codePostal, businessException);
		this.validerVille(ville, businessException);
		
		if (!businessException.hasErreur()) {

			Article art = new Article();
			ArticleManager am = new ArticleManager();
			
			try {
				art=am.selectById(idArticle);
			}catch(Exception e){
				e.printStackTrace();
			}

			retrait = new Retrait();
			retrait.setArticle(art);
			retrait.setRue(rue);
			retrait.setCodePostal(codePostal);
			retrait.setVille(ville);
			
			this.retraitDAO.insert(retrait, idArticle);
			
		} else {
			throw businessException;
		}
		
		return retrait;
	}
	
	public Retrait selectById(int idArticle) throws BusinessException {
		System.out.println("On passe par le manager");
		return this.retraitDAO.selectByArticleId(idArticle);
	}
	
	private void validerRue(String rue, BusinessException businessException) {
		if (rue == null || rue.trim().length() > 30) {
			businessException.addErreur(CodeResultatBLL.REGLE_RETRAIT_RUE_ERREUR);
		}
	}
	
	private void validerCodePostal(String codePostal, BusinessException businessException) {
		if (codePostal == null || codePostal.trim().length() > 15) {
			businessException.addErreur(CodeResultatBLL.REGLE_RETRAIT_CODEPOSTAL_ERREUR);
		}
	}
	
	private void validerVille(String ville, BusinessException businessException) {
		if (ville == null || ville.trim().length() > 30) {
			businessException.addErreur(CodeResultatBLL.REGLE_RETRAIT_VILLE_ERREUR);
		}
	}
}