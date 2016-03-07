package fr.mgs.dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import fr.mgs.connection.Connection;
import fr.mgs.model.order.Order;
import fr.mgs.model.order.OrderLine;
import fr.mgs.model.user.Team;
import fr.mgs.model.user.User;

/**
 * Dao used to manage order line entity
 * 
 * @author Ismaël
 *
 */
public class OrderLineDAO extends GenericDAO<OrderLine, Integer> {

	public OrderLineDAO(Connection connection) {
		super.connection = connection;
	}

	/**
	 * store an orderLine in database
	 * 
	 * @param the orderLine to add
	 */
	public void add(OrderLine orderLine) throws SQLException {
		beginTransaction();
		em.persist(orderLine);
		commit();
		closeEm();
	}

	/**
	 * remove an orderLine stored in database using his id
	 * 
	 * @param orderLine's id
	 */
	public void remove(Integer orderLineId) throws SQLException {
		OrderLine orderLine = find(orderLineId);
		beginTransaction();
		em.remove(em.merge(orderLine));
		commit();
		closeEm();
	}

	/**
	 * update a orderLine's attributes according to the fact the user is already
	 * stored in database
	 * 
	 * @param orderLine's bean updated
	 */
	public void update(OrderLine orderLine) throws SQLException {
		beginTransaction();
		em.persist(em.merge(orderLine));
		commit();
		closeEm();
	}

	/**
	 * Search if a orderLine exists
	 * 
	 * @param orderLine's id
	 */
	public boolean exists(Integer orderLineId) throws SQLException {
		return (find(orderLineId) != null);
	}

	/**
	 * find a orderLine using his id
	 * 
	 * @param orderLine's id
	 */
	public OrderLine find(Integer orderLineId) throws SQLException {
		loadEm();
		OrderLine orderLine = em.find(OrderLine.class, orderLineId);
		closeEm();
		return orderLine;
	}
	
	/**
	 * return the given order's orderLines
	 * 
	 * @param the order
	 */
	public List<OrderLine> findOrderLineByOrder(Order order) {
		loadEm();
		Query query = em.createQuery("SELECT o FROM orderLines o WHERE o.order = :or");
		query.setParameter("or", order);
		return (List<OrderLine>) query.getResultList();
	}
	
	//Unused

	@Override
	public Collection<OrderLine> findAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
