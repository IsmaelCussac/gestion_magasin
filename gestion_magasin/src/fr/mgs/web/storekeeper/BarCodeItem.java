package fr.mgs.web.storekeeper;

import java.io.Serializable;

public class BarCodeItem implements Serializable {

	private String productId;
	private String productName;
	private String categoryName;
	private byte[] productImage;
	private byte[] productBarCode;

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

	public byte[] getProductBarCode() {
		return productBarCode;
	}

	public void setProductBarCode(byte[] productBarCode) {
		this.productBarCode = productBarCode;
	}

	public void setBarCodeItem(int id, String name, String category, byte[] image) {
		setProductId(String.valueOf(id));
		setProductName(name);
		setCategoryName(category);
		setProductImage(image);

	}

}
