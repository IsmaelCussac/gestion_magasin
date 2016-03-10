package fr.mgs.tests;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.mgs.business.ProductManager;
import fr.mgs.connection.DataSource;
import fr.mgs.model.product.Category;
import fr.mgs.model.product.Lot;
import fr.mgs.model.product.Product;
import fr.mgs.model.product.SubCategory;

/**
 * 
 * @author Mariana
 *
 */

public class LotDAOTest {

	private static ProductManager productManager;

	private static Lot lot;
	private static Product product;
	private static SubCategory subCategory;
	private static Date date;

	@BeforeClass
	public static void setUpBeforeClass() throws SQLException {		
		productManager = new ProductManager();
	}

	@AfterClass
	public static void tearDownAfterAll() throws SQLException {
		productManager.getDaoManager().close();
	}

	@Before
	public void setUp() throws SQLException {
		productManager.init(DataSource.H2);
		
		subCategory = new SubCategory();
		subCategory.setSubCategory("Aiguilles", Category.PLASTIC);
		productManager.addSubCategory(subCategory);

		product = new Product();
		product.setProduct("Aiguille 0.4mm", subCategory, 20, 40, 4.52, true, null, 100);
		
		lot = new Lot();
		date = new Date("2017-02-26 20:15:00");
		
		lot.setLot(date, product, 15);
		
	}

	@Test
	public void testAddLot() throws SQLException { 
		productManager.addLot(lot);
		assertNotNull(productManager.findLot(1));
	}
	
	@Test(expected=Exception.class)
	public void testAddExistingLot() throws SQLException{
		productManager.addLot(lot);
		productManager.addLot(lot);
	}

	@Test
	public void testUpdateLot() throws SQLException {
		productManager.addLot(lot);
		lot.setQuantity(10);
		productManager.updateLot(lot);
		Lot recupLot = productManager.findLot(1);
		assertEquals(10, recupLot.getQuantity());
	}

	@Test
	public void testExistsString() throws SQLException {
		productManager.addLot(lot);
		assertTrue(productManager.lotExists(1));
	}

	@Test
	public void testFindString() throws SQLException {
		productManager.addLot(lot);
		assertNotNull(productManager.findLot(1));
	}

	@Test
	public void testFindAll() throws SQLException {
		productManager.addLot(lot);
		Collection<Lot> collection = productManager.findAllLots();
		assertNotNull(collection.size());
	}

	@Test
	public void testRemoveString() throws SQLException {
		productManager.addLot(lot);
		productManager.removeLot(1);
		assertNull(productManager.findLot(1));
	}
	
	@Test(expected=Exception.class)
	public void testRemoveNoExistingLot() throws SQLException{
		productManager.removeLot(100);
	}

}
