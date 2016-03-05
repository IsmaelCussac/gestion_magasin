package fr.mgs.tests;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import javax.persistence.RollbackException;
import fr.mgs.business.UserManager;
import fr.mgs.connection.DataSource;
import fr.mgs.model.user.Privilege;
import fr.mgs.model.user.Team;
import fr.mgs.model.user.User;

/**
 * Class used to test UserDao
 * 
 * @author Ismaël
 *
 */
public class UserDaoTest {

	private static UserManager userManager;
	private Team team;
	private User user;

	@SuppressWarnings("unchecked")
	@BeforeClass
	public static void setUpBeforeClass() throws SQLException {
		userManager = new UserManager();
	}

	@AfterClass
	public static void tearDownAfterAll() {
		userManager.getDaoManager().close();
	}

	@Before
	public void setUp() throws SQLException {
		userManager.init(DataSource.H2);
		
		team = new Team();
		team.setTeam("ccc", "CCC", 7, Privilege.CUSTOMER);
		userManager.addTeam(team);
		
		user = new User();
		user.setUser("d1102526", "Jean-Louis", "De Beauregard", team, "0442060504", "jean-louis.de-beauregard@mail.fr",
				"secret");
	}

	@After
	public void tearDown() {
		
	}

	@Test
	public void testAddUser() throws SQLException {
		userManager.addUser(user);

		assertNotNull(userManager.findUser("d1102526"));
	}
	
	@Test(expected=RollbackException.class)
	public void testAddExistingUser() throws SQLException {
		userManager.addUser(user);
		userManager.addUser(user);
	}
	
	@Test
	public void testRemoveUser() throws SQLException {
		userManager.addUser(user);
		userManager.removeUser("d1102526");
		assertNull(userManager.findUser("d1102526"));
	}
	

}
