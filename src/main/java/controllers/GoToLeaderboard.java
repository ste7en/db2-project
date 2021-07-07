package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import model.Leaderboard;
import model.User;
import services.LeaderboardService;
import services.UserService;

/**
 * Servlet implementation class GoToLeaderBoard
 */

@WebServlet("/GoToLeaderboard")
public class GoToLeaderboard extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	//the client(webServlet) interacts with a business object ->EJB
	@EJB(name = "db2-project.src.main.java.services/LeaderboardService")
	private LeaderboardService leaderboardService;
	@EJB(name = "db2-project.src.main.java.services/UserService")
	private UserService userService;

	public GoToLeaderboard() {
		super();
	}

	public void init() throws ServletException {
		ServletContext servletContext = getServletContext();
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
		templateResolver.setTemplateMode(TemplateMode.HTML);
		this.templateEngine = new TemplateEngine();
		this.templateEngine.setTemplateResolver(templateResolver);
		templateResolver.setSuffix(".html");		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		Date date = new Date();  
		User user = (User) userService.findUser((int)session.getAttribute("session-user-id"));

		String loginpath = getServletContext().getContextPath() + "/index.html";
		
		if (session.isNew() || user == null || user.getBlocked()) {
			session.invalidate();
			response.sendRedirect(loginpath);
			return;
		}
		
		List<Leaderboard> leaderboards = new ArrayList<>();
		try {
			leaderboards = leaderboardService.findLeaderboardsByDate(date);
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing Leaderbord for this day!");
			return;
		}
		
		
		// If the user is not logged in (not present in session) redirect to the login
		String path = "/WEB-INF/LeaderBoard.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		ctx.setVariable("leaderboards", leaderboards);
		ctx.setVariable("leaderboardService", leaderboardService);
		templateEngine.process(path, ctx, response.getWriter());

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	public void destroy() {}

}
