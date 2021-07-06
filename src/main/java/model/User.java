package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * The persistent class for the User database table.
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "User.checkCredentials", query = "SELECT u FROM User u WHERE u.username = ?1 and u.password = ?2"),
	@NamedQuery(name = "User.checkByUserAndEmail", query = "SELECT count(u) FROM User as u WHERE u.username = ?1 or u.email = ?2")	
})
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int user_id;

	@Column(name = "admin")
	private Boolean admin;
	
	@Column(name = "blocked")
	private Boolean blocked;
	
	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "username")
	private String username;

	/**
	 * Not necessary but implemented as bidirectional relationship.
	 */
	@ElementCollection
	(fetch = FetchType.LAZY)
	@CollectionTable(name = "review", joinColumns = @JoinColumn(name = "user_id"))
	@MapKeyJoinColumn(name = "product_id")
	@Column(name = "text")
	private Map<Product, String> productReviews;
	
	/**
	 * Not necessary but implemented as bidirectional relationship.
	 */
	@OneToMany
	(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", insertable = false, updatable = false) //read-only
	private List<StatisticalAnswer> statisticalAnswers;
	
	/**
	 * Not necessary but implemented as bidirectional relationship.
	 */
	@OneToMany
	(fetch = FetchType.LAZY)
	@JoinColumn(name = "user", insertable = false, updatable = false) //read-only
	private List<Leaderboard> leaderboards;
	
	/**
	 * Not necessary but implemented as bidirectional relationship.
	 */
	@OneToMany
	(fetch = FetchType.LAZY)
	@JoinColumn(name = "user", insertable = false, updatable = false) //read-only
	private List<Log> logs;
	
	/**
	 * Not necessary but implemented as bidirectional relationship.
	 */
	@ElementCollection
	(fetch = FetchType.LAZY)
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

	/**
	 * Getters and setters
	 */
	
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
		return Objects.hash(admin, blocked, email, leaderboards, logs, marketingAnswers, password, productReviews,
				statisticalAnswers, user_id, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		return Objects.equals(admin, other.admin) && Objects.equals(blocked, other.blocked)
				&& Objects.equals(email, other.email) && Objects.equals(leaderboards, other.leaderboards)
				&& Objects.equals(logs, other.logs) && Objects.equals(marketingAnswers, other.marketingAnswers)
				&& Objects.equals(password, other.password) && Objects.equals(productReviews, other.productReviews)
				&& Objects.equals(statisticalAnswers, other.statisticalAnswers) && user_id == other.user_id
				&& Objects.equals(username, other.username);
	}
}