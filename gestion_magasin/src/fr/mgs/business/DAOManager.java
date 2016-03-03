package fr.mgs.business;

import java.sql.SQLException;

import javax.annotation.PostConstruct;

import fr.mgs.connection.Connection;
import fr.mgs.dao.GenericDAO;
import fr.mgs.dao.HistoricalDAO;
import fr.mgs.dao.LotDAO;
import fr.mgs.dao.OrderDAO;
import fr.mgs.dao.OrderLineDAO;
import fr.mgs.dao.ProductDAO;
import fr.mgs.dao.SubCategoryDAO;
import fr.mgs.dao.TeamDAO;
import fr.mgs.dao.UserDAO;
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
