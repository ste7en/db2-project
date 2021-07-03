//An INSPECTION page for accessing the data of a past questionnaire. The visualized data for a given questionnaire include
//- List of users who submitted the questionnaire. 
//- List of users who cancelled the questionnaire. 
//- Questionnaire answers of each user.

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

import model.User;
import model.StatisticalAnswer;
import model.MarketingAnswer;
import model.MarketingQuestion;
import services.MarketingAnswerService;
import services.MarketingQuestionService;
import services.StatisticalAnswerService;

/**
 * Servlet implementation class GoToInspectionPage
 */

@WebServlet("/GoToInspectionPage")
public class GoToInspectionPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	//the client(webServlet) interacts with a business object ->EJB
	@EJB(name = "db2-project.src.main.java.services/MarketingQuestionnaireService")
	private MarketingQuestionService mqService;
	@EJB(name = "db2-project.src.main.java.services/StatisticalAnswerService")
	private StatisticalAnswerService saService;
	private MarketingAnswerService maService;
	
	public GoToInspectionPage() {
		super();
		// TODO Auto-generated constructor stub
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
		
		String loginpath = getServletContext().getContextPath() + "/index.html";
		HttpSession session = request.getSession();
		if (session.isNew() || session.getAttribute("admin") == null) {
			response.sendRedirect(loginpath);
			return;
		}
		
		//to modify to show answers sorted by users
		List<StatisticalAnswer> statisticalAnswers = new ArrayList<>();
		try {
			statisticalAnswers = saService.findAllStatisticalAnswers();
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "");
			return;
		}
		
		List<MarketingAnswer> marketingAnswers= new ArrayList<>();
		try {
			marketingAnswers = maService.findAllMarketingAnswers();
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "");
			return;
		}
		
		//to add list of users who submitted the questionnaire & list of users who cancelled the questionnaire
		
		
		
		
		// If the user is not logged in (not present in session) redirect to the login
		String path = "/WEB-INF/InspectionPage.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		
		ctx.setVariable("statisticalAnswers", statisticalAnswers);
		ctx.setVariable("marketingAnswers", marketingAnswers);
		
		templateEngine.process(path, ctx, response.getWriter());

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	public void destroy() {}


}
