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
import fr.mgs.model.user.Person;
import fr.mgs.model.user.Privilege;
import fr.mgs.model.user.Team;

/**
 * Class used to test PersonDao
 * 
 * @author Ismaël
 *
 */
public class PersonManagerTest {

	private static UserManager userManager;
	private Team team;
	private Person person;

	@BeforeClass
	public static void setUpBeforeClass() throws SQLException {
		userManager = new UserManager();
		userManager.init(DataSource.H2);

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
		userManager.addTeam(team);

		person = new Person();
		person.setPerson("d1102526", "Jean-Louis", "De Beauregard", team, "0442060504",
				"jean-louis.de-beauregard@mail.fr", "secret");
	}

	@After
	public void tearDown() {

	}

	@Test
	public void testAddPerson() throws SQLException {
		userManager.addPerson(person);

		assertNotNull(userManager.findPerson("d1102526"));
	}

	@Test(expected = RollbackException.class)
	public void testAddExistingPerson() throws SQLException {
		userManager.addPerson(person);
		userManager.addPerson(person);
	}

	@Test
	public void testRemovePerson() throws SQLException {
		userManager.addPerson(person);
		userManager.removePerson("d1102526");
		assertNull(userManager.findPerson("d1102526"));
	}

	@Test(expected = Exception.class)
	public void testRemoveNonExistingPerson() throws SQLException {
		userManager.removePerson("d1102526");
	}

	@Test
	public void testFindPerson() throws SQLException {
		userManager.addPerson(person);
		assertEquals("d1102526", userManager.findPerson("d1102526").getPersonId());
	}

	@Test
	public void testFindNonExistingPerson() throws SQLException {
		assertNull(userManager.findPerson("d1102526"));
	}

	@Test
	public void testFindAllPersons() throws SQLException {
		userManager.addPerson(person);
		assertNotNull(userManager.findAllPersons());
	}

	@Test
	public void testFindAllPersonsEmpty() throws SQLException {
		assertEquals(0, userManager.findAllPersons().size());
	}

	@Test
	public void testExistsPerson() throws SQLException {
		userManager.addPerson(person);
		assertTrue(userManager.personExists("d1102526"));
	}

	@Test
	public void testNotExistsPerson() throws SQLException {
		assertFalse(userManager.personExists("d1102526"));
	}

	@Test
	public void testUpdatePerson() throws SQLException {
		userManager.addPerson(person);

		Person updatePerson = userManager.findPerson("d1102526");
		updatePerson.setFirstName("Jean-Lou");

		userManager.updatePerson(updatePerson);
		assertEquals("Jean-Lou", userManager.findPerson("d1102526").getFirstName());
	}

	@Test
	public void testUpdatePersonTeam() throws SQLException {
		userManager.addPerson(person);

		Team newTeam = new Team();
		newTeam.setTeam("New Team", "New team to test the method", 3, Privilege.CUSTOMER);
		userManager.addTeam(newTeam);

		Person updatePerson = userManager.findPerson("d1102526");
		updatePerson.setTeam(newTeam);

		userManager.updatePerson(updatePerson);
		assertEquals("New team to test the method", userManager.findPerson("d1102526").getTeam().getName());
	}

}
