//An INSPECTION page for accessing the data of a past questionnaire. The visualized data for a given questionnaire include
//- List of users who submitted the questionnaire. 
//- List of users who cancelled the questionnaire. 
//- Questionnaire answers of each user.

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

import model.StatisticalAnswer;
import model.Log;
import model.MarketingAnswer;
import model.ProductOfTheDay;
import services.LogService;
import services.MarketingAnswerService;
import services.ProductOfTheDayService;
import services.StatisticalAnswerService;

/**
 * Servlet implementation class GoToInspectionPage
 */

@WebServlet("/GoToInspectionPage")
public class GoToInspectionPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	//the client(webServlet) interacts with a business object ->EJB
	@EJB(name = "db2-project.src.main.java.services/StatisticalAnswerService")
	private StatisticalAnswerService statisticalAnswerService;
	@EJB(name = "db2-project.src.main.java.services/MarketingAnswerService")
	private MarketingAnswerService marketingAnswerService;
	@EJB(name = "db2-project.src.main.java.services/ProductOfTheDayService")
	private ProductOfTheDayService productOfTheDayService;
	@EJB(name = "db2-project.src.main.java.services/LogService")
	private LogService logService;
	
	DateFormat dateFormat;
	Date date;
	String today;
	
	public GoToInspectionPage() {
		super();
		this.dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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
		if (session.isNew() || !(boolean)session.getAttribute("admin")) {
			session.invalidate();
			response.sendRedirect(loginpath);
			return;
		}
		
		this.date = new Date();
		this.today = dateFormat.format(date);
		
		String path = "/WEB-INF/InspectionPage.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		ctx.setVariable("today", today);
		templateEngine.process(path, ctx, response.getWriter());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String path = "/WEB-INF/InspectionPage.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		
		String dateOfQuestionnaire=null;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ITALIAN);
		Date dateToInsert = null;
		List<StatisticalAnswer> statisticalAnswers = null;
		List<MarketingAnswer> marketingAnswers = null;
		List<Log> logs;
		
		this.date = new Date();
		this.today = dateFormat.format(date);
		
		String loginpath = servletContext.getContextPath() + "/index.html";
		HttpSession session = request.getSession();
		if (session.isNew() || session.getAttribute("admin") == null) {
			response.sendRedirect(loginpath);
			return;
		}
		
		try {
			dateOfQuestionnaire = StringEscapeUtils.escapeJava(request.getParameter("questionnaireDate"));
			if (dateOfQuestionnaire == null || dateOfQuestionnaire.isEmpty()) {
				throw new Exception("Missing or empty questionnaire to delete value");
			}
		} catch (Exception e) {
			// for debugging only e.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing questionnaire to delete value");
			return;
		}
		
		try {
			dateToInsert = format.parse(dateOfQuestionnaire);
		} catch (ParseException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}
		
		Date date = new Date();
		
		if(dateToInsert.after(date)) {
			ctx.setVariable("statusMsg", "ERROR: You cannot inspect a questionnaire of a future date");
		}
		
		
		ProductOfTheDay productOfTheDay = productOfTheDayService.findProductByDate(dateToInsert);
		
		if(productOfTheDay == null) {
			ctx.setVariable("statusMsg", "ERROR: We're sorry, there is no product of the day for the selected day.");
		} else {
			statisticalAnswers = statisticalAnswerService.findByDate(dateToInsert);
			marketingAnswers = marketingAnswerService.findByDate(dateToInsert);
		}
		
		logs = logService.findByDate(dateToInsert);
		
		ctx.setVariable("statisticalAnswers", statisticalAnswers);
		ctx.setVariable("marketingAnswers", marketingAnswers);
		ctx.setVariable("today", today);
		ctx.setVariable("logs", logs);

		templateEngine.process(path, ctx, response.getWriter());
	}
}
