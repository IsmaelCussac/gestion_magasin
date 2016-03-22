package fr.mgs.web.storekeeper;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import fr.mgs.model.order.Order;

public class EditOrderActionListener implements ActionListener{

	@Override
	public void processAction(ActionEvent event) throws AbortProcessingException {
		UIComponent c = event.getComponent();
		OrderController odersView = (OrderController) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("ordersView");
		odersView.setOrderToEdit((Order) c.getAttributes().get("orderToEdit"));
	}

}
