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
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import model.MarketingAnswer;
import model.MarketingQuestion;
import model.User;
import services.MarketingQuestionService;

/**
 * Servlet implementation class SubmitMarketingQuestionnaire
 */

@WebServlet("/SubmitMarketingQuestionnaire")
public class SubmitMarketingQuestionnaire extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	//the client(webServlet) interacts with a business object ->EJB
	@EJB(name = "db2-project.src.main.java.services/MarketingQuestionnaireService")
	private MarketingQuestionService marketingQuestionService;
	
	public SubmitMarketingQuestionnaire() {
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
		ServletContext servletContext = getServletContext();
		HttpSession session = request.getSession();
		Date sessionDate = (Date) session.getAttribute("session-date");
		User user = (User) session.getAttribute("session-user");

		// If the user is not logged in (not present in session) redirect to the login
		String loginpath = servletContext.getContextPath() + "/index.html";
		if (session.isNew() || user == null) {
			response.sendRedirect(loginpath);
			return;
		}
		
		List<MarketingAnswer> answers = new ArrayList<MarketingAnswer>();
		
		//creation of marketing answers
		for(int i = 1; ;i++) {
			String answer = request.getParameter(Integer.toString(i));
			if (answer == null) break;
			MarketingQuestion mquestion = marketingQuestionService.findMarketingQuestion(sessionDate, i);
			if (mquestion == null)
				throw new ServletException("Question q of session date " + sessionDate.toString() + " and number " + i + " does not exist.");
			answers.add(new MarketingAnswer(user, mquestion, answer));
		}
		session.setAttribute("marketing-answers", answers);
		
		String path = servletContext.getContextPath() + "/GoToStatisticalQuestionnaire";
		response.sendRedirect(path);
	}
}
