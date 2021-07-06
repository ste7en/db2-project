package services;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.MarketingQuestion;
import model.MarketingQuestionID;
import model.ProductOfTheDay;

@Stateless
public class MarketingQuestionService {
	@PersistenceContext(unitName = "db2-alparone-ferrara-formicola")
	protected EntityManager em;
	
	public MarketingQuestionService() {
	}
	
	/**
	 * Service method used in the @see GoToCreationPage POST request to create a new marketing question
	 * @param number
	 * @param questionnaire_date
	 * @param text
	 * @param p ProductOfTheDay
	 * @return a new MarketingQuestion instance
	 */
	public MarketingQuestion createMarketingQuestion(int number, Date questionnaire_date, String text, ProductOfTheDay p) {
		MarketingQuestion mq = new MarketingQuestion(p, number, text);
		em.persist(mq);
		return mq;
	}
	
	/**
	 * Service method used in the @see SubmitMarketingQuestionnaire POST request to create a relative @see MarketingAnswer
	 * @param d Date
	 * @param number
	 * @return the MarketingQuestion managed entity
	 */
	public MarketingQuestion findMarketingQuestion(Date d, int number) {
		return em.find(MarketingQuestion.class, new MarketingQuestionID(number, d));
	}
	
	/**
	 * Service method used in the @see GoToMarketingQuestionnaire @see GoToInspectionPage servlets 
	 * @param d Date
	 * @return a list of marketing questions corresponding to the date d
	 */
	public List<MarketingQuestion> findByDate(Date d) {
		return em.createNamedQuery("MarketingQuestion.findByDate", MarketingQuestion.class).setParameter(1, d).getResultList();
	}
}
