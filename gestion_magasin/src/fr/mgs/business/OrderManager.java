package fr.mgs.business;

import java.sql.SQLException;
import java.util.Collection;

import javax.annotation.PostConstruct;

import fr.mgs.connection.DataSource;
import fr.mgs.dao.DAOManager;
import fr.mgs.dao.GenericDAO;
import fr.mgs.dao.Table;
import fr.mgs.model.order.Order;
import fr.mgs.model.order.OrderLine;

/**
 * Business class that manage the following DAOs to access database and process
 * data : - OrderDAO - OrderLineDAO
 * 
 * @author Ismaël
 * @author Ibrahima
 */
public class OrderManager {

	private DAOManager daoManager;
	private GenericDAO<Order, Integer> orderDao;
	private GenericDAO<OrderLine, ?> orderLineDao;

	public OrderManager() {
	}

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init(DataSource ds) throws SQLException {
		daoManager = new DAOManager();
		daoManager.init(ds);
		orderDao = (GenericDAO<Order, Integer>) daoManager.getDAO(Table.ORDER);
		orderLineDao = (GenericDAO<OrderLine, ?>) daoManager.getDAO(Table.ORDER_LINE);
	}

	// GETTERS - SETTERS

	public DAOManager getDaoManager() {
		return daoManager;
	}

	public void setDaoManager(DAOManager daoManager) {
		this.daoManager = daoManager;
	}

	public GenericDAO<Order, Integer> getOrderDao() {
		return orderDao;
	}

	public void setOrderDao(GenericDAO<Order, Integer> orderDao) {
		this.orderDao = orderDao;
	}

	public GenericDAO<OrderLine, ?> getOrderLineDao() {
		return orderLineDao;
	}

	public void setOrderLineDao(GenericDAO<OrderLine, ?> orderLineDao) {
		this.orderLineDao = orderLineDao;
	}

	// METHODS

	public void addOrder(Order order) throws SQLException {
		orderDao.add(order);
	}

	public Order findOrder(int id) throws SQLException {
		return orderDao.find(id);
	}

	public Collection<Order> findAllOrders() throws SQLException {
		return orderDao.findAll();
	}

}
