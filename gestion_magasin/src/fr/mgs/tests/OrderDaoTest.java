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

	private static Order marcOrder;
	private static Order paulOrder;
	private static Order marieOrder;

	private static Team team;
	private static Team team2;

	private static User marc;
	private static User paul;
	private static User marie;

	@SuppressWarnings("unchecked")
	@BeforeClass
	public static void setUpBeforeClass() throws SQLException {
		userManager = new UserManager();
		userManager.init(DataSource.H2);

		orderManager = new OrderManager();
		orderManager.init(DataSource.H2);

		specificOrderDao = new OrderDAO(orderManager.getOrderDao().getConnection());

		team = new Team();
		team2 = new Team();

		marc = new User();
		paul = new User();
		marie = new User();

		marcOrder = new Order();
		paulOrder = new Order();
		marieOrder = new Order();

	}

	@AfterClass
	public static void tearDownAfterAll() throws SQLException {
		assertNotNull(specificOrderDao);
		// test remove order
		specificOrderDao.remove(1);
		assertEquals(null, orderManager.findOrder(1));

		// closing managers
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
		team2.setTeam("2", "Biologie des épithéliums ciliés", 8, Privilege.CUSTOMER);

		marc.setUser("s14027276", "Marc", "Dupont", team, "0452050554", "marc.dupont@mail.fr", "pass");
		paul.setUser("s14027277", "Paul", "Durand", team, "0482060564", "paul.durand@mail.fr", "pass");
		marie.setUser("s14027278", "Marie", "Curie", team2, "0482067563", "marie.curie@mail.fr", "pass");

		marcOrder.setOrder(marc, new Date(), new Date(), null, "commande de marc", OrderStatus.NOT_VALIDATED);
		paulOrder.setOrder(paul, new Date(), new Date(), null, "commande de paul", OrderStatus.VALIDATED);
		marieOrder.setOrder(marie, new Date(), new Date(), null, "commande de marie", OrderStatus.VALIDATED);

		userManager.addTeam(team);
		userManager.addTeam(team2);

		userManager.addUser(marc);
		userManager.addUser(paul);
		userManager.addUser(marie);

		orderManager.addOrder(marcOrder);
		orderManager.addOrder(paulOrder);
		orderManager.addOrder(marieOrder);
	}

	@Test
	public void testOrderFindOrder() throws SQLException {
		assertNotNull(orderManager.findOrder(1));
		assertEquals("Dupont", orderManager.findOrder(1).getOrderUser().getLastName());

	}

	@Test
	public void testOrderFindAllOrders() throws SQLException {
		assertEquals(3, orderManager.findAllOrders().size());

	}

	@Test
	public void testOrderByStatus() {
		assertNotNull(specificOrderDao);
		assertEquals(2, specificOrderDao.findOrderByStatus(OrderStatus.VALIDATED).size());
		assertEquals(1, specificOrderDao.findOrderByStatus(OrderStatus.NOT_VALIDATED).size());

	}

	@Test
	public void testOrderByUser() {
		assertNotNull(specificOrderDao);
		ArrayList<Order> marcOrders = (ArrayList<Order>) specificOrderDao.findOrderByUser(marc);
		assertEquals(1, marcOrders.size());
		assertEquals("Marc", marcOrders.get(0).getOrderUser().getFirstName());

	}

	@Test
	public void testOrderByTeam() {
		assertNotNull(specificOrderDao);
		ArrayList<Order> teamOrders = (ArrayList<Order>) specificOrderDao.findOrderByTeam(team);
		assertEquals(2, teamOrders.size());

	}

	@Test
	public void testRemoveOrder() throws SQLException {
		// see tear down method
	}

}
