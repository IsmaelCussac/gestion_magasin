package fr.mgs.business;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import fr.mgs.model.product.Category;
import fr.mgs.model.product.Lot;
import fr.mgs.model.product.Product;
import fr.mgs.model.product.SubCategory;

/**
 * Business class that manage the following DAOs to access database and process
 * data : 
 * - Lot 
 * - Product 
 * - SubCategory
 * 
 * @author Ismaël
 * @author Ibrahima
 * @author Mana
 */
public class ProductManager extends Manager {

	// LOT

	/**
	 * Add a new lot
	 * 
	 * @param lot
	 *            the new lot
	 */
	public void addLot(Lot lot) throws SQLException {
		beginTransaction();
		em.persist(lot);
		commit();
		closeEm();
	}

	/**
	 * update a lot's properties
	 * 
	 * @param lot
	 *            the lot update
	 */
	public void updateLot(Lot lot) throws SQLException {
		beginTransaction();
		em.persist(em.merge(lot));
		commit();
		closeEm();
	}

	/**
	 * Verify if the lot exist
	 * 
	 * @param id
	 *            lot id
	 */
	public boolean lotExists(Integer id) throws SQLException {
		return findLot(id) != null;
	}

	/**
	 * Find one lot
	 * 
	 * @param id
	 *            the lot id to find
	 */
	public Lot findLot(Integer id) throws SQLException {
		loadEm();
		return em.find(Lot.class, id);
		
	}

	/**
	 * Get all lot
	 * 
	 * @return Collection lot's collection
	 */
	public Collection<Lot> findAllLots() throws SQLException {
		loadEm();
		Query query = em.createQuery("SELECT l FROM lots l");
		return (Collection<Lot>) query.getResultList();
	}

	/**
	 * Delete a lot
	 * 
	 * @param id
	 *            lot id
	 */
	public void removeLot(Integer id) throws SQLException {
		Lot lot = findLot(id);
		beginTransaction();
		em.remove(em.merge(lot));
		commit();
		closeEm();
	}
	
	public Collection<Lot> findAllOutOfDateLots() {
		loadEm();
		Date currentDate = new Date(System.currentTimeMillis());
		Query query = em.createQuery("SELECT l FROM lots l WHERE l.expirationDate <= DATE(:currentDate) + l.lotProduct.warningPeriod");
		query.setParameter("currentDate", currentDate);
		return (Collection<Lot>) query.getResultList();
		
	}
	
	


	// PRODUCT

	/**
	 * store a product in database
	 * 
	 * @param product
	 *            the product to add
	 */
	public void addProduct(Product product) throws SQLException {
		beginTransaction();
		em.persist(product);
		commit();
		closeEm();
	}

	/**
	 * remove a product stored in database using his id
	 * 
	 * @param productId
	 *            product's id
	 */
	public void removeProduct(Integer productId) throws SQLException {
		Product product = findProduct(productId);
		beginTransaction();
		em.remove(em.merge(product));
		commit();
		closeEm();
	}

	/**
	 * update a product's attributes according to the fact the product is
	 * already stored in database
	 * 
	 * @param product
	 *            product's bean updated
	 */
	public void updateProduct(Product product) throws SQLException {
		beginTransaction();
		em.persist(em.merge(product));
		commit();
		closeEm();
	}

	/**
	 * Search if a product exists
	 * 
	 * @param productId
	 *            product's id
	 */
	public boolean productExists(Integer productId) throws SQLException {
		return findProduct(productId) != null;
	}

	/**
	 * find a product using his id
	 * 
	 * @param productId
	 *            product's id
	 */
	public Product findProduct(Integer productId) throws SQLException {
		loadEm();
		Product product = em.find(Product.class, productId);
		closeEm();
		return product;
	}

	/**
	 * return all the stored products ordered by their designation
	 */
	public Collection<Product> findAllProducts() throws SQLException {
		loadEm();
		TypedQuery<Product> query = em.createQuery("FROM products p order by p.designation, p.subCategory.name asc", Product.class);
		List<Product> result = query.getResultList();
		closeEm();
		return result;
	}

	/**
	 * return the given subCategory's products only in visible state
	 * 
	 * @param subCategory
	 *            the sub category
	 */
	public Collection<Product> findProductsBySubCategoryVisible(SubCategory subCategory) {
		loadEm();
		Query query = em.createQuery("SELECT p FROM products p WHERE p.subCategory = :sc AND p.visibility = true");
		query.setParameter("sc", subCategory);
		return query.getResultList();
	}

	/**
	 * return the given subCategory's products
	 * 
	 * @param subCategory
	 *            the sub category
	 */
	public Collection<Product> findProductsBySubCategory(SubCategory subCategory) {
		loadEm();
		Query query = em.createQuery("SELECT p FROM products p WHERE p.subCategory = :sc");
		query.setParameter("sc", subCategory);
		return query.getResultList();
	}

	// SUB CATEGORY

	/**
	 * store a subCategory in database
	 * 
	 * @param subCategory
	 *            the sub category to add
	 */
	public void addSubCategory(SubCategory subCategory) throws SQLException {
		beginTransaction();
		em.persist(subCategory);
		commit();
		closeEm();
	}

	/**
	 * remove a subCategory stored in database using his id
	 * 
	 * @param subCategoryId
	 *            sub category's id
	 */
	public void removeSubCategory(String subCategoryId) throws SQLException {
		SubCategory subCategory = findSubCategory(subCategoryId);
		beginTransaction();
		em.remove(em.merge(subCategory));
		commit();
		closeEm();
	}

	/**
	 * update a subCategory's attributes according to the fact the subCategory
	 * is already stored in database
	 * 
	 * @param subCategory
	 *            sub category's bean updated
	 */
	public void updateSubCategory(SubCategory subCategory) throws SQLException {
		beginTransaction();
		em.persist(em.merge(subCategory));
		commit();
		closeEm();
	}

	/**
	 * Search if a subCategory exists
	 * 
	 * @param subCategoryId
	 *            sub category's id
	 */
	public boolean subCategoryExists(String subCategoryId) throws SQLException {
		return findSubCategory(subCategoryId) != null;
	}

	/**
	 * find a subCategory using his id
	 * 
	 * @param subCategoryId
	 *            user's id
	 */
	public SubCategory findSubCategory(String subCategoryId) throws SQLException {
		loadEm();
		SubCategory subCategory = em.find(SubCategory.class, subCategoryId);
		closeEm();
		return subCategory;
	}

	/**
	 * return all the stored subCategories ordered by their name
	 */
	public Collection<SubCategory> findAllSubCategories() throws SQLException {
		loadEm();
		TypedQuery<SubCategory> query = em.createQuery("FROM subCategories s order by s.name asc", SubCategory.class);
		List<SubCategory> result = query.getResultList();
		closeEm();
		return result;
	}

	/**
	 * return the given category's subCategories
	 * 
	 * @param category
	 *            the category
	 */
	public Collection<SubCategory> findSubCategoriesByCategory(Category category) {
		loadEm();
		Query query = em.createQuery("SELECT s FROM subCategories s WHERE s.category = :c");
		query.setParameter("c", category);
		return query.getResultList();
	}
	
}
