package fr.mgs.dao;

import java.sql.SQLException;
import java.util.Collection;

import fr.mgs.connection.Connection;
import fr.mgs.model.monitor.LogMonitor;

public class LogMonitorDAO extends GenericDAO<LogMonitor, Integer> {

	public LogMonitorDAO(Connection connection) {
		super.connection = connection;
	}

	@Override
	public void add(LogMonitor t) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(LogMonitor t) throws SQLException {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean exists(Integer id) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public LogMonitor find(Integer id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<LogMonitor> findAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(Integer id) throws SQLException {
		// TODO Auto-generated method stub
		
	}

}
