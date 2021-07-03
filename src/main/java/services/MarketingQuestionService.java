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
	
	public MarketingQuestion createMarketingQuestion(int number, Date questionnaire_date, String text, ProductOfTheDay p) {
		MarketingQuestion mq = new MarketingQuestion(p, number, text);
		em.persist(mq);
		return mq;
	}
	
	public void removeMarketingQuestion(Date d, int number) {
		MarketingQuestion mq = findMarketingQuestion(d, number);
		if (mq != null) {
			em.remove(mq);
		}
	}
	
	public MarketingQuestion findMarketingQuestion(Date d, int number) {
		return em.find(MarketingQuestion.class, new MarketingQuestionID(number, d));
	}
	
	public List<MarketingQuestion> findByDate(Date d) {
		return em.createNamedQuery("MarketingQuestion.findByDate", MarketingQuestion.class).setParameter(1, d).getResultList();
	}
	
	public List<MarketingQuestion> findAllMarketingQuestions(){
		return em.createNamedQuery("MarketingQuestion.findAll", MarketingQuestion.class).getResultList();
	}
}
