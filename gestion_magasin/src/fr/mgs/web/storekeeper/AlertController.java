package fr.mgs.web.storekeeper;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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

	public Map<Product, Double> getOnDemandProducts() throws SQLException {
		Collection<Order> validatedOrders = orderManager.findAllValidatedOrders();

		Map<Integer, Double> quantities = new HashMap<Integer, Double>();
		Map<Product, Double> products = new HashMap<Product, Double>();

		// parcours toutes les commandes validées ou avec reliquats, puis chaque ligne de chaque commande
		// pour récupérer le nombre total d'articles en demande
		for (Order order : validatedOrders) {
			for (OrderLine orderLine : order.getOrderLines()) {
				if (quantities.containsKey(orderLine.getProduct().getProductId())) {
					double newValue = quantities.get(orderLine.getProduct().getProductId()) + orderLine.getQuantity() - orderLine.getDeliveredQuantity();
					quantities.put(orderLine.getProduct().getProductId(), newValue);
				} else {
					quantities.put(orderLine.getProduct().getProductId(), orderLine.getQuantity());
				}
			}
		}
		
		//Parcours la Map contenant le nombre d'articles demandés, cherche le produit associé, parcours les Lots 
		// et déduis le nombre d'article présent dans chaque lot
		for (Entry<Integer, Double> entry : quantities.entrySet()) {
			Product prod = productManager.findProduct(entry.getKey());
			for (Lot lot : prod.getLots()) {
				quantities.put(prod.getProductId(), entry.getValue() - lot.getQuantity());
			}
		}
		
		// crée la map utilisée pour l'affichage
		for(Entry<Integer, Double> entry : quantities.entrySet()){
			Product prod = productManager.findProduct(entry.getKey());
			if(entry.getValue() <= 0){
				products.put(prod, -entry.getValue());
			}
		}
		System.out.println(products);
		return products;
	}
}
