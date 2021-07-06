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
	
	/**
	 * Service method used in the CreationPage for a new questionnaire
	 * @param p
	 * @param d
	 * @return the new created ProductOfTheDay
	 */
	public ProductOfTheDay createProductOfTheDay(Product p, Date d) {
		ProductOfTheDay pofd = new ProductOfTheDay(d, p);
		em.persist(pofd);
		return pofd;
	}
	
	/**
	 * Service method used in the HomePage to retrieve the product of the day
	 * @param d Date
	 * @return the product of the day
	 */
	public ProductOfTheDay findProductByDate(Date d) {
		return em.find(ProductOfTheDay.class, d);
	}
	
	/**
	 * Deletes a ProductOfTheDay corresponding to a date
	 * @param d Date
	 * @return true if the product of the day exists, false otherwise
	 */
	public boolean removeProductOfTheDay(Date d) {
		ProductOfTheDay pofd= findProductByDate(d);
		if(pofd == null) return false;
		em.remove(pofd);
		return true;
	}
}
