package services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
		QUESTIONNAIRE_STARTED("Marketing questionnaire started."),
		MARKETING_ANSWERS_FILLED("Marketing answers filled."),
		MARKETING_ANSWERS_SUBMITTED("Marketing answers submitted."),
		MARKETING_QUESTIONNAIRE_CANCELLED("Marketing questionnaire cancelled."),
		STATISTICAL_ANSWERS_SUBMITTED("Statistical answers filled and submitted."),
		STATISTICAL_QUESTIONNAIRE_CANCELLED("Statistical questionnaire cancelled."),
		USER_BLOCKED("User blocked for using a bad words."),
		ADMIN_CREATED_QUESTIONNAIRE("Admin created a questionnaire with a new product of the day"),
		ADMIN_DELETED_QUESTIONNAIRE("Admin deleted a questionnaire");
		
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
}
