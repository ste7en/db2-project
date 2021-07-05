package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

import java.util.Date;


/**
 * The persistent class for the product_of_the_day database table.
 * 
 */
@Entity
@Table(name="product_of_the_day")
@NamedQueries({
	@NamedQuery(name="ProductOfTheDay.findAll", query="SELECT p FROM ProductOfTheDay p")
})
public class ProductOfTheDay implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Temporal(TemporalType.DATE)
	private Date date;
	
	@ManyToOne
	@JoinColumn(name="product_of_the_day", referencedColumnName = "product_id")
	private Product product;
	
	@OneToMany(mappedBy = "productOfTheDay")
	@JoinColumn(name = "questionnaire_date", updatable = false, insertable = false)
	private List<MarketingQuestion> marketingQuestions;
	
	@OneToMany(mappedBy = "productOfTheDay")
	@JoinColumn(name = "questionnaire_date", updatable = false, insertable = false)
	private List<StatisticalAnswer> statisticalAnswers;
	
	@OneToMany(mappedBy = "productOfTheDay")
	@JoinColumn(name = "questionnaire_date", updatable = false, insertable = false)
	private List<Leaderboard> leaderboards;

	public ProductOfTheDay() {}

	public ProductOfTheDay(Date d, Product p) {
		this.date = d;
		this.product = p;
	}

	public Date getDate() {
		return this.date;
	}
	
	public Product getProduct() {
		return this.product;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + this.product.getProduct_id();
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
		ProductOfTheDay other = (ProductOfTheDay) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (this.product != other.product)
			return false;
		return true;
	}
}