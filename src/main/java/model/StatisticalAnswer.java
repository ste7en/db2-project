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
	@JoinColumn(name = "user_id")
	private User user;
	
	@Id
	@Temporal(TemporalType.DATE)
	private Date questionnaire_date;
	
	@Column(name="age")
	private int age;
	
	@Column(name="sex")
	private char sex;
	
	@Column(name="experience")
	private int experience;
	
	public StatisticalAnswer() {}
	
	public StatisticalAnswer(User u, Date d, int age, char sex, int experience) {
		this.user = u;
		this.questionnaire_date = d;
		this.age = age;
		this.sex = sex;
		this.experience = experience;
	}

	public int getAge() {
		return age;
	}

	public char getSex() {
		return sex;
	}

	public int getExperience() {
		return experience;
	}

	public User getUser() {
		return this.user;
	}
	
	public Date getQuestionnaireDate() {
		return this.questionnaire_date;
	}
	
}
