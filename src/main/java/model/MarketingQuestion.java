package model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import javax.persistence.*;


/**
 * The persistent class for the marketing_question database table.
 * 
 */
@Entity
@Table(name="marketing_question")
@NamedQueries({
	@NamedQuery(name="MarketingQuestion.findAll", query="SELECT m FROM MarketingQuestion m"),
	@NamedQuery(name="MarketingQuestion.findByDate", query="SELECT mq from MarketingQuestion mq WHERE mq.identifier.questionnaire_date = ?1")
})
public class MarketingQuestion implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private MarketingQuestionID identifier;
	
	@Column(length=45)
	private String text;

	//bidirectional many-to-one association to ProductOfTheDay
	@ManyToOne
	@JoinColumn(name="questionnaire_date", updatable = false, insertable = false)
	private ProductOfTheDay productOfTheDay;
	
	@ElementCollection
	@CollectionTable(name = "marketing_answer", 
					joinColumns = { @JoinColumn(name = "questionnaire_date", referencedColumnName = "questionnaire_date"), 
									@JoinColumn(name = "questionnaire_number", referencedColumnName = "number")
								}
					)
	@MapKeyJoinColumn(name = "user_id")
	@Column(name = "text")
	private Map<User, String> answers;
	
	public MarketingQuestion() {}
	
	public MarketingQuestion(ProductOfTheDay p, int number, String question) {
		this.identifier = new MarketingQuestionID(number, p.getDate());
		this.text = question;
	}
	
	public MarketingQuestionID getId() {
		return this.identifier;
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
	
	public int getNumber() {
		return this.identifier.getNumber();
	}

	@Override
	public int hashCode() {
		return Objects.hash(answers, identifier, text);
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
		return Objects.equals(answers, other.answers) && Objects.equals(identifier, other.identifier)
				&& Objects.equals(text, other.text);
	}
}