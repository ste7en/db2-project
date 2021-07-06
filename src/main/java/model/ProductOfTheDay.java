package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Date;


/**
 * Model class describing a ProductOfTheDay as it is implemented
 * in the database schema.
 */
@Entity
@Table(name = "product_of_the_day")
public class ProductOfTheDay implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Temporal(TemporalType.DATE)
	private Date date;
	
	/**
	 * ManyToOne relationship describing the product of a given date.
	 * Used to retrieve the product of the day to show it in the HomePage.
	 * @see ProductOfTheDayService
	 * @see GoToHomePage
	 */
	@ManyToOne
	(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_of_the_day", referencedColumnName = "product_id")
	private Product product;
	
	/**
	 * Not necessary but implemented as bidirectional read-only relationship.
	 * The lines `updatable = false, insertable = false` enable it to be read-only.
	 */
	@OneToMany
	(fetch = FetchType.LAZY, mappedBy = "productOfTheDay")
	@JoinColumn(name = "questionnaire_date", updatable = false, insertable = false)
	private List<MarketingQuestion> marketingQuestions;
	
	/**
	 * Not necessary but implemented as bidirectional read-only relationship.
	 * The lines `updatable = false, insertable = false` enable it to be read-only.
	 */
	@OneToMany
	(fetch = FetchType.LAZY, mappedBy = "productOfTheDay")
	@JoinColumn(name = "questionnaire_date", updatable = false, insertable = false)
	private List<StatisticalAnswer> statisticalAnswers;
	
	/**
	 * Not necessary but implemented as bidirectional read-only relationship.
	 * The lines `updatable = false, insertable = false` enable it to be read-only.
	 */
	@OneToMany
	(fetch = FetchType.LAZY, mappedBy = "productOfTheDay")
	@JoinColumn(name = "questionnaire_date", updatable = false, insertable = false)
	private List<Leaderboard> leaderboards;

	public ProductOfTheDay() {}

	public ProductOfTheDay(Date d, Product p) {
		this.date = d;
		this.product = p;
	}

	/**
	 * Getters and setters
	 */
	
	public Date getDate() {
		return this.date;
	}
	
	public Product getProduct() {
		return this.product;
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, leaderboards, marketingQuestions, product, statisticalAnswers);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ProductOfTheDay))
			return false;
		ProductOfTheDay other = (ProductOfTheDay) obj;
		return Objects.equals(date, other.date) && Objects.equals(leaderboards, other.leaderboards)
				&& Objects.equals(marketingQuestions, other.marketingQuestions)
				&& Objects.equals(product, other.product)
				&& Objects.equals(statisticalAnswers, other.statisticalAnswers);
	}
}