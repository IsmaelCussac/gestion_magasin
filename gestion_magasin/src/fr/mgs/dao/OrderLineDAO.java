package fr.mgs.dao;

import java.sql.SQLException;
import java.util.Collection;

import fr.mgs.connection.Connection;
import fr.mgs.model.order.OrderLine;

public class OrderLineDAO extends GenericDAO<OrderLine> {

	public OrderLineDAO(Connection connection) {
		super.connection = connection;
	}

	@Override
	public void add(OrderLine t) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(OrderLine t) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean exists(String id) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public OrderLine find(String id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<OrderLine> findAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	

}