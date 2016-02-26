package fr.mgs.models.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	
	@Column(name = "first_name", length = 100, nullable = false)
	private String firstName;
	
	@Column(name = "last_name", length = 100, nullable = false)
	private String lastName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team")
	private Team team;
	
	@Column(name = "phone_number", length = 10, nullable = true)
	private String phoneNumber;
	
	@Column(name = "email", length = 100, nullable = false)
	private String email;
	
	@Column(name = "password", length = 100, nullable = false)
	private String password;
	
	public User(){}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUser(String id, String firstName, String lastName, Team team, String phoneNumber, String email, String password){
		setId(id);
		setFirstName(firstName);
		setLastName(lastName);
		setTeam(team);
		setPhoneNumber(phoneNumber);
		setEmail(email);
		setPassword(password);
		
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", team=" + team
				+ ", phoneNumber=" + phoneNumber + ", email=" + email + ", password=" + password + "]";
	}

}
