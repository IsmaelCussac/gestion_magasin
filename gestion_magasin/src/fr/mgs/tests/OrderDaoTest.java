package fr.mgs.tests;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import fr.mgs.business.OrderManager;
import fr.mgs.business.UserManager;
import fr.mgs.model.order.Order;
import fr.mgs.model.order.OrderStatus;
import fr.mgs.model.user.Privilege;
import fr.mgs.model.user.Team;
import fr.mgs.model.user.User;

/**
 * This class is used to test orders DAO
 * 
 * @author Ibrahima
 *
 */
public class OrderDaoTest {
	private static OrderManager orderManager;
	private static UserManager userManager;

	@SuppressWarnings("unchecked")
	@BeforeClass
	public static void setUpBeforeClass() throws SQLException {
		userManager = new UserManager();
		userManager.init();

		orderManager = new OrderManager();
		orderManager.init();

	}

	@AfterClass
	public static void tearDownAfterAll() {
		userManager.getDaoManager().close();
		orderManager.getDaoManager().close();
	}

	/* Ignored to avoid primary key constraint violation in database.
	 * Remove Ignore annotation the first time you run this test
	 */
	@Ignore
	@Test
	public void testOrderCreateOrder() throws SQLException {
		assertNotNull(userManager);
		assertNotNull(orderManager);
		assertNotNull(orderManager.getOrderDao());

		Team team = new Team();
		team.setTeam("20", "Approches physiques de la dynamique cellulaire et de la morphogénèse des tissus", 8,
				Privilege.CUSTOMER);
		userManager.addTeam(team);

		User user = new User();
		user.setUser("s14027279", "Marc", "Dupont", team, "0452050554", "marc.dupont@mail.fr", "pass");
		userManager.addUser(user);

		Order order = new Order();
		order.setOrder(user, new Date(), new Date(), null, "", OrderStatus.NOT_VALIDATED);

		orderManager.addOrder(order);

	}

	@Test
	public void testOrderFindOrder() throws SQLException {

		assertNotNull(orderManager.findOrder(1));
	}

	@Test
	public void testOrderFindAllOrders() throws SQLException {
		assertEquals(4, orderManager.findAllOrders().size());
	}

}
