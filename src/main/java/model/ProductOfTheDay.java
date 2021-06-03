package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the product_of_the_day database table.
 * 
 */
@Entity
@Table(name="product_of_the_day")
@NamedQuery(name="ProductOfTheDay.findAll", query="SELECT p FROM ProductOfTheDay p")
public class ProductOfTheDay implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Temporal(TemporalType.DATE)
	@Column(name="date")
	private Date date;

	@Column(name="product_of_the_day")
	private int productOfTheDay;

	public ProductOfTheDay() {
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getProductOfTheDay() {
		return this.productOfTheDay;
	}

	public void setProductOfTheDay(int productOfTheDay) {
		this.productOfTheDay = productOfTheDay;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + productOfTheDay;
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
		if (productOfTheDay != other.productOfTheDay)
			return false;
		return true;
	}
	
	@ManyToOne
	private Product product;
	
	public void setProduct(Product p) {
		this.product=p;
	}
	
	public Product getProduct() {
		return this.product;
	}
}