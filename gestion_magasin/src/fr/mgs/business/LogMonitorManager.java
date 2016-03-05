package fr.mgs.business;

import java.sql.SQLException;

import javax.annotation.PostConstruct;

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
	private GenericDAO<LogMonitor, ?> logMonitorDao;

	public LogMonitorManager() {
	}

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() throws SQLException {
		daoManager = new DAOManager();
		daoManager.init();
		logMonitorDao = (GenericDAO<LogMonitor, ?>) daoManager.getDAO(Table.LOG_MONITOR);
	}

	// GETTERS - SETTERS

	public DAOManager getDaoManager() {
		return daoManager;
	}

	public void setDaoManager(DAOManager daoManager) {
		this.daoManager = daoManager;
	}

	public GenericDAO<LogMonitor, ?> getLogMonitorDao() {
		return logMonitorDao;
	}

	public void setLogMonitorDao(GenericDAO<LogMonitor, ?> logMonitorDao) {
		this.logMonitorDao = logMonitorDao;
	}

	// METHODS

}
