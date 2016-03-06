package fr.mgs.dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.persistence.TypedQuery;

import fr.mgs.connection.Connection;
import fr.mgs.model.product.Product;

/**
 * Dao used to manage product entity
 * 
 * @author Ismaël
 * @author Ibrahima
 */
public class ProductDAO extends GenericDAO<Product, Integer> {

	public ProductDAO(Connection connection) {
		super.connection = connection;
	}

	/**
	 * store a product in database
	 * 
	 * @param the
	 *            product to add
	 */
	public void add(Product product) throws SQLException {
		beginTransaction();
		em.persist(product);
		commit();
		closeEm();
	}

	/**
	 * remove a product stored in database using his id
	 * 
	 * @param product's
	 *            id
	 */
	public void remove(Integer productId) throws SQLException {
		Product product = find(productId);
		beginTransaction();
		em.remove(em.merge(product));
		commit();
		closeEm();
	}

	/**
	 * update a product's attributes according to the fact the product is
	 * already stored in database
	 * 
	 * @param product's
	 *            bean updated
	 */
	public void update(Product product) throws SQLException {
		beginTransaction();
		em.persist(em.merge(product));
		commit();
		closeEm();
	}

	/**
	 * Search if a product exists
	 * 
	 * @param product's
	 *            id
	 */
	public boolean exists(Integer productId) throws SQLException {
		return (find(productId) != null);
	}

	/**
	 * find a product using his id
	 * 
	 * @param product's
	 *            id
	 */
	public Product find(Integer productId) throws SQLException {
		loadEm();
		Product product = em.find(Product.class, productId);
		closeEm();
		return product;
	}

	/**
	 * return all the stored products ordered by their designation
	 */
	public Collection<Product> findAll() throws SQLException {
		loadEm();
		TypedQuery<Product> query = em.createQuery("FROM products p order by p.designation asc", Product.class);
		List<Product> result = query.getResultList();
		closeEm();
		return result;
	}
}
