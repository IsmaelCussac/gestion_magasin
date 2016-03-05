package fr.mgs.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.mgs.business.OrderManager;
import fr.mgs.business.UserManager;
import fr.mgs.connection.DataSource;
import fr.mgs.dao.OrderDAO;
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
	private static OrderDAO specificOrderDao;
	private static Order order;
	private static Order order2;
	private static Team team;
	private static User user;

	@SuppressWarnings("unchecked")
	@BeforeClass
	public static void setUpBeforeClass() throws SQLException {
		userManager = new UserManager();
		userManager.init(DataSource.H2);

		orderManager = new OrderManager();
		orderManager.init(DataSource.H2);

		specificOrderDao = new OrderDAO(orderManager.getOrderDao().getConnection());

		team = new Team();
		order = new Order();
		user = new User();
		order2 = new Order();
	}

	@AfterClass
	public static void tearDownAfterAll() {
		userManager.getDaoManager().close();
		orderManager.getDaoManager().close();
	}

	@Test
	public void testOrderCreateOrder() throws SQLException {
		assertNotNull(userManager);
		assertNotNull(orderManager);
		assertNotNull(orderManager.getOrderDao());

		team.setTeam("20", "Approches physiques de la dynamique cellulaire et de la morphogénèse des tissus", 8,
				Privilege.CUSTOMER);
		userManager.addTeam(team);

		user.setUser("s14027279", "Marc", "Dupont", team, "0452050554", "marc.dupont@mail.fr", "pass");
		userManager.addUser(user);

		order.setOrder(user, new Date(), new Date(), null, "", OrderStatus.NOT_VALIDATED);

		order2.setOrder(user, new Date(), new Date(), null, "", OrderStatus.VALIDATED);

		orderManager.addOrder(order);
		orderManager.addOrder(order2);

	}

	@Test
	public void testOrderFindOrder() throws SQLException {
		assertNotNull(orderManager.findOrder(1));
	}

	@Test
	public void testOrderFindAllOrders() throws SQLException {
		assertEquals(2, orderManager.findAllOrders().size());
	}

	@Test
	public void testOrderByStatus() {
		assertNotNull(specificOrderDao);
		assertEquals(1, specificOrderDao.findOrderByStatus(OrderStatus.VALIDATED).size());
	}

	@Test
	public void testOrderByUser() {
		assertNotNull(specificOrderDao);
		ArrayList<Order> ordersByUser = (ArrayList<Order>) specificOrderDao.findOrderByUser(user);
		assertEquals(2,ordersByUser.size());
	}

}
