//A DELETION page for ERASING the questionnaire data and the related responses and points of all users who filled in the questionnaire. 
//Deletion should be possible only for a date preceding the current date.

package controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
import model.StatisticalAnswer;
import model.MarketingQuestion;
import services.UserService;
import services.StatisticalAnswerService;
import services.LeaderboardService;
import services.MarketingQuestionnaireService;

/**
 * Servlet implementation class GoToDeletionPage
 */

@WebServlet("/GoToDeletionPage")
public class GoToDeletionPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	//the client(webServlet) interacts with a business object ->EJB
	@EJB(name = "db2-project.src.main.java.services/MarketingQuestionnaireService")
	private MarketingQuestionnaireService mqService;
	@EJB(name = "db2-project.src.main.java.services/StatisticalAnswerService")
	private StatisticalAnswerService saService;
	private UserService us;
	private LeaderboardService ls;

	public GoToDeletionPage() {
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
		Date date = new Date();  
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String loginpath = getServletContext().getContextPath() + "/index.html";
		HttpSession session = request.getSession();
		if (session.isNew() || session.getAttribute("admin") == null) {
			response.sendRedirect(loginpath);
			return;
		}
		
		// If the user is not logged in (not present in session) redirect to the login
		String path = "/WEB-INF/DeletionPage.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		templateEngine.process(path, ctx, response.getWriter());

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String date_of_questionnaire=null;
		
		
		try {
			date_of_questionnaire= StringEscapeUtils.escapeJava(request.getParameter("questionnaireDate"));
			if ( date_of_questionnaire==null|| date_of_questionnaire.isEmpty()) {
				throw new Exception("Missing or empty questionnaire to delete value");
			}
		} catch (Exception e) {
			// for debugging only e.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing questionnaire to delete value");
			return;
		}
		
		//deletion of questionnaire data
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd", Locale.ITALIAN);
		Date date_to_insert=null;
		try {
			date_to_insert = format.parse(date_of_questionnaire);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Date date= new Date();
		if(date_to_insert.after(date)) {
			throw new RuntimeException("You cannot delete a questionnaire that has the current date or higher");
		}
		
		List<MarketingQuestion> questionnaire= mqService.findByDate(date_to_insert);
		if(questionnaire!=null) {
			for(MarketingQuestion mq : questionnaire) {
				questionnaire.remove(mq);
			}
		}	
	}
	
	public void destroy() {}

}
