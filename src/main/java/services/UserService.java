package services;

import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import model.User;
import services.LogService.Events;

@Stateless
public class UserService {
	@PersistenceContext(unitName = "db2-alparone-ferrara-formicola")
	protected EntityManager em;
	@EJB(name = "LogService")
	private LogService logService;
	
	public UserService() {}
	
	public User registration(String username, String email, String password) {		
		User u = new User(username, email, password);
		em.persist(u);
		logService.createInstantLog(u, Events.SIGN_UP);
		return u;
	}
	
	public void blockUser(int user_id) {
		User u = findUser(user_id);
		if(u != null) {
			u.setBlocked(true);
			logService.createInstantLog(u, Events.USER_BLOCKED);
		}
	}
	
	public User findUser(int user_id) {
		return em.find(User.class, user_id);
	}
	
	public Collection<User> findAllUsers(){
		TypedQuery<User> query= em.createQuery("SELECT u from USER u", User.class);
		return query.getResultList();
	}
	
	public Boolean checkByUserAndEmail(String username, String email) {
		return em.createNamedQuery("User.checkByUserAndEmail", Long.class)
			.setParameter(1, username)
			.setParameter(2, email)
			.getSingleResult()
			.intValue() != 0;
	}
	
	public User checkCredentials(String usrn, String pwd) {
		User u;
		try {
			u = em.createNamedQuery("User.checkCredentials", User.class)
					.setParameter(1, usrn)
					.setParameter(2, pwd)
					.setHint("javax.persistence.cache.storeMode", "REFRESH")
					.getSingleResult();
		} catch (Exception e) {
			return null;
		}
		if (u != null)
			logService.createInstantLog(u, Events.LOG_IN);
		return u;
	}
}
