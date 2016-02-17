package fr.mgs.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * This class describes a product entity in database. It contains : 
 * - an id
 * - a name
 * - a subcategory
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
@Table(name = "products_t")
public class Product  implements Serializable {

}
