package fr.mgs.models.users;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
* This class describes an user entity in database. It contains : 
 * - an id
 * - a first name
 * - a last name
 * - a team
 * - a phone number
 * - a mail
 * - a password
 * 
 * @author Ismaël
 *
 */
@Entity(name = "users")
@Table(name = "user_t")
public class User implements Serializable {

	@Id
	@Column(name = "user_id")
	private String id;
}
