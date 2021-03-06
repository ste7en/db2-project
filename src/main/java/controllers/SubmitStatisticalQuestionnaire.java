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

import model.MarketingAnswer;
import model.StatisticalAnswer;
import model.User;
import services.MarketingAnswerService;
import services.StatisticalAnswerService;
import services.UserService;

/**
 * Servlet implementation class SubmitStatisticalQuestionnaire
 */

@WebServlet("/SubmitStatisticalQuestionnaire")
public class SubmitStatisticalQuestionnaire extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	//the client(webServlet) interacts with a business object ->EJB
	@EJB(name = "db2-project.src.main.java.services/StatisticalAnswerService")
	private StatisticalAnswerService statisticalAnswerService;
	@EJB(name = "db2-project.src.main.java.services/MarketingAnswerService")
	private MarketingAnswerService marketingAnswerService;
	@EJB(name = "db2-project.src.main.java.services/UserService")
	private UserService userService;
	
	public SubmitStatisticalQuestionnaire() {
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
		HttpSession session = request.getSession();
		ServletContext servletContext = getServletContext();

		Date sessionDate = (Date) session.getAttribute("session-date");
		User user = (User) userService.findUser((int)session.getAttribute("session-user-id"));
		
		// Retrieving the list of marketing answers saved in the client's session
		@SuppressWarnings("unchecked")
		List<MarketingAnswer> answers = (List<MarketingAnswer>) session.getAttribute("marketing-answers");

		// If the user is not logged in (not present in session) redirect to the login
		String loginpath = servletContext.getContextPath() + "/index.html";
		if (session.isNew() || user == null) {
			session.invalidate();
			response.sendRedirect(loginpath);
			return;
		} else if (answers == null) {
			throw new ServletException("The user submitted a statistical questionnaire before the marketing questionnaire.");
		}
		
		Integer age = null;
		Character sex = null;
		Integer experience = null;
		
		try { age = Integer.parseInt(request.getParameter("age")); } catch (Exception e) {}
		try { sex = request.getParameter("gender").charAt(0); } catch (Exception e) {}
		try { experience = Integer.parseInt(request.getParameter("experience")); } catch (Exception e) {}
		
		StatisticalAnswer statisticalAnswer = new StatisticalAnswer(user, sessionDate, age, sex, experience);
		
		// Calling business components to save the answers to the questionnaires
		marketingAnswerService.saveMarketingAnswers(answers);
		statisticalAnswerService.saveStatisticalAnswer(statisticalAnswer);

		String path = "/WEB-INF/ThanksPage.html";
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		
		templateEngine.process(path, ctx, response.getWriter());
	}
}
