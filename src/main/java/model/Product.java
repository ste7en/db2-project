package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;
import java.util.Map;


/**
 * The persistent class for the product database table.
 * 
 */
@Entity
@Table(name="product")
@NamedQuery(name="Product.findAll", query="SELECT p FROM Product p")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int product_id;

	@Lob
	@Column(name="image")
	private byte[] image;

	@Column(name="name")
	private String name;

	@Column(name="price")
	private float price;

	//bi-directional many-to-one association to ProductOfTheDay
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "product")
	private List<ProductOfTheDay> productOfTheDays;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "review", joinColumns = @JoinColumn(name = "product_id"))
	@MapKeyJoinColumn(name = "user_id")
	@Column(name = "text")
	private Map<User, String> reviews;
	
	public Map<User,String> getReviews(){
		return this.reviews;
	}
	
	public Product() {}

	public int getProduct_id() {
		return this.product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public byte[] getImage() {
		return this.image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return this.price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public List<ProductOfTheDay> getProductOfTheDays() {
		return this.productOfTheDays;
	}

	public void setProductOfTheDays(List<ProductOfTheDay> productOfTheDays) {
		this.productOfTheDays = productOfTheDays;
	}
	
	public ProductOfTheDay addProductOfTheDay(ProductOfTheDay productOfTheDay) {
		getProductOfTheDays().add(productOfTheDay);
		productOfTheDay.setProduct(this);

		return productOfTheDay;
	}

	public ProductOfTheDay removeProductOfTheDay(ProductOfTheDay productOfTheDay) {
		getProductOfTheDays().remove(productOfTheDay);
		productOfTheDay.setProduct(null);

		return productOfTheDay;
	}

}