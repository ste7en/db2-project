package controllers;

import java.io.IOException;
import java.util.Date;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import model.User;
import services.UserService;


@WebServlet("/CheckLogin")
public class CheckLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "db2-project.src.main.java.services/UserService")
	private UserService userService;

	public CheckLogin() {
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// obtain and escape params
		String username = null;
		String password = null;
		String path;
		Date sessionDate = new Date();
		HttpSession session = request.getSession();
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		
		try {
			username = StringEscapeUtils.escapeJava(request.getParameter("username"));
			password = StringEscapeUtils.escapeJava(request.getParameter("password"));
			if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
				throw new Exception("Missing or empty credential values.");
			}
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
			return;
		}
		User user =  userService.checkCredentials(username, password);

		// If the user exists, add info to the session and go to home page, otherwise
		// show login page with error message
		if (user == null || user.getBlocked()) {
			if (user == null)
				ctx.setVariable("errorMsg", "Incorrect username or password");
			else if (user.getBlocked())
				ctx.setVariable("errorMsg", "You have been blocked from the application.");
			path = "/index.html";
			templateEngine.process(path, ctx, response.getWriter());
		} else {
			session.setAttribute("session-user", user);
			session.setAttribute("session-user-id", user.getId());
			session.setAttribute("admin", user.getAdmin());
			session.setAttribute("session-date", sessionDate);
			path = servletContext.getContextPath() + "/GoToHomePage";
			response.sendRedirect(path);
		}

	}
}