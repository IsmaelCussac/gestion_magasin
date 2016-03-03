package fr.mgs.dao;

import java.sql.SQLException;
import java.util.Collection;

import javax.persistence.EntityManager;

import fr.mgs.connection.Connection;

public abstract class GenericDAO<T> {
	
	protected Connection connection;
	protected EntityManager em;
	
	/**
	 * Method to init the Entity Manager Factory
	 */
	public void init() {
		connection = new Connection();
		this.connection.initEmf();
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public void setConnection(Connection connection){
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

	public abstract boolean exists(String id) throws SQLException;
	
	public abstract T find(String id) throws SQLException;
	
	public abstract Collection<T> findAll() throws SQLException;

}
