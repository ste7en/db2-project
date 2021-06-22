package services;

import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.Product;
import model.ProductOfTheDay;

@Stateless
public class ProductOfTheDayService {
	@PersistenceContext(unitName = "db2-alparone-ferrara-formicola")
	protected EntityManager em;
	
	public ProductOfTheDayService() {}
	
	public ProductOfTheDay createProductOfTheDay(Product p, Date d) {
		ProductOfTheDay pofd=new ProductOfTheDay();
		pofd.setProduct(p);
		pofd.setDate(d);
		em.persist(pofd);
		return pofd;
	}
	
	public ProductOfTheDay findProductByDate(Date d) {
		return em.find(ProductOfTheDay.class, d);
	}
	
	public ProductOfTheDay findProductByProduct(Product p) {
		return em.find(ProductOfTheDay.class, p);
	}

	public void removeProductOfTheDay(Product p) {
		ProductOfTheDay pofd= findProductByProduct(p);
		if(p!=null)
			em.remove(p);
	}
	
	
	

}
