package services;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import model.StatisticalAnswer;
import model.User;

@Stateless
public class StatisticalAnswerService {
	@PersistenceContext(unitName = "db2-alparone-ferrara-formicola")
	protected EntityManager em;
	@EJB(name = "db2-project.src.main.java.services/UserService")
	private UserService userService;
	
	public StatisticalAnswerService() {}
	
	public StatisticalAnswer createStatisticalAnswer(int user_id, Date d, int age, char sex, int experience) {
		User u = new UserService().findUser(user_id);
		StatisticalAnswer sa = new StatisticalAnswer(u, d, age, sex, experience);
		em.persist(sa);
		return sa;
	}
	
	public void saveStatisticalAnswer(StatisticalAnswer answer) {
		final User u = userService.findUser(answer.getUser().getId());
		if (!u.getBlocked())
			em.persist(answer);
	}
	
	public StatisticalAnswer findStatisticalAnswer(int user_id) {
		return em.find(StatisticalAnswer.class, user_id);
	}

	public void removeProduct(int user_id) {
		StatisticalAnswer sa=findStatisticalAnswer(user_id);
		if(sa!=null)
			em.remove(sa);
	}
	
	public List<StatisticalAnswer> findByDate(Date d) {
		TypedQuery<StatisticalAnswer> query = em.createNamedQuery("StatisticalAnswer.findByDate", StatisticalAnswer.class).setParameter(1, d);
		return query.getResultList();
	}
	
	public StatisticalAnswer findByUserAndDate(User u, Date d) {
		TypedQuery<StatisticalAnswer> query = em.createNamedQuery("StatisticalAnswer.findByUserAndDate", StatisticalAnswer.class).setParameter(1, d).setParameter(2, u);
		return query.getSingleResult();
	}

}
