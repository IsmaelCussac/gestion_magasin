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
import fr.mgs.model.product.Product;
import fr.mgs.model.user.Person;
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

	private Map<Team, Collection<Order>> ordersByTeam = new HashMap<Team, Collection<Order>>();

	private OrderManager orderManager;
	private UserManager userManager;
	private OrderDAO orderDao;

	private ProductManager prodManager;

	private Team selectedTeam;
	private Order orderToEdit;

	//Just a test ==> to be removed
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
			for (Team team : userManager.findAllTeams()) {
				if (team.getUsers() != null && !team.getUsers().isEmpty()) {
					ordersByTeam.put(team, orderDao.findOrderByTeam(team));
				}
			}

			// Order o = orderManager.findOrder(101);
			// Product p = prodManager.findProduct(14);
			//
			// OrderLine ol = new OrderLine();
			// ol.setQuantity(6);
			// ol.setProduct(p);
			//
			// Set<OrderLine> lines = new HashSet<>();
			// lines.add(ol);
			// // orderManager.addOrderLine(ol);
			// Person per = userManager.findUser("s14027278");
			// o.setOrderUser(per);
			// o.setOrderLines(lines);
			// o.setDeliveryDate(null);
			// o.setSubmissionDate(new Date());
			// o.setStatus(OrderStatus.VALIDATED);
			//
			// orderDao.update(o);

		}

		catch (SQLException ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * return all products of the selected team for delivery
	 * 
	 */
	public Collection<OrderLine> getSelectedTeamOls() {
		Collection<OrderLine> teamsOrderLines = new ArrayList<OrderLine>();

		for (Order ord : this.ordersByTeam.get(this.selectedTeam)) {
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

	public Map<Team, Collection<Order>> getOrdersByTeam() {
		return ordersByTeam;
	}

	public void setOrdersByTeam(Map<Team, Collection<Order>> ordersByTeam) {
		this.ordersByTeam = ordersByTeam;
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
}