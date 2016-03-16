package fr.mgs.web.storekeeper;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.CellEditEvent;

import fr.mgs.business.OrderManager;
import fr.mgs.business.ProductManager;
import fr.mgs.business.UserManager;
import fr.mgs.connection.DataSource;
import fr.mgs.model.order.Order;
import fr.mgs.model.order.OrderLine;
import fr.mgs.model.order.OrderStatus;
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

	private String initScan;

	private OrderManager orderManager;
	private UserManager userManager;

	private ProductManager prodManager;

	private Team selectedTeam;
	private Order orderToEdit;

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
			selectedTeamOrderLines = new ArrayList<OrderLine>();

			deliveredOrdersByTeam = new HashMap<Team, Collection<Order>>();
			deliveredProducts = new ArrayList<OrderLine>();

			for (Team team : userManager.findAllTeams()) {
				if (!team.getUsers().isEmpty()) {
					Collection<Order> teamDeliveredOrders = new ArrayList<Order>();
					// for looking out of stock orders we display only delivered
					// orders
					for (Order order : orderManager.findOrderByTeam(team)) {
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
					for (Order order : orderManager.findOrderByTeam(team)) {
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

	public void scan() {
		System.out.println("TEST" + initScan);
		for (OrderLine orderLine : selectedTeamOrderLines) {
			if (initScan.equalsIgnoreCase(String.valueOf(orderLine.getProduct().getProductId()))) {
				System.out.println("Produit correspondant est :" + orderLine.getProduct().getDesignation());
				if (deliveredProducts.contains(orderLine)) {
					deliveredProducts.remove(orderLine);
				}
				orderLine.setDeliveredQuantity(orderLine.getDeliveredQuantity() + 1);
				deliveredProducts.add(orderLine);
				System.out.println(orderLine.getDeliveredQuantity());
			}
		}
		initScan = "";
	}

	/**
	 * save delivred orders
	 */
	public void saveDeliveredProducts() {

		Set<Integer> checkedOrders = new HashSet<Integer>();
		for (OrderLine orderLine : deliveredProducts) {
			try {
				orderManager.updateOrderLine(orderLine);
				Order o = orderLine.getOrder();

				if (checkedOrders.contains(o) && o.getStatus() == OrderStatus.SHORTAGE) {
					break;
				}

				if (checkedOrders.contains(o.getOrderId()) && o.getStatus() == OrderStatus.DELIVERED) {
					if (orderLine.getDeliveredQuantity() < orderLine.getQuantity()) {
						o.setStatus(OrderStatus.SHORTAGE);
					}
				}

				if (!checkedOrders.contains(o.getOrderId())) {
					if (orderLine.getDeliveredQuantity() < orderLine.getQuantity()) {
						o.setStatus(OrderStatus.SHORTAGE);
						checkedOrders.add(Integer.valueOf(o.getOrderId()));
					}
				}

				if (!checkedOrders.contains(o.getOrderId())) {
					if (orderLine.getDeliveredQuantity() == orderLine.getQuantity()) {
						o.setStatus(OrderStatus.DELIVERED);
						checkedOrders.add(Integer.valueOf(o.getOrderId()));
						// this.getOrdersToDeliverByTeam().get(selectedTeam).remove(o);
						this.getOrdersToDeliverByTeam().remove(selectedTeam);

					}
				}
				o.setDeliveryDate(new Date());
				orderManager.updateOrder(o);

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

	public Map<Team, Collection<Order>> getDeliveredOrdersByTeam() throws SQLException {
		return deliveredOrdersByTeam;
	}

	public void setDeliveredOrdersByTeam(Map<Team, Collection<Order>> deliveredOrdersByTeam) {
		this.deliveredOrdersByTeam = deliveredOrdersByTeam;
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

	public String getInitScan() {
		return initScan;
	}

	public void setInitScan(String val) {
		this.initScan = val;
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