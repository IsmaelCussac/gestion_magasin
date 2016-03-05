package fr.mgs.tests;

import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.mgs.business.UserManager;
import fr.mgs.model.user.Privilege;
import fr.mgs.model.user.Team;
import fr.mgs.model.user.User;

public class UserDaoTest {

	private static UserManager userManager;

	@SuppressWarnings("unchecked")
	@BeforeClass
	public static void setUpBeforeClass() throws SQLException {
		userManager = new UserManager();
		userManager.init();
	}

	@AfterClass
	public static void tearDownAfterAll() {
		userManager.getDaoManager().close();
	}

	@Before
	public void setUp() throws SQLException {

	}

	@After
	public void tearDown() {
	}

	@Test
	public void testAddPerson() throws SQLException {

		Team team = new Team();
		team.setTeam("ccc", "CCC", 2, Privilege.CUSTOMER);
		userManager.addTeam(team);

		User user = new User();
		user.setUser("d1102526", "Jean-Louis", "De Beauregard", team, "0442060504", "jean-louis.de-beauregard@mail.fr",
				"secret");
		userManager.addUser(user);

		assertNotNull(userManager.findUser("d1102526"));
	}
}
