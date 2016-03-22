package fr.mgs.business;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import fr.mgs.connection.Connection;
import fr.mgs.connection.DataSource;

/**
 * DAO manager that implement the factory design pattern
 * 
 * @author Ismaël
 * @author Ibrahima
 */
public class Manager {

	private Connection connection;
	protected EntityManager em;

	/**
	 * Method to init the Entity Manager Factory
	 */
	@PostConstruct
	public void init(DataSource ds) {
		connection = new Connection();
		connection.initEmf(ds);
	}

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
		if (em.isOpen())
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
		connection.close();
	}
}
