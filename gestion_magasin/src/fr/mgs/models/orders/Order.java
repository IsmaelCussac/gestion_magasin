package fr.mgs.models.orders;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

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
@Table(name = "orders_t")
public class Order  implements Serializable {

}
