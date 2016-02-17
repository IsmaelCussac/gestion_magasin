package fr.mgs.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
* This class describes an order line entity in database. It contains : 
 * - an order id
 * - a product
 * - a quantity
 * 
 * @author Ismaël
 *
 */
@Entity(name = "orderLines")
@Table(name = "orderlines_t")
public class OrderLine implements Serializable{

}
