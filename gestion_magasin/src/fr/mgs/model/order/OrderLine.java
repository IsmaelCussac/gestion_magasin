package fr.mgs.model.order;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.validation.constraints.Min;

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
public class OrderLine implements Serializable{

	@EmbeddedId
	OrderLinePK orderLinePK;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("orderId")
	@JoinColumn(name = "order_line_order")
	private Order order;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("productId")
	@JoinColumn(name = "order_line_product")
	private Product product;

	@Column(name = "quantity", nullable = false)
	@Min(0)
	private double quantity;

	@Column(name = "delivered_quantity", nullable = true)
	@Min(value = 0, message = "La quantité ne peut être négatif")
	private double deliveredQuantity;

	public OrderLinePK getOrderLinePK() {
		return orderLinePK;
	}

	public void setOrderLinePK(OrderLinePK orderLinePK) {
		this.orderLinePK = orderLinePK;
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

	public void setOrderLine(Order order, Product product, double quantity, double deleveredQuantity) {
		setOrderLinePK(new OrderLinePK(product.getProductId(), order.getOrderId()));
		setOrder(order);
		setProduct(product);
		setQuantity(quantity);
		setDeliveredQuantity(deleveredQuantity);
	}

	// @Override
	// public int hashCode() {
	// final int prime = 31;
	// int result = 1;
	// result = prime * result + ((product == null) ? 0 : product.hashCode());
	// return result;
	// }
	//
	// public boolean equals(OrderLine ol) {
	// if (getProduct().getProductId() == ol.getProduct().getProductId())
	// return true;
	// return false;
	// }

	@Override
	public String toString() {
		return "OrderLine [ order=" + order + ", product=" + product + ", quantity=" + quantity + ", deliveredQuantity="
				+ deliveredQuantity + "]";
	}

}
