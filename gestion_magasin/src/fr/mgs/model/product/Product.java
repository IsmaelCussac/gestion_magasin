package fr.mgs.model.product;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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

/**
 * This class describes a product entity in database. It contains : 
 * - an id
 * - a name
 * - a level 2 category
 * - a warning period
 * - a minimal quantity in stock
 * - a price
 * - a visibility state
 * - a picture
 * - a conditioning
 * - a list of Lots
 * 
 * @author Ismaël
 *
 */
@Entity(name = "products")
@Table(name = "product_t")
public class Product implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "product_id")
	private int id;

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
	private double minQuantity;

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
	
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY, orphanRemoval = false)
	private Set<OrderLine> orderLines = new HashSet<OrderLine>();

	public Product() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public boolean isVisibility() {
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
	public String toString() {
		return "Product [id=" + id + ", designation=" + designation + ", subCategory=" + subCategory + ", warningPeriod="
				+ warningPeriod + ", minQuantity=" + minQuantity + ", price=" + price + ", visibility=" + visibility
				+ ", picture=" + picture + ", conditioning=" + conditioning + "]";
	}

}
