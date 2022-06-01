package fr.eni.org.enchere.ihm.utilisateur;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import fr.eni.org.enchere.BusinessException;
import fr.eni.org.enchere.bll.utilisateurs.UtilisateurManager;
import fr.eni.org.enchere.bo.Utilisateur;
import fr.eni.org.enchere.ihm.CodeResultatServlet;

@WebServlet(urlPatterns = { "/ProfilUtilisateur", "/SupprimerUtilisateur", })
public class ProfilUtilisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UtilisateurManager um = new UtilisateurManager();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		List<Integer> listeCodesErreur = new ArrayList<>();
		um = new UtilisateurManager();
		Utilisateur user;
			try {
				int idUser =(int)request.getSession().getAttribute("userIdSessionAttr");
				System.out.println(idUser+" id user depuis profile user");
				user = um.selectUtilisateurById(idUser);
				System.out.println(user.toString()+"  user depuis profile user");

				request.setAttribute("user", user);
			} catch (BusinessException e) {
				request.setAttribute("listeCodesErreur",e.getListeCodesErreur());
				e.printStackTrace();
				listeCodesErreur.add(CodeResultatServlet.ECHEC_LECTURE_USER);

			}

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/pages/utilisateur/profilUtilisateur.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		RequestDispatcher rd;
		request.setCharacterEncoding("UTF-8");
		List<Integer> listeCodesErreur = new ArrayList<>();
		HttpSession session = request.getSession();

		
		int idUser =(int)request.getSession().getAttribute("userIdSessionAttr");

		if (request.getServletPath().equals("/ProfilUtilisateur")) {
			MiseAJourUtilisateur(request, response,idUser, listeCodesErreur);

			response.sendRedirect("home");


		} else if (request.getServletPath().equals("/SupprimerUtilisateur")) {

			try {
				um.deleteUser(idUser);
				Cookie[] cookies = request.getCookies();
				for (Cookie cookie : cookies) {
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
				if (session.getAttribute("userIdSessionAttr") != null) {
					session.removeAttribute("userIdSessionAttr");
				}
			} catch (BusinessException e) {
				request.setAttribute("listeCodesErreur",e.getListeCodesErreur());
				e.printStackTrace();

			}
			response.sendRedirect("home");
		}

	}
	
	

	public void MiseAJourUtilisateur(HttpServletRequest request, HttpServletResponse response,int userIdentifiant,List<Integer> listeCodesErreur) {

		Utilisateur userUpdate = new Utilisateur();
		Utilisateur userAfterUpdate = new Utilisateur();
		Utilisateur userBeforUpdate = new Utilisateur();

		String holdMdp = request.getParameter("holdmdp");
		String confMdp = request.getParameter("confMdp");

		userUpdate.setPseudo(request.getParameter("pseudo"));
		userUpdate.setNom(request.getParameter("nom"));
		userUpdate.setPrenom(request.getParameter("prenom"));
		userUpdate.setEmail(request.getParameter("email"));
		userUpdate.setTelephone(request.getParameter("telephone"));
		userUpdate.setRue(request.getParameter("rue"));
		userUpdate.setCodePostal(request.getParameter("cp"));
		userUpdate.setVille(request.getParameter("ville"));
		userUpdate.setMotDePasse(request.getParameter("mdp"));
				

		try {
			userBeforUpdate = um.selectUtilisateurById(userIdentifiant);

			if (userBeforUpdate != null) {

				if (!userBeforUpdate.getPseudo().equals(userUpdate.getPseudo())) {
					userAfterUpdate.setPseudo(userUpdate.getPseudo());
				} else {
					userAfterUpdate.setPseudo(userBeforUpdate.getPseudo());
				}

				if (!userBeforUpdate.getNom().equals(userUpdate.getNom())) {
					userAfterUpdate.setNom(userUpdate.getNom());
				} else {
					userAfterUpdate.setNom(userBeforUpdate.getNom());
				}

				if (!userBeforUpdate.getPrenom().equals(userUpdate.getPrenom())) {
					userAfterUpdate.setPrenom(userUpdate.getPrenom());
				} else {
					userAfterUpdate.setPrenom(userBeforUpdate.getPrenom());
				}

				if (!userBeforUpdate.getEmail().equals(userUpdate.getEmail())) {
					userAfterUpdate.setEmail(userUpdate.getEmail());
				} else {
					userAfterUpdate.setEmail(userBeforUpdate.getEmail());
				}

				if (!userBeforUpdate.getTelephone().equals(userUpdate.getTelephone())) {
					userAfterUpdate.setTelephone(userUpdate.getTelephone());
				} else {
					userAfterUpdate.setTelephone(userBeforUpdate.getTelephone());
				}

				if (!userBeforUpdate.getRue().equals(userUpdate.getRue())) {
					userAfterUpdate.setRue(userUpdate.getRue());
				} else {
					userAfterUpdate.setRue(userBeforUpdate.getRue());
				}

				if (!userBeforUpdate.getCodePostal().equals(userUpdate.getCodePostal())) {
					userAfterUpdate.setCodePostal(userUpdate.getCodePostal());
				} else {
					userAfterUpdate.setCodePostal(userBeforUpdate.getCodePostal());
				}

				if (!userBeforUpdate.getVille().equals(userUpdate.getVille())) {
					userAfterUpdate.setVille(userUpdate.getVille());
				} else {
					userAfterUpdate.setVille(userBeforUpdate.getVille());
				}

				if (!userBeforUpdate.getMotDePasse().equals(userUpdate.getMotDePasse())) {
					if (userBeforUpdate.getMotDePasse().equals(holdMdp)) {
						if(holdMdp.equals(userBeforUpdate.getMotDePasse())) {
							userAfterUpdate.setMotDePasse(userUpdate.getMotDePasse());
						}
					}
				} else {
					
					userAfterUpdate.setMotDePasse(userBeforUpdate.getMotDePasse());
				}

				um.updateUser(userAfterUpdate, userIdentifiant, confMdp,userBeforUpdate);

				System.out.println("on à update");
			}
		} catch (BusinessException e) {
			listeCodesErreur.add(CodeResultatServlet.ECHEC_UPDATE_USER);
			request.setAttribute("listeCodesErreur",e.getListeCodesErreur());

		}
	}

}
