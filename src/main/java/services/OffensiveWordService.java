package services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.OffensiveWord;

@Stateless
public class OffensiveWordService {
	@PersistenceContext(unitName = "db2-alparone-ferrara-formicola")
	protected EntityManager em;
	
	public OffensiveWordService(EntityManager em) {
		this.em=em;
	}
	
	public OffensiveWord createOffensiveWord(String word, int occurrence) {
		OffensiveWord ow= new OffensiveWord();
		ow.setWord(word);
		ow.setOccurrence(occurrence);
		em.persist(ow);
		return ow;
	}
	
	public OffensiveWord findOffensiveWord(String word) {
		return em.find(OffensiveWord.class, word);
	}
	
	public void removeOffensiveWord(String word) {
		OffensiveWord ow= findOffensiveWord(word);
		if(ow!=null) {
			em.remove(ow);
		}
	}

}
