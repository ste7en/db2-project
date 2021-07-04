package services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.OffensiveWord;

@Stateless
public class OffensiveWordService {
	@PersistenceContext(unitName = "db2-alparone-ferrara-formicola")
	protected EntityManager em;
	
	public OffensiveWordService() {}
	
	public OffensiveWord findOffensiveWord(String word) {
		return em.find(OffensiveWord.class, word);
	}
	
	public void incrementOffensiveWordOccurrence(String word) {
		em.find(OffensiveWord.class, word).incrementOccurrence();
	}
	
	public List<String> findAll() {
		return em.createNamedQuery("OffensiveWord.findAllWords", String.class).getResultList();
	}

}