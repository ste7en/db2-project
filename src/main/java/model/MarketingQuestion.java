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

	private String text;

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

}