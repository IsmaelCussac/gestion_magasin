package fr.mgs.tests;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

import org.junit.After;
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
import junit.framework.Assert;

/**
 * 
 * @author Mariana
 *
 */

public class LotDAOTest {

	private static ProductManager productManager;

	private  Lot lot;
	private  Product product;
	private  SubCategory subCategory;

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
		subCategory.setSubCategory("Gants", Category.PLASTIC);
		productManager.addSubCategory(subCategory);

		product = new Product();
		product.setProduct("Gant t.S", subCategory, 20, 40, 4.52, true, null, 100);
		productManager.addProduct(product);
		
		lot = new Lot();
		Date date = new Date();
		
		lot.setLot(date, product, 15);
	}
	
	@After
	public void tearDown() {
	}

	@Test
	public void testAddLot() throws SQLException { 
		productManager.addLot(lot);
		System.out.println("lot dest : "+lot.getLotId());
		System.out.println("size liste : "+ productManager.findAllLots().size());
		assertNotNull(productManager.findAllLots());
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
		Lot recupLot = productManager.findLot(lot.getLotId());
		assertEquals(10, recupLot.getQuantity(), 0);
	}

	@Test
	public void testExistsString() throws SQLException {
		productManager.addLot(lot);
		assertTrue(productManager.lotExists(lot.getLotId()));
	}

	@Test
	public void testFindString() throws SQLException {
		productManager.addLot(lot);
		assertNotNull(productManager.findLot(lot.getLotId()));
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
		productManager.removeLot(lot.getLotId());
		assertNull(productManager.findLot(lot.getLotId()));
	}
	
	@Test(expected=Exception.class)
	public void testRemoveNoExistingLot() throws SQLException{
		productManager.removeLot(100);
	}

}
