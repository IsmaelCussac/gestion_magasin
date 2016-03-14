package fr.mgs.web.authentication;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

@ManagedBean(name = "user")
@SessionScoped
public class UserController {

	private User user;

	public UserController() {
	}

	public User getUser() {
		setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUsername() {
		return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
	}
	
}
