//A CREATION page for inserting the product of the day for the current date 
//or for a posterior date and for creating a variable number of marketing questions about such product.

package controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
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
import services.ProductService;
import services.ProductOfTheDayService;
import services.MarketingQuestionService;

/**
 * Servlet implementation class GoToCreationPage
 */

@WebServlet("/GoToCreationPage")
public class GoToCreationPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "db2-project.src.main.java.services/ProductOfTheDayService")
	private ProductOfTheDayService pofs;
	@EJB(name = "db2-project.src.main.java.services/MarketingQuestionnaireService")
	private MarketingQuestionService mqs;
	@EJB(name = "db2-project.src.main.java.services/ProductService")
	private ProductService ps;
	//the client(webServlet) interacts with a business object ->EJB

	public GoToCreationPage() {
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
		if (session.isNew() || !(boolean)(session.getAttribute("admin"))) {
			response.sendRedirect(loginpath);
			return;
		}
		List<Product> products = ps.findAllProducts();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String today = dateFormat.format(date);
		
		// If the user is not logged in (not present in session) redirect to the login
		String path = "/WEB-INF/CreationPage.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		
		ctx.setVariable("products", products);
		ctx.setVariable("today", today);
		//Check if a product/Questionnaire already exists for the selected date
		
		templateEngine.process(path, ctx, response.getWriter());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Product p = ps.findProduct(Integer.parseInt(request.getParameter("product_id")));
		String date_of_p=null;
		try {
			date_of_p = StringEscapeUtils.escapeJava(request.getParameter("questionnaireDate"));
			if ( date_of_p==null || date_of_p.isEmpty()) {
				throw new Exception("Missing or empty product value");
			}
		} catch (Exception e) {
			// for debugging only e.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing product value");
			return;
		}
		
		//creation of ProductOfTheDay
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		Date date_to_insert=null;
		try {
			date_to_insert = format.parse(date_of_p);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ProductOfTheDay poftd=null;
		if(p!=null) {
			poftd=pofs.createProductOfTheDay(p, date_to_insert);
		}
		
		//creation of new marketing questions for the product just inserted
		for(int i = 1; ;i++) {
			String question = request.getParameter(Integer.toString(i));
			if (question == null) break;
			mqs.createMarketingQuestion(i, date_to_insert, question, poftd);
		}
		response.sendRedirect(getServletContext().getContextPath() + "/GoToCreationPage");
	}
	
}
