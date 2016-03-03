package fr.mgs.dao;

import java.sql.SQLException;
import java.util.Collection;

import fr.mgs.connection.Connection;
import fr.mgs.model.historical.Historical;

public class HistoricalDAO extends GenericDAO<Historical> {

	public HistoricalDAO(Connection connection) {
		super.connection = connection;
	}

	@Override
	public void add(Historical t) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Historical t) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean exists(String id) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Historical find(String id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Historical> findAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
