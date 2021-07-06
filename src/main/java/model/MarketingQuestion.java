package model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import javax.persistence.*;


/**
 * Model class describing a MarketingQuestion as it is implemented
 * in the database schema. It uses an `@EmbeddedId`annotation to refer
 * to its primary (foreign) composite key. 
 * @see MarketingQuestionID for further documentation.
 */
@Entity
@Table(name = "marketing_question")
@NamedQueries({
	@NamedQuery(name = "MarketingQuestion.findByDate", query = "SELECT mq from MarketingQuestion mq WHERE mq.identifier.questionnaire_date = ?1")
})
public class MarketingQuestion implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 	Differently from the `@IdClass` annotation,
	 * 	the composite key fields (date, number) of 
	 *  this entity are entirely encapsulated in a 
	 *  single attribute of type @see MarketingQuestionID.
	 * 
	 *  A JPQL query must refer to the identifier when 
	 *  querying for marketing questions.
	 */
	@EmbeddedId
	private MarketingQuestionID identifier;
	
	@Column(length = 100)
	private String text;

	/**
	 * Not necessary because redundant, but implemented as bidirectional relationship.
	 * Its primary key (date), indeed, is needed because it is part of the composite key of
	 * this entity, implemented as EmbeddedId with an Embeddable entity.
	 */
	@ManyToOne
	(fetch = FetchType.EAGER)
	@JoinColumn(name="questionnaire_date", updatable = false, insertable = false)
	private ProductOfTheDay productOfTheDay;
	
	/**
	 * Not necessary because the mapping of this relationship has already been implemented
	 * in the entity @see MarketingAnswer, but it has been added as bidirectional relationship.
	 */
	@ElementCollection
	(fetch = FetchType.LAZY)
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