package fr.mgs.web.storekeeper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
		OrderController odersView = (OrderController) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("ordersView");
		Team teamToDeliver = (Team) c.getAttributes().get("teamToDeliver");
		List<OrderLine> teamsOls = new ArrayList<OrderLine>();
		Collection<Order> orders = new ArrayList<Order>();
		odersView.setSelectedTeam(teamToDeliver);
		try {
			Map<Team, Collection<Order>> ordersByTeam = odersView.getOrdersToDeliverByTeam();
			for (Iterator<Team> i = ordersByTeam.keySet().iterator(); i.hasNext();) {
				Team t = i.next();
				if (t.getName().equals(teamToDeliver.getName())) {
					orders = ordersByTeam.get(t);
					break;
				}

			}
			for (Order ord : orders) {
				for (OrderLine ol : ord.getOrderLines()) {
					teamsOls.add(ol);
				}
			}

			odersView.setSelectedTeamOrderLines(teamsOls);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
