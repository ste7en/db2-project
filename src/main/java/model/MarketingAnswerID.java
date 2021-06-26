package model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;

@Embeddable
public class MarketingAnswerID implements Serializable {
	private static final long serialVersionUID = 1L;

	private int user_id;
	private MarketingQuestionID question;
	
	public MarketingAnswerID() {}

	public MarketingAnswerID(int user, MarketingQuestionID question) {
		super();
		this.user_id = user;
		this.question = question;
	}

	public int getUserId() {
		return user_id;
	}

	public MarketingQuestionID getQuestion() {
		return question;
	}

	@Override
	public int hashCode() {
		return Objects.hash(question, user_id);
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
		return Objects.equals(question, other.question) && Objects.equals(user_id, other.getUserId());
	}
	
	
}