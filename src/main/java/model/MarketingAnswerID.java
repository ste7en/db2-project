package model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;

/**
 * Model class describing the composite key used
 * to identify a MarketingAnswer entity. 
 *
 * It encapsulates the IDs corresponding to the foreign keys of
 * tables MarketingQuestion (Date, number) and User (int), used
 * in combination with the JEE annotation `@EmbeddedId`.
 * 
 * For further usages @see MarketingAnswer class.
 */
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