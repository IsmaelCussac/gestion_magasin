package fr.mgs.model.order;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import fr.mgs.model.product.Product;

/**
 * This class describes an order line entity in database. It contains : - an id
 * - an user - a product - a quantity - a delivered quantity
 * 
 * @author Ismaël
 *
 */
@Entity(name = "orderLines")
@Table(name = "order_line_t")
public class OrderLine {

	@EmbeddedId
	OrderLinePK orderLinePK;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "order_line_order", nullable = false)
	@MapsId("orderId")
	private Order order;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "order_line_product", nullable = false)
	@MapsId("productId")
	private Product product;

	@Column(name = "quantity", nullable = false)
	private double quantity;

	@Column(name = "delivered_quantity", nullable = true)
	private double deliveredQuantity;

	public OrderLine() {
	}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		return result;
	}

	public boolean equals(OrderLine ol) {
		if (getProduct().getProductId() == ol.getProduct().getProductId())
			return true;
		return false;
	}

	@Override
	public String toString() {
		return "OrderLine [ order=" + order + ", product=" + product + ", quantity=" + quantity
				+ ", deliveredQuantity=" + deliveredQuantity + "]";
	}

}
