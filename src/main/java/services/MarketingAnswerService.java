package services;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import model.MarketingAnswer;
import model.MarketingQuestion;
import model.User;

@Stateless
public class MarketingAnswerService {
	@PersistenceContext(unitName = "db2-alparone-ferrara-formicola")
	protected EntityManager em;
	
	public MarketingAnswerService() {}
	
	public MarketingAnswer createMarketingAnswer(User user, MarketingQuestion question, String answer) {
		MarketingAnswer ma= new MarketingAnswer(user,question,answer);
		em.persist(ma);
		return ma;
	}
	
	public void saveMarketingAnswers(List<MarketingAnswer> answers) {
		for (MarketingAnswer answer : answers) {					
			em.persist(answer);
		}
	}
	
	public List<MarketingAnswer> findAllMarketingAnswers(){
		TypedQuery<MarketingAnswer> query= em.createQuery("SELECT ma from MarketingAnswer ma", MarketingAnswer.class);
		return query.getResultList();
	}
	
	public List<MarketingAnswer> findByUserAndDate(User u, Date d){
		TypedQuery<MarketingAnswer> query= em.createNamedQuery("MarketingAnswer.findByUserIdAndDate", MarketingAnswer.class)
				.setParameter(1, u).setParameter(2, d);
		return query.getResultList();
	}
	
	public List<MarketingAnswer> findByDate(Date d){
		TypedQuery<MarketingAnswer> query= em.createNamedQuery("MarketingAnswer.findByDate", MarketingAnswer.class)
				.setParameter(1, d);
		return query.getResultList();
	}
	
	public boolean checkIfExists(User u, Date d) {
		return !findByUserAndDate(u, d).isEmpty();
	}
}
