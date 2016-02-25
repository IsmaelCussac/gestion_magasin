package fr.mgs.models.order;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
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
	@Column(name = "order_id")
	private int id;
	
	private User user;
	
	private Date submissionDate;
	
	private Date deliveryDate;
	
	private HashMap<Product, Integer> orderList;
	
	private String comment;
	
	private OrderStatus status;

}
