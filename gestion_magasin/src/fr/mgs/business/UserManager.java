package fr.mgs.business;

import java.sql.SQLException;
import java.util.Collection;

import javax.annotation.PostConstruct;

import fr.mgs.connection.DataSource;
import fr.mgs.dao.DAOManager;
import fr.mgs.dao.GenericDAO;
import fr.mgs.dao.Table;
import fr.mgs.model.user.Team;
import fr.mgs.model.user.Person;

/**
 * Business class that manage the following DAOs to access database and process
 * data : - UserDAO - TeamDAO
 * 
 * @author Ismaël
 * @author Ibrahima
 *
 */
public class UserManager {

	private DAOManager daoManager;
	private GenericDAO<Person, String> userDao;
	private GenericDAO<Team, String> teamDao;

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init(DataSource ds) throws SQLException {
		daoManager = new DAOManager();
		daoManager.init(ds);
		userDao = (GenericDAO<Person, String>) daoManager.getDAO(Table.USER);
		teamDao = (GenericDAO<Team, String>) daoManager.getDAO(Table.TEAM);
	}

	// GETTERS - SETTERS

	public DAOManager getDaoManager() {
		return daoManager;
	}

	public void setDaoManager(DAOManager daoManager) {
		this.daoManager = daoManager;
	}

	public GenericDAO<Person, String> getUserDao() {
		return userDao;
	}

	public void setUserDao(GenericDAO<Person, String> userDao) {
		this.userDao = userDao;
	}

	public GenericDAO<Team, String> getTeamDao() {
		return teamDao;
	}

	public void setTeamDao(GenericDAO<Team, String> teamDao) {
		this.teamDao = teamDao;
	}

	// METHODS

	public void addUser(Person user) throws SQLException {
		userDao.add(user);
	}

	public void addTeam(Team team) throws SQLException {
		teamDao.add(team);
	}

	public Person findUser(String id) throws SQLException {
		return userDao.find(id);
	}
	
	public Team findTeam(String id) throws SQLException {
		return teamDao.find(id);
	}
	
	public void removeUser(String id) throws SQLException{
		userDao.remove(id);
	}
	
	public void removeTeam(String id) throws SQLException{
		teamDao.remove(id);
	}

	public Collection<Person> findAllUsers() throws SQLException {
		return userDao.findAll();
	}
	
	public Collection<Team> findAllTeams() throws SQLException {
		return teamDao.findAll();
	}

	public boolean userExists(String id) throws SQLException {
		return userDao.exists(id);
	}
	
	public boolean teamExists(String id) throws SQLException {
		return teamDao.exists(id);
	}
	
	public void updateUser(Person user) throws SQLException{
		userDao.update(user);
	}
	
	public void updateTeam(Team team) throws SQLException{
		teamDao.update(team);
	}
	
}
