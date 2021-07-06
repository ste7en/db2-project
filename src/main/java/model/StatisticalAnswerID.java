package model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
/**
 * Model class describing the composite key used
 * to identify a StatisticalAnswer entity. 
 *
 * It encapsulates the IDs corresponding to the foreign keys of
 * tables user (int) and product_of_the_day (Date) and is used
 * in combination with the JEE annotation `@IdClass`.
 * 
 * For further usages @see StatisticalAnswer class.
 */
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
		if (!(obj instanceof StatisticalAnswerID))
			return false;
		StatisticalAnswerID other = (StatisticalAnswerID) obj;
		return Objects.equals(questionnaire_date, other.questionnaire_date) && user == other.user;
	}
}
