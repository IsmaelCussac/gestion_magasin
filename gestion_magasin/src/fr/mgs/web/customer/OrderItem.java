package fr.mgs.web.customer;

import java.io.Serializable;

import fr.mgs.model.product.SubCategory;

public class OrderItem implements Serializable{
	
	private int productId;
	private String designation;
	private String picture;
	private double quantity;
	
	public OrderItem() {
	
	}

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

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	
	public void setOrderItem(int productId, String designation, String picture, double quantity){
		setProductId(productId);
		setDesignation(designation);
		setPicture(picture);
		setQuantity(quantity);
	}

	@Override
	public String toString() {
		return "OrderItem [productId=" + productId + ", designation=" + designation + ", picture=" + picture
				+ ", quantity=" + quantity + "]";
	}
	
	
}
