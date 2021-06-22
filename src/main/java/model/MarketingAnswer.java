package model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="marketing_answer")
@NamedQuery(name="MarketingAnswer.findByUserIdAndDate", query="SELECT m FROM MarketingAnswer m WHERE m.identifier.user = ?1 AND m.identifier.question.questionnaire_date = ?2")
public class MarketingAnswer implements Serializable {
	private static final long serialVersionUID = 1L;
	
    @EmbeddedId
    private MarketingAnswerID identifier;
	
	@Column(name="text")
	private String answer;
	
	public MarketingAnswer() {}
	
	public MarketingAnswer(User user, MarketingQuestionID question, String answer) {
		super();
		MarketingAnswerID identifier = new MarketingAnswerID(user, question);
		this.identifier = identifier;
		this.answer = answer;
	}

	public String getAnswer() {
		return this.answer;
	}
	
	public User getUser() {
		return this.identifier.getUser();
	}
	
	public MarketingQuestionID getQuestion() {
		return this.identifier.getQuestion();
	}
}