package fr.mgs.model.order;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import fr.mgs.model.user.Person;

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
	private Person orderUser;

	@Temporal(TemporalType.DATE)
	@Column(name = "submission_date", nullable = true)
	private Date submissionDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "delivery_date", nullable = true)
	private Date deliveryDate;

	@OneToMany(mappedBy = "order", fetch = FetchType.EAGER, orphanRemoval = true,cascade = { CascadeType.REMOVE, CascadeType.MERGE })
	private Set<OrderLine> orderLines = new HashSet<OrderLine>();

	@Column(name = "comment", length = 250, nullable = true)
	private String comment;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	public Order() {
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Person getOrderUser() {
		return orderUser;
	}

	public void setOrderUser(Person orderUser) {
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

	public Set<OrderLine> getOrderLines() {
		return orderLines;
	}

	public void setOrderLines(Set<OrderLine> orderLines) {
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

	public void setOrder(Person orderUser, Date submissionDate, Date deliveryDate, Set<OrderLine> orderLines,
			String comment, OrderStatus status) {
		setOrderUser(orderUser);
		setSubmissionDate(submissionDate);
		setDeliveryDate(deliveryDate);
		setOrderLines(orderLines);
		setComment(comment);
		setStatus(status);
	}
	
	public void addOrderLine(OrderLine orderLine){
		this.orderLines.add(orderLine);
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", orderUser=" + orderUser + ", submissionDate=" + submissionDate
				+ ", deliveryDate=" + deliveryDate + ", orderLines=" + orderLines + ", comment=" + comment + ", status="
				+ status + "]";
	}
}
