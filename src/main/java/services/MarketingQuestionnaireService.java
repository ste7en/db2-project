package services;

import java.util.Collection;

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
		MarketingQuestion mq= new MarketingQuestion();
		mq.setProductOfTheDay(p);
		mq.setNumber(number);
		mq.setQuestionnaireDate(questionnaire_date);
		mq.setText(text);
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
		return (em.createQuery("SELECT mq from marketing_question mq WHERE mq.date=d", MarketingQuestion.class)).getResultList();
	}
	
	public Collection<MarketingQuestion> findAllMarketingQuestions(){
		TypedQuery query= em.createQuery("SELECT mq from MARKETINGQUESTION mq", MarketingQuestion.class);
		return query.getResultList();
	}
	
	public void destroy() {}
}
