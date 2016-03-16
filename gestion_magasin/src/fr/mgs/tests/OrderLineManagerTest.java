package fr.mgs.tests;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import fr.mgs.business.OrderManager;
import fr.mgs.business.ProductManager;
import fr.mgs.business.UserManager;
import fr.mgs.connection.DataSource;
import fr.mgs.model.order.Order;
import fr.mgs.model.order.OrderLine;
import fr.mgs.model.order.OrderLinePK;
import fr.mgs.model.order.OrderStatus;
import fr.mgs.model.product.Category;
import fr.mgs.model.product.Product;
import fr.mgs.model.product.SubCategory;
import fr.mgs.model.user.Person;
import fr.mgs.model.user.Privilege;
import fr.mgs.model.user.Team;

/**
 * A REFAIRE QUAND PUSH
 * 
 * @author Mariana
 *
 */
public class OrderLineManagerTest {

	private static OrderManager orderManager;
	private static ProductManager productManager;
	private static UserManager userManager;

	private Order order;
	private OrderLine orderline;
	private Product product;
	private SubCategory subCategory;
	private Person person;
	private Team team;


	@BeforeClass
	public static void setUpBeforeClass() throws SQLException {
		orderManager = new OrderManager();
		productManager = new ProductManager();
		userManager = new UserManager();
	}

	@AfterClass
	public static void tearDownAfterAll() {
		orderManager.close();
		productManager.close();
		userManager.close();
	}

	@Before
	public void setUp() throws SQLException {
		orderManager.init(DataSource.H2);
		productManager.init(DataSource.H2);
		userManager.init(DataSource.H2);

		subCategory = new SubCategory();
		subCategory.setSubCategory("Aiguilles", Category.PLASTIC);
		productManager.addSubCategory(subCategory);

		product = new Product();
		product.setProduct(1, "Aiguille 0.4mm", subCategory, 20, 40, 4.52, true, null, 100);
		productManager.addProduct(product);

		Date dateSub = new Date();
		Date dateDeli = new Date();
		
		team = new Team();
        team.setTeam("APDCMT", "Approches physiques de la dynamique cellulaire et de la morphogénèse des tissus", 7,
                Privilege.CUSTOMER);
        userManager.addTeam(team);
		
		person = new Person();
		person.setPerson("d1102526", "Jean-Louis", "De Beauregard", team, "0442060504",
                "jean-louis.de-beauregard@mail.fr", "secret");
		userManager.addPerson(person);

		order = new Order();
		order.setOrder(person, dateSub, dateDeli, null, "", OrderStatus.DELIVERED);
		orderManager.addOrder(order);

		orderline = new OrderLine();
		orderline.setOrderLine(order, product, 10.5, 10.5);
	}

	@After
	public void tearDown() {

	}

	 @Test
	 public void testAddOrderLine() throws SQLException {
	 orderManager.addOrderLine(orderline);
	 System.out.println(orderline.getOrderLinePK());
	 assertNotNull(orderManager.findOrderLine(orderline.getOrderLinePK()));
	 }

	 @Test
	 public void testRemoveInteger() throws SQLException {
	 orderManager.addOrderLine(orderline);
	 orderManager.removeOrderLine(orderline.getOrderLinePK());
	 assertNull(orderManager.findOrderLine(orderline.getOrderLinePK()));
	 }

	 @Test
	 public void testUpdateOrderLine() throws SQLException {
	 orderManager.addOrderLine(orderline);
	 orderline.setQuantity(9.1);
	 orderManager.updateOrderLine(orderline);
	 OrderLine recupOL = orderManager.findOrderLine(orderline.getOrderLinePK());
	 assertEquals(9.1, recupOL.getQuantity(), 0);
	 }
	
	 @Test
	 public void testExistsInteger() throws SQLException {
	 orderManager.addOrderLine(orderline);
	 assertTrue(orderManager.orderLineExists(orderline.getOrderLinePK()));
	 }
	
	 @Test
	 public void testFindInteger() throws SQLException {
	 orderManager.addOrderLine(orderline);
	 assertNotNull(orderManager.findOrderLine(orderline.getOrderLinePK()));
	 }

	@Test
	@Ignore
	public void testFindOrderLineByOrder() {
	}

}
