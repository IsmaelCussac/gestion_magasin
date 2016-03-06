package fr.mgs.dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import fr.mgs.connection.Connection;
import fr.mgs.model.event.Action;
import fr.mgs.model.user.Team;
import fr.mgs.model.user.User;

/**
 * Dao used to manage user entity
 * 
 * @author Ismaël
 *
 */
public class UserDAO extends GenericDAO<User,String> {

	public UserDAO(Connection connection) {
		super.connection = connection;
	}

	public UserDAO() {
	}

	/**
	 * store a user in database
	 * 
	 * @param the
	 *            user to add
	 */
	public void add(User user) throws SQLException {
		beginTransaction();
		em.persist(user);
		commit();
		closeEm();
	}

	/**
	 * remove a user stored in database using his id
	 * 
	 * @param user's
	 *            id
	 */
	public void remove(String userId) throws SQLException {
		User user = find(userId);
		beginTransaction();
		em.remove(em.merge(user));
		commit();
		closeEm();
	}

	/**
	 * update a user's attributes according to the fact the user is already
	 * stored in database
	 * 
	 * @param user's
	 *            bean updated
	 */
	public void update(User user) throws SQLException {
		beginTransaction();
		em.persist(em.merge(user));
		commit();
		closeEm();
	}

	/**
	 * Search if a user exists
	 * 
	 * @param user's
	 *            id
	 */
	public boolean exists(String userId) throws SQLException {
		return (find(userId) != null);
	}

	/**
	 * find a user using his id
	 * 
	 * @param user's
	 *            id
	 */
	public User find(String userId) throws SQLException {
		loadEm();
		User user = em.find(User.class, userId);
		closeEm();
		return user;
	}

	/**
	 * return all the stored users ordered by their first name at first, then
	 * their name
	 */
	public Collection<User> findAll() throws SQLException {
		loadEm();
		TypedQuery<User> query = em.createQuery("FROM users u order by u.firstName, u.lastName asc", User.class);
		List<User> result = query.getResultList();
		closeEm();
		return result;
	}
	
	/**
	 * return the given team's users
	 * 
	 * @param the team
	 */
	public List<User> findUserByTeam(Team t) {
		loadEm();
		Query query = em.createQuery("SELECT u FROM users u WHERE u.team = :ot");
		query.setParameter("ot", t);
		return (List<User>) query.getResultList();
	}

}
