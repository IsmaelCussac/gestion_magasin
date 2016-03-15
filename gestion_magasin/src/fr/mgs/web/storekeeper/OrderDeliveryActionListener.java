package fr.mgs.web.storekeeper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import fr.mgs.model.order.Order;
import fr.mgs.model.order.OrderLine;
import fr.mgs.model.product.Product;
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
		// test scan
		OrderLine oltestscan = new OrderLine();
		Product p = new Product();
		p.setProductId(2554363);
		p.setDesignation("chargeur");
		oltestscan.setQuantity(5);
		oltestscan.setProduct(p);
		// fin test scan

		UIComponent c = event.getComponent();
		OrdersView odersView = (OrdersView) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("ordersView");
		Team team = (Team) c.getAttributes().get("teamToDeliver");
		List<OrderLine> teamsOls = new ArrayList<OrderLine>();
		odersView.setSelectedTeam(team);
		try {
			for (Order ord : odersView.getOrdersToDeliverByTeam().get(team)) {
				for (OrderLine ol : ord.getOrderLines()) {
					teamsOls.add(ol);
				}
			}
//			teamsOls.add(oltestscan);

			odersView.setSelectedTeamOrderLines(teamsOls);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
