package fr.mgs.service.authentication;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import fr.mgs.business.UserManager;
import fr.mgs.connection.DataSource;
import fr.mgs.model.user.Person;
import fr.mgs.model.user.Privilege;

/**
 * UserDetailService implementation used by Spring Security
 *
 * @author Ismaël
 *
 */
public class UserService implements UserDetailsService {

	private UserManager userManager;

	public UserService() throws SQLException {
		userManager = new UserManager();
		userManager.init(DataSource.LOCAL);

	}

	/**
	 * Overrided method which load a spring security user
	 * 
	 * @param userId
	 *            the user's id
	 */
	@Override
	public UserDetails loadUserByUsername(String userId) {
		Person user = null;
		try {
			user = userManager.findPerson(userId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		List<GrantedAuthority> authorities = null;
		if (user != null) {
			authorities = buildAuthorities(user.getTeam().getPrivilege());
		}
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
	private UserDetailWithName buildUserForAuthentication(Person user,
			List<GrantedAuthority> authorities) {
		UserDetailWithName result = null;
		if (user != null) {
			result = new UserDetailWithName(user.getPersonId(),
					user.getPassword(), authorities);
			result.setFirstname(user.getFirstName());
		}

		return result;
	}

	/**
	 * Use a user's authorization list to build a spring security authorization
	 * list
	 * 
	 * @param privilege
	 * @return a properly built authorization list
	 */
	private List<GrantedAuthority> buildAuthorities(Privilege privilege) {
		ArrayList<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();

		auths.add(new SimpleGrantedAuthority(privilege.name()));

		return auths;
	}

}
