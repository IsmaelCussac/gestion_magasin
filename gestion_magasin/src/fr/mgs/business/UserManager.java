package fr.mgs.business;

import java.sql.SQLException;

import javax.annotation.PostConstruct;

import fr.mgs.dao.DAOManager;
import fr.mgs.dao.GenericDAO;
import fr.mgs.dao.Table;
import fr.mgs.model.user.Team;
import fr.mgs.model.user.User;


public class UserManager{

	private DAOManager daoManager;
	private GenericDAO<User> userDao;
	private GenericDAO<Team> teamDao;

	public UserManager(){}
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() throws SQLException{
		daoManager = new DAOManager();
		daoManager.init();
		userDao = (GenericDAO<User>) daoManager.getDAO(Table.USER);
		teamDao = (GenericDAO<Team>) daoManager.getDAO(Table.TEAM);
	}

	public DAOManager getDaoManager() {
		return daoManager;
	}

	// GETTERS - SETTERS
	
	public void setDaoManager(DAOManager daoManager) {
		this.daoManager = daoManager;
	}

	public GenericDAO<User> getUserDao() {
		return userDao;
	}

	public void setUserDao(GenericDAO<User> userDao) {
		this.userDao = userDao;
	}

	public GenericDAO<Team> getTeamDao() {
		return teamDao;
	}

	public void setTeamDao(GenericDAO<Team> teamDao) {
		this.teamDao = teamDao;
	}
	
	// METHODS
	
	public void addUser(User user) throws SQLException{
		userDao.add(user);
	}
	
	public void addTeam(Team team) throws SQLException{
		teamDao.add(team);
	}

	public User findUser(String id) throws SQLException {
		return userDao.find(id);
	}
}
