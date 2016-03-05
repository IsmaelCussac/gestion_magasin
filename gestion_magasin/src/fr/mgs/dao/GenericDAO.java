package fr.mgs.dao;

import java.sql.SQLException;
import java.util.Collection;

import javax.persistence.EntityManager;

import fr.mgs.connection.Connection;

/**
 * Abstract class used that contains the connection instance for all the DAOs
 * 
 * @author Ismaël
 * @author Ibrahima
 * @param <T,U>
 */
public abstract class GenericDAO<T, U> {

	protected Connection connection;
	protected EntityManager em;

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	/**
	 * Use the persistence unit to load a new entityManager into this.em
	 */
	protected void loadEm() {
		em = connection.createEm();
	}

	/**
	 * Close Entity Manager
	 */
	protected void closeEm() {
		em.close();
	}

	/**
	 * Load a new EntityManager then begin a transaction
	 */
	protected void beginTransaction() {
		loadEm();
		em.getTransaction().begin();
	}

	/**
	 * Commit the persistence context
	 */
	protected void commit() {
		em.getTransaction().commit();
	}

	/**
	 * Close the persistence unit
	 */
	public void close() {
		this.connection.close();
	}

	public abstract void add(T t) throws SQLException;

	public abstract void update(T t) throws SQLException;

	public abstract boolean exists(U id) throws SQLException;

	public abstract T find(U id) throws SQLException;

	public abstract Collection<T> findAll() throws SQLException;

}
