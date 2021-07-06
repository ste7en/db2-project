package model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.*;

/**
 * Model class describing a StatisticalAnswer as it is implemented
 * in the database schema. It uses an `@IdClass`annotation to refer
 * to its primary (foreign) composite key. @see StatisticalAnswerID
 * for further documentation.
 */
@Entity
@IdClass(StatisticalAnswerID.class)
@Table(name  ="statistical_answer")
@NamedQueries({
	@NamedQuery(name = "StatisticalAnswer.findByDate", query = "SELECT sa FROM StatisticalAnswer sa WHERE sa.questionnaire_date = ?1"),
	@NamedQuery(name = "StatisticalAnswer.findByUserAndDate", query = "SELECT sa FROM StatisticalAnswer sa WHERE sa.questionnaire_date = ?1 AND sa.user = ?2")

})
public class StatisticalAnswer implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * This maps the ManyToOne relationship with User as a composite ID
	 */
	@Id
	@ManyToOne
	(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;
	
	/**
	 * This maps the ManyToOne relationship with ProductOfTheDay as a composite ID
	 */
	@Id
	@JoinTable(name = "product_of_the_day",
				joinColumns = {@JoinColumn(name = "questionnaire_date")},
				inverseJoinColumns = {@JoinColumn(name = "date")})
	@Temporal(TemporalType.DATE)
	private Date questionnaire_date;
	
	@Column(name = "age")
	private Integer age;
	
	@Column(name = "sex")
	private Character sex;
	
	@Column(name = "experience")
	private Integer experience;
	
	/**
	 * Not necessary because redundant, but implemented as bidirectional relationship.
	 * Its primary key (date), indeed, is needed because it is part of the composite key of
	 * this entity.
	 */
	@ManyToOne
	(fetch = FetchType.EAGER)
	@JoinColumn(name="questionnaire_date", referencedColumnName = "date", updatable = false, insertable = false)
	private ProductOfTheDay productOfTheDay;
	
	public StatisticalAnswer() {}
	
	public StatisticalAnswer(User u, Date d, Integer age, Character sex, Integer experience) {
		this.user = u;
		this.questionnaire_date = d;
		this.age = age;
		this.sex = sex;
		this.experience = experience;
	}
	
	/**
	 * Getters and setters
	 */

	public Integer getAge() {
		return age;
	}

	public Character getSex() {
		return sex;
	}

	public Integer getExperience() {
		return experience;
	}

	public User getUser() {
		return this.user;
	}
	
	public Date getQuestionnaireDate() {
		return this.questionnaire_date;
	}

	@Override
	public int hashCode() {
		return Objects.hash(age, experience, productOfTheDay, questionnaire_date, sex, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof StatisticalAnswer))
			return false;
		StatisticalAnswer other = (StatisticalAnswer) obj;
		return Objects.equals(age, other.age) && Objects.equals(experience, other.experience)
				&& Objects.equals(productOfTheDay, other.productOfTheDay)
				&& Objects.equals(questionnaire_date, other.questionnaire_date) && Objects.equals(sex, other.sex)
				&& Objects.equals(user, other.user);
	}
}
