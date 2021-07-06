package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Model class describing a MarketingAnswer as it is implemented
 * in the database schema. It uses an `@EmbeddedId`annotation to refer
 * to its primary (foreign) composite key. 
 * @see MarketingAnswerID for further documentation.
 */
@Entity
@Table(name="marketing_answer")
@NamedQueries({
	@NamedQuery(name="MarketingAnswer.findByUserIdAndDate", query="SELECT m FROM MarketingAnswer m WHERE m.user = ?1 and m.id.question.questionnaire_date = ?2"),
	@NamedQuery(name="MarketingAnswer.findByDate", query="SELECT m from MarketingAnswer m WHERE m.id.question.questionnaire_date = ?1")
})
public class MarketingAnswer implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 	Differently from the `@IdClass` annotation,
	 * 	the composite key fields (questionnaire_date, 
	 *  questionnaire_number, user_id) of 
	 *  this entity are entirely encapsulated in a 
	 *  single attribute of type @see MarketingAnswerID.
	 * 
	 *  A JPQL query must refer to the identifier when 
	 *  querying for marketing questions.
	 */
	@EmbeddedId
    private MarketingAnswerID id;
	
	/**
	 * Not necessary because redundant, but implemented as bidirectional relationship.
	 * Its primary key (user_id), indeed, is needed because it is part of the composite key of
	 * this entity.
	 */
	@MapsId("user_id")
	@ManyToOne
	(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;
    
	/**
	 * Not necessary because redundant, but implemented as bidirectional relationship.
	 * Its primary key (date, number) is, indeed, needed because it is part of the 
	 * composite key of this entity.
	 */
    @MapsId("question")
    @ManyToOne
	(fetch = FetchType.EAGER)
    @JoinColumns({
    	@JoinColumn(name = "questionnaire_number", referencedColumnName = "number"),
    	@JoinColumn(name = "questionnaire_date", referencedColumnName = "questionnaire_date")
    })
    private MarketingQuestion question;
	
	@Column(name = "text")
	private String answer;
	
	public MarketingAnswer() {}
	
	public MarketingAnswer(User user, MarketingQuestion question, String answer) {
		super();
		MarketingQuestionID questionID = question.getId();
		if (questionID == null)
			throw new RuntimeException("Question ID is null for question " + question.getText());
		this.id = new MarketingAnswerID(user.getId(), questionID);
		this.user = user;
		this.answer = answer;
		this.question = question;
	}

	public String getAnswer() {
		return this.answer;
	}
	
	public User getUser() {
		return this.user;
	}
	
	public int getNumber() {
		return this.id.getQuestion().getNumber();
	}
}