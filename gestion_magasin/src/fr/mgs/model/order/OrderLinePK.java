package fr.mgs.model.order;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 
 * @author Ismaël
 *
 */
@Embeddable
public class OrderLinePK implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer productId;
	
	private Integer orderId;
	
	public OrderLinePK(){}

	public OrderLinePK(Integer product, Integer order) {
		this.productId = product;
		this.orderId = order;
	}

	public Integer getProduct() {
		return productId;
	}

	public void setProduct(Integer productId) {
		this.productId = productId;
	}

	public Integer getOrder() {
		return orderId;
	}

	public void setOrder(Integer orderId) {
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