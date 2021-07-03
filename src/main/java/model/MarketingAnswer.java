package model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="marketing_answer")
@NamedQuery(name="MarketingAnswer.findByUserIdAndDate", query="SELECT m FROM MarketingAnswer m WHERE m.user = ?1 and m.question = ?2")
public class MarketingAnswer implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
    private MarketingAnswerID id;
	
	@MapsId("user_id")
	@JoinColumn(name = "user_id")
	@ManyToOne
	private User user;
    
    @MapsId("question")
    @JoinColumns({
    	@JoinColumn(name = "questionnaire_number", referencedColumnName = "number"),
    	@JoinColumn(name = "questionnaire_date", referencedColumnName = "questionnaire_date")
    })
    @ManyToOne
    private MarketingQuestion question;
	
	@Column(name="text")
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
	}

	public String getAnswer() {
		return this.answer;
	}
	
	public User getUser() {
		return this.user;
	}
	
	public MarketingQuestion getQuestion() {
		return this.question;
	}
}