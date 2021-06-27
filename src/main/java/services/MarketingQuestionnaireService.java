package services;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import model.MarketingQuestion;
import model.ProductOfTheDay;

@Stateless
public class MarketingQuestionnaireService {
	@PersistenceContext(unitName = "db2-alparone-ferrara-formicola")
	protected EntityManager em;
	
	public MarketingQuestionnaireService() {
	}
	
	public MarketingQuestion createMarketingQuestion(int number, Date questionnaire_date, String text, ProductOfTheDay p) {
		MarketingQuestion mq = new MarketingQuestion(p, number, text);
		em.persist(mq);
		return mq;
	}
	
	public void removeMarketingQuestion(int number) {
		MarketingQuestion mq= findMarketingQuestion(number);
		if(mq!=null) {
			em.remove(mq);
		}
	}
	
	public MarketingQuestion findMarketingQuestion(int number) {
		return em.find(MarketingQuestion.class,number);
	}
	
	public MarketingQuestion findbyProductOfTheDay(ProductOfTheDay p) {
		return em.find(MarketingQuestion.class, p);
	}
	
	public List<MarketingQuestion> findByDate(Date d) {
		return (em.createQuery("SELECT mq from MarketingQuestion mq WHERE mq.questionnaire_date=?1", MarketingQuestion.class).setParameter(1, d)).getResultList();
	}
	
	public List<MarketingQuestion> findAllMarketingQuestions(){
		TypedQuery<MarketingQuestion> query= em.createQuery("SELECT mq from MarketingQuestion mq", MarketingQuestion.class);
		return query.getResultList();
	}
	
	public void destroy() {}
}
