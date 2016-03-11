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
		
		subCategory = new SubCategory();
		subCategory.setSubCategory("Aiguilles", Category.PLASTIC);
		productManager.addSubCategory(subCategory);
		
		
		
		product = new Product();
		product.setProduct("Aiguille 0.4mm", subCategory, 20, 40, 4.52, true, null, 100);
		
	}

	@After
	public void tearDown() {
		
	}

	@Test
	public void testAddProduct() throws SQLException {
		productManager.addProduct(product);
		assertNotNull(productManager.findProduct(1));
	}

	@Test(expected=Exception.class)
	public void testAddExistingProduct() throws SQLException {
		productManager.addProduct(product);
		productManager.addProduct(product);
	}
	
	@Test
	public void testRemoveProduct() throws SQLException {
		productManager.addProduct(product);
		productManager.removeProduct(1);
		assertNull(productManager.findProduct(1));
	}
	
	@Test(expected=Exception.class)
	public void testRemoveNonExistingProduct() throws SQLException {
		productManager.removeProduct(10);
	}
	
	@Test
	public void testFindProduct() throws SQLException {
		productManager.addProduct(product);
		assertEquals("Aiguille 0.4mm", productManager.findProduct(1).getDesignation());
	}
	
	@Test
	public void testFindNonExistingProduct() throws SQLException {
		assertNull(productManager.findProduct(1));
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
		assertTrue(productManager.productExists(1));
	}
	
	@Test
	public void testNotExistsProduct() throws SQLException {
		assertFalse(productManager.productExists(1));
	}
	
	@Test
	public void testUpdateProduct() throws SQLException{
		productManager.addProduct(product);
		
		Product updateProduct = productManager.findProduct(1);
		updateProduct.setDesignation("Aiguille 0.6mm");
		
		productManager.updateProduct(updateProduct);
		assertEquals("Aiguille 0.6mm", productManager.findProduct(1).getDesignation());
	}
	
	@Test
	public void testUpdateProductSubCategory() throws SQLException{
		productManager.addProduct(product);
		
		SubCategory newSubCategory = new SubCategory();
		newSubCategory.setSubCategory("Seringues", Category.PLASTIC);
		productManager.addSubCategory(newSubCategory);
		
		Product updateProduct = productManager.findProduct(1);
		updateProduct.setSubCategory(newSubCategory);
		
		productManager.updateProduct(updateProduct);
		assertEquals("Seringues", productManager.findProduct(1).getSubCategory().getName());
	}
	
	@Test
	public void testFindProductsBySubCategory() throws SQLException{
		productManager.addProduct(product);
		
		Product product1 = new Product();
		product1.setProduct("Aiguille 0.3mm", subCategory, 20, 40, 4.52, true, null, 100);
		productManager.addProduct(product1);
		assertEquals(2,productManager.findProductsBySubCategory(subCategory).size());
		
	}
	
}
