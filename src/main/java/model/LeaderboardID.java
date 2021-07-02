package model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class LeaderboardID implements Serializable{

	private static final long serialVersionUID = 1L;

	private int user;
	private Date questionnaire_date;
	
	public LeaderboardID() {}

	public int getUser() {
		return user;
	}

	public Date getQuestionnaire_date() {
		return questionnaire_date;
	}

	@Override
	public int hashCode() {
		return Objects.hash(questionnaire_date, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LeaderboardID other = (LeaderboardID) obj;
		return Objects.equals(questionnaire_date, other.questionnaire_date) && user == other.user;
	}
	
	
}
