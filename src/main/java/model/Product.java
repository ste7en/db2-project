package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.Base64;


/**
 * Model class describing a Product entity as it is implemented
 * in the database schema.
 */
@Entity
@Table(name = "product")
@NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int product_id;

	@Lob
	@Column(name = "image")
	private byte[] image;

	@Column(name = "name")
	private String name;

	@Column(name = "price")
	private float price;

	/**
	 * Not necessary but implemented as bidirectional read-only relationship.
	 * The lines `updatable = false, insertable = false` enable it to be read-only.
	 */
	@OneToMany
	(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_of_the_day", insertable = false, updatable = false)
	private List<ProductOfTheDay> productOfTheDays;
	
	/**
	 * ManyToMany relationship allowing to retrieve, for each user, their
	 * reviews about the product described by this entity.
	 */
	@ElementCollection
	(fetch = FetchType.LAZY)
	@CollectionTable(name = "review", joinColumns = @JoinColumn(name = "product_id"))
	@MapKeyJoinColumn(name = "user_id")
	@Column(name = "text")
	private Map<User, String> reviews;
	
	public Product() {}
	
	/**
	 * Getters and setters
	 */
	
	public Map<User,String> getReviews(){
		return this.reviews;
	}

	public int getProduct_id() {
		return this.product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public String getImage() {
		return Base64.getMimeEncoder().encodeToString(image);
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
		return productOfTheDay;
	}

	public ProductOfTheDay removeProductOfTheDay(ProductOfTheDay productOfTheDay) {
		getProductOfTheDays().remove(productOfTheDay);
		return productOfTheDay;
	}

}