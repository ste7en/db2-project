package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the user database table.
 * 
 */
@Embeddable
public class UserPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="Host")
	private String host;

	@Column(name="User")
	private String user;

	public UserPK() {
	}
	public String getHost() {
		return this.host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getUser() {
		return this.user;
	}
	public void setUser(String user) {
		this.user = user;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof UserPK)) {
			return false;
		}
		UserPK castOther = (UserPK)other;
		return 
			this.host.equals(castOther.host)
			&& this.user.equals(castOther.user);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.host.hashCode();
		hash = hash * prime + this.user.hashCode();
		
		return hash;
	}
}