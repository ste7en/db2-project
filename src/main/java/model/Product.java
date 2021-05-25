package model;

import java.io.Serializable;
import javax.persistence.*;


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
	@Column(name="`product-id`")
	private int product_id;

	private String description;

	private int id;

	@Lob
	private byte[] image;

	private String name;


	private float price;

	private int unitcost;

	public Product() {
	}

	public int getProduct_id() {
		return this.product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getUnitcost() {
		return this.unitcost;
	}

	public void setUnitcost(int unitcost) {
		this.unitcost = unitcost;
	}

}