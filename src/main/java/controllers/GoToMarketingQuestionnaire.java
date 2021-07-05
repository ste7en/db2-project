package controllers;

import java.io.IOException;
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

import model.MarketingQuestion;
import model.User;
import services.LogService;
import services.LogService.Events;
import services.MarketingQuestionService;

/**
 * Servlet implementation class GoToMarketingQuestionnaire
 */


@WebServlet("/GoToMarketingQuestionnaire")
public class GoToMarketingQuestionnaire extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "db2-project.src.main.java.services/MarketingQuestionnaireService")
	private MarketingQuestionService marketingQuestionService;
	@EJB(name = "db2-project.src.main.java.services/LogService")
	private LogService logService;
	
	public GoToMarketingQuestionnaire() {
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

		// If the user is not logged in (not present in session) redirect to the login
		String loginpath = getServletContext().getContextPath() + "/index.html";
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("session-user");
		if (session.isNew() || user == null) {
			response.sendRedirect(loginpath);
			return;
		}
		
		logService.createInstantLog(user, Events.QUESTIONNAIRE_STARTED);
		
		Date sessionDate = (Date) session.getAttribute("session-date");
		List<MarketingQuestion> marketingQuestions = marketingQuestionService.findByDate(sessionDate);
		
		// Redirect to the Home page and add missions to the parameters	
		String path = "/WEB-INF/MarketingQuestionnairePage.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		ctx.setVariable("marketingQuestions", marketingQuestions);
		templateEngine.process(path, ctx, response.getWriter());
	}

}