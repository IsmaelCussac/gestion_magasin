package fr.mgs.web.storekeeper;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.primefaces.event.CellEditEvent;

import fr.mgs.business.OrderManager;
import fr.mgs.business.ProductManager;
import fr.mgs.business.UserManager;
import fr.mgs.connection.DataSource;
import fr.mgs.dao.OrderDAO;
import fr.mgs.model.order.Order;
import fr.mgs.model.order.OrderLine;
import fr.mgs.model.order.OrderStatus;
import fr.mgs.model.product.Product;
import fr.mgs.model.user.Team;

/**
 * Manage storekeeper oders views
 * 
 * @author Ibrahima
 */
@ManagedBean(name = "ordersView", eager = false)
@SessionScoped
public class OrdersView implements Serializable {
	private static final long serialVersionUID = -5914169092116908790L;

	private Map<Team, Collection<Order>> ordersToDeliverByTeam;
	private Map<Team, Collection<Order>> deliveredOrdersByTeam;
	private List<OrderLine> deliveredProducts;
	private Collection<OrderLine> selectedTeamOrderLines;

	private String maValeur;

	private OrderManager orderManager;
	private UserManager userManager;
	private OrderDAO orderDao;

	private ProductManager prodManager;

	private Team selectedTeam;
	private Order orderToEdit;

	// Just a test ==> to be removed
	private String scannedQte = "10";

	@PostConstruct
	public void init() {
		try {
			orderManager = new OrderManager();
			orderManager.init(DataSource.LOCAL);
			userManager = new UserManager();
			userManager.init(DataSource.LOCAL);
			prodManager = new ProductManager();
			prodManager.init(DataSource.LOCAL);
			selectedTeam = new Team();
			orderToEdit = new Order();
			orderDao = new OrderDAO(orderManager.getOrderDao().getConnection());
			selectedTeamOrderLines = new ArrayList<OrderLine>();
			// test scan
			OrderLine oltestscan = new OrderLine();
			Product p = new Product();
			p.setProductId(2554363);
			p.setDesignation("chargeur");
			oltestscan.setQuantity(5);
			oltestscan.setProduct(p);
			selectedTeamOrderLines.add(oltestscan);
			// fin test scan
			deliveredOrdersByTeam = new HashMap<Team, Collection<Order>>();
			deliveredProducts = new ArrayList<OrderLine>();

			for (Team team : userManager.findAllTeams()) {
				if (!team.getUsers().isEmpty()) {
					Collection<Order> teamDeliveredOrders = new ArrayList<Order>();
					// for looking out of stock orders we display only delivered
					// orders
					for (Order order : orderDao.findOrderByTeam(team)) {
						if (order.getStatus().toString().equals(OrderStatus.DELIVERED.toString())) {
							teamDeliveredOrders.add(order);
						}
					}
					if (!teamDeliveredOrders.isEmpty()) {
						deliveredOrdersByTeam.put(team, teamDeliveredOrders);

					}

				}
			}

			ordersToDeliverByTeam = new HashMap<Team, Collection<Order>>();

			for (Team team : userManager.findAllTeams()) {
				if (!team.getUsers().isEmpty()) {
					Collection<Order> teamOrdersToDeliver = new ArrayList<Order>();
					for (Order order : orderDao.findOrderByTeam(team)) {
						if (order.getStatus().toString().equals(OrderStatus.VALIDATED.toString())
								|| order.getStatus().toString().equals(OrderStatus.SHORTAGE.toString())) {
							teamOrdersToDeliver.add(order);
						}
					}
					if (!teamOrdersToDeliver.isEmpty()) {
						ordersToDeliverByTeam.put(team, teamOrdersToDeliver);

					}
				}
			}

		}

		catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	// test scan
	public void test() {
		System.out.println("TEST" + maValeur);
		for (OrderLine orderLine : selectedTeamOrderLines) {
			if (maValeur.equalsIgnoreCase(String.valueOf(orderLine.getProduct().getProductId()))) {
				System.out.println("Produit correspondant est :" + orderLine.getProduct().getDesignation());
				orderLine.setDeliveredQuantity(orderLine.getDeliveredQuantity() + 1);
				System.out.println(orderLine.getDeliveredQuantity());
			}
		}
	}

	/**
	 * save delivred orders
	 */
	public void saveDeliveredProducts() {

		for (OrderLine orderLine : deliveredProducts) {
			try {
				if (orderLine.getDeliveredQuantity() == orderLine.getQuantity()) {
					Order o = orderLine.getOrder();
					o.setStatus(OrderStatus.DELIVERED);
					o.setDeliveryDate(new Date());
					orderManager.updateOrderLine(orderLine);
					orderManager.updateOrder(o);

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Livraison effectuée", "");
		FacesContext.getCurrentInstance().addMessage(null, msg);

	}

	// manual edition
	public void onCellEdit(CellEditEvent event) {

		Map<String, Object> atts = event.getColumn().getCellEditor().getAttributes();
		OrderLine ol = (OrderLine) atts.get("editedOl");
		Double oldValue = (Double) event.getOldValue();
		Double newValue = (Double) event.getNewValue();

		if (newValue > ol.getQuantity()) {
			ol.setDeliveredQuantity(oldValue);
			newValue = oldValue;
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL,
					"La quantité saisie doit être inférieure à la quantité demandée", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else {
			ol.setDeliveredQuantity(newValue);
			deliveredProducts.add(ol);
		}

	}

	/**
	 * getters and setters
	 * 
	 * @throws SQLException
	 * 
	 */

	public Map<Team, Collection<Order>> getOrdersToDeliverByTeam() throws SQLException {
		return ordersToDeliverByTeam;
	}

	public void setOrdersToDeliverByTeam(Map<Team, Collection<Order>> ordersByTeam) {
		this.ordersToDeliverByTeam = ordersByTeam;
	}

	public void setSelectedTeam(Team selectedTeam) {
		this.selectedTeam = selectedTeam;
	}

	public Team getSelectedTeam() {
		return selectedTeam;
	}

	public Order getOrderToEdit() {
		return this.orderToEdit;
	}

	public void setOrderToEdit(Order order) {
		this.orderToEdit = order;
	}

	public String getScannedQte() {
		return scannedQte;
	}

	public void setScannedQte(String scannedQte) {
		this.scannedQte = scannedQte;
	}

	public Map<Team, Collection<Order>> getDeliveredOrdersByTeam() throws SQLException {
		return deliveredOrdersByTeam;
	}

	public void setDeliveredOrdersByTeam(Map<Team, Collection<Order>> deliveredOrdersByTeam) {
		this.deliveredOrdersByTeam = deliveredOrdersByTeam;
	}

	public String getMaValeur() {
		return maValeur;
	}

	public void setMaValeur(String maValeur) {
		this.maValeur = maValeur;
	}

	public List<OrderLine> getDeliveredProducts() {
		return deliveredProducts;
	}

	public void setDeliveredProducts(List<OrderLine> deliveredProducts) {
		this.deliveredProducts = deliveredProducts;
	}

	public Collection<OrderLine> getSelectedTeamOrderLines() {
		return selectedTeamOrderLines;
	}

	public void setSelectedTeamOrderLines(Collection<OrderLine> teamsOrderLines) {
		this.selectedTeamOrderLines = teamsOrderLines;
	}
}