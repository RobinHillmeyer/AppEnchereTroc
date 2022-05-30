package fr.eni.org.enchere.ihm.login;

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

@WebServlet(urlPatterns = { "/signUp", "/signIn", "/signOut" })
public class ServletLogin extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		RequestDispatcher rd;
		if (request.getServletPath().equals("/signIn")) {
			rd = request.getRequestDispatcher("/WEB-INF/jsp/pages/connexion/signIn.jsp");
			rd.forward(request, response);
		} else if (request.getServletPath().equals("/signUp")) {
			rd = request.getRequestDispatcher("/WEB-INF/jsp/pages/connexion/signUp.jsp");
			rd.forward(request, response);
		} else if (request.getServletPath().equals("/signOut")) {
			Cookie[] cookies = request.getCookies();
			for (Cookie cookie : cookies) {
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
			if (session.getAttribute("userIdSessionAttr") != null) {
				session.removeAttribute("userIdSessionAttr");
			}
			rd = request.getRequestDispatcher("/WEB-INF/jsp/pages/home.jsp");
			rd.forward(request, response);
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		List<Integer> listeCodesErreur = new ArrayList<>();

		if (request.getServletPath().equals("/signIn")) {

			this.creerUtilisateur(request, response, listeCodesErreur);

			if (listeCodesErreur.size() > 0) {

				System.out.println("listeCodeErr dans /signIn : " + listeCodesErreur.toString());

				request.setAttribute("listeCodesErreur", listeCodesErreur);
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/pages/connexion/signIn.jsp");
				rd.forward(request, response);
			} else {
				System.out.println("on passe dans le signIn post");
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/pages/home.jsp");
				rd.forward(request, response);
			}

		} else if (request.getServletPath().equals("/signUp")) {

			this.checkAuth(request, response, listeCodesErreur);

			if (listeCodesErreur.size() > 0) {
				request.setAttribute("listeCodesErreur", listeCodesErreur);
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/pages/connexion/signUp.jsp");
				rd.forward(request, response);
			} else {
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/pages/home.jsp");
				rd.forward(request, response);
			}

		}

	}

	public void creerUtilisateur(HttpServletRequest request, HttpServletResponse response,
			List<Integer> listeCodesErreur) {

		Utilisateur user = new Utilisateur();
		UtilisateurManager umng = new UtilisateurManager();

		user.setPseudo(request.getParameter("pseudo"));
		user.setNom(request.getParameter("nom"));
		user.setPrenom(request.getParameter("prenom"));
		user.setEmail(request.getParameter("email"));
		user.setTelephone(request.getParameter("telephone"));
		user.setRue(request.getParameter("rue"));
		user.setCodePostal(request.getParameter("cp"));
		user.setVille(request.getParameter("ville"));
		user.setMotDePasse(request.getParameter("mdp"));

		String confPass = request.getParameter("confMdp");
		HttpSession session = request.getSession();

		try {
			umng.addUser(user, confPass);

			request.setAttribute("userId", user.getNoUtilisateur());

			session.setAttribute("userIdSessionAttr", user.getNoUtilisateur());

			Cookie cookieIdUser = new Cookie("cookieIdUser", Integer.toString(user.getNoUtilisateur()));
			cookieIdUser.setMaxAge(50000);
			response.addCookie(cookieIdUser);

		} catch (BusinessException e) {
			listeCodesErreur.add(CodeResultatServlet.ECHEC_CREATE_NEW_USER);

		}
	}

	public void checkAuth(HttpServletRequest request, HttpServletResponse response, List<Integer> listeCodesErreur) {
		UtilisateurManager umng = new UtilisateurManager();
		String pseudo = request.getParameter("identifiant");
		String password = request.getParameter("mdp");
		try {
			if (pseudo != " " && password != " ") {
				int idUser = umng.selectUtilisateurAuth(pseudo, password);
				if (idUser != 0) {
					request.getSession().setAttribute("userIdSessionAttr", idUser);
					request.setAttribute("userIdAttr", idUser);
					Cookie cookieIdUser = new Cookie("cookieIdUser", Integer.toString(idUser));
					cookieIdUser.setMaxAge(50000);
					response.addCookie(cookieIdUser);
				}

			}

		} catch (BusinessException e) {
			listeCodesErreur.add(CodeResultatServlet.ECHEC_LOG_USER);
			e.printStackTrace();

		}
	}

}
