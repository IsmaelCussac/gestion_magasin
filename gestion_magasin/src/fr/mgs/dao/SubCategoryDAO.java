package fr.mgs.dao;

import java.sql.SQLException;
import java.util.Collection;

import fr.mgs.connection.Connection;
import fr.mgs.model.product.SubCategory;

//Claire GERARD

public class SubCategoryDAO extends GenericDAO<SubCategory,String> {

	public SubCategoryDAO(Connection connection) {
		super.connection = connection;
	}

	@Override
	public void add(SubCategory t) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(SubCategory t) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean exists(String id) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public SubCategory find(String id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<SubCategory> findAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(String id) throws SQLException {
		// TODO Auto-generated method stub
		
	}

}
