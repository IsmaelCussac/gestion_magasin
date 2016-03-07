package fr.mgs.dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import fr.mgs.connection.Connection;
import fr.mgs.model.product.Category;
import fr.mgs.model.product.Product;
import fr.mgs.model.product.SubCategory;
import fr.mgs.model.user.Team;

/**
 * Dao used to manage SubCategory entity
 * 
 * @author Ismaël
 *
 */
public class SubCategoryDAO extends GenericDAO<SubCategory,String> {

	public SubCategoryDAO(Connection connection) {
		super.connection = connection;
	}

	/**
	 * store a subCategory in database
	 * 
	 * @param the subCategory to add
	 */
	public void add(SubCategory subCategory) throws SQLException {
		beginTransaction();
		em.persist(subCategory);
		commit();
		closeEm();
	}

	/**
	 * remove a subCategory stored in database using his id
	 * 
	 * @param subCategory's  id
	 */
	public void remove(String subCategoryId) throws SQLException {
		SubCategory subCategory = find(subCategoryId);
		beginTransaction();
		em.remove(em.merge(subCategory));
		commit();
		closeEm();
	}

	/**
	 * update a subCategory's attributes according to the fact the subCategory is already
	 * stored in database
	 * 
	 * @param subCategory's bean updated
	 */
	public void update(SubCategory subCategory) throws SQLException {
		beginTransaction();
		em.persist(em.merge(subCategory));
		commit();
		closeEm();
	}

	/**
	 * Search if a subCategory exists
	 * 
	 * @param subCategory's id
	 */
	public boolean exists(String subCategoryId) throws SQLException {
		return (find(subCategoryId) != null);
	}

	/**
	 * find a subCategory using his id
	 * 
	 * @param user's id
	 */
	public SubCategory find(String subCategoryId) throws SQLException {
		loadEm();
		SubCategory subCategory = em.find(SubCategory.class, subCategoryId);
		closeEm();
		return subCategory;
	}

	/**
	 * return all the stored subCategories ordered by their name
	 */
	public Collection<SubCategory> findAll() throws SQLException {
		loadEm();
		TypedQuery<SubCategory> query = em.createQuery("FROM subCategories s order by s.name asc", SubCategory.class);
		List<SubCategory> result = query.getResultList();
		closeEm();
		return result;
	}

	/**
	 * return the given category's subCategories
	 * 
	 * @param the category
	 */
	public Collection<SubCategory> findProductsBySubCategory(Category category) {
		loadEm();
		Query query = em.createQuery("SELECT s FROM subCategories s WHERE s.category = :c");
		query.setParameter("c", category);
		return query.getResultList();
	}
}
