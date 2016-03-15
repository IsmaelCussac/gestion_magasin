package fr.mgs.services.business;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * Class extending spring user security to pass firstname in parameter
 * 
 * @author Ismaël
 *
 */
public class UserDetailWithName extends User {
	
	private String firstname;
	
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public UserDetailWithName(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

}

