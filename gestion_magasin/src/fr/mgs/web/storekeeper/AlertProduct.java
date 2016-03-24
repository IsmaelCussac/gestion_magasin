package fr.mgs.web.storekeeper;

import java.io.Serializable;

/**
 * Object used to display product's information in the alert list. It contains:
 * - a product id
 * - a product designation
 * - the product sub category
 * - the needed quantity
 * - the missing quantity
 * - the available quantity
 * - the required quantity
 * 
 * @author Ismaël
 *
 */
public class AlertProduct implements Serializable {

	private int productId;

	private String designation;

	private String subCategory;
	
	private double neededQuantity;

	private double missingQuantity;

	private double availableQuantity;

	private double requiredQuantity;

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

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public double getNeededQuantity() {
		return neededQuantity;
	}

	public void setNeededQuantity(double neededQuantity) {
		this.neededQuantity = neededQuantity;
	}

	public double getMissingQuantity() {
		return missingQuantity;
	}

	public void setMissingQuantity(double missingQuantity) {
		this.missingQuantity = missingQuantity;
	}

	public double getAvailableQuantity() {
		return availableQuantity;
	}

	public void setAvailableQuantity(double availableQuantity) {
		this.availableQuantity = availableQuantity;
	}

	public double getRequiredQuantity() {
		return requiredQuantity;
	}

	public void setRequiredQuantity(double requiredQuantity) {
		this.requiredQuantity = requiredQuantity;
	}

	public void setAlertProduct(int productId, String designation, String subCategory, double neededQuantity, double missingQuantity,
			double availableQuantity, double requiredQuantity) {
		setProductId(productId);
		setDesignation(designation);
		setSubCategory(subCategory);
		setNeededQuantity(neededQuantity);
		setMissingQuantity(missingQuantity);
		setAvailableQuantity(availableQuantity);
		setRequiredQuantity(requiredQuantity);
	}

	@Override
	public String toString() {
		return "AlertProduct [productId=" + productId + ", designation=" + designation + ", subCategory=" + subCategory
				+ ", neededQuantity=" + neededQuantity + ", missingQuantity=" + missingQuantity + ", availableQuantity="
				+ availableQuantity + ", requiredQuantity=" + requiredQuantity + "]";
	}
	
	
}
