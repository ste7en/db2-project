package model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Table(name="user")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="`user-id`")
	private int user_id;

	@Column(name="account_locked")
	private String accountLocked;

	@Column(name="admin")
	private byte admin;
	
	@Column(name="blocked")
	private byte blocked;
	
	@Column(name="email")
	private String email;

	@Column(name="password")
	private String password;

	@Column(name="username")
	private String username;

	public User() {
	}

	public int getId() {
		return this.user_id;
	}

	public void setId(int id) {
		this.user_id = id;
	}

	public String getAccountLocked() {
		return this.accountLocked;
	}

	public void setAccountLocked(String accountLocked) {
		this.accountLocked = accountLocked;
	}

	public byte getAdmin() {
		return this.admin;
	}

	public void setAdmin(byte admin) {
		this.admin = admin;
	}

	public byte getBlocked() {
		return this.blocked;
	}

	public void setBlocked(byte blocked) {
		this.blocked = blocked;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountLocked == null) ? 0 : accountLocked.hashCode());
		result = prime * result + admin;
		result = prime * result + blocked;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + user_id;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		User other = (User) obj;
		if (accountLocked == null) {
			if (other.accountLocked != null)
				return false;
		} else if (!accountLocked.equals(other.accountLocked))
			return false;
		if (admin != other.admin)
			return false;
		if (blocked != other.blocked)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (user_id != other.user_id)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}


}