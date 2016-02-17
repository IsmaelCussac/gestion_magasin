package fr.mgs.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
* This class describes a historical entity in database. It contains : 
 * - an id
 * - a store keeper user
 * - a product
 * - an action
 * - a date
 * - a comment
 * 
 * @author Ismaël
 *
 */
@Entity(name = "historicals")
@Table(name = "historicals_t")
public class Historical  implements Serializable {

}
