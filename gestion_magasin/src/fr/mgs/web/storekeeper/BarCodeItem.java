package fr.mgs.web.storekeeper;

import java.io.Serializable;

/**
 * Object used to display barcode's information in the barcode view. It contains:
 * - a product id
 * - a product designation
 * - the product sub category
 * - the product image
 * 
 * @author Ismaël
 *
 */
public class BarCodeItem implements Serializable {

	private String productId;
	private String productName;
	private String categoryName;
	private byte[] productImage;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public byte[] getProductImage() {
		return productImage;
	}

	public void setProductImage(byte[] productImage) {
		this.productImage = productImage;
	}

	public void setBarCodeItem(int id, String name, String category, byte[] image) {
		setProductId(String.valueOf(id));
		setProductName(name);
		setCategoryName(category);
		setProductImage(image);

	}
}
