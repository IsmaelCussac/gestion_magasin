package fr.mgs.dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import fr.mgs.connection.Connection;
import fr.mgs.model.order.Order;
import fr.mgs.model.order.OrderLine;
import fr.mgs.model.order.OrderLinePK;
import fr.mgs.model.product.Product;
import fr.mgs.model.user.Team;
import fr.mgs.model.user.Person;

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
	 * @param the
	 *            orderLine to add
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
	 * @param orderLine's
	 *            id
	 */
	public void remove(Integer orderLineId) throws SQLException {
		OrderLine orderLine = find(orderLineId);
		beginTransaction();
		if (orderLine != null)
			em.remove(em.merge(orderLine));
		commit();
		closeEm();
	}

	/**
	 * update a orderLine's attributes according to the fact the user is already
	 * stored in database
	 * 
	 * @param orderLine's
	 *            bean updated
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
	 * @param orderLine's
	 *            id
	 */
	public boolean exists(Integer orderLineId) throws SQLException {
		return (find(orderLineId) != null);
	}

	/**
	 * find a orderLine using his id
	 * 
	 * @param orderLine's
	 *            id
	 */
	public OrderLine find(OrderLinePK orderLinePK) throws SQLException {
		loadEm();
		OrderLine orderLine = em.find(OrderLine.class, orderLinePK);
		closeEm();
		return orderLine;
	}

	/**
	 * return the given order's orderLines
	 * 
	 * @param the
	 *            order
	 */
	public List<OrderLine> findOrderLineByOrder(Order order) {
		loadEm();
		Query query = em.createQuery("SELECT o FROM orderLines o WHERE o.order = :or");
		query.setParameter("or", order);
		return (List<OrderLine>) query.getResultList();
	}

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

	@Override
	public Collection<OrderLine> findAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderLine find(Integer id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
