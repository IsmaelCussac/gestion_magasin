package fr.mgs.dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import fr.mgs.connection.Connection;
import fr.mgs.model.order.Order;
import fr.mgs.model.order.OrderLine;
import fr.mgs.model.order.OrderStatus;
import fr.mgs.model.user.Team;
import fr.mgs.model.user.Person;

/**
 * This class is used to manage order's dao
 * 
 * @author Ibrahima
 *
 */
public class OrderDAO extends GenericDAO<Order, Integer> {

	public OrderDAO(Connection connection) {
		super.connection = connection;
	}

	/**
	 * create new order
	 * 
	 * @param the
	 *            order to create
	 */
	@Override
	public void add(Order o) throws SQLException {
		beginTransaction();
		em.persist(o);
		commit();
		closeEm();

	}

	/**
	 * update the given order's properties
	 * 
	 * @param the
	 *            order to update
	 */
	@Override
	public void update(Order o) throws SQLException {
		beginTransaction();
		em.persist(em.merge(o));
		commit();
		closeEm();
	}

	/**
	 * checking if an order exists
	 * 
	 * @param the
	 *            order's id
	 */
	@Override
	public boolean exists(Integer id) throws SQLException {
		return (find(id) != null);
	}

	/**
	 * return an order by given it's id
	 * 
	 * @param the
	 *            order's id
	 */
	@Override
	public Order find(Integer id) throws SQLException {
		loadEm();
		Order orderToFind = em.find(Order.class, id);
		return orderToFind;
	}

	/**
	 * return all orders
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Order> findAll() throws SQLException {
		loadEm();
		Query query = em.createQuery("SELECT o FROM orders o");
		return (List<Order>) query.getResultList();
	}

	/**
	 * return all orders with the status "status"
	 * 
	 * @param the
	 *            order's status
	 */
	@SuppressWarnings("unchecked")
	public List<Order> findOrderByStatus(OrderStatus status) {
		loadEm();
		Query query = em.createQuery("SELECT o FROM orders o WHERE o.status = :os");
		query.setParameter("os", status);
		return (List<Order>) query.getResultList();
	}

	/**
	 * return the given user's orders
	 * 
	 * @param the
	 *            user
	 */

	@SuppressWarnings("unchecked")
	public List<Order> findOrderByUser(Person u) {
		loadEm();
		Query query = em.createQuery("SELECT o FROM orders o WHERE o.orderUser.userId = :ou");
		query.setParameter("ou", u.getUserId());
		return (List<Order>) query.getResultList();
	}

	/**
	 * return the given team's orders
	 * 
	 * @param the
	 *            team
	 */
	public Collection<Order> findOrderByTeam(Team t) {
		loadEm();
		Query query = em.createQuery("SELECT o FROM orders o WHERE o.orderUser.team = :ot");
		query.setParameter("ot", t);
		return (Collection<Order>) query.getResultList();
	}

	/**
	 * remove the given order id
	 * 
	 * @param the
	 *            order's id
	 * 
	 */
	@Override
	public void remove(Integer id) throws SQLException {
		Order order = find(id);
		beginTransaction();
		em.remove(em.merge(order));
		commit();
		closeEm();
	}

	public Collection<Order> findOrdersByUser(String userId) {
		loadEm();
		Query query = em.createQuery("SELECT o FROM orders o WHERE o.orderUser.userId = :u ORDER BY o.orderId desc");
		query.setParameter("u", userId);
		return query.getResultList();

	}

	public boolean hasNotValidatedOrder(String userId) {
		return (findNotValidatedOrder(userId).size() > 0);
	}

	public Collection<Order> findNotValidatedOrder(String userId) {
		loadEm();
		Query query = em.createQuery("SELECT o FROM orders o WHERE o.orderUser.userId = :u AND o.status = :s");
		query.setParameter("u", userId);
		query.setParameter("s", OrderStatus.NOT_VALIDATED);
		return query.getResultList();
	}

	public List<Order> findOrdersByUser(Person p) {
		loadEm();
		Query query = em.createQuery("SELECT o FROM orders o WHERE o.orderUser = :u ORDER BY o.orderId desc");
		query.setParameter("u", p);
		return query.getResultList();
	}

}
