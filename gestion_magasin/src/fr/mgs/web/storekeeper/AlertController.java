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
import javax.faces.bean.ViewScoped;

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
@ViewScoped
public class AlertController {

	private ProductManager productManager;
	private OrderManager orderManager;
	private List<AlertProduct> alertList;

	private List<Lot> outOfDateLots;
	private List<AlertProduct> onDemandProducts;
	private List<AlertProduct> shortageStockProducts;

	@PostConstruct
	public void init() {

		productManager = new ProductManager();
		productManager.init(DataSource.LOCAL);

		orderManager = new OrderManager();
		orderManager.init(DataSource.LOCAL);
		try {
			alertList = getAlerts();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		outOfDateLots = new ArrayList<Lot>();
		onDemandProducts = new ArrayList<AlertProduct>();
		shortageStockProducts = new ArrayList<AlertProduct>();

		findOutOfDateLots();
		findOnDemandProducts();
		findShortageStockProducts();
	}

	public void findOutOfDateLots() {
		outOfDateLots = (List<Lot>) productManager.findAllOutOfDateLots();
	}

	public void findOnDemandProducts() {

		for (AlertProduct prod : alertList) {
			if (prod.getNeededQuantity() != 0) {
				onDemandProducts.add(prod);
			}
		}
	}

	public void findShortageStockProducts() {

		for (AlertProduct prod : alertList) {
			if (prod.getAvailableQuantity() < prod.getRequiredQuantity()) {
				shortageStockProducts.add(prod);
			}
		}
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

	/**
	 * Load shortage and on-demand alerts
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<AlertProduct> getAlerts() throws SQLException {

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

		// parcours tous les produits, puis tous les lot pour calculer la
		// quantité nécessaire
		for (Product product : products) {
			double sumLot = 0;
			for (Lot lot : product.getLots()) {
				sumLot += lot.getQuantity();
			}
			// si la quantité cumulée des lots est inférieure à la quantité
			// minimale on ajoute à la liste
			if (sumLot < product.getMinQuantity() || sumLot < quantities.get(product.getProductId())) {
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

	public List<AlertProduct> getOnDemandProducts() {
		return onDemandProducts;
	}

	public void setOnDemandProducts(List<AlertProduct> onDemandProducts) {
		this.onDemandProducts = onDemandProducts;
	}

	public List<AlertProduct> getShortageStockProducts() {
		return shortageStockProducts;
	}

	public void setShortageStockProducts(List<AlertProduct> shortageStockProducts) {
		this.shortageStockProducts = shortageStockProducts;
	}

	public List<Lot> getOutOfDateLots() {
		return outOfDateLots;
	}

	public void setOutOfDateLots(List<Lot> outOfDateLots) {
		this.outOfDateLots = outOfDateLots;
	}

}
