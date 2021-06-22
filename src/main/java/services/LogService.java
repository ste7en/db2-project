package services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.Log;

@Stateless
public class LogService {
	@PersistenceContext(unitName = "db2-alparone-ferrara-formicola")
	protected EntityManager em;
	
	public LogService(EntityManager em) {
		this.em=em;
	}
	
	public Log createLog(int user_id, String event) {
		Log l= new Log();
		l.setUser_id(user_id);
		l.setEvent(event);
		em.persist(l);
		return l;
	}
	
	public Log findLogByUser(int user_id) {
		return em.find(Log.class, user_id);
	}
}
