package fr.mgs.web.order;

import javax.faces.bean.ManagedBean;

/**
 * This class is used to manage navigation rules
 * 
 * @author Ibrahima
 *
 */

@ManagedBean
public class NavigationController {
	public String showSkOrders() {
		return "showSkOrders?faces-redirect=true";
	}

}
