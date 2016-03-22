package fr.mgs.tests;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.mgs.barcode.coder.Barcode;
import fr.mgs.barcode.coder.BarcodeFactory;
import fr.mgs.barcode.coder.BarcodeType;
import fr.mgs.business.ProductManager;
import fr.mgs.connection.DataSource;
import fr.mgs.model.product.Category;
import fr.mgs.model.product.Product;
import fr.mgs.model.product.SubCategory;
import fr.mgs.toolbox.BarCode;

public class BarcodeTest {

	private static ProductManager productManager;
	private SubCategory subCategory;
	private Product product;

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
		productManager.addSubCategory(subCategory);

		product = new Product();
		product.setProduct(1, "Aiguille 0.4mm", subCategory, 20, 40, 4.52, true, null, 100);

	}

	@Test
	public void testCreateBarcodeBarcodeTypeCodabarStringBoolean() {
		Barcode bar = BarcodeFactory.createBarcode(BarcodeType.Codabar, String.valueOf(BarCode.generateRandomInt()),
				true);
		assertNotNull(bar.getCode());
	}

	@Test
	public void testCreateBarcodeBarcodeType128StringBoolean() {
		Barcode bar = BarcodeFactory.createBarcode(BarcodeType.Code128, String.valueOf(BarCode.generateRandomInt()),
				true);
		assertNotNull(bar.getCode());
	}

	@Test
	public void testCreateBarcodeBarcodeType11StringBoolean() {
		Barcode bar = BarcodeFactory.createBarcode(BarcodeType.Code11, String.valueOf(BarCode.generateRandomInt()),
				true);
		assertNotNull(bar.getCode());
	}

	@Test
	public void testCreateBarcodeBarcodeType39StringBoolean() {
		Barcode bar = BarcodeFactory.createBarcode(BarcodeType.Code39, String.valueOf(BarCode.generateRandomInt()),
				true);
		assertNotNull(bar.getCode());
	}

	@Test
	public void testCreateBarcodeBarcodeType93StringBoolean() {
		Barcode bar = BarcodeFactory.createBarcode(BarcodeType.Code93, String.valueOf(BarCode.generateRandomInt()),
				true);
		assertNotNull(bar.getCode());
	}

	@Test
	public void testCreateBarcodeBarcodeTypeDamatrixStringBoolean() {
		Barcode bar = BarcodeFactory.createBarcode(BarcodeType.Datamatrix, String.valueOf(BarCode.generateRandomInt()),
				true);
		assertNotNull(bar.getCode());
	}

	@Test
	public void testCreateBarcodeBarcodeTypeEAN13StringBoolean() {
		Barcode bar = BarcodeFactory.createBarcode(BarcodeType.EAN13, String.valueOf(BarCode.generateRandomInt()),
				true);
		assertNotNull(bar.getCode());
	}

	@Test
	public void testCreateBarcodeBarcodeTypeEAN8StringBoolean() {
		Barcode bar = BarcodeFactory.createBarcode(BarcodeType.EAN8, String.valueOf(BarCode.generateRandomInt()), true);
		assertNotNull(bar.getCode());
	}

	@Test
	public void testCreateBarcodeBarcodeType2of5StringBoolean() {
		Barcode bar = BarcodeFactory.createBarcode(BarcodeType.Interleaved2of5,
				String.valueOf(BarCode.generateRandomInt()), true);
		assertNotNull(bar.getCode());
	}

	@Test
	public void testCreateBarcodeBarcodeTypeMSIStringBoolean() {
		Barcode bar = BarcodeFactory.createBarcode(BarcodeType.MSI, String.valueOf(BarCode.generateRandomInt()), true);
		assertNotNull(bar.getCode());
	}

	@Test
	public void testCreateBarcodeBarcodeTypeSt2of5StringBoolean() {
		Barcode bar = BarcodeFactory.createBarcode(BarcodeType.Standard2of5,
				String.valueOf(BarCode.generateRandomInt()), true);
		assertNotNull(bar.getCode());
	}

	@Test
	public void testCreateBarcodeBarcodeType128() {
		Barcode bar = BarcodeFactory.createBarcode(BarcodeType.Code128, String.valueOf(BarCode.generateRandomInt()));
		assertNotNull(bar.getCode());
	}

	@Test
	public void testCreateBarcodeBarcodeTypeCodabar() {
		Barcode bar = BarcodeFactory.createBarcode(BarcodeType.Codabar, String.valueOf(BarCode.generateRandomInt()));
		assertNotNull(bar.getCode());
	}

	@Test
	public void testCreateBarcodeBarcodeType11() {
		Barcode bar = BarcodeFactory.createBarcode(BarcodeType.Code11, String.valueOf(BarCode.generateRandomInt()));
		assertNotNull(bar.getCode());
	}

	@Test
	public void testCreateBarcodeBarcodeType39() {
		Barcode bar = BarcodeFactory.createBarcode(BarcodeType.Code39, String.valueOf(BarCode.generateRandomInt()));
		assertNotNull(bar.getCode());
	}

	@Test
	public void testCreateBarcodeBarcodeType93() {
		Barcode bar = BarcodeFactory.createBarcode(BarcodeType.Code93, String.valueOf(BarCode.generateRandomInt()));
		assertNotNull(bar.getCode());
	}

	@Test
	public void testCreateBarcodeBarcodeTypeDamatrix() {
		Barcode bar = BarcodeFactory.createBarcode(BarcodeType.Datamatrix, String.valueOf(BarCode.generateRandomInt()));
		assertNotNull(bar.getCode());
	}

	@Test
	public void testCreateBarcodeBarcodeTypeEAN13() {
		Barcode bar = BarcodeFactory.createBarcode(BarcodeType.EAN13, String.valueOf(BarCode.generateRandomInt()));
		assertNotNull(bar.getCode());
	}

	@Test
	public void testCreateBarcodeBarcodeTypeEAN8() {
		Barcode bar = BarcodeFactory.createBarcode(BarcodeType.EAN8, String.valueOf(BarCode.generateRandomInt()));
		assertNotNull(bar.getCode());
	}

	@Test
	public void testCreateBarcodeBarcodeTypeInt2of5() {
		Barcode bar = BarcodeFactory.createBarcode(BarcodeType.Interleaved2of5,
				String.valueOf(BarCode.generateRandomInt()));
		assertNotNull(bar.getCode());
	}

	@Test
	public void testCreateBarcodeBarcodeTypeMSI() {
		Barcode bar = BarcodeFactory.createBarcode(BarcodeType.MSI, String.valueOf(BarCode.generateRandomInt()));
		assertNotNull(bar.getCode());
	}

	@Test
	public void testCreateBarcodeBarcodeTypeStan2of5() {
		Barcode bar = BarcodeFactory.createBarcode(BarcodeType.Standard2of5,
				String.valueOf(BarCode.generateRandomInt()));
		assertNotNull(bar.getCode());
	}

}
