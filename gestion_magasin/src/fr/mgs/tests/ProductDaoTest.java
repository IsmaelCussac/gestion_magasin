package fr.mgs.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import javax.persistence.RollbackException;

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

/**
 * Class used to test ProductDao
 * 
 * @author Ismaël
 *
 */
public class ProductDaoTest {
	private static ProductManager productManager;
	private SubCategory subCategory;
	private Lot lot;
	private Product product;

	@BeforeClass
	public static void setUpBeforeClass() throws SQLException {
		productManager = new ProductManager();
	}

	@AfterClass
	public static void tearDownAfterAll() {
		productManager.getDaoManager().close();
	}

	@Before
	public void setUp() throws SQLException {
		productManager.init(DataSource.H2);
		
	/*	subCategory = new SubCategory();
		subCategory.setSubCategory("Aiguilles", Category.PLASTIC);
		productManager.addSubCategory(subCategory);
		
		
		
		product = new Product();
		product.setProduct("d1102526", "Jean-Louis", "De Beauregard", subCategory, "0442060504", "jean-louis.de-beauregard@mail.fr",
				"secret");
		*/
	}

	@After
	public void tearDown() {
		
	}
/*
	@Test
	public void testAddProduct() throws SQLException {
		productManager.addProduct(product);

		assertNotNull(productManager.findProduct("d1102526"));
	}
	
	@Test(expected=RollbackException.class)
	public void testAddExistingProduct() throws SQLException {
		productManager.addProduct(product);
		productManager.addProduct(product);
	}
	
	@Test
	public void testRemoveProduct() throws SQLException {
		productManager.addProduct(product);
		productManager.removeProduct("d1102526");
		assertNull(productManager.findProduct("d1102526"));
	}
	
	@Test(expected=Exception.class)
	public void testRemoveNonExistingProduct() throws SQLException {
		productManager.removeProduct("d1102526");
	}
	
	@Test
	public void testFindProduct() throws SQLException {
		productManager.addProduct(product);
		assertEquals("d1102526", productManager.findProduct("d1102526").getProductId());
	}
	
	@Test
	public void testFindNonExistingProduct() throws SQLException {
		assertNull(productManager.findProduct("d1102526"));
	}
	
	@Test
	public void testFindAllProducts() throws SQLException {
		productManager.addProduct(product);
		assertNotNull(productManager.findAllProducts());
	}
	
	@Test
	public void testFindAllProductsEmpty() throws SQLException {
		assertEquals(0, productManager.findAllProducts().size());
	}
	
	@Test
	public void testExistsProduct() throws SQLException {
		productManager.addProduct(product);
		assertTrue(productManager.productExists("d1102526"));
	}
	
	@Test
	public void testNotExistsProduct() throws SQLException {
		assertFalse(productManager.productExists("d1102526"));
	}
	
	@Test
	public void testUpdateProduct() throws SQLException{
		productManager.addProduct(product);
		
		Product updateProduct = productManager.findProduct("d1102526");
		updateProduct.setFirstName("Jean-Lou");
		
		productManager.updateProduct(updateProduct);
		assertEquals("Jean-Lou", productManager.findProduct("d1102526").getFirstName());
	}
	
	@Test
	public void testUpdateProductSubCategory() throws SQLException{
		productManager.addProduct(product);
		
		SubCategory newSubCategory = new SubCategory();
		newSubCategory.setSubCategory("New SubCategory", "New subCategory to test the method", 3, Privilege.CUSTOMER);
		productManager.addSubCategory(newSubCategory);
		
		Product updateProduct = productManager.findProduct("d1102526");
		updateProduct.setSubCategory(newSubCategory);
		
		productManager.updateProduct(updateProduct);
		assertEquals("New subCategory to test the method", productManager.findProduct("d1102526").getSubCategory().getName());
	}
	*/
}
