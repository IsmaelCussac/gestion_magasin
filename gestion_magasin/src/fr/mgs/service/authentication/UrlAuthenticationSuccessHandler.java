package fr.mgs.service.authentication;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import fr.mgs.model.user.Privilege;
/**
 * The class manage the url authentification
 * @author Ismael
 *
 */
public class UrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {
		handle(request, response, authentication);
		clearAuthenticationAttributes(request);
	}

	/**
	 * This method detemine target url and redirect
	 * @param request the request
	 * @param response the reponse
	 * @param authentication the authentification
	 * @throws IOException the exception
	 */
	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException {
		String targetUrl = determineTargetUrl(authentication);

		if (response.isCommitted()) {
			return;
		}

		redirectStrategy.sendRedirect(request, response, targetUrl);
	}

	/**
	 * determine the target url
	 * @param authentication the authentification 
	 * @return url redirection
	 */
	protected String determineTargetUrl(Authentication authentication) {

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals(Privilege.CUSTOMER.name())) {
				return "/customer/products";
			} else if (grantedAuthority.getAuthority().equals(Privilege.STORE_KEEPER.name())) {
				return "/storekeeper/products";
			} else if (grantedAuthority.getAuthority().equals(Privilege.APP_ADMIN.name())) {
				return "/admin/events";
			}
		}
		return "/403";
	}

	/**
	 * Clear the authentification attributes
	 * @param request the request
	 */
	protected void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		}
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}

	/**
	 * Set the redirection strategy
	 * @param redirectStrategy
	 */
	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

	/**
	 * Return the redirect strategy
	 * @return redirectStrategy the redirection
	 */
	protected RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}

}
