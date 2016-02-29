package fr.mgs.tests;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.mgs.dao.UserDAO;
import fr.mgs.model.user.User;

public class UserDaoTest {
	
	static UserDAO dao;


	@BeforeClass
	public static void setUpBeforeClass() throws SQLException {
		dao = new UserDAO();
	}

	@AfterClass
	public static void tearDownAfterAll() {
	}

	@Before
	public void setUp() throws SQLException {
		dao.init();
	
	}

	@After
	public void tearDown() {
		dao.close();
	}

	@Test
	public void testAddPerson() throws SQLException {
		User user = new User();
		user.setUser("d1102526", "Jean-Louis", "De Beauregard", null, "0442060504", "jean-louis.de-beauregard@mail.fr", "secret");
		dao.add(user);
		
		assertNotNull(dao.find("d1102526"));
	}
}
