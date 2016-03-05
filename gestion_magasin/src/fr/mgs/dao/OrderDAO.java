package fr.mgs.dao;

import java.sql.SQLException;
import java.util.Collection;

import javax.persistence.Query;

import fr.mgs.connection.Connection;
import fr.mgs.model.order.Order;

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

	public Order find(Integer id) throws SQLException {
		return em.find(Order.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Order> findAll() throws SQLException {
		Query query = em.createQuery("SELECT o FROM Order o");
		return (Collection<Order>) query.getResultList();
	}

}
