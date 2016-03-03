package fr.mgs.dao;

import java.sql.SQLException;

import javax.annotation.PostConstruct;

import fr.mgs.connection.Connection;

/**
 * DAO manager that implement the factory design pattern
 * 
 * @author Ismaël
 *
 */
public class DAOManager {

	private Connection connection;

	public DAOManager() {
	}

	/**
	 * Method to init the Entity Manager Factory
	 */
	@PostConstruct
	public void init() {
		connection = new Connection();
		connection.initEmf();
	}

	public GenericDAO<?> getDAO(Table table) throws SQLException {
		switch (table) {
		case USER:
			return new UserDAO(connection);
		case TEAM:
			return new TeamDAO(connection);
		case PRODUCT:
			return new ProductDAO(connection);
		case LOT:
			return new LotDAO(connection);
		case SUB_CATEGORY:
			return new SubCategoryDAO(connection);
		case ORDER:
			return new OrderDAO(connection);
		case ORDER_LINE:
			return new OrderLineDAO(connection);
		case HISTORICAL:
			return new HistoricalDAO(connection);
		default:
			throw new SQLException("Trying to link to an unexistant table.");
		}
	}

	public void close() {
		connection.close();
	}
}
