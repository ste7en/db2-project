package model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.*;

@Entity
@IdClass(LogID.class)
@Table(name = "log")
@NamedQueries({
	@NamedQuery(name = "Log.findAll", query = "SELECT l FROM Log l ORDER BY l.timestamp ASC")
})
public class Log implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private Timestamp timestamp;
	
	@Id
	@ManyToOne
	(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column(name = "event")
	private String event;
	
	public Log() {}
	
	public Log(User u, String event) {
		this.timestamp = new Timestamp(new Date().getTime());
		this.event = event;
		this.user = u;
	}
	
	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User u) {
		this.user = u;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((event == null) ? 0 : event.hashCode());
		result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
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
		Log other = (Log) obj;
		if (event == null) {
			if (other.event != null)
				return false;
		} else if (!event.equals(other.event))
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		if (user != other.user)
			return false;
		return true;
	}
	
	


}
