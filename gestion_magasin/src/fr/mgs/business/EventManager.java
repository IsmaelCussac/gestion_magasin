package fr.mgs.business;

import java.sql.SQLException;
import java.util.Collection;

import javax.annotation.PostConstruct;

import fr.mgs.connection.DataSource;
import fr.mgs.dao.DAOManager;
import fr.mgs.dao.GenericDAO;
import fr.mgs.dao.Table;
import fr.mgs.model.event.Event;

/**
 * Business class that manage the following DAO to access database and process
 * data : - LogMonitorDAO
 * 
 * @author Ismaël
 * @author Ibrahima
 * 
 *
 */
public class EventManager {

	private DAOManager daoManager;
	private GenericDAO<Event, Integer> logMonitorDao;

	public EventManager() {
	}

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init(DataSource ds) throws SQLException {
		daoManager = new DAOManager();
		daoManager.init(ds);
		logMonitorDao = (GenericDAO<Event, Integer>) daoManager.getDAO(Table.EVENT);
	}

	// GETTERS - SETTERS

	public DAOManager getDaoManager() {
		return daoManager;
	}

	public void setDaoManager(DAOManager daoManager) {
		this.daoManager = daoManager;
	}

	public GenericDAO<Event, Integer> getLogMonitorDao() {
		return logMonitorDao;
	}

	public void setLogMonitorDao(GenericDAO<Event, Integer> logMonitorDao) {
		this.logMonitorDao = logMonitorDao;
	}

	// METHODS
	
	public void addLogMonitor(Event logMonitor) throws SQLException {
		logMonitorDao.add(logMonitor);
	}

	public Event findLogMonitor(Integer id) throws SQLException {
		return logMonitorDao.find(id);
	}
	
	public void removeLogMonitor(Integer id) throws SQLException{
		logMonitorDao.remove(id);
	}
	
	public Collection<Event> findAllLogMonitors() throws SQLException {
		return logMonitorDao.findAll();
	}
	
	public boolean logMonitorExists(Integer id) throws SQLException {
		return logMonitorDao.exists(id);
	}
	
	public void updateLogMonitor(Event logMonitor) throws SQLException{
		logMonitorDao.update(logMonitor);
	}
	
}
