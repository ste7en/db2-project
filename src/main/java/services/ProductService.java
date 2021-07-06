package services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import model.Product;

@Stateless
public class ProductService {
	@PersistenceContext(unitName = "db2-alparone-ferrara-formicola")
	protected EntityManager em;
	
	public ProductService() {}
	
	public Product findProduct(int product_id) {
		return em.find(Product.class, product_id);
	}
	
	public List<Product> findAllProducts(){
		TypedQuery<Product> query = em.createNamedQuery("Product.findAll", Product.class);
		return query.getResultList();
	}
}
