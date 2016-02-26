package fr.mgs.dao;

import java.sql.SQLException;

import fr.mgs.models.user.User;

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
}
