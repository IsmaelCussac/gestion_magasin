package fr.mgs.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import javax.persistence.RollbackException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.mgs.business.UserManager;
import fr.mgs.connection.DataSource;
import fr.mgs.model.user.Privilege;
import fr.mgs.model.user.Team;

/**
 * Class used to test TeamDao
 * 
 * @author Ismaël
 *
 */
public class TeamManagerTest {

	private static UserManager userManager;
	private Team team;

	@BeforeClass
	public static void setUpBeforeClass() throws SQLException {
		userManager = new UserManager();
	}

	@AfterClass
	public static void tearDownAfterAll() {
		userManager.close();
	}

	@Before
	public void setUp() throws SQLException {
		userManager.init(DataSource.H2);

		team = new Team();
		team.setTeam("APDCMT", "Approches physiques de la dynamique cellulaire et de la morphogénèse des tissus", 7,
				Privilege.CUSTOMER);
	}

	@After
	public void tearDown() {

	}

	@Test
	public void testAddTeam() throws SQLException {
		userManager.addTeam(team);
		assertNotNull(userManager.findTeam("APDCMT"));
	}

	@Test(expected = RollbackException.class)
	public void testAddExistingTeam() throws SQLException {
		userManager.addTeam(team);
		userManager.addTeam(team);
	}

	@Test
	public void testRemoveTeam() throws SQLException {
		userManager.addTeam(team);
		userManager.removeTeam("APDCMT");
		assertNull(userManager.findTeam("APDCMT"));
	}

	@Test(expected = Exception.class)
	public void testRemoveNonExistingTeam() throws SQLException {
		userManager.removeTeam("APDCMT");
	}

	@Test
	public void testFindTeam() throws SQLException {
		userManager.addTeam(team);
		assertEquals("APDCMT", userManager.findTeam("APDCMT").getTeamId());
	}

	@Test
	public void testFindNonExistingTeam() throws SQLException {
		assertNull(userManager.findTeam("APDCMT"));
	}

	@Test
	public void testFindAllTeams() throws SQLException {
		userManager.addTeam(team);
		assertNotNull(userManager.findAllTeams());
	}

	@Test
	public void testFindAllTeamsEmpty() throws SQLException {
		assertEquals(0, userManager.findAllTeams().size());
	}

	@Test
	public void testExistsTeam() throws SQLException {
		userManager.addTeam(team);
		assertTrue(userManager.teamExists("APDCMT"));
	}

	@Test
	public void testNotExistsTeam() throws SQLException {
		assertFalse(userManager.teamExists("APDCMT"));
	}

	@Test
	public void testUpdateTeam() throws SQLException {
		userManager.addTeam(team);

		Team updateTeam = userManager.findTeam("APDCMT");
		updateTeam.setName("New team name to test the method");

		userManager.updateTeam(updateTeam);
		assertEquals("New team name to test the method", userManager.findTeam("APDCMT").getName());
	}
}
