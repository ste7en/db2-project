package model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;

import java.util.Date;

/**
 * Model class describing the composite key used
 * to identify a MarketingQuestion entity. 
 *
 * It encapsulates the IDs corresponding to the foreign keys of
 * table product_of_the_day (Date) and the key number (int), used
 * in combination with the JEE annotation `@EmbeddedId`.
 * 
 * For further usages @see MarketingQuestion class.
 */
@Embeddable
public class MarketingQuestionID implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int number;
	/**
	 * This maps the ManyToOne relationship with 
	 * ProductOfTheDay's date as a composite ID
	 */
	@JoinTable(name = "product_of_the_day",
			joinColumns = {@JoinColumn(name = "questionnaire_date")},
			inverseJoinColumns = {@JoinColumn(name = "date")})
	@Temporal(TemporalType.DATE)
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
		if (!(obj instanceof MarketingQuestionID))
			return false;
		MarketingQuestionID other = (MarketingQuestionID) obj;
		return number == other.number && Objects.equals(questionnaire_date, other.questionnaire_date);
	}
}
