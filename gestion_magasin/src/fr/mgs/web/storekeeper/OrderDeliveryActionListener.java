package fr.mgs.web.storekeeper;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

/**
 * This class is used to manage performed actions for orders
 * 
 * @author Ibrahima
 *
 */

public class OrderDeliveryActionListener implements ActionListener {

	@Override
	public void processAction(ActionEvent event) throws AbortProcessingException {
		UIComponent c = event.getComponent();
		OrdersView odersView = (OrdersView) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("ordersView");
		odersView.setSelectedTeamName((String) c.getAttributes().get("teamToDeliver"));
	}

}
