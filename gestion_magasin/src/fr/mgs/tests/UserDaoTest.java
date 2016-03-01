package fr.mgs.tests;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.mgs.dao.TeamDAO;
import fr.mgs.dao.UserDAO;
import fr.mgs.model.user.Privilege;
import fr.mgs.model.user.Team;
import fr.mgs.model.user.User;

public class UserDaoTest {
	
	static UserDAO userDao;
	static TeamDAO teamDao;


	@BeforeClass
	public static void setUpBeforeClass() throws SQLException {
		userDao = new UserDAO();
		teamDao = new TeamDAO();
	}

	@AfterClass
	public static void tearDownAfterAll() {
	}

	@Before
	public void setUp() throws SQLException {
		userDao.init();
		teamDao.init();
	
	}

	@After
	public void tearDown() {
		userDao.close();
	}

	@Test
	public void testAddPerson() throws SQLException {
		
		Team team = new Team();
		team.setTeam("ccc", "CCC", 2, Privilege.CUSTOMER);
		teamDao.add(team);
		
		User user = new User();
		user.setUser("d1102526", "Jean-Louis", "De Beauregard", team, "0442060504", "jean-louis.de-beauregard@mail.fr", "secret");
		userDao.add(user);
		
		assertNotNull(userDao.find("d1102526"));
	}
}
