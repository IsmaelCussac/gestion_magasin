package fr.mgs.models.products;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This class describes a product entity in database. It contains : 
 * - an id
 * - a name
 * - a level 2 category
 * - a quantity
 * - a minimal quantity in stock
 * - a price
 * - a visibility state
 * - a picture
 * - a conditioning
 * 
 * @author Ismaël
 *
 */
@Entity(name = "products")
@Table(name = "product_t")
public class Product implements Serializable {

	
	@Id
	@Column(name = "product_id")
	private int id;
}
