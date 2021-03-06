package fr.mgs.web.storekeeper;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.CellEditEvent;

import fr.mgs.business.OrderManager;
import fr.mgs.business.ProductManager;
import fr.mgs.business.UserManager;
import fr.mgs.connection.DataSource;
import fr.mgs.model.order.Order;
import fr.mgs.model.order.OrderLine;
import fr.mgs.model.order.OrderStatus;
import fr.mgs.model.product.Lot;
import fr.mgs.model.product.Product;
import fr.mgs.model.user.Team;
import fr.mgs.toolbox.SortMap;

/**
 * Manage storekeeper oders views
 * 
 * @author Ibrahima
 */
@ManagedBean(name = "ordersView")
@SessionScoped
public class OrderController implements Serializable {
	private static final long serialVersionUID = -5914169092116908790L;

	private List<OrderLine> deliveredProducts;
	private Collection<OrderLine> selectedTeamOrderLines;
	private Map<Integer, Boolean> checkedOrders;

	private String initScan;

	private OrderManager orderManager;
	private UserManager userManager;

	private ProductManager prodManager;

	private Team selectedTeam;
	private Order orderToEdit;
	private List<Lot> ordersLots;
	private Lot selectedLot;

	private String console;

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
		selectedLot = new Lot();
		ordersLots = new ArrayList<Lot>();

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
				if (newDelivredQt > orderLine.getQuantity() || newDelivredQt < 0) {
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Quanité incorrecte ou supérieur à celle demandée", "");
					FacesContext.getCurrentInstance().addMessage(null, msg);
				} else {
					if (orderLine.getDeliveredQuantity() < orderLine.getQuantity()) {
						orderLine.setDeliveredQuantity(newDelivredQt);
					}

				}
				deliveredProducts.add(orderLine);
				break;

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
	 * @throws ParseException
	 */
	public String saveDeliveredProducts() throws SQLException, ParseException {
		for (OrderLine orderLine : deliveredProducts) {
			try {
				orderManager.updateOrderLine(orderLine);
				Order o = orderLine.getOrder();
				updateStatus(orderLine, o);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date dateBase = sdf.parse("2050-12-31");
				Lot minLot = new Lot();
				minLot.setExpirationDate(dateBase);

				for (Lot lot : orderLine.getProduct().getLots()) {
					if (lot.getExpirationDate().before(minLot.getExpirationDate())) {
						minLot = lot;
					}
				}
				minLot.setQuantity(minLot.getQuantity() - orderLine.getDeliveredQuantity());
				prodManager.updateLot(minLot);

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		endDelivery();

		return "pretty:skOrders";

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

	// public void updateLots() throws SQLException {
	// for (OrderLine orderLine : deliveredProducts) {
	// for (Lot lot : ordersLots) {
	// if (orderLine.getProduct().getProductId() ==
	// lot.getLotProduct().getProductId()) {
	// lot.setQuantity(lot.getQuantity() - orderLine.getDeliveredQuantity());
	// prodManager.updateLot(lot);
	// }
	//
	// }
	// }
	// }

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
		Map<Team, Collection<Order>> ordersToDeliverByTeam = new HashMap<Team, Collection<Order>>();
		SortMap sortMap = new SortMap();
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

	public void onLotChanges() throws SQLException {
		Lot lot = prodManager.findLot(selectedLot.getLotId());
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Lot ajouté", "");
		FacesContext.getCurrentInstance().addMessage(null, msg);

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

	public Lot getSelectedLot() {
		return selectedLot;
	}

	public void setSelectedLot(Lot selectedLot) {
		this.selectedLot = selectedLot;
	}

	public List<Lot> getOrdersLots() {
		return ordersLots;
	}

	public void setOrdersLots(List<Lot> ordersLots) {
		this.ordersLots = ordersLots;
	}

	public String getConsole() {
		return console;
	}

	public void setConsole(String console) {
		this.console = console;
	}
}