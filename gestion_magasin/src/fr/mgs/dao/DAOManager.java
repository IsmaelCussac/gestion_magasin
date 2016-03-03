package fr.mgs.dao;

import java.sql.SQLException;

import javax.annotation.PostConstruct;

import fr.mgs.connection.Connection;
import fr.mgs.model.user.Team;
import fr.mgs.model.user.User;

public class DAOManager {

	private Connection connection;
	private GenericDAO<?> dao;


	public DAOManager() {
	}

	@PostConstruct
	public void init() {
		connection = new Connection();
		connection.initEmf();
		dao.setConnection(connection);
	}

	public GenericDAO getDAO(Table table) throws SQLException {
		switch (table) {
		case USER:
			return new UserDAO();
		case TEAM:
			return new TeamDAO();
		case PRODUCT:
			return new ProductDAO();
		case LOT:
			return new LotDAO();
		case SUB_CATEGORY:
			return new SubCategoryDAO();
		case ORDER:
			return new OrderDAO();
		case ORDER_LINE:
			return new OrderLineDAO();
		case HISTORICAL:
			return new HistoricalDAO();
		default:
			throw new SQLException("Trying to link to an unexistant table.");
		}
	}
}
