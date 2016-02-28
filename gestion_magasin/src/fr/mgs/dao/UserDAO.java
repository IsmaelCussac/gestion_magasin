package fr.mgs.dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.persistence.TypedQuery;

import fr.mgs.models.user.User;

/**
 * Dao used to manage user entity
 * 
 * @author Ismaël
 *
 */
public class UserDAO extends DAO {

	
	/**
	 * store a user in database
	 * @param the user to add
	 */
	public void add(User user) throws SQLException {
		beginTransaction();
		em.persist(user);
		commit();
		closeEm();
	}
	
	/**
	 * remove a user stored in database using his id
	 * @param user's id
	 */
	public void remove(String userId) throws SQLException {
		User user = find(userId);
		beginTransaction();
		em.remove(em.merge(user));
		commit();
		closeEm();
	}
	
	/**
	 * update a user's attributes
	 * according to the fact the user is already stored in database
	 * @param user's bean updated
	 */
	public void update(User user) throws SQLException {
		beginTransaction();
		em.persist(em.merge(user));
		commit();
		closeEm();
	}
	
	/**
	 * Search if a user exists
	 * @param user's id
	 */
	public boolean exists(String userId) throws SQLException {
		return (find(userId) != null);
	}
	
	/**
	 * find a user using his id
	 * @param user's id
	 */
	public User find(String userId) throws SQLException {
		loadEm();
		User user = em.find(User.class, userId);
		closeEm();
		return user;
	}

	/**
	 * return all the stored users
	 * ordered by their firstname at first, then their name
	 */
	public Collection<User> findAll() throws SQLException {
		loadEm();
		TypedQuery<User> query = em.createQuery("FROM users u order by u.firstName, u.lastName asc",
				User.class);
		List<User> result = query.getResultList();
		closeEm();
		return result;
	}
	
	
}
