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
	
	@EmbeddedId
	private MarketingQuestionID identifier;
	
	@Column(length=45)
	private String text;

	//bi-directional many-to-one association to ProductOfTheDay
	@ManyToOne
	@JoinColumn(name="`questionnaire_date`", nullable=false, insertable=false, updatable=false)
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
		return this.identifier.getQuestionnaireDate();
	}

	public ProductOfTheDay getProductOfTheDay() {
		return this.productOfTheDay;
	}

	public void setProductOfTheDay(ProductOfTheDay productOfTheDay) {
		this.productOfTheDay = productOfTheDay;
	}
	
	public int getNumber() {
		return this.identifier.getNumber();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.getNumber();
		result = prime * result + ((productOfTheDay == null) ? 0 : productOfTheDay.hashCode());
		result = prime * result + ((this.getQuestionnaireDate() == null) ? 0 : this.getQuestionnaireDate().hashCode());
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
		if (this.getNumber() != other.getNumber())
			return false;
		if (productOfTheDay == null) {
			if (other.productOfTheDay != null)
				return false;
		} else if (!productOfTheDay.equals(other.productOfTheDay))
			return false;
		if (this.getQuestionnaireDate() == null) {
			if (other.getQuestionnaireDate() != null)
				return false;
		} else if (!this.getQuestionnaireDate().equals(other.getQuestionnaireDate()))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}
	
	

}