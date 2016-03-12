package fr.mgs.web.storekeeper;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import fr.mgs.business.OrderManager;
import fr.mgs.business.ProductManager;
import fr.mgs.business.UserManager;
import fr.mgs.connection.DataSource;
import fr.mgs.dao.OrderDAO;
import fr.mgs.model.order.Order;
import fr.mgs.model.order.OrderLine;
import fr.mgs.model.order.OrderStatus;
import fr.mgs.model.user.Person;
import fr.mgs.model.user.Privilege;
import fr.mgs.model.user.Team;

/**
 * Manage storekeeper oders views
 * 
 * @author Ibrahima
 *
 */
@ManagedBean(name = "ordersView", eager = false)
@SessionScoped
public class OrdersView implements Serializable {
	private static final long serialVersionUID = -5914169092116908790L;

	private Map<Team, Collection<Order>> ordersToDeliverByTeam;
	private Map<Team, Collection<Order>> deliveredOrdersByTeam;

	private OrderManager orderManager;
	private UserManager userManager;
	private OrderDAO orderDao;

	private ProductManager prodManager;

	private Team selectedTeam;
	private Order orderToEdit;

	// Just a test ==> to be removed
	private String scannedQte;

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
			ordersToDeliverByTeam = new HashMap<Team, Collection<Order>>();
			deliveredOrdersByTeam = new HashMap<Team, Collection<Order>>();

			for (Team team : userManager.findAllTeams()) {
				if (!team.getUsers().isEmpty()) {
					Collection<Order> teamOrdersToDeliver = new ArrayList<>();
					for (Order order : orderDao.findOrderByTeam(team)) {
						if (order.getStatus().toString().equals(OrderStatus.VALIDATED.toString())
								|| order.getStatus().toString().equals(OrderStatus.SHORTAGE.toString())) {
							teamOrdersToDeliver.add(order);
						}
					}
					ordersToDeliverByTeam.put(team, teamOrdersToDeliver);
				}
			}

			for (Team team : userManager.findAllTeams()) {
				if (!team.getUsers().isEmpty()) {
					Collection<Order> teamDeliveredOrders = new ArrayList<>();
					// for looking out of stock orders we display only delivered
					// orders
					for (Order order : orderDao.findOrderByTeam(team)) {
						if (order.getStatus().toString().equals(OrderStatus.DELIVERED.toString())) {
							teamDeliveredOrders.add(order);
						}
					}
					deliveredOrdersByTeam.put(team, teamDeliveredOrders);
				}
			}

		}

		catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * return all order lines of the selected team for delivery
	 */
	public Collection<OrderLine> getSelectedTeamOls() {
		Collection<OrderLine> teamsOrderLines = new ArrayList<OrderLine>();

		for (Order ord : this.ordersToDeliverByTeam.get(this.selectedTeam)) {
			for (OrderLine ol : ord.getOrderLines()) {
				teamsOrderLines.add(ol);
			}
		}
		return teamsOrderLines;
	}

	/**
	 * getters and setters
	 * 
	 */

	public Map<Team, Collection<Order>> getOrdersToDeliverByTeam() {
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

	public Map<Team, Collection<Order>> getDeliveredOrdersByTeam() {
		return deliveredOrdersByTeam;
	}

	public void setDeliveredOrdersByTeam(Map<Team, Collection<Order>> deliveredOrdersByTeam) {
		this.deliveredOrdersByTeam = deliveredOrdersByTeam;
	}
}