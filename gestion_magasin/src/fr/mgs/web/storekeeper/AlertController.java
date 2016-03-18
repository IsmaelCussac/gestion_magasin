package fr.mgs.web.storekeeper;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import fr.mgs.business.OrderManager;
import fr.mgs.business.ProductManager;
import fr.mgs.connection.DataSource;
import fr.mgs.model.order.Order;
import fr.mgs.model.order.OrderLine;
import fr.mgs.model.product.Lot;
import fr.mgs.model.product.Product;

/**
 * 
 * @author Ismaël
 *
 */
@ManagedBean(name = "skAlert")
@SessionScoped
public class AlertController {

	private ProductManager productManager;
	private OrderManager orderManager;
	private List<AlertProduct> limitedProducts;

	@PostConstruct
	public void init() {

		productManager = new ProductManager();
		productManager.init(DataSource.LOCAL);

		orderManager = new OrderManager();
		orderManager.init(DataSource.LOCAL);
	}

	public Collection<Lot> getOutOfDateLots() {
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

	public List<AlertProduct> getOnDemandProducts() throws SQLException {

		List<AlertProduct> list = getShortageStockProducts();
		List<AlertProduct> resultList = new ArrayList<AlertProduct>();
		
		for (AlertProduct prod : list) {
			if (prod.getNeededQuantity() != 0) {
				resultList.add(prod);
			}
		}
		return resultList;
	}

	public List<AlertProduct> getShortageStockProducts() throws SQLException {
		List<AlertProduct> limitedProds = new ArrayList<AlertProduct>();
		Collection<Order> validatedOrders = orderManager.findAllValidatedOrders();
		Collection<Product> products = productManager.findAllProducts();
		Map<Integer, Double> quantities = new HashMap<Integer, Double>();
		

		// parcours toutes les commandes validées ou avec reliquats, puis chaque
		// ligne de chaque commande
		// pour récupérer le nombre total d'articles en demande
		for (Order order : validatedOrders) {
			for (OrderLine orderLine : order.getOrderLines()) {
				if (quantities.containsKey(orderLine.getProduct().getProductId())) {
					double newValue = quantities.get(orderLine.getProduct().getProductId()) + orderLine.getQuantity()
							- orderLine.getDeliveredQuantity();
					quantities.put(orderLine.getProduct().getProductId(), newValue);
				} else {
					quantities.put(orderLine.getProduct().getProductId(), orderLine.getQuantity());
				}
			}
		}

		for (Product product : products) {
			double sumLot = 0;
			for (Lot lot : product.getLots()) {
				sumLot += lot.getQuantity();
			}
			if (sumLot < product.getMinQuantity()) {
				AlertProduct aP = new AlertProduct();
				if (quantities.containsKey(product.getProductId())) {
					aP.setAlertProduct(product.getProductId(), product.getDesignation(),
							product.getSubCategory().getName(), quantities.get(product.getProductId()),
							product.getMinQuantity() - sumLot, sumLot, product.getMinQuantity());
				} else {
					aP.setAlertProduct(product.getProductId(), product.getDesignation(),
							product.getSubCategory().getName(), 0, product.getMinQuantity() - sumLot, sumLot,
							product.getMinQuantity());
				}
				limitedProds.add(aP);
			}
		}
		return limitedProds;
	}
}
