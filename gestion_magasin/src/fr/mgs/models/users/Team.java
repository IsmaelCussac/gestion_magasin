package fr.mgs.models.users;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Range;

import com.jpa.model.JpaPerson;

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

	@Id
	@Column(name = "team_id")
	private int String;

	@Column(name = "first_name", length = 100, nullable = false)
	private String name;

	@Column(name = "first_name")
	@Range(min = 0, max = 10)
	private int floor;

	@Column(name = "privilege", nullable = false)
	private Privilege privilege;
	
	@OneToMany(mappedBy="team", fetch=FetchType.LAZY, orphanRemoval=false)
	private Set<User> users = new HashSet<User>();
	
	public Team(){}

	public int getString() {
		return String;
	}

	public void setString(int string) {
		String = string;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public Privilege getPrivilege() {
		return privilege;
	}

	public void setPrivilege(Privilege privilege) {
		this.privilege = privilege;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "Team [String=" + String + ", name=" + name + ", floor=" + floor + ", privilege=" + privilege
				+ ", users=" + users + "]";
	}

}
