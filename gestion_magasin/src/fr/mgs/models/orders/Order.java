package fr.mgs.models.orders;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import fr.mgs.models.users.User;

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
	@Column(name = "order_id")
	private int id;
	
	private User user;
	
	private Date submissionDate;
	
	private Date deliveryDate;
	
	// order line //
	
	private String comment;
	
	private OrderStatus status;

}
