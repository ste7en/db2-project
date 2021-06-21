package controllers;

import java.io.IOException;
import java.util.Date;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import model.Product;
import model.ProductOfTheDay;
import model.User;
import services.ProductOfTheDayService;
import services.ProductService;

/**
 * Servlet implementation class GoToHomePage
 */

@WebServlet("/GoToHomePage")
public class GoToHomePage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	//the client(webServlet) interacts with a business object ->EJB
	@EJB(name = "db2-project.src.main.java.services/ProductOfTheDayService")
	private ProductOfTheDayService potdService;
	@EJB(name = "db2-project.src.main.java.services/ProductService")
	private ProductService pService;
	private Date d;

	public GoToHomePage() {
		super();
		// After auto-generated constructor stub
		this.d = new Date();
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

		User userId = (User) request.getSession().getAttribute("session-user");
		
		ProductOfTheDay productOtd = potdService.findProductByDate(d);
		Product product= (Product) pService.getReviews(productOtd.getProduct());
		
		
		// If the user is not logged in (not present in session) redirect to the login
		String path = "/WEB-INF/Home.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		
		ctx.setVariable("product of the day", productOtd);
		ctx.setVariable("product", product);
		
		templateEngine.process(path, ctx, response.getWriter());

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
