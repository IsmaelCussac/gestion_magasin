package fr.mgs.web.customer;

import java.io.Serializable;

/**
 * 
 * @author Ismaël
 *
 */
public class StoreItem implements Serializable {

	private int productId;
	private String designation;
	private byte[] picture;
	private double quantity;
	private String subCategory;

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public void setStoreItem(int productId, String designation, byte[] picture, double quantity, String subCategory) {
		setProductId(productId);
		setDesignation(designation);
		setPicture(picture);
		setQuantity(quantity);
		setSubCategory(subCategory);
	}

	@Override
	public String toString() {
		return "OrderItem [productId=" + productId + ", designation=" + designation + ", quantity=" + quantity
				+ ", subCategory=" + subCategory + "]";
	}

}
