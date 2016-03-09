package fr.mgs.model.order;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import fr.mgs.model.product.Product;

/**
* This class describes an order line entity in database. It contains : 
 * - an id
 * - an user
 * - a product
 * - a quantity
 * - a delivered quantity
 * 
 * @author Ismaël
 *
 */
@Entity(name = "orderLines")
@Table(name = "order_line_t")
public class OrderLine{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "order_line_id")
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_line_order")
	private Order order;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_line_product")
	private Product product;
	
	@Column(name = "quantity", nullable = false)
	private double quantity;
	
	@Column(name = "delivered_quantity", nullable = true)
	private double deliveredQuantity;
	
	public OrderLine(){	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public double getDeliveredQuantity() {
		return deliveredQuantity;
	}

	public void setDeliveredQuantity(double deliveredQuantity) {
		this.deliveredQuantity = deliveredQuantity;
	}

	public void setOrderLine(Order order, Product product, double quantity, double deleveredQuantity){
		setOrder(order);
		setProduct(product);
		setQuantity(quantity);
		setDeliveredQuantity(deleveredQuantity);
	}
	
	@Override
	public String toString() {
		return "OrderLine [id=" + id + ", order=" + order + ", product=" + product + ", quantity=" + quantity
				+ ", deliveredQuantity=" + deliveredQuantity + "]";
	}

}
