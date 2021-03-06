package controllers;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

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

import model.Product;
import model.ProductOfTheDay;
import model.User;
import services.MarketingAnswerService;
import services.ProductOfTheDayService;
import services.ProductService;
import services.UserService;

/**
 * Servlet implementation class GoToHomePage
 */

@WebServlet("/GoToHomePage")
public class GoToHomePage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	//the client(webServlet) interacts with a business object ->EJB
	@EJB(name = "db2-project.src.main.java.services/ProductOfTheDayService")
	private ProductOfTheDayService productOfTheDayService;
	@EJB(name = "db2-project.src.main.java.services/ProductService")
	private ProductService productService;
	@EJB(name = "db2-project.src.main.java.services/MarketingAnswerService")
	private MarketingAnswerService marketingAnswerService;
	@EJB(name = "db2-project.src.main.java.services/UserService")
	private UserService userService;

	public GoToHomePage() {
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

	@SuppressWarnings("deprecation")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user;
		Date sessionDate;
		Product productOfTheDay = null;
		Map<User, String> reviews = null;
		
		// If the user is not logged in (not present in session) redirect to the login
		String loginpath = getServletContext().getContextPath() + "/index.html";
		HttpSession session = request.getSession();

		user = (User) userService.findUser((int)session.getAttribute("session-user-id"));
		sessionDate = (Date) session.getAttribute("session-date");
		
		if (session.isNew() || user == null || user.getBlocked()) {
			session.invalidate();
			response.sendRedirect(loginpath);
			return;
		}
		
		// If the user returns to the home page after logging in a check on the session 
		// date is performed to set it to the new date in case of the get is performed a day after
		if (sessionDate == null || sessionDate.getDay() != new Date().getDay()) {
			sessionDate = new Date();
			session.setAttribute("session-date", sessionDate);
		}
		
		ProductOfTheDay potd = productOfTheDayService.findProductByDate(sessionDate);
		if (potd != null) {
			productOfTheDay = potd.getProduct();
			reviews = productOfTheDay.getReviews();
		}
		
		String path = "/WEB-INF/Home.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		
		ctx.setVariable("user", user);
		ctx.setVariable("product", productOfTheDay);
		ctx.setVariable("reviews", reviews);
		ctx.setVariable("userHasAlreadyFilled", marketingAnswerService.checkIfExists(user, sessionDate));
		
		templateEngine.process(path, ctx, response.getWriter());
	}

}
