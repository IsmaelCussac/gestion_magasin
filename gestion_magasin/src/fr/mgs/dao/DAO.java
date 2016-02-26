package fr.mgs.dao;

import javax.persistence.EntityManager;

public abstract class DAO {
	
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
	
	protected void setConnection(Connection connection){
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

}
