package fr.mgs.models.users;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import fr.mgs.models.products.Product;

/**
* This class describes a historical entity in database. It contains : 
 * - an id
 * - a store keeper id
 * - a product
 * - an action
 * - a date
 * - a comment
 * 
 * @author Ismaël
 *
 */
@Entity(name = "historicals")
@Table(name = "historical_t")
public class Historical implements Serializable {

	@Id
	@Column(name = "historical_id")
	private int id;
	
	private User storeKeeper;
	
	private Product product;
	
	private Action action;
	
	private Date date;
	
	private String comment;
}
