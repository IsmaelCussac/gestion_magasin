package fr.mgs.business;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.persistence.Query;

import fr.mgs.model.order.Order;
import fr.mgs.model.order.OrderLine;
import fr.mgs.model.order.OrderLinePK;
import fr.mgs.model.order.OrderStatus;
import fr.mgs.model.product.Product;
import fr.mgs.model.user.Person;
import fr.mgs.model.user.Team;

/**
 * Business class that manage the following DAOs to access database and process
 * data : 
 * - OrderDAO 
 * - OrderLineDAO
 * 
 * @author Ismaël
 * @author Ibrahima
 */
public class OrderManager extends Manager {

	// ORDER

	/**
	 * create new order
	 * 
	 * @param order
	 *            the order to create
	 */
	public void addOrder(Order order) throws SQLException {
		beginTransaction();
		em.persist(order);
		commit();
		closeEm();

	}

	/**
	 * update the given order's properties
	 * 
	 * @param order
	 *            the order to update
	 */
	public void updateOrder(Order order) throws SQLException {
		beginTransaction();
		em.persist(em.merge(order));
		commit();
		closeEm();
	}

	/**
	 * checking if an order exists
	 * 
	 * @param orderId
	 *            the order's id
	 */
	public boolean orderExists(Integer orderId) throws SQLException {
		return findOrder(orderId) != null;
	}

	/**
	 * return an order by given it's id
	 * 
	 * @param orderId
	 *            the order's id
	 */
	public Order findOrder(Integer orderId) throws SQLException {
		loadEm();
		return em.find(Order.class, orderId);
	}

	/**
	 * return all orders
	 */
	public List<Order> findAllOrders() throws SQLException {
		loadEm();
		Query query = em.createQuery("SELECT o FROM orders o");
		return (List<Order>) query.getResultList();
	}

	/**
	 * return all orders with the status "status"
	 * 
	 * @param status
	 *            the order's status
	 */
	public List<Order> findOrderByStatus(OrderStatus status) {
		loadEm();
		Query query = em.createQuery("SELECT o FROM orders o WHERE o.status = :os");
		query.setParameter("os", status);
		return (List<Order>) query.getResultList();
	}

	/**
	 * return the given person's orders
	 * 
	 * @param person
	 *            the person
	 */
	public List<Order> findOrderByUser(Person person) {
		loadEm();
		Query query = em.createQuery("SELECT o FROM orders o WHERE o.orderUser.userId = :ou");
		query.setParameter("ou", person.getPersonId());
		return (List<Order>) query.getResultList();
	}

	/**
	 * return the given team's orders
	 * 
	 * @param team
	 *            the team
	 */
	public Collection<Order> findOrderByTeam(Team team) {
		loadEm();
		Query query = em.createQuery("SELECT o FROM orders o WHERE o.orderUser.team = :ot");
		query.setParameter("ot", team);
		return (Collection<Order>) query.getResultList();
	}

	/**
	 * remove the given order id
	 * 
	 * @param orderId
	 *            the order's id
	 * 
	 */
	public void removeOrder(Integer orderId) throws SQLException {
		Order order = findOrder(orderId);
		beginTransaction();
		em.remove(em.merge(order));
		commit();
		closeEm();
	}

	/**
	 * Find all orders belonging to a user
	 * 
	 * @param userId
	 *            the person's id
	 * @return Collection of Order
	 */
	public Collection<Order> findAllOrdersByPerson(String personId) {
		loadEm();
		Query query = em.createQuery("SELECT o FROM orders o WHERE o.orderUser.userId = :u ORDER BY o.orderId desc");
		query.setParameter("u", personId);
		return query.getResultList();

	}

	/**
	 * Find all orders belonging to a user
	 * 
	 * @param person
	 *            the person
	 * @return Collection of Order
	 */
	public List<Order> findAllOrdersByPerson(Person person) {
		loadEm();
		Query query = em.createQuery("SELECT o FROM orders o WHERE o.orderUser = :u ORDER BY o.orderId desc");
		query.setParameter("u", person);
		return query.getResultList();
	}

	/**
	 * Looks for a NOT_VALIDATED order
	 * 
	 * @param userId
	 * @return true if a NOT_VALIDATEd order exists, else false.
	 */
	public boolean hasNotValidatedOrder(String userId) {
		return !findNotValidatedOrder(userId).isEmpty();
	}

	/**
	 * Looks for a NOT_VALIDATED order
	 * 
	 * @param userId
	 * @return A collection of NOT_VALIDATED orders
	 */
	public Collection<Order> findNotValidatedOrder(String userId) {
		loadEm();
		Query query = em.createQuery("SELECT o FROM orders o WHERE o.orderUser.userId = :u AND o.status = :s");
		query.setParameter("u", userId);
		query.setParameter("s", OrderStatus.NOT_VALIDATED);
		return query.getResultList();
	}

	// ORDERLINE

	/**
	 * store an orderLine in database
	 * 
	 * @param orderLine
	 *            the orderLine to add
	 */
	public void addOrderLine(OrderLine orderLine) throws SQLException {
		beginTransaction();
		em.persist(orderLine);
		commit();
		closeEm();
	}

	/**
	 * update a orderLine's attributes according to the fact the user is already
	 * stored in database
	 * 
	 * @param orderLine
	 *            orderLine's bean updated
	 */
	public void updateOrderLine(OrderLine orderLine) throws SQLException {
		beginTransaction();
		em.persist(em.merge(orderLine));
		commit();
		closeEm();
	}

	/**
	 * Search if a orderLine exists
	 * 
	 * @param orderLinePK
	 *            orderLine's id
	 */
	public boolean orderLineExists(OrderLinePK orderLinePK) throws SQLException {
		return findOrderLine(orderLinePK) != null;
	}

	/**
	 * find a orderLine using his id
	 * 
	 * @param orderLinePK
	 *            orderLine's id
	 */
	public OrderLine findOrderLine(OrderLinePK orderLinePK) throws SQLException {
		loadEm();
		OrderLine orderLine = em.find(OrderLine.class, orderLinePK);
		closeEm();
		return orderLine;
	}

	/**
	 * return the given order's orderLines
	 * 
	 * @param order
	 *            the order
	 */
//	public List<OrderLine> findOrderLineByOrder(Order order) {
//		loadEm();
//		Query query = em.createQuery("SELECT o FROM orderLines o WHERE o.order = :or");
//		query.setParameter("or", order);
//		return (List<OrderLine>) query.getResultList();
//	}

	/**
	 * remove an orderLine stored in database using his PK
	 * 
	 * @param orderLinePK
	 *            orderLine's pk
	 */
	public void removeOrderLine(OrderLinePK orderLinePK) throws SQLException {
		beginTransaction();
		Order ord = em.find(Order.class, orderLinePK.getOrder());
		Product prod = em.find(Product.class, orderLinePK.getProduct());
		Query query = em.createQuery("DELETE FROM orderLines ol WHERE ol.order = :ord AND ol.product = :prod");
		query.setParameter("ord", ord);
		query.setParameter("prod", prod);
		query.executeUpdate();
		commit();
		closeEm();
	}

}
