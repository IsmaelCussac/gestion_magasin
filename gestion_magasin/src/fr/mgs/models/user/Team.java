package fr.mgs.models.user;

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
	private String teamId;

	@Column(name = "name", length = 100, nullable = false)
	private String name;

	@Column(name = "floor")
	@Range(min = 0, max = 10)
	private int floor;

	@Column(name = "privilege", nullable = false)
	private Privilege privilege;
	
	@OneToMany(mappedBy="team", fetch=FetchType.LAZY, orphanRemoval=false)
	private Set<User> users = new HashSet<User>();
	
	public Team(){}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
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
	
	public void setTeam(){
		setTeamId(teamId);
		setName(name);
		setFloor(floor);
		setPrivilege(privilege);
	}

	@Override
	public String toString() {
		return "Team [teamId=" + teamId + ", name=" + name + ", floor=" + floor + ", privilege=" + privilege
				+ ", users=" + users + "]";
	}

}
