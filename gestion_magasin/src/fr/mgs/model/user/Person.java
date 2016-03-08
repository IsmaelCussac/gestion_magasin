package fr.mgs.model.user;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import fr.mgs.model.order.Order;
import fr.mgs.toolbox.Hasher;

/**
 * This class describes an user entity in database. It contains : - an id - a
 * first name - a last name - a team - a phone number - a mail - a password
 * 
 * @author Ismaël
 *
 */
@Entity(name = "users")
@Table(name = "user_t")
public class Person implements Serializable {

	@Id
	@Column(name = "user_id")
	private String userId;

	@Column(name = "first_name", length = 100, nullable = false)
	private String firstName;

	@Column(name = "last_name", length = 100, nullable = false)
	private String lastName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_team")
	private Team team;

	@Column(name = "phone_number", length = 10, nullable = true)
	private String phoneNumber;

	@Column(name = "email", length = 100, nullable = false)
	private String email;

	@Column(name = "password", length = 100, nullable = false)
	private String password;

	@OneToMany(mappedBy = "orderUser", fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<Order> orders = new HashSet<Order>();

	public Person() {
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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
		this.password = Hasher.hash(password);
	}

	public void setUser(String userId, String firstName, String lastName, Team team, String phoneNumber, String email,
			String password) {
		setUserId(userId);
		setFirstName(firstName);
		setLastName(lastName);
		setTeam(team);
		setPhoneNumber(phoneNumber);
		setEmail(email);
		setPassword(password);

	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", team=" + team
				+ ", phoneNumber=" + phoneNumber + ", email=" + email + ", password=" + password + "]";
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

}
