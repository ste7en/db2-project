package services;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.User;
import services.LogService.Events;

@Stateless
public class UserService {
	@PersistenceContext(unitName = "db2-alparone-ferrara-formicola")
	protected EntityManager em;
	@EJB(name = "LogService")
	private LogService logService;
	
	public UserService() {}
	
	/**
	 * Service method used to register a new user
	 * @param username
	 * @param email
	 * @param password
	 * @return the new created user instance
	 */
	public User registration(String username, String email, String password) {		
		User u = new User(username, email, password);
		em.persist(u);
		logService.createInstantLog(u, Events.SIGN_UP);
		return u;
	}
	
	/**
	 * Service method to retrieve a user from the database
	 * using the EntityManager and User's primary key
	 * @param user_id
	 * @return a User entity identified by the user_id ID
	 */
	public User findUser(int user_id) {
		return em.find(User.class, user_id);
	}
	
	/**
	 * Checks whether a user with a specific username or email exists
	 * @param username
	 * @param email
	 * @return true if the user exists, false otherwise
	 */
	public Boolean checkByUserAndEmail(String username, String email) {
		return em.createNamedQuery("User.checkByUserAndEmail", Long.class)
			.setParameter(1, username)
			.setParameter(2, email)
			.getSingleResult()
			.intValue() != 0;
	}
	
	/**
	 * Service method to implement the login
	 * @param usrn
	 * @param pwd
	 * @return the existing User if exists, null otherwise
	 */
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
		if (u != null && !u.getBlocked())
			logService.createInstantLog(u, Events.LOG_IN);
		return u;
	}
}
