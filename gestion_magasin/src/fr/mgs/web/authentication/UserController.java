package fr.mgs.web.authentication;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

/**
 * Bean used to manage the authenticated user
 * 
 * @author Ismaël
 *
 */
@ManagedBean(name = "user")
@SessionScoped
public class UserController {

	/**
	 * Authenticated user
	 */
	private User user;

	public User getUser() {
		setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Return the user name
	 * @return
	 */
	public String getUsername() {
		return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
	}
	
}
