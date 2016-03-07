package fr.mgs.web.order;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import fr.mgs.business.OrderManager;
import fr.mgs.business.UserManager;
import fr.mgs.connection.DataSource;
import fr.mgs.model.order.Order;
import fr.mgs.model.user.Team;

/**
 * Manage storekeeper oders views
 * 
 * @author Ibrahima
 *
 */
@ManagedBean(name="ordersView")
@ViewScoped
public class OrdersView {

	private List<Order> orders;
	private List<Team> teams;

	private Order selectedOrder;
	private OrderManager orderManager;
	private UserManager userManager;

	private boolean checkBox = false;

	@PostConstruct
	public void init() {
		try {
			orderManager = new OrderManager();
			orderManager.init(DataSource.LOCAL);
			userManager = new UserManager();
			userManager.init(DataSource.LOCAL);
			teams = (List<Team>) userManager.findAllTeams();
			orders = (List<Order>) orderManager.findAllOrders();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public void cbListener() {
		System.out.println("Cocher = " + !checkBox);
	}

	public Order getSelectedOrder() {

		return selectedOrder;
	}

	public void setSelectedOrder(Order selectedOrder) {
		this.selectedOrder = selectedOrder;
	}

	public List<Team> getTeams() {
		return teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

	public boolean isCheckBox() {
		return checkBox;
	}

	public void setCheckBox(boolean checkBox) {
		this.checkBox = checkBox;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
}