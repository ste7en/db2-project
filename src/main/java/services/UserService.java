package services;

import java.util.Collection;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import model.User;

@Stateless
public class UserService {
	@PersistenceContext(unitName = "db2-alparone-ferrara-formicola")
	protected EntityManager em;
	
	public UserService() { }
	
	public User registration(String username, String email, String password) {		
		User u = new User(username, email, password);
		
		em.persist(u);
		return u;
	}
	
	public boolean isAdmin(User u) {
		if(findUser(u.getId())==null) {
			throw new RuntimeException("Utente non esistente,riprovare");
		}
		else {
			if(u.getAdmin()) {
				return true;
			}
			return false;
		}
	}
	
	public void removeUser(int user_id) {
		User u = findUser(user_id);
		if(u!=null) {
			em.remove(u);
		}
	}
	
	public User findUser(int user_id) {
		return em.find(User.class, user_id);
	}
	
	public User findUserByUsername(String username) {
		return em.find(User.class, username);
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
	
	public User checkCredentials(String usrn, String pwd) throws NonUniqueResultException {
		List<User> uList = null;
		try {
			uList = em.createNamedQuery("User.checkCredentials", User.class)
					.setParameter(1, usrn).setParameter(2, pwd)
					.getResultList();
		} catch (PersistenceException e) {
			throw new RuntimeException("Could not verify credentals: " + e.getMessage());
		}
		if (uList.isEmpty())
			return null;
		else if (uList.size() == 1)
			return uList.get(0);
		throw new NonUniqueResultException("More than one user registered with same credentials");

	}

}
