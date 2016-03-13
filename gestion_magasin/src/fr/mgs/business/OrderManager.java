package fr.mgs.business;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import fr.mgs.connection.DataSource;
import fr.mgs.dao.DAOManager;
import fr.mgs.dao.GenericDAO;
import fr.mgs.dao.OrderDAO;
import fr.mgs.dao.OrderLineDAO;
import fr.mgs.dao.Table;
import fr.mgs.model.order.Order;
import fr.mgs.model.order.OrderLine;
import fr.mgs.model.order.OrderLinePK;
import fr.mgs.model.user.Person;

/**
 * Business class that manage the following DAOs to access database and process
 * data : - OrderDAO - OrderLineDAO
 * 
 * @author Ismaël
 * @author Ibrahima
 */
public class OrderManager {

	private DAOManager daoManager;
	private OrderDAO orderDao;
	private OrderLineDAO orderLineDao;

	public OrderManager() {
	}

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init(DataSource ds) throws SQLException {
		daoManager = new DAOManager();
		daoManager.init(ds);
		orderDao = (OrderDAO) daoManager.getDAO(Table.ORDER);
		orderLineDao = (OrderLineDAO) daoManager.getDAO(Table.ORDER_LINE);
	}

	// GETTERS - SETTERS

	public DAOManager getDaoManager() {
		return daoManager;
	}

	public void setDaoManager(DAOManager daoManager) {
		this.daoManager = daoManager;
	}

	public OrderDAO getOrderDao() {
		return orderDao;
	}

	public void setOrderDao(OrderDAO orderDao) {
		this.orderDao = orderDao;
	}

	public OrderLineDAO getOrderLineDao() {
		return orderLineDao;
	}

	public void setOrderLineDao(OrderLineDAO orderLineDao) {
		this.orderLineDao = orderLineDao;
	}

	// METHODS

	public void addOrder(Order order) throws SQLException {
		orderDao.add(order);
	}

	public void addOrderLine(OrderLine orderLine) throws SQLException {
		orderLineDao.add(orderLine);
	}

	public Order findOrder(Integer id) throws SQLException {
		return orderDao.find(id);
	}
	
	public OrderLine findOrderLine(Integer id) throws SQLException {
		return orderLineDao.find(id);
	}
	
	public void removeOrder(Integer id) throws SQLException{
		orderDao.remove(id);
	}
	
	public void removeOrderLine(Integer id) throws SQLException{
		orderLineDao.remove(id);
	}

	public Collection<Order> findAllOrders() throws SQLException {
		return orderDao.findAll();
	}
	
	public Collection<OrderLine> findAllOrderLines() throws SQLException {
		return orderLineDao.findAll();
	}

	public boolean orderExists(Integer id) throws SQLException {
		return orderDao.exists(id);
	}
	
	public boolean orderLineExists(Integer id) throws SQLException {
		return orderLineDao.exists(id);
	}
	
	public void updateOrder(Order order) throws SQLException{
		orderDao.update(order);
	}
	
	public void updateOrderLine(OrderLine orderLine) throws SQLException{
		orderLineDao.update(orderLine);
	}

	public boolean hasNotValidatedOrder(String userId) {
		return orderDao.hasNotValidatedOrder(userId);
	}
	
	public Collection<Order> findNotValidatedOrder(String userId){
		return orderDao.findNotValidatedOrder(userId);
	}

	public List<Order> findAllOrdersByUser(String userId) {
		return (List<Order>) orderDao.findOrdersByUser(userId);
	}

	public Collection<Order> findOrdersByUser(Person p) {
		return (List<Order>) orderDao.findOrdersByUser(p);
	}

	public void removeOrderLine(OrderLinePK orderLinePK) throws SQLException {
		orderLineDao.removeOrderLine(orderLinePK);
		
	}
}
