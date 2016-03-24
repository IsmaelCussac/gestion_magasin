package fr.mgs.service.converter;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import fr.mgs.business.ProductManager;
import fr.mgs.connection.DataSource;
import fr.mgs.model.product.Lot;
import fr.mgs.web.storekeeper.OrderController;

/**
 * Class lot Converter
 * 
 * @author Ibrahima
 *
 */
@FacesConverter("lotConverter")
public class LotConverter implements Converter, Serializable {

	private ProductManager prodManager;
	ArrayList<Lot> selectedLot = new ArrayList<Lot>();

	/**
	 * return the object
	 */
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		prodManager = new ProductManager();
		prodManager.init(DataSource.LOCAL);
		if (value != null && value.trim().length() > 0) {
			try {

				OrderController orderController = (OrderController) FacesContext.getCurrentInstance()
						.getExternalContext().getSessionMap().get("ordersView");

				try {
					Lot l = prodManager.findLot(Integer.parseInt(value));
					System.out.println(l.getLotId());
					orderController.setSelectedLot(l);
					if (orderController.getOrdersLots() != null && orderController.getOrdersLots().contains(l)) {
						orderController.getOrdersLots().add(l);
					}
					return l;
				} catch (SQLException e) {
					e.printStackTrace();
				}

			} catch (NumberFormatException e) {
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", ""));
			}
		} else {
			return null;
		}
		return value;
	}

	/**
	 * Return a string value of the lot
	 */
	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if (object != null) {
			return String.valueOf(((Lot) object).getLotId());
		} else {
			return null;
		}
	}
}
