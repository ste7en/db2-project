package model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;

@Embeddable
public class MarketingAnswerID implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Column(name="user_id")
	private User user;
	
	@AttributeOverrides({
		@AttributeOverride(name="number", column=@Column(name="questionnaire_number"))
	})
	private MarketingQuestionID question;
	
	public MarketingAnswerID() {}

	public MarketingAnswerID(User user, MarketingQuestionID question) {
		super();
		this.user = user;
		this.question = question;
	}

	public User getUser() {
		return user;
	}

	public MarketingQuestionID getQuestion() {
		return question;
	}

	@Override
	public int hashCode() {
		return Objects.hash(question, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MarketingAnswerID other = (MarketingAnswerID) obj;
		return Objects.equals(question, other.question) && Objects.equals(user, other.user);
	}
	
	
}