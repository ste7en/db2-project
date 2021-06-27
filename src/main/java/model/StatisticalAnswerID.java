package model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class StatisticalAnswerID implements Serializable{

	private static final long serialVersionUID = 1L;

	private int user;
	private Date questionnaire_date;
	
	public StatisticalAnswerID() {}

	@Override
	public int hashCode() {
		return Objects.hash(questionnaire_date, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StatisticalAnswerID other = (StatisticalAnswerID) obj;
		return Objects.equals(questionnaire_date, other.questionnaire_date) && user == other.user;
	}

	
}
