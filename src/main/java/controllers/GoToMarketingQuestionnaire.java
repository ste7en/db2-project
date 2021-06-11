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
import services.MarketingQuestionnaireService;
import services.ProductOfTheDayService;

/**
 * Servlet implementation class GoMarketingQuestionnaire
 */


@WebServlet("/GotoMarketingQuestionnaire")
public class GoToMarketingQuestionnaire extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "db2-project.src.main.java.services/MarketingQuestionnaireService")
	private MarketingQuestionnaireService mqService;
	private ProductOfTheDayService pService;

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
		if (session.isNew() || session.getAttribute("user") == null) {
			response.sendRedirect(loginpath);
			return;
		}
		Date d= new Date();
		List<MarketingQuestion> marketingQuestions= mqService.findByDate(d);
		

		// Redirect to the Home page and add missions to the parameters	
		String path = "/WEB-INF/Marketing.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		ctx.setVariable("marketing questions", marketingQuestions);
		templateEngine.process(path, ctx, response.getWriter());
		
	}

	public void destroy() {
	}

}