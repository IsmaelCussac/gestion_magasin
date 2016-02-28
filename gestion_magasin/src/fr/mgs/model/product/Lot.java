package fr.mgs.model.product;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * This class describes a lot entity in database. It contains : 
 * - an id
 * - an expiration date
 * - a product id
 * - a quantity
 *
 * @author Ismaël
 *
 */
@Entity(name = "lots")
@Table(name = "lot_t")
public class Lot implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "lots_id")
	private int lotId;

	@Temporal(TemporalType.DATE)
	@Column(name = "expiration_date", nullable = true)
	private Date expirationDate;

	@Column(name = "quantity")
	private double quantity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lot_product")
	private Product lotProduct;

	public Lot() {
	}

	public int getLotId() {
		return lotId;
	}

	public void setLotId(int lotId) {
		this.lotId = lotId;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Product getLotProduct() {
		return lotProduct;
	}

	public void setLotProduct(Product lotProduct) {
		this.lotProduct = lotProduct;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public void setLot(Date expirationDate, Product lotProduct, double quantity) {
		setExpirationDate(expirationDate);
		setLotProduct(lotProduct);
		setQuantity(quantity);
	}

	@Override
	public String toString() {
		return "Lot [lotId=" + lotId + ", expirationDate=" + expirationDate + ", lotProduct=" + lotProduct
				+ ", quantity=" + quantity + "]";
	}
}
