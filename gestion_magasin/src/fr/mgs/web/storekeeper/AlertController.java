package fr.mgs.web.storekeeper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import fr.mgs.business.ProductManager;
import fr.mgs.connection.DataSource;
import fr.mgs.model.product.Lot;

/**
 * 
 * @author Ismaël
 *
 */
@ManagedBean(name = "skAlert")
@SessionScoped
public class AlertController {

	private ProductManager productManager;

	@PostConstruct
	public void init() {

		productManager = new ProductManager();
		productManager.init(DataSource.LOCAL);
	}

	public Collection<Lot> getOutOfDateLot() {
		return productManager.findAllOutOfDateLots();
	}

	public int daysLeft(Date expirationDate) {
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String currDate = dateFormat.format(new Date());
		Date currentDate = null;
		try {
			currentDate = dateFormat.parse(currDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return (int) (expirationDate.getTime() - currentDate.getTime()) / 60 / 60 / 24 / 1000;
	}
}
