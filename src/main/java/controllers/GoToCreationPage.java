//A CREATION page for inserting the product of the day for the current date 
//or for a posterior date and for creating a variable number of marketing questions about such product.

package controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import model.Product;
import model.ProductOfTheDay;
import services.ProductOfTheDayService;
import services.MarketingQuestionnaireService;

/**
 * Servlet implementation class GoToCreationPage
 */

@WebServlet("/GoToCreationPage")
public class GoToCreationPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	private ProductOfTheDayService pofs;
	private MarketingQuestionnaireService mqs;
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
		Date date = new Date();  
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		String loginpath = getServletContext().getContextPath() + "/index.html";
		HttpSession session = request.getSession();
		if (session.isNew() || (boolean)(session.getAttribute("admin"))) {
			response.sendRedirect(loginpath);
			return;
		}

		// If the user is not logged in (not present in session) redirect to the login
		String path = "/WEB-INF/CreationPage.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		
		//Check if a product/Questionnaire already exists for the selected date
		
		templateEngine.process(path, ctx, response.getWriter());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Product p=null;
		String date_of_p=null;
		String text=null;
		String number=null;
		
		try {
			date_of_p= StringEscapeUtils.escapeJava(request.getParameter("date"));
			if ( date_of_p==null || date_of_p.isEmpty()) {
				throw new Exception("Missing or empty product value");
			}
		} catch (Exception e) {
			// for debugging only e.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing product value");
			return;
		}
		
		//creation of ProductOfTheDay
				DateFormat format=new SimpleDateFormat("MMMM d, yyyy", Locale.ITALIAN);
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

				try {
					text = StringEscapeUtils.escapeJava(request.getParameter("text"));
					number=StringEscapeUtils.escapeJava(request.getParameter("number"));
					if (text == null || number==null || number.isEmpty()||text.isEmpty()) {
						throw new Exception("Missing or empty marketing question value");
					}
				} catch (Exception e) {
					// for debugging only e.printStackTrace();
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing marketing question value");
					return;
				}
				
				mqs.createMarketingQuestion(Integer.parseInt(number),date_to_insert,text ,poftd);
	}
	
	public void destroy() {}

}
