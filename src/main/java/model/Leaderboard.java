package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@IdClass(LeaderboardID.class)
@Table(name="leaderboard")
@NamedQueries ({ @NamedQuery(name="Leaderboard.findAll", query="SELECT l FROM Leaderboard L"),
				@NamedQuery(name="Leaderboard.totalScore", query="SELECT sum(l.points) FROM Leaderboard l WHERE l.user = ?1"),
				@NamedQuery(name="Leaderboard.findLeaderboardsByDate", query="SELECT l FROM Leaderboard l WHERE l.questionnaire_date = ?1 ORDER BY l.points DESC")
})

public class Leaderboard implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne
	@JoinColumn(name = "user")
	private User user;
	
	@Id
	@Temporal(TemporalType.DATE)
	private Date questionnaire_date;

	@Column(name="points")
	private int points;
	
	@ManyToOne
	@JoinColumn(name = "leaderboard", referencedColumnName = "questionnaire_date")
	private ProductOfTheDay productOfTheDay;
	
	public Leaderboard() {}

	public User getUser() {
		return user;
	}

	public Date getQuestionnaire_date() {
		return questionnaire_date;
	}

	public int getPoints() {
		return points;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + points;
		result = prime * result + ((questionnaire_date == null) ? 0 : questionnaire_date.hashCode());
		result = prime * result + user.getId();
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
