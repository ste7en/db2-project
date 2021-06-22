package model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="statistical_answer")
@NamedQuery(name="StatisticalAnswer.findAll", query="SELECT sa FROM StatisticalAnswer sa")
public class StatisticalAnswer implements Serializable {
	private static final long serialVersionUID = 1L;

	//Statistical_answer(user-id, questionnaire-date, age, sex, experience) 
	@Id
	private int userId;
	
	@ManyToOne
	@JoinColumn(name="`questionnaire_date`", nullable=false, insertable=false, updatable=false)
	private ProductOfTheDay productOfTheDay;
	
	@Column(name="age")
	private int age;
	
	@Column(name="sex")
	private String sex;
	
	@Column(name="experience")
	private String experience;

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * @return the experience
	 */
	public String getExperience() {
		return experience;
	}

	/**
	 * @param experience the experience to set
	 */
	public void setExperience(String experience) {
		this.experience = experience;
	}
	
	public int getUserId() {
		return this.userId;
	}
	
	public void setUserId(int userId) {
		this.userId=userId;
	}
	
	
}
