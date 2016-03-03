package fr.mgs.business;

import java.sql.SQLException;

import javax.annotation.PostConstruct;

import fr.mgs.dao.DAOManager;
import fr.mgs.dao.GenericDAO;
import fr.mgs.dao.Table;
import fr.mgs.model.order.Order;
import fr.mgs.model.order.OrderLine;

/**
 * Business class that manage the following DAOs to access database and process data :
 * - OrderDAO
 * - OrderLineDAO
 * 
 * @author Ismaël
 *
 */
public class OrderManager{
	
	private DAOManager daoManager;
	private GenericDAO<Order> orderDao;
	private GenericDAO<OrderLine> orderLineDao;

	public OrderManager(){}
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() throws SQLException{
		daoManager = new DAOManager();
		daoManager.init();
		orderDao = (GenericDAO<Order>) daoManager.getDAO(Table.ORDER);
		orderLineDao = (GenericDAO<OrderLine>) daoManager.getDAO(Table.ORDER_LINE);
	}
	
	// GETTERS - SETTERS

	public DAOManager getDaoManager() {
		return daoManager;
	}

	public void setDaoManager(DAOManager daoManager) {
		this.daoManager = daoManager;
	}

	public GenericDAO<Order> getOrderDao() {
		return orderDao;
	}

	public void setOrderDao(GenericDAO<Order> orderDao) {
		this.orderDao = orderDao;
	}

	public GenericDAO<OrderLine> getOrderLineDao() {
		return orderLineDao;
	}

	public void setOrderLineDao(GenericDAO<OrderLine> orderLineDao) {
		this.orderLineDao = orderLineDao;
	}
	
	// METHODS
	
	
}
