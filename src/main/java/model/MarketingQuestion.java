package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the marketing_question database table.
 * 
 */
@Entity
@Table(name="marketing_question")
@NamedQuery(name="MarketingQuestion.findAll", query="SELECT m FROM MarketingQuestion m")
public class MarketingQuestion implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="number")
	private int number;
	
	@Temporal(TemporalType.DATE)
	@Column(name="questionnaire-date")
	private Date questionnaire_date;
	
	@Column(length=45)
	private String text;

	//bi-directional many-to-one association to ProductOfTheDay
	@ManyToOne
	@JoinColumn(name="`questionnaire-date`", nullable=false, insertable=false, updatable=false)
	private ProductOfTheDay productOfTheDay;

	public MarketingQuestion() {
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public Date getQuestionnaireDate() {
		return this.questionnaire_date;
	}
	
	public void setQuestionnaireDate(Date questionnaire_date) {
		this.questionnaire_date=questionnaire_date;
	}

	public ProductOfTheDay getProductOfTheDay() {
		return this.productOfTheDay;
	}

	public void setProductOfTheDay(ProductOfTheDay productOfTheDay) {
		this.productOfTheDay = productOfTheDay;
	}
	
	public int getNumber() {
		return this.number;
	}
	
	public void setNumber(int number) {
		this.number=number;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + number;
		result = prime * result + ((productOfTheDay == null) ? 0 : productOfTheDay.hashCode());
		result = prime * result + ((questionnaire_date == null) ? 0 : questionnaire_date.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MarketingQuestion other = (MarketingQuestion) obj;
		if (number != other.number)
			return false;
		if (productOfTheDay == null) {
			if (other.productOfTheDay != null)
				return false;
		} else if (!productOfTheDay.equals(other.productOfTheDay))
			return false;
		if (questionnaire_date == null) {
			if (other.questionnaire_date != null)
				return false;
		} else if (!questionnaire_date.equals(other.questionnaire_date))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}
	
	

}