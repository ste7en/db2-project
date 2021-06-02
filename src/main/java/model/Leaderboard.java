package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="product")
@NamedQuery(name="Leaderboard.findAll", query="SELECT l FROM Leaderboard L")
public class Leaderboard implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="user")
	private int user;
	
	@Temporal(TemporalType.DATE)
	@Column(name="questionnaire-date")
	private Date questionnaire_date;

	@Column(name="points")
	private int points;

	public Leaderboard() {
	}

	public int getUser() {
		return user;
	}

	public void setUser(int user) {
		this.user = user;
	}

	public Date getQuestionnaire_date() {
		return questionnaire_date;
	}

	public void setQuestionnaire_date(Date questionnaire_date) {
		this.questionnaire_date = questionnaire_date;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + points;
		result = prime * result + ((questionnaire_date == null) ? 0 : questionnaire_date.hashCode());
		result = prime * result + user;
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
		Leaderboard other = (Leaderboard) obj;
		if (points != other.points)
			return false;
		if (questionnaire_date == null) {
			if (other.questionnaire_date != null)
				return false;
		} else if (!questionnaire_date.equals(other.questionnaire_date))
			return false;
		if (user != other.user)
			return false;
		return true;
	}
	
	

}
