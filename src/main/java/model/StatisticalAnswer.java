package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@IdClass(StatisticalAnswerID.class)
@Table(name="statistical_answer")
@NamedQueries({
	@NamedQuery(name="StatisticalAnswer.findAll", query="SELECT sa FROM StatisticalAnswer sa"),
	@NamedQuery(name="StatisticalAnswer.findByDate", query="SELECT sa FROM StatisticalAnswer sa WHERE sa.questionnaire_date = ?1"),
	@NamedQuery(name="StatisticalAnswer.findByUserAndDate", query="SELECT sa FROM StatisticalAnswer sa WHERE sa.questionnaire_date = ?1 AND sa.user = ?2")

})
public class StatisticalAnswer implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@ManyToOne
	(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;
	
	/**
	 * This maps the ManyToOne relationship with ProductOfTheDay in an ID
	 */
	@Id
	@JoinTable(name = "product_of_the_day",
				joinColumns = {@JoinColumn(name = "questionnaire_date")},
				inverseJoinColumns = {@JoinColumn(name = "date")})
	@Temporal(TemporalType.DATE)
	private Date questionnaire_date;
	
	@Column(name="age")
	private Integer age;
	
	@Column(name="sex")
	private Character sex;
	
	@Column(name="experience")
	private Integer experience;
	
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
	
}
