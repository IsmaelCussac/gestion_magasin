package fr.mgs.business;

import java.sql.SQLException;
import java.util.Collection;

import javax.annotation.PostConstruct;

import fr.mgs.connection.DataSource;
import fr.mgs.dao.DAOManager;
import fr.mgs.dao.GenericDAO;
import fr.mgs.dao.Table;
import fr.mgs.model.monitor.LogMonitor;

/**
 * Business class that manage the following DAO to access database and process
 * data : - LogMonitorDAO
 * 
 * @author Ismaël
 * @author Ibrahima
 * 
 *
 */
public class LogMonitorManager {

	private DAOManager daoManager;
	private GenericDAO<LogMonitor, Integer> logMonitorDao;

	public LogMonitorManager() {
	}

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init(DataSource ds) throws SQLException {
		daoManager = new DAOManager();
		daoManager.init(ds);
		logMonitorDao = (GenericDAO<LogMonitor, Integer>) daoManager.getDAO(Table.LOG_MONITOR);
	}

	// GETTERS - SETTERS

	public DAOManager getDaoManager() {
		return daoManager;
	}

	public void setDaoManager(DAOManager daoManager) {
		this.daoManager = daoManager;
	}

	public GenericDAO<LogMonitor, Integer> getLogMonitorDao() {
		return logMonitorDao;
	}

	public void setLogMonitorDao(GenericDAO<LogMonitor, Integer> logMonitorDao) {
		this.logMonitorDao = logMonitorDao;
	}

	// METHODS
	
	public void addLogMonitor(LogMonitor logMonitor) throws SQLException {
		logMonitorDao.add(logMonitor);
	}

	public LogMonitor findLogMonitor(Integer id) throws SQLException {
		return logMonitorDao.find(id);
	}
	
	public void removeLogMonitor(Integer id) throws SQLException{
		logMonitorDao.remove(id);
	}
	
	public Collection<LogMonitor> findAllLogMonitors() throws SQLException {
		return logMonitorDao.findAll();
	}
	
	public boolean logMonitorExists(Integer id) throws SQLException {
		return logMonitorDao.exists(id);
	}
	
	public void updateLogMonitor(LogMonitor logMonitor) throws SQLException{
		logMonitorDao.update(logMonitor);
	}
	
}
