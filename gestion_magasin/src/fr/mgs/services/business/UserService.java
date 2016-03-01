package fr.mgs.services.business;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import fr.mgs.dao.UserDAO;
import fr.mgs.model.user.User;

/**
 * UserDetailService implementation used by Spring Security
 *
 * @author Ismaël
 *
 */
public class UserService implements UserDetailsService {

	private UserDAO userDao = new UserDAO();

	public UserService() {
		userDao.init();
	}

	/**
	 * Overrided method which load a spring security user
	 * 
	 * @param userId
	 *            the user's id
	 */
	public UserDetails loadUserByUsername(String userId)
			throws UsernameNotFoundException {
		User user = null;
		try {
			user = userDao.find(userId);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		List<GrantedAuthority> authorities = buildAuthorities(user.getTeam()
				.getPrivilege().toString());
		return buildUserForAuthentication(user, authorities);
	}

	/**
	 * Build a spring security user
	 * 
	 * @param user
	 * @param authorities
	 *            previously built authority list
	 * @return a built spring security user
	 */
	private UserDetailWithName buildUserForAuthentication(User user,
			List<GrantedAuthority> authorities) {
		UserDetailWithName result = new UserDetailWithName(user.getUserId(),
				user.getPassword(), authorities);

		result.setFirstname(user.getFirstName());

		return result;
	}

	/**
	 * Use a user's authorization list to build a spring security authorization
	 * list
	 * 
	 * @param privilege
	 * @return a properly built authorization list
	 */
	private List<GrantedAuthority> buildAuthorities(String privilege) {
		ArrayList<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();

		auths.add(new SimpleGrantedAuthority(privilege.toString()));

		return auths;
	}

}
