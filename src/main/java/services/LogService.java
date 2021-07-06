package services;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.*;

import model.Log;
import model.User;

@Stateless
public class LogService {
	@PersistenceContext(unitName = "db2-alparone-ferrara-formicola")
	protected EntityManager em;
	
	public LogService() {}
	
	public enum Events {
		SIGN_UP("New user created."),
		LOG_IN("User logged in."),
		LOG_OUT("User logged out."),
		QUESTIONNAIRE_STARTED("Marketing questionnaire started."),
		QUESTIONNAIRE_CANCELLED("Marketing questionnaire cancelled."),
		MARKETING_ANSWERS_FILLED("Marketing answers filled."),
		MARKETING_ANSWERS_SUBMITTED("Marketing answers submitted."),
		STATISTICAL_ANSWERS_SUBMITTED("Statistical answers filled and submitted."),
		USER_BLOCKED("User blocked for using a bad words."),
		ADMIN_CREATED_QUESTIONNAIRE("Admin created a questionnaire with a new product of the day."),
		ADMIN_DELETED_QUESTIONNAIRE("Admin deleted a questionnaire.");
		
        private String message;

        Events(String s) { message = s; }

        String value() { return message; }
	}
	
	public void createInstantLog(User u, Events event) {
		Log l = new Log(u, event.value());
		em.persist(l);
	}
	
	public List<Log> findAllLogs() {
		return em.createNamedQuery("Log.findAll", Log.class).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Log> findByDate(Date d) {
		return em.createNativeQuery("SELECT * FROM Log WHERE date(timestamp) LIKE ?1 ORDER BY timestamp ASC", Log.class)
				.setParameter(1, d, TemporalType.DATE)
				.getResultList();
	}
}
