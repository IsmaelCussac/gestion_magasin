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
import fr.mgs.model.user.User;

/**
 * Manage storekeeper oders views
 * 
 * @author Ibrahima
 *
 */
@ManagedBean(name="ordersView")
@ViewScoped
public class OrdersView {

	private Map<Team, List<User>> usersByTeam = new HashMap<>();;
	private Map<User, List<Order>> ordersByUser = new HashMap<>();

	private Order selectedOrder;
	private OrderManager orderManager;
	private UserManager userManager;

	private boolean checkBox = false;

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		try {
			orderManager = new OrderManager();
			orderManager.init(DataSource.LOCAL);
			userManager = new UserManager();
			userManager.init(DataSource.LOCAL);

			for (Team team : userManager.findAllTeams()) {
				if (!team.getUsers().isEmpty()) {
					usersByTeam.put(team, (List<User>) team.getUsers());
				}

			}

			for (User user : userManager.findAllUsers()) {
				if (!user.getOrders().isEmpty()) {
					ordersByUser.put(user, (List<Order>) user.getOrders());
				}
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public void cbListener(Team t) {
		System.out.println("Equipe selectionnée = " + t.getName());
	}

	public Order getSelectedOrder() {

		return selectedOrder;
	}

	public void setSelectedOrder(Order selectedOrder) {
		System.out.println(selectedOrder.getOrderId());
		this.selectedOrder = selectedOrder;
	}

	public boolean isCheckBox() {
		return checkBox;
	}

	public void setCheckBox(boolean checkBox) {
		this.checkBox = checkBox;
	}

}