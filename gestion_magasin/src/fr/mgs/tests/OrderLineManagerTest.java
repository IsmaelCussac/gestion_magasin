package fr.mgs.tests;

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
import fr.mgs.connection.DataSource;
import fr.mgs.model.order.Order;
import fr.mgs.model.order.OrderLine;
import fr.mgs.model.order.OrderStatus;
import fr.mgs.model.product.Category;
import fr.mgs.model.product.Product;
import fr.mgs.model.product.SubCategory;
import fr.mgs.model.user.Person;

/**
 * A REFAIRE QUAND PUSH
 * 
 * @author Mariana
 *
 */
public class OrderLineManagerTest {

	private static OrderManager orderManager;
	private static ProductManager productManager;

	private Order order;
	private OrderLine orderline;
	private Product product;
	private SubCategory subCategory;
	private Person person;
	// private Set<OrderLine> orderLines;

	@BeforeClass
	public static void setUpBeforeClass() throws SQLException {
		orderManager = new OrderManager();
		productManager = new ProductManager();
	}

	@AfterClass
	public static void tearDownAfterAll() {
		orderManager.close();
		productManager.close();
	}

	@Before
	public void setUp() throws SQLException {
		orderManager.init(DataSource.H2);
		productManager.init(DataSource.H2);

		subCategory = new SubCategory();
		subCategory.setSubCategory("Aiguilles", Category.PLASTIC);
		productManager.addSubCategory(subCategory);

		product = new Product();
		product.setProduct("Aiguille 0.4mm", subCategory, 20, 40, 4.52, true, null, 100);
		productManager.addProduct(product);

		Date dateSub = new Date();
		Date dateDeli = new Date();

		order = new Order();
		order.setOrder(person, dateSub, dateDeli, null, "euh", OrderStatus.DELIVERED);
		orderManager.addOrder(order);

		orderline = new OrderLine();
		orderline.setOrderLine(order, product, 10, 10);
	}

	@After
	public void tearDown() {

	}

	// @Test
	// public void testAddOrderLine() throws SQLException {
	// orderManager.addOrderLine(orderline);
	// assertNotNull(orderManager.findOrderLine(orderline.getId()));
	// }

	// @Test
	// public void testRemoveInteger() throws SQLException {
	// orderManager.addOrderLine(orderline);
	// orderManager.removeOrderLine(orderline.getId());
	// assertNull(orderManager.findOrderLine(orderline.getId()));
	// }

	// @Test
	// public void testUpdateOrderLine() throws SQLException {
	// orderManager.addOrderLine(orderline);
	// orderline.setQuantity(9);
	// orderManager.updateOrderLine(orderline);
	// OrderLine recupOL = orderManager.findOrderLine(orderline.getId());
	// assertEquals(9, recupOL.getQuantity());
	// }
	//
	// @Test
	// public void testExistsInteger() throws SQLException {
	// orderManager.addOrderLine(orderline);
	// assertTrue(orderManager.orderLineExists(orderline.getId()));
	// }
	//
	// @Test
	// public void testFindInteger() throws SQLException {
	// orderManager.addOrderLine(orderline);
	// assertNotNull(orderManager.findOrderLine(orderline.getId()));
	// }

	@Test
	@Ignore
	public void testFindOrderLineByOrder() {
	}

}
