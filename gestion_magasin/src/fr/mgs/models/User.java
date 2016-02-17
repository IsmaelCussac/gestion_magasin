package fr.mgs.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
* This class describes an user entity in database. It contains : 
 * - an id
 * - a first name
 * - a name
 * - a team
 * - a phone number
 * - a mail
 * 
 * @author Ismaël
 *
 */
@Entity(name = "users")
@Table(name = "users_t")
public class User  implements Serializable {

}
