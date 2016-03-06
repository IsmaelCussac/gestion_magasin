package fr.mgs.dao;

import java.sql.SQLException;
import java.util.Collection;

import javax.persistence.Query;

import fr.mgs.connection.Connection;
import fr.mgs.model.product.Lot;

/**
 * This class is used to manage the lot
 * 
 * @author Mana
 *
 */
public class LotDAO extends GenericDAO<Lot, String> {

	/**
	 * Connection to the Dao
	 * 
	 * @param connection
	 */
	public LotDAO(Connection connection) {
		super.connection = connection;
	}

	/**
	 * Add a new lot
	 * 
	 * @param lot
	 *            the new lot
	 */
	@Override
	public void add(Lot t) throws SQLException {
		beginTransaction();
		em.persist(t);
		commit();
		closeEm();
	}

	/**
	 * update a lot's properties
	 * 
	 * @param lot
	 *            the lot update
	 */
	@Override
	public void update(Lot t) throws SQLException {
		beginTransaction();
		em.persist(em.merge(t));
		commit();
		closeEm();
	}

	/**
	 * Verify if the lot exist
	 * 
	 * @param id
	 *            lot id
	 */
	@Override
	public boolean exists(String id) throws SQLException {
		return (find(id) != null);
	}

	/**
	 * Find one lot
	 * 
	 * @param id
	 *            the lot id to find
	 */
	@Override
	public Lot find(String id) throws SQLException {
		loadEm();
		Lot orderToFind = em.find(Lot.class, id);
		return orderToFind;
	}

	/**
	 * Get all lot
	 * 
	 * @return Collection lot's collection
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Lot> findAll() throws SQLException {
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
	@Override
	public void remove(String id) throws SQLException {
		Lot lot = find(id);
		beginTransaction();
		em.remove(em.merge(lot));
		commit();
		closeEm();
	}

}
