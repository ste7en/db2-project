package controllers;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;
import services.LogService;
import services.UserService;
import services.LogService.Events;


@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB(name = "db2-project.src.main.java.services/LogService")
	private LogService logService;
	@EJB(name = "db2-project.src.main.java.services/UserService")
	private UserService userService;
	
	public Logout() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		User user = (User) userService.findUser((int)session.getAttribute("session-user-id"));
		if (session != null || user == null) {
			session.invalidate();
			logService.createInstantLog(user, Events.LOG_OUT);
		}
		String path = getServletContext().getContextPath() + "/index.html";
		response.sendRedirect(path);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
