//A CREATION page for inserting the product of the day for the current date 
//or for a posterior date and for creating a variable number of marketing questions about such product.

package controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

import org.apache.commons.lang.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import model.Product;
import model.ProductOfTheDay;
import model.User;
import services.ProductService;
import services.UserService;
import services.ProductOfTheDayService;
import services.LogService;
import services.LogService.Events;
import services.MarketingQuestionService;

/**
 * Servlet implementation class GoToCreationPage
 */

@WebServlet("/GoToCreationPage")
public class GoToCreationPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String templatePath = "/WEB-INF/CreationPage.html";
	private TemplateEngine templateEngine;
	//the client(webServlet) interacts with a business object ->EJB
	@EJB(name = "db2-project.src.main.java.services/ProductOfTheDayService")
	private ProductOfTheDayService productOfTheDayService;
	@EJB(name = "db2-project.src.main.java.services/MarketingQuestionnaireService")
	private MarketingQuestionService marketingQuestionService;
	@EJB(name = "db2-project.src.main.java.services/ProductService")
	private ProductService productService;
	@EJB(name = "db2-project.src.main.java.services/LogService")
	private LogService logService;
	@EJB(name = "db2-project.src.main.java.services/UserService")
	private UserService userService;

	public GoToCreationPage() {
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
		ServletContext servletContext = getServletContext();
		String loginpath = servletContext.getContextPath() + "/index.html";
		HttpSession session = request.getSession();
		// If the user is not logged in (not present in session) redirect to the login
		if (session.isNew() || !(boolean)(session.getAttribute("admin"))) {
			session.invalidate();
			response.sendRedirect(loginpath);
			return;
		}
		
		List<Product> products = productService.findAllProducts();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String today = dateFormat.format(date);
		
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		
		ctx.setVariable("products", products);
		ctx.setVariable("today", today);
		ctx.setVariable("statusMsg", request.getAttribute("statusMsg"));
		
		templateEngine.process(this.templatePath, ctx, response.getWriter());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int productId = Integer.parseInt(request.getParameter("product_id"));
		Product product = productService.findProduct(productId);
		String formattedDate;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		ProductOfTheDay productOfTheDay;
		
		ServletContext servletContext = getServletContext();
		String loginpath = servletContext.getContextPath() + "/index.html";
		HttpSession session = request.getSession();
		User user = (User) userService.findUser((int)session.getAttribute("session-user-id"));
		// If the user is not logged in (not present in session) redirect to the login
		if (session.isNew() || !user.getAdmin()) {
			session.invalidate();
			response.sendRedirect(loginpath);
			return;
		}
		
		try {
			formattedDate = StringEscapeUtils.escapeJava(request.getParameter("questionnaireDate"));
			if (formattedDate == null || formattedDate.isEmpty())
				throw new ServletException("Missing or empty date value.");
			if (product == null)
				throw new RuntimeException("Product does not exist.");
			date = format.parse(formattedDate);
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
			response.sendRedirect("/GoToCreationPage");
			return;
		}
		
		if (productOfTheDayService.findProductByDate(date) != null) {
			request.setAttribute("statusMsg", "ERROR: A questionnaire already exists for the given date.");
			doGet(request, response);
			return;
		}
		
		// Creation of ProductOfTheDay
		productOfTheDay = productOfTheDayService.createProductOfTheDay(product, date);
		
		// Creation of new marketing questions for the product just inserted
		for(int i = 1; ;i++) {
			String question = request.getParameter(Integer.toString(i));
			if (question == null) break;
			marketingQuestionService.createMarketingQuestion(i, date, question, productOfTheDay);
		}
		
		logService.createInstantLog(user, Events.ADMIN_CREATED_QUESTIONNAIRE);
		
		// Questionnaire created, redirecting the user to the creation page.
		request.setAttribute("statusMsg", "Questionnaire successfully created!");
		doGet(request, response);
		return;
	}
	
}
