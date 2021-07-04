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
		ProductOfTheDay pofd = new ProductOfTheDay(d, p);
		em.persist(pofd);
		return pofd;
	}
	
	public ProductOfTheDay findProductByDate(Date d) {
		return em.find(ProductOfTheDay.class, d);
	}
	
	public boolean removeProductOfTheDay(Date d) {
		ProductOfTheDay pofd= findProductByDate(d);
		if(pofd == null) return false;
		em.remove(pofd);
		return true;
	}
}
