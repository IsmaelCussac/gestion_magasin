package fr.mgs.models.users;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
}
