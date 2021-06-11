package services;

import java.util.Collection;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import model.User;
import model.Product;

public class ProductService {
	protected EntityManager em;
	
	public ProductService(EntityManager em) {
		this.em=em;
	}
	
	public Product createProduct(int product_id, byte[] image, String name, float price) {
		Product p=new Product();
		p.setProduct_id(product_id);
		p.setImage(image);
		p.setName(name);
		p.setPrice(price);
		em.persist(p);
		return p;
	}
	
	public Product findProduct(int product_id) {
		return em.find(Product.class, product_id);
	}

	public void removeProduct(int product_id) {
		Product p=findProduct(product_id);
		if(p!=null)
			em.remove(p);
	}
	
	public Collection<Product> findAllProducts(){
		TypedQuery query=em.createQuery("SELECT p FROM Product p", Product.class);
		return query.getResultList();
	}
	
	public Map<User,String> getReviews(Product p){
		Product product= findProduct(p.getProduct_id());
		if(product==null) {
			throw new RuntimeException("Prodotto non esistente, riprovare");
		}
		return p.getReviews();
	}
}