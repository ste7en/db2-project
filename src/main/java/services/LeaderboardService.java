package services;

import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.Leaderboard;

@Stateless
public class LeaderboardService {
	@PersistenceContext(unitName = "db2-alparone-ferrara-formicola")
	protected EntityManager em;
	
	public LeaderboardService() {
	}
	
	public Leaderboard createLeaderboard(int user, Date questionnaire_date) {
		Leaderboard l= new Leaderboard();
		l.setUser(user);
		l.setQuestionnaire_date(questionnaire_date);
		l.setPoints(0);
		em.persist(l);
		return l;
	}
	
	public Leaderboard findLeaderboardByUser(int user) {
		return em.find(Leaderboard.class, user);
	}
	
	
	
	
	

}
