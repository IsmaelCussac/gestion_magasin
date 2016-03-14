package fr.mgs.web.storekeeper;

import java.sql.SQLException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import fr.mgs.model.order.Order;
import fr.mgs.model.order.OrderLine;
import fr.mgs.model.user.Team;

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
		Team team = (Team) c.getAttributes().get("teamToDeliver");
		odersView.setSelectedTeam(team);
		try {
			for (Order ord : odersView.getOrdersToDeliverByTeam().get(team)) {
				for (OrderLine ol : ord.getOrderLines()) {
					odersView.getSelectedTeamOrderLines().add(ol);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
