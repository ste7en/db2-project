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
import services.LogService.Events;

/**
 * Servlet implementation class CancelQuestionnaire
 */

@WebServlet("/CancelQuestionnaire")
public class CancelQuestionnaire extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//the client(webServlet) interacts with a business object ->EJB
	@EJB(name = "db2-project.src.main.java.services/LogService")
	private LogService logService;

	public CancelQuestionnaire() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("session-user");

		if (user != null) {
			logService.createInstantLog(user, Events.QUESTIONNAIRE_CANCELLED);
		}
		
		String homePath = getServletContext().getContextPath() + "/GoToHomePage";
		response.sendRedirect(homePath);
	}
}
