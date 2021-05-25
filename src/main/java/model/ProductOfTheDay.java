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

}