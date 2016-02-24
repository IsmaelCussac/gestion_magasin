package fr.mgs.models.products;

import java.io.Serializable;

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
 * @author Isma�l
 *
 */
@Entity(name = "lots")
@Table(name = "lot_t")
public class Lot implements Serializable {

	@Id
	@Column(name = "lots_id")
	private int id;
}
