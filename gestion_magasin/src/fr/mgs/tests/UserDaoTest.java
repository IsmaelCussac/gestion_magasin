package fr.mgs.tests;

import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.mgs.dao.UserDAO;
import fr.mgs.models.user.User;

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
		user.setEmail("fgeger");
		user.setFirstName("eeee");
		user.setId("1");
		user.setLastName("grg");
		user.setPassword("vrbr");
		user.setTeam(null);
		dao.add(user);
		
	}
}
