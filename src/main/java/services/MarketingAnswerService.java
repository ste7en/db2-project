package services;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import model.MarketingAnswer;
import model.User;
import services.LogService.Events;

@Stateless
public class MarketingAnswerService {
	@PersistenceContext(unitName = "db2-alparone-ferrara-formicola")
	protected EntityManager em;
	@EJB(name = "db2-project.src.main.java.services/OffensiveWordService")
	private OffensiveWordService offensiveWordService;
	@EJB(name = "db2-project.src.main.java.services/UserService")
	private UserService userService;
	@EJB(name = "LogService")
	private LogService logService;
	
	public MarketingAnswerService() {}
	
	/**
	 * Service method to save the marketing questions at the end of each
	 * questionnaire ( @see SubmitStatisticalQuestionnaire ) using a 
	 * bulk insertion. For each answer it performs
	 * a check on the text to ban users who inserted offensive words.
	 * @param answers
	 */
	public void saveMarketingAnswers(List<MarketingAnswer> answers) {
		List<String> offensiveWords = offensiveWordService.findAll();
		final User u = userService.findUser(answers.get(0).getUser().getId());
		// Performing a first check on answers if not containing offensive words
		for (MarketingAnswer answer : answers) {
			offensiveWords.forEach(offensiveWord -> {
				if (answer.getAnswer().toLowerCase().contains(offensiveWord)) {
					u.setBlocked(true);
					logService.createInstantLog(u, Events.USER_BLOCKED);
					offensiveWordService.incrementOffensiveWordOccurrence(offensiveWord);
				}
			});
		}
		// Bulk insert of the answers into DB
		// granted by the persistence context
		if (!u.getBlocked())
			answers.forEach(answer -> em.persist(answer));
	}
	
	/**
	 * Service method to check the marketing answers belonging 
	 * to a specific user and filled in a specific date
	 * @param u User
	 * @param d Date
	 * @return a list of MarketingAnswer
	 */
	public List<MarketingAnswer> findByUserAndDate(User u, Date d){
		TypedQuery<MarketingAnswer> query= em.createNamedQuery("MarketingAnswer.findByUserIdAndDate", MarketingAnswer.class)
				.setParameter(1, u).setParameter(2, d);
		return query.getResultList();
	}
	
	public List<MarketingAnswer> findByDate(Date d){
		TypedQuery<MarketingAnswer> query= em.createNamedQuery("MarketingAnswer.findByDate", MarketingAnswer.class)
				.setParameter(1, d);
		return query.getResultList();
	}
	
	/**
	 * Service method used in the HomePage to check if the logged user
	 * already filled in the questionnaire of the day
	 * @param u User
	 * @param d Date
	 * @return true if the User u already filled in the questionnaire, false otherwise
	 */
	public boolean checkIfExists(User u, Date d) {
		return !findByUserAndDate(u, d).isEmpty();
	}
}
