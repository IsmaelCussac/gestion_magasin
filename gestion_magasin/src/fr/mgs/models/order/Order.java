package fr.mgs.models.order;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "order_id")
	private int orderId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_user")
	private User orderUser;

	@Temporal(TemporalType.DATE)
	@Column(name = "submission_date", nullable = true)
	private Date submissionDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "delivery_date", nullable = true)
	private Date deliveryDate;

	@ElementCollection
	@CollectionTable(name = "order_line_t", joinColumns = @JoinColumn(name = "order_id_line"))
	@MapKeyJoinColumn(name = "product_id_line")
	@Column(name = "quantity", nullable = false)
	private Map<Product, Integer> orderLines;

	@Column(name = "comment", length = 250, nullable = true)
	private String comment;

	@Column(name = "status")
	private OrderStatus status;

	public Order() {
	}

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

	public Map<Product, Integer> getOrderLines() {
		return orderLines;
	}

	public void setOrderLines(Map<Product, Integer> orderLines) {
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

	public void setOrder(User orderUser, Date submissionDate, Date deliveryDate, Map<Product, Integer> orderLines,
			String comment, OrderStatus status) {
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
