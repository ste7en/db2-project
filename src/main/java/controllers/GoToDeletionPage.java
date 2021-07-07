//A DELETION page for ERASING the questionnaire data and the related responses and points of all users who filled in the questionnaire. 
//Deletion should be possible only for a date preceding the current date.

package controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import services.StatisticalAnswerService;
import services.UserService;
import services.LogService;
import services.LogService.Events;
import services.MarketingQuestionService;
import services.ProductOfTheDayService;

/**
 * Servlet implementation class GoToDeletionPage
 */

@WebServlet("/GoToDeletionPage")
public class GoToDeletionPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	//the client(webServlet) interacts with a business object ->EJB
	@EJB(name = "db2-project.src.main.java.services/MarketingQuestionnaireService")
	private MarketingQuestionService marketingQuestionService;
	@EJB(name = "db2-project.src.main.java.services/StatisticalAnswerService")
	private StatisticalAnswerService statisticalAnswerService;
	@EJB(name = "db2-project.src.main.java.services/ProductOfTheDayService")
	private ProductOfTheDayService productOfTheDayService;
	@EJB(name = "db2-project.src.main.java.services/LogService")
	private LogService logService;
	@EJB(name = "db2-project.src.main.java.services/UserService")
	private UserService userService;
	private DateFormat dateFormat;
	
	public GoToDeletionPage() {
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
		// If the user is not logged in (not present in session) redirect to the login
		if (session.isNew() || !(boolean)session.getAttribute("admin")) {
			session.invalidate();
			response.sendRedirect(loginpath);
			return;
		}
		
		String path = "/WEB-INF/DeletionPage.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		
		// Getting yesterday's date to forbid the deletion of a questionnaire
		// corresponding to the current date (see project specifications)
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DATE, -1);
		String yesterday = this.dateFormat.format(date.getTime());

		ctx.setVariable("minDate", yesterday);
		ctx.setVariable("statusMsg", request.getAttribute("statusMsg"));
		templateEngine.process(path, ctx, response.getWriter());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String formattedDate;
		Date date;
		
		String loginpath = getServletContext().getContextPath() + "/index.html";
		HttpSession session = request.getSession();
		User user = (User)userService.findUser((int)session.getAttribute("session-user-id"));
		// If the user is not logged in (not present in session) redirect to the login
		if (session.isNew() || !user.getAdmin()) {
			session.invalidate();
			response.sendRedirect(loginpath);
			return;
		}
		
		try {
			formattedDate = StringEscapeUtils.escapeJava(request.getParameter("questionnaireDate"));
			if (formattedDate == null || formattedDate.isEmpty())
				throw new Exception("Missing or empty questionnaire to delete value");
			
			date = dateFormat.parse(formattedDate);
			
			if(date.after(new Date()))
				throw new RuntimeException("You cannot delete a questionnaire that has the current date or higher");
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
			return;
		}
		// By deleting a relationship of product of the day between a product and a date
		// the whole questionnaires and their answers get deleted by a cascade
		if (productOfTheDayService.removeProductOfTheDay(date))	{
			request.setAttribute("statusMsg", formattedDate + " " + "questionnaire, its answers and user points have successfully been deleted.");
			logService.createInstantLog(user, Events.ADMIN_DELETED_QUESTIONNAIRE);
		} else
			request.setAttribute("statusMsg", "ERROR: Couldn't find any questionnaire to delete.");
		
		doGet(request, response);
		return;
	}
}
