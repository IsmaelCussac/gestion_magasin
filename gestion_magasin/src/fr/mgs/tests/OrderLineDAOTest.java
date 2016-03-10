package fr.mgs.tests;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.Date;
import java.util.Set;

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
 * PAAAAS FINIIIIIIIIIIT !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * @author Mariana
 *
 */
public class OrderLineDAOTest {
	
	private static OrderManager orderManager;
	private static ProductManager productManager;
	
	
	private static Order order;
	private static OrderLine orderline;
	private static Product product;
	private static SubCategory subCategory;
	private static Person person;
	private static Set<OrderLine> orderLines;
	
	@BeforeClass
	public static void setUpBeforeClass() throws SQLException {
		orderManager = new OrderManager();
		productManager = new ProductManager();
	}

	@AfterClass
	public static void tearDownAfterAll() {
		orderManager.getDaoManager().close();
		productManager.getDaoManager().close();
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
		
		Date dateSub = new Date("2016-02-26 20:15:00");
		Date dateDeli = new Date("2016-03-03 20:15:00");
		
		order = new Order();
		order.setOrder(person, dateSub, dateDeli, orderLines, "", OrderStatus.DELIVERED);
		
		orderline = new OrderLine();
		orderline.setOrderLine(order, product, 10, 10);
	}

	@After
	public void tearDown() {
		
	}

	@Test
	public void testAddOrderLine() throws SQLException {
		orderManager.addOrderLine(orderline);
		assertNotNull(orderManager.findOrderLine(1));
	}

	@Test
	public void testRemoveInteger() throws SQLException {
		orderManager.addOrderLine(orderline);
		orderManager.removeOrderLine(1);
		assertNull(orderManager.findOrderLine(1));
	}

	@Test
	public void testUpdateOrderLine() throws SQLException {
		orderManager.addOrderLine(orderline);
		orderline.setQuantity(9);
		orderManager.updateOrderLine(orderline);
		assertEquals(9, orderManager.getOrderLineDao().find(1).getQuantity());
	}

	@Test
	public void testExistsInteger() throws SQLException {
		orderManager.addOrderLine(orderline);
		assertTrue(orderManager.orderLineExists(1));
	}

	@Test
	public void testFindInteger() throws SQLException {
		orderManager.addOrderLine(orderline);
		assertNotNull(orderManager.findOrderLine(1));
	}

	@Test
	@Ignore
	public void testFindOrderLineByOrder() {
	}

	@Test
	public void testFindAll() throws SQLException {
		orderManager.addOrderLine(orderline);
		assertNotNull(orderManager.findAllOrderLines());
	}

}
