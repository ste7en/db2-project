package model;

import java.io.Serializable;
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
	private MarketingQuestionPK id;

	@Column(length=45)
	private String text;

	//bi-directional many-to-one association to ProductOfTheDay
	@ManyToOne
	@JoinColumn(name="`questionnaire-date`", nullable=false, insertable=false, updatable=false)
	private ProductOfTheDay productOfTheDay;

	public MarketingQuestion() {
	}

	public MarketingQuestionPK getId() {
		return this.id;
	}

	public void setId(MarketingQuestionPK id) {
		this.id = id;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public ProductOfTheDay getProductOfTheDay() {
		return this.productOfTheDay;
	}

	public void setProductOfTheDay(ProductOfTheDay productOfTheDay) {
		this.productOfTheDay = productOfTheDay;
	}

}