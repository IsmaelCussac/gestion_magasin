package fr.mgs.model.product;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import fr.mgs.model.order.OrderLine;
import fr.mgs.toolbox.BarCode;

/**
 * This class describes a product entity in database. It contains : - an id - a
 * name - a level 2 category - a warning period - a minimal quantity in stock -
 * a price - a visibility state - a picture - a conditioning - a list of Lots
 * 
 * @author Ismaël
 *
 */
@Entity(name = "products")
@Table(name = "product_t")
public class Product {

	@Id
	@Column(name = "product_id")
	private int productId;

	@Column(name = "designation", length = 100, nullable = false)
	private String designation;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sub_category")
	private SubCategory subCategory;

	@Column(name = "warning_period", nullable = true)
	@Min(0)
	private Integer warningPeriod;

	@Column(name = "min_quantity", nullable = true)
	@Min(0)
	private Double minQuantity;

	@Column(name = "price", nullable = true)
	@Min(0)
	private Double price;

	@Column(name = "visibility", nullable = false)
	private boolean visibility;

	@Column(name = "picture", length = 100, nullable = true)
	private String picture;

	@Column(name = "conditioning", nullable = true)
	@Min(0)
	private Double conditioning;

	@OneToMany(mappedBy = "lotProduct", fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<Lot> lots = new HashSet<Lot>();

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY, orphanRemoval = false, cascade = CascadeType.ALL)
	private Set<OrderLine> orderLines = new HashSet<OrderLine>();

	public Product() {
	}

	public int getProductId() {
		return productId;
	}
	
	public void setProductId(int product) {
	this.productId = product;
}

//	public void setProductId() {
//		this.productId = BarCode.getProductBarCode(designation, subCategory);
//	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public SubCategory getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}

	public Integer getWarningPeriod() {
		return warningPeriod;
	}

	public void setWarningPeriod(Integer warningPeriod) {
		this.warningPeriod = warningPeriod;
	}

	public double getMinQuantity() {
		return minQuantity;
	}

	public void setMinQuantity(double minQuantity) {
		this.minQuantity = minQuantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public boolean isVisible() {
		return visibility;
	}

	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Double getConditioning() {
		return conditioning;
	}

	public void setConditioning(Double conditioning) {
		this.conditioning = conditioning;
	}

	public Set<Lot> getLots() {
		return lots;
	}

	public void setLots(Set<Lot> lots) {
		this.lots = lots;
	}

	public Set<OrderLine> getOrderLines() {
		return orderLines;
	}

	public void setOrderLines(Set<OrderLine> orderLines) {
		this.orderLines = orderLines;
	}

	public void setProduct(String designation, SubCategory subCategory, Integer warningPeriod, double minQuantity,
			double price, boolean visibility, String picture, double conditioning) {
		setDesignation(designation);
		setSubCategory(subCategory);
		setWarningPeriod(warningPeriod);
		setMinQuantity(minQuantity);
		setPrice(price);
		setVisibility(visibility);
		setPicture(picture);
		setConditioning(conditioning);
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((conditioning == null) ? 0 : conditioning.hashCode());
		result = prime * result + ((designation == null) ? 0 : designation.hashCode());
		result = prime * result + ((minQuantity == null) ? 0 : minQuantity.hashCode());
		result = prime * result + ((picture == null) ? 0 : picture.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + (visibility ? 1231 : 1237);
		result = prime * result + ((warningPeriod == null) ? 0 : warningPeriod.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (conditioning == null) {
			if (other.conditioning != null)
				return false;
		} else if (!conditioning.equals(other.conditioning))
			return false;
		if (designation == null) {
			if (other.designation != null)
				return false;
		} else if (!designation.equals(other.designation))
			return false;
		if (minQuantity == null) {
			if (other.minQuantity != null)
				return false;
		} else if (!minQuantity.equals(other.minQuantity))
			return false;
		if (picture == null) {
			if (other.picture != null)
				return false;
		} else if (!picture.equals(other.picture))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (productId != other.productId)
			return false;
		if (visibility != other.visibility)
			return false;
		if (warningPeriod == null) {
			if (other.warningPeriod != null)
				return false;
		} else if (!warningPeriod.equals(other.warningPeriod))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", designation=" + designation + ", subCategory=" + subCategory
				+ ", warningPeriod=" + warningPeriod + ", minQuantity=" + minQuantity + ", price=" + price
				+ ", visibility=" + visibility + ", picture=" + picture + ", conditioning=" + conditioning + "]";
	}

}
