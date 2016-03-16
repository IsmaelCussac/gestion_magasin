package fr.mgs.tests;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.Collection;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.mgs.business.ProductManager;
import fr.mgs.connection.DataSource;
import fr.mgs.model.product.Category;
import fr.mgs.model.product.SubCategory;

public class SubCategoryManagerTest {

	private static ProductManager productManager;
	private SubCategory subCategory;

	@BeforeClass
	public static void setUpBeforeClass() throws SQLException {
		productManager = new ProductManager();
	}

	@AfterClass
	public static void tearDownAfterAll() {
		productManager.close();
	}

	@Before
	public void setUp() throws SQLException {
		productManager.init(DataSource.H2);

		subCategory = new SubCategory();
		subCategory.setSubCategory("Aiguilles", Category.PLASTIC);

	}

	@Test
	public void testAddSubCategory() throws SQLException {
		productManager.addSubCategory(subCategory);
		assertNotNull(productManager.findSubCategory(subCategory.getName()));
	}

	@Test(expected = Exception.class)
	public void testAddExistingProduct() throws SQLException {
		productManager.addSubCategory(subCategory);
		productManager.addSubCategory(subCategory);
	}

	@Test
	public void testRemoveString() throws SQLException {
		productManager.addSubCategory(subCategory);
		productManager.removeSubCategory(subCategory.getName());
		assertNull(productManager.findSubCategory(subCategory.getName()));
	}

	@Test(expected = Exception.class)
	public void testRemoveNonExistingProduct() throws SQLException {
		productManager.removeSubCategory("10");
	}

	@Test
	public void testUpdateSubCategory() throws SQLException {
		productManager.addSubCategory(subCategory);

		SubCategory updateSubCat = productManager.findSubCategory(subCategory.getName());
		updateSubCat.setCategory(Category.CULTURE_PLASTIC);
		;

		productManager.updateSubCategory(updateSubCat);
		assertEquals(Category.CULTURE_PLASTIC, productManager.findSubCategory(subCategory.getName()).getCategory());
	}

	@Test
	public void testExistsString() throws SQLException {
		productManager.addSubCategory(subCategory);
		assertNotNull(productManager.subCategoryExists(subCategory.getName()));
	}

	@Test
	public void testFindString() throws SQLException {
		productManager.addSubCategory(subCategory);
		assertNotNull(productManager.findSubCategory(subCategory.getName()));
	}

	@Test
	public void testFindAll() throws SQLException {
		productManager.addSubCategory(subCategory);
		Collection<SubCategory> collection = productManager.findAllSubCategories();
		assertNotNull(collection.size());
	}

	@Test
	public void testFindProductsBySubCategory() throws SQLException {
		productManager.addSubCategory(subCategory);
		Collection<SubCategory> collection = productManager.findSubCategoriesByCategory(Category.PLASTIC);
		assertNotNull(collection.size());
	}

}
