//An INSPECTION page for accessing the data of a past questionnaire. The visualized data for a given questionnaire include
//- List of users who submitted the questionnaire. 
//- List of users who cancelled the questionnaire. 
//- Questionnaire answers of each user.

package controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import model.StatisticalAnswer;
import model.MarketingAnswer;
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
	@EJB(name = "db2-project.src.main.java.services/MarketingAnswerService")
	private MarketingAnswerService maService;
	DateFormat dateFormat;
	Date date;
	String today;
	
	public GoToInspectionPage() {
		super();
		this.dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		this.date = new Date();
		this.today = dateFormat.format(date);
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
		if (session.isNew() || session.getAttribute("admin") == null || (boolean)session.getAttribute("admin") == false) {
			response.sendRedirect(loginpath);
			return;
		}	
		
		String path = "/WEB-INF/InspectionPage.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		ctx.setVariable("today", today);
		templateEngine.process(path, ctx, response.getWriter());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String date_of_questionnaire=null;
		
		String loginpath = getServletContext().getContextPath() + "/index.html";
		HttpSession session = request.getSession();
		if (session.isNew() || session.getAttribute("admin") == null) {
			response.sendRedirect(loginpath);
			return;
		}
		
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
			throw new RuntimeException("You cannot show data for a not yet created questionnaire");
		}
		
		//to modify to show answers sorted by users
		List<StatisticalAnswer> statisticalAnswers = new ArrayList<>();
		try {
			statisticalAnswers = saService.findByDate(date_to_insert);
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "");
			return;
		}
		
		List<MarketingAnswer> marketingAnswers= new ArrayList<>();
		try {
			marketingAnswers = maService.findByDate(date_to_insert);
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "");
			return;
		}
		
		String path = "/WEB-INF/InspectionPage.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		
		ctx.setVariable("statisticalAnswers", statisticalAnswers);
		ctx.setVariable("marketingAnswers", marketingAnswers);
		ctx.setVariable("today", today);

		templateEngine.process(path, ctx, response.getWriter());
	}

}
