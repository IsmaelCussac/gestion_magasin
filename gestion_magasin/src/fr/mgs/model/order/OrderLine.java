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
public class OrderLine implements Serializable {
	
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
	private int quantity;
	
	@Column(name = "delivered_quantity", nullable = true)
	private int deliveredQuantity;
	
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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getDeliveredQuantity() {
		return deliveredQuantity;
	}

	public void setDeliveredQuantity(int deliveredQuantity) {
		this.deliveredQuantity = deliveredQuantity;
	}

	public void setOrderLine(Order order, Product product, int quantity, int deleveredQuantity){
		setOrder(order);
		setProduct(product);
		setQuantity(deleveredQuantity);
		setDeliveredQuantity(deleveredQuantity);
	}
	
	@Override
	public String toString() {
		return "OrderLine [id=" + id + ", order=" + order + ", product=" + product + ", quantity=" + quantity
				+ ", deliveredQuantity=" + deliveredQuantity + "]";
	}

}
