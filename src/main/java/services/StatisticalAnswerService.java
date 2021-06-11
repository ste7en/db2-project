package services;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import model.StatisticalAnswer;

public class StatisticalAnswerService {
	
protected EntityManager em;
	
	public StatisticalAnswerService(EntityManager em) {
		this.em=em;
	}
	
	public StatisticalAnswer createStatisticalAnswer(int user_id, int age, String sex, String experience) {
		StatisticalAnswer sa= new StatisticalAnswer();
		sa.setUserId(user_id);
		sa.setAge(age);
		sa.setSex(sex);
		sa.setExperience(experience);
		em.persist(sa);
		return sa;
	}
	
	public StatisticalAnswer findStatisticalAnswer(int user_id) {
		return em.find(StatisticalAnswer.class, user_id);
	}

	public void removeProduct(int user_id) {
		StatisticalAnswer sa=findStatisticalAnswer(user_id);
		if(sa!=null)
			em.remove(sa);
	}
	
	public Collection<StatisticalAnswer> findAllStatisticalAnswers(){
		TypedQuery query=em.createQuery("SELECT sa FROM StatisticalAnswer sa", StatisticalAnswer.class);
		return query.getResultList();
	}

}
