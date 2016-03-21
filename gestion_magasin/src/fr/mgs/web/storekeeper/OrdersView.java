package fr.mgs.web.storekeeper;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.hibernate.cache.internal.OldNaturalIdCacheKey;
import org.primefaces.event.CellEditEvent;

import fr.mgs.business.OrderManager;
import fr.mgs.business.ProductManager;
import fr.mgs.business.UserManager;
import fr.mgs.connection.DataSource;
import fr.mgs.model.order.Order;
import fr.mgs.model.order.OrderLine;
import fr.mgs.model.order.OrderStatus;
import fr.mgs.model.user.Team;
import fr.mgs.toolbox.SortMap;

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
	private List<OrderLine> deliveredProducts;
	private Collection<OrderLine> selectedTeamOrderLines;
	private Map<Integer, Boolean> checkedOrders;

	private String initScan;

	private OrderManager orderManager;
	private UserManager userManager;

	private ProductManager prodManager;

	private Team selectedTeam;
	private Order orderToEdit;
	private SortMap sortMap;

	@PostConstruct
	public void init() {

		orderManager = new OrderManager();
		orderManager.init(DataSource.LOCAL);
		userManager = new UserManager();
		userManager.init(DataSource.LOCAL);
		prodManager = new ProductManager();
		prodManager.init(DataSource.LOCAL);
		selectedTeam = new Team();
		orderToEdit = new Order();
		selectedTeamOrderLines = new ArrayList<OrderLine>();
		deliveredProducts = new ArrayList<OrderLine>();
		checkedOrders = new HashMap<Integer, Boolean>();
		sortMap = new SortMap();

	}

	/**
	 * save scanned values
	 */

	public void scan() {
		for (OrderLine orderLine : selectedTeamOrderLines) {
			if (initScan.equalsIgnoreCase(String.valueOf(orderLine.getProduct().getProductId()))) {

				if (deliveredProducts.contains(orderLine)) {
					deliveredProducts.remove(orderLine);
				}
				double newDelivredQt = orderLine.getDeliveredQuantity() + 1;
				if (newDelivredQt > orderLine.getQuantity()) {
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"La quantité saisie doit être inférieure à la quantité demandée", "");
					FacesContext.getCurrentInstance().addMessage(null, msg);
				} else {
					orderLine.setDeliveredQuantity(newDelivredQt);
				}
				deliveredProducts.add(orderLine);

			}
		}
		initScan = "";
	}

	/**
	 * reset scanned values
	 */
	public void resetScan() {

		for (OrderLine orderLine : selectedTeamOrderLines) {
			orderLine.setDeliveredQuantity(0);
		}
	}

	/**
	 * save delivered orders
	 * 
	 * @throws SQLException
	 */
	public void saveDeliveredProducts() throws SQLException {
		for (OrderLine orderLine : deliveredProducts) {
			try {
				orderManager.updateOrderLine(orderLine);
				Order o = orderLine.getOrder();
				updateStatus(orderLine, o);

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		endDelivery();

	}

	public void endDelivery() throws SQLException {
		for (Iterator<Integer> i = checkedOrders.keySet().iterator(); i.hasNext();) {
			Integer key = (Integer) i.next();
			Order o = orderManager.findOrder(key);
			if (checkedOrders.get(key)) {
				o.setStatus(OrderStatus.SHORTAGE);

			} else {
				o.setStatus(OrderStatus.DELIVERED);
			}
			o.setDeliveryDate(new Date());
			orderManager.updateOrder(o);
		}

		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Livraison effectuée", "");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void updateStatus(OrderLine ol, Order o) throws SQLException {

		if (checkedOrders.get(o.getOrderId()) == null) {
			if (ol.getDeliveredQuantity() < ol.getQuantity())
				// in case of shortage
				checkedOrders.put(o.getOrderId(), true);
			else
				checkedOrders.put(o.getOrderId(), false);

		} else if (ol.getDeliveredQuantity() < ol.getQuantity()) {

			checkedOrders.put(o.getOrderId(), true);

		} else {
			checkedOrders.put(o.getOrderId(), false);
		}
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

		sortMap.getTreeMap().putAll(ordersToDeliverByTeam);
		return sortMap.getTreeMap();
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