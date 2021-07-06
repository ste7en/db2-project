package services;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.Leaderboard;
import model.User;

@Stateless
public class LeaderboardService {
	@PersistenceContext(unitName = "db2-alparone-ferrara-formicola")
	protected EntityManager em;
	
	public LeaderboardService() {}
	
	public Leaderboard findLeaderboardByUser(int user) {
		return em.find(Leaderboard.class, user);
	}
	
	public List<Leaderboard> findLeaderboardsByDate(Date d) {		
		return em.createNamedQuery("Leaderboard.findLeaderboardsByDate", Leaderboard.class)
				.setParameter(1, d)
				.setHint("javax.persistence.cache.storeMode", "REFRESH")
				.getResultList();
	}
	
	public long totalScore(User u){
		return em.createNamedQuery("Leaderboard.totalScore", Long.class)
				.setParameter(1, u)
				.setHint("javax.persistence.cache.storeMode", "REFRESH")
				.getSingleResult();
	}

}
