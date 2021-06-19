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
	
	public User registration(int user_id, String email, String password, String username, byte admin) {
		User u= new User();
		u.setId(user_id);
		u.setEmail(email);
		u.setPassword(password);
		User presente= findUserByUsername(username);
		if(presente!=null) { 
			throw new RuntimeException("Username gi� esistente, sceglierne un altro");
		}
		u.setUsername(username);
		u.setAdmin(admin);
		u.setBlocked(Byte.parseByte("0"));
		em.persist(u);
		return u;
	}
	
	public void removeUser(int user_id) {
		User u= findUser(user_id);
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
		TypedQuery query= em.createQuery("SELECT u from USER u", User.class);
		return query.getResultList();
	}
	
	public User checkCredentials(String usrn, String pwd) throws  NonUniqueResultException {
		List<User> uList = null;
		try {
			uList = em.createNamedQuery("User.checkCredentials", User.class).setParameter(1, usrn).setParameter(2, pwd)
					.getResultList();
		} catch (PersistenceException e) {
			throw new RuntimeException("Could not verify credentals");
		}
		if (uList.isEmpty())
			return null;
		else if (uList.size() == 1)
			return uList.get(0);
		throw new NonUniqueResultException("More than one user registered with same credentials");

	}

}
