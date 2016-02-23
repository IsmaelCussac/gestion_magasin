package fr.mgs.models.users;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
* This class describes a team entity in database. It contains : 
 * - an id
 * - a name
 * - a floor
 * - a privilege
 * 
 * @author Ismaël
 *
 */
@Entity(name = "teams")
@Table(name = "team_t")
public class Team implements Serializable {

}
