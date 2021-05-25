package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the marketing_question database table.
 * 
 */
@Embeddable
public class MarketingQuestionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int number;

	@Temporal(TemporalType.DATE)
	@Column(name="`questionnaire-date`", insertable=false, updatable=false)
	private java.util.Date questionnaire_date;

	public MarketingQuestionPK() {
	}
	public int getNumber() {
		return this.number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public java.util.Date getQuestionnaire_date() {
		return this.questionnaire_date;
	}
	public void setQuestionnaire_date(java.util.Date questionnaire_date) {
		this.questionnaire_date = questionnaire_date;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MarketingQuestionPK)) {
			return false;
		}
		MarketingQuestionPK castOther = (MarketingQuestionPK)other;
		return 
			(this.number == castOther.number)
			&& this.questionnaire_date.equals(castOther.questionnaire_date);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.number;
		hash = hash * prime + this.questionnaire_date.hashCode();
		
		return hash;
	}
}