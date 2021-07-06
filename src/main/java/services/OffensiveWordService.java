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
	
	/**
	 * Service method to retrieve an OffensiveWord from the database
	 * using the EntityManager and entity's primary key (word)
	 * @param word
	 * @return the OffensiveWord managed entity
	 */
	public OffensiveWord findOffensiveWord(String word) {
		return em.find(OffensiveWord.class, word);
	}
	
	/**
	 * Increments the occurrence of an offensive word when encountered
	 * during the check of the text of a MarketingAnswer.
	 * @see MarketingAnswerService
	 * @param word
	 */
	public void incrementOffensiveWordOccurrence(String word) {
		em.find(OffensiveWord.class, word).incrementOccurrence();
	}
	
	public List<String> findAll() {
		return em.createNamedQuery("OffensiveWord.findAllWords", String.class).getResultList();
	}

}