package fr.mgs.models.order;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import fr.mgs.models.product.Product;
import fr.mgs.models.user.User;

/**
* This class describes an order entity in database. It contains : 
 * - an id
 * - an user id
 * - a submission date
 * - a delivery date
 * - a list of order lines
 * - a comment
 * - an order status
 * 
 * @author Ismaël
 *
 */
@Entity(name = "orders")
@Table(name = "order_t")
public class Order implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "order_id")
	private int orderId;
	
	private User orderUser;
	
	private Date submissionDate;
	
	private Date deliveryDate;
	
	private HashMap<Product, Integer> orderLines;
	
	private String comment;
	
	private OrderStatus status;
	
	public Order(){}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public User getOrderUser() {
		return orderUser;
	}

	public void setOrderUser(User orderUser) {
		this.orderUser = orderUser;
	}

	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public HashMap<Product, Integer> getOrderLines() {
		return orderLines;
	}

	public void setOrderLines(HashMap<Product, Integer> orderLines) {
		this.orderLines = orderLines;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	
	public void setOrder(User orderUser, Date submissionDate, Date deliveryDate, HashMap<Product, Integer> orderLines, String comment, OrderStatus status){
		setOrderUser(orderUser);
		setSubmissionDate(submissionDate);
		setDeliveryDate(deliveryDate);
		setOrderLines(orderLines);
		setComment(comment);
		setStatus(status);
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", orderUser=" + orderUser + ", submissionDate=" + submissionDate
				+ ", deliveryDate=" + deliveryDate + ", orderLines=" + orderLines + ", comment=" + comment + ", status="
				+ status + "]";
	}
}
