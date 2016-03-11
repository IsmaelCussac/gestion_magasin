package fr.mgs.model.order;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OrderLinePK implements Serializable {

	@Column(name="product_id_pk")
	private int productId;
	
	@Column(name="order_id_pk")
	private int orderId;
	
	public OrderLinePK(){}

	public OrderLinePK(int product, int order) {
		this.productId = product;
		this.orderId = order;
	}

	public int getProduct() {
		return productId;
	}

	public void setProduct(int productId) {
		this.productId = productId;
	}

	public int getOrder() {
		return orderId;
	}

	public void setOrder(int orderId) {
		this.orderId = orderId;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + orderId;
		result = prime * result + productId;
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
		OrderLinePK other = (OrderLinePK) obj;
		if (orderId != other.orderId)
			return false;
		if (productId != other.productId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OrderLinePK [product=" + productId + ", order=" + orderId + "]";
	}
}