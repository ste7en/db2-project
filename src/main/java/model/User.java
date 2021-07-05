package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;
import java.util.Map;

/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="User.findAll", query="SELECT u FROM User u"),
	@NamedQuery(name="User.checkCredentials", query="SELECT u FROM User u WHERE u.username = ?1 and u.password = ?2"),
	@NamedQuery(name="User.checkByUserAndEmail", query="SELECT count(u) FROM User as u WHERE u.username = ?1 or u.email = ?2")	
})
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int user_id;

	@Column(name="admin")
	private Boolean admin;
	
	@Column(name="blocked")
	private Boolean blocked;
	
	@Column(name="email")
	private String email;

	@Column(name="password")
	private String password;

	@Column(name="username")
	private String username;

	@ElementCollection
	@CollectionTable(name = "review", joinColumns = @JoinColumn(name = "user_id"))
	@MapKeyJoinColumn(name = "product_id")
	@Column(name = "text")
	private Map<Product, String> productReviews;
		
	@OneToMany(mappedBy = "user")
	private List<StatisticalAnswer> statisticalAnswers;
	
	@OneToMany(mappedBy = "user")
	private List<Leaderboard> leaderboards;
	
	@OneToMany(mappedBy = "user")
	private List<Log> logs;
	
	@ElementCollection
	@CollectionTable(name = "marketing_answer", joinColumns = @JoinColumn(name = "user_id"))
	@MapKeyJoinColumns({
		@MapKeyJoinColumn(name = "questionnaire_date", referencedColumnName = "questionnaire_date"), 
		@MapKeyJoinColumn(name = "questionnaire_number", referencedColumnName = "number")})
	@Column(name = "text")
	private Map<MarketingQuestion, String> marketingAnswers;
	
	public User() {}
	
	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.admin = false;
		this.blocked = false;
	}

	public int getId() {
		return this.user_id;
	}

	public void setId(int id) {
		this.user_id = id;
	}

	public Boolean getAdmin() {
		return this.admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	public Boolean getBlocked() {
		return this.blocked;
	}

	public void setBlocked(Boolean blocked) {
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
		result = prime * result + admin.hashCode();
		result = prime * result + blocked.hashCode();
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