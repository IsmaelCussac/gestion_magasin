package fr.mgs.business;

import java.sql.SQLException;

import javax.annotation.PostConstruct;

import fr.mgs.dao.DAOManager;
import fr.mgs.dao.GenericDAO;
import fr.mgs.dao.Table;
import fr.mgs.model.historical.Historical;

/**
 * Business class that manage the following DAO to access database and process data :
 * - HistoricalDAO
 * 
 * @author Ismaël
 *
 */
public class HistoricalManager{

	
	private DAOManager daoManager;
	private GenericDAO<Historical> historicalDao;

	public HistoricalManager(){}
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() throws SQLException{
		daoManager = new DAOManager();
		daoManager.init();
		historicalDao = (GenericDAO<Historical>) daoManager.getDAO(Table.HISTORICAL);
	}
	
	// GETTERS - SETTERS

	public DAOManager getDaoManager() {
		return daoManager;
	}

	public void setDaoManager(DAOManager daoManager) {
		this.daoManager = daoManager;
	}

	public GenericDAO<Historical> getHistoricalDao() {
		return historicalDao;
	}

	public void setHistoricalDao(GenericDAO<Historical> historicalDao) {
		this.historicalDao = historicalDao;
	}
	
	// METHODS
	
	
}
