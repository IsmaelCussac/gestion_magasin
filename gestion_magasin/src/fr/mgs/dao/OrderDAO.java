package fr.mgs.dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.persistence.Query;

import fr.mgs.connection.Connection;
import fr.mgs.model.order.Order;
import fr.mgs.model.order.OrderStatus;
import fr.mgs.model.user.Team;
import fr.mgs.model.user.User;

public class OrderDAO extends GenericDAO<Order, Integer> {

	public OrderDAO(Connection connection) {
		super.connection = connection;
	}

	@Override
	public void add(Order o) throws SQLException {
		beginTransaction();
		em.persist(o);
		commit();
		closeEm();

	}

	@Override
	public void update(Order o) throws SQLException {
		beginTransaction();
		em.persist(em.merge(o));
		commit();
		closeEm();
	}

	@Override
	public boolean exists(Integer id) throws SQLException {
		return (find(id) != null);
	}

	@Override
	public Order find(Integer id) throws SQLException {
		loadEm();
		Order orderToFind = em.find(Order.class, id);
		return orderToFind;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Order> findAll() throws SQLException {
		loadEm();
		Query query = em.createQuery("SELECT o FROM orders o");
		return (Collection<Order>) query.getResultList();
	}

	public Collection<Order> findOrderByStatus(OrderStatus status) {
		loadEm();
		Query query = em.createQuery("SELECT o FROM orders o WHERE o.status = :os");
		query.setParameter("os", status);
		return (Collection<Order>) query.getResultList();
	}

	public List<Order> findOrderByTeam(Team t) {

		return null;
	}

	public List<Order> findOrderByUser(User u) {

		return null;
	}

	@Override
	public void remove(Integer id) throws SQLException {
		// TODO Auto-generated method stub

	}

}
