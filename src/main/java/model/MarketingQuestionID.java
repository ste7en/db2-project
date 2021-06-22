package model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;

import java.util.Date;

@Embeddable
public class MarketingQuestionID implements Serializable {
	private static final long serialVersionUID = 1L;

	private int number;
	private Date questionnaire_date;
	
	public MarketingQuestionID() {}
	
	public MarketingQuestionID(int number, Date questionnaire_date) {
		super();
		this.number = number;
		this.questionnaire_date = questionnaire_date;
	}

	public int getNumber() {
		return number;
	}

	public Date getQuestionnaireDate() {
		return questionnaire_date;
	}

	@Override
	public int hashCode() {
		return Objects.hash(number, questionnaire_date);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MarketingQuestionID other = (MarketingQuestionID) obj;
		return number == other.number && Objects.equals(questionnaire_date, other.questionnaire_date);
	}
	
	
}
