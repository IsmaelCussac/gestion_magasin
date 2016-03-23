package fr.mgs.web.storekeeper;

import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import fr.mgs.business.ProductManager;
import fr.mgs.connection.DataSource;
import fr.mgs.model.product.Lot;

@FacesConverter("lotConverter")
public class LotConverter implements Converter {

	private ProductManager prodManager;

	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		prodManager = new ProductManager();
		prodManager.init(DataSource.LOCAL);
		if (value != null && value.trim().length() > 0) {
			try {
				OrderController service = (OrderController) fc.getExternalContext().getApplicationMap()
						.get("ordersView");

				try {
					return prodManager.findLot(Integer.parseInt(value));
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

	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if (object != null) {
			return String.valueOf(((Lot) object).getLotId());
		} else {
			return null;
		}
	}
}
