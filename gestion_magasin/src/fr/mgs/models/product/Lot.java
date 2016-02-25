package fr.mgs.models.product;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This class describes a lot entity in database. It contains : 
 * - an id
 * - an expiration date
 * - a product id
 * - a quantity
 *
 * @author Ismaël
 *
 */
@Entity(name = "lots")
@Table(name = "lot_t")
public class Lot implements Serializable {

	@Id
	@Column(name = "lots_id")
	private int id;
	
	private Date expirationDate;
	
	private Product product;
	
	private double quantity;
}
