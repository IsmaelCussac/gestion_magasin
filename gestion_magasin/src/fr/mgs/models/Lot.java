package fr.mgs.models;

import java.io.Serializable;

import javax.persistence.Entity;
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
@Table(name = "lots_t")
public class Lot  implements Serializable {

}
