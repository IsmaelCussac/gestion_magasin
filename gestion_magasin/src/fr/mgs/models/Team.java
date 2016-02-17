package fr.mgs.models;

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
@Table(name = "teams_t")
public class Team  implements Serializable {

}
