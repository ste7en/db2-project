package services;

import java.util.Collection;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import model.Product;
import model.ProductOfTheDay;

public class ProductOfTheDayService {
protected EntityManager em;
	
	public ProductOfTheDayService(EntityManager em) {
		this.em=em;
	}
	
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