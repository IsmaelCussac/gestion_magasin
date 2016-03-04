package fr.mgs.dao;

import java.sql.SQLException;
import java.util.Collection;

import fr.mgs.connection.Connection;
import fr.mgs.model.product.Lot;

//Mariana ANDUJAR


public class LotDAO extends GenericDAO<Lot> {

	public LotDAO(Connection connection) {
		super.connection = connection;
	}

	@Override
	public void add(Lot t) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Lot t) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean exists(String id) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Lot find(String id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Lot> findAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
