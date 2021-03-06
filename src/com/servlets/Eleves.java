package com.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth.Password;
import com.auth.Roles;
import com.beans.Classe;
import com.beans.ClasseBean;
import com.beans.CoursBean;
import com.beans.Personne;
import com.beans.PersonneBean;
import com.beans.RoleBean;

public class Eleves extends HttpServlet {

	private static List<Roles> autorized = Arrays.asList(Roles.ADMIN, Roles.DIRECTEUR);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// Redirect if not logged
			if (req.getSession().getAttribute("user") == null) {
				resp.sendRedirect(req.getContextPath() + "/home");
				return;
			}

			// Redirect if not authorized
			Personne user = (Personne) req.getSession().getAttribute("user");
			if (!autorized.contains(user.getRole().getEnum())) {
				resp.sendRedirect(req.getContextPath() + "/home");
				return;
			}

			PersonneBean pb = new PersonneBean();
			List<Personne> eleves = new ArrayList<Personne>();
			eleves = pb.getAll().stream().filter(p -> p.getRole().getId() == Roles.ELEVE.getId())
					.collect(Collectors.toList());
			
			ClasseBean classeBean = new ClasseBean();
			List<Classe> classes = classeBean.getAll();

			req.setAttribute("eleves", eleves);
			req.setAttribute("classes", classes);

			this.getServletContext().getRequestDispatcher("/WEB-INF/Eleves.jsp").forward(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Redirect if not logged
		if (req.getSession().getAttribute("user") == null) {
			resp.sendRedirect(req.getContextPath() + "/home");
			return;
		}

		// Redirect if not authorized
		Personne user = (Personne) req.getSession().getAttribute("user");
		if (!autorized.contains(user.getRole().getEnum())) {
			resp.sendRedirect(req.getContextPath() + "/home");
			return;
		}

		switch (req.getParameter("action")) {
		case "ajouterEleve":
			AjouterEleve(req, resp);
			break;
		case "supprimerEleve":
			SupprimerEleve(req, resp);
			break;
		case "modifierEleve":
			ModifierEleve(req, resp);
			break;
		}
	}

	private void ModifierEleve(HttpServletRequest req, HttpServletResponse resp) {
		try {
			PersonneBean personneBean = new PersonneBean();

			int idEleve = Integer.parseInt(req.getParameter("idEleve_modifier"));
			
			String prenom = req.getParameter("prenom");
			String nom = req.getParameter("nom");
			int idClasse = Integer.parseInt(req.getParameter("idClasse"));
			
			ClasseBean classesBean = new ClasseBean();
			Classe classe = classesBean.get(idClasse);
			Personne personne = personneBean.get(idEleve);
			personne.setNom(nom);
			personne.setPrenom(prenom);
			personne.setClasse(classe);
			
			personneBean.update(personne);
			
			resp.sendRedirect(req.getContextPath() + "/eleves");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void SupprimerEleve(HttpServletRequest req, HttpServletResponse resp) {
		try {
			int idEleve = Integer.parseInt(req.getParameter("idEleve"));
			
			PersonneBean personneBean = new PersonneBean();
			personneBean.delete(personneBean.get(idEleve));
			
			resp.sendRedirect(req.getContextPath() + "/eleves");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void AjouterEleve(HttpServletRequest req, HttpServletResponse resp) {
		try {
			String prenom = req.getParameter("prenom");
			String nom = req.getParameter("nom");
			int idClasse = Integer.parseInt(req.getParameter("idClasse"));
			
			ClasseBean classesBean = new ClasseBean();
			Classe classe = classesBean.get(idClasse);
			Personne personne = new Personne();
			personne.setNom(nom);
			personne.setPrenom(prenom);
			personne.setClasse(classe);
			RoleBean roleBean = new RoleBean();
			personne.setRole(roleBean.get(Roles.ELEVE.getId()));
			personne.setHash(Password.getSaltedHash(personne.getLogin()));
			
			PersonneBean eleveBean = new PersonneBean();
			eleveBean.insert(personne);
			
			resp.sendRedirect(req.getContextPath() + "/eleves");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
