package fr.mgs.web.order;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import fr.mgs.business.OrderManager;
import fr.mgs.business.UserManager;
import fr.mgs.connection.DataSource;
import fr.mgs.dao.OrderDAO;
import fr.mgs.model.order.Order;
import fr.mgs.model.user.Person;
import fr.mgs.model.user.Team;

/**
 * Manage storekeeper oders views
 * 
 * @author Ibrahima
 *
 */

@ManagedBean(name = "ordersView")
@ViewScoped
public class OrdersView {

	private Map<Team, Collection<Order>> ordersByTeam = new HashMap<>();
	private Map<Team, List<Person>> usersByTeam = new HashMap<>();;
	private Map<Person, List<Order>> ordersByUser = new HashMap<>();

	private Order selectedOrder;
	private OrderManager orderManager;
	private UserManager userManager;
	private OrderDAO orderDao;

	private boolean checkBox = false;

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		try {
			orderManager = new OrderManager();
			orderManager.init(DataSource.LOCAL);
			userManager = new UserManager();
			userManager.init(DataSource.LOCAL);
			orderDao = new OrderDAO(orderManager.getOrderDao().getConnection());

			for (Person user : userManager.findAllUsers()) {
				if (!user.getOrders().isEmpty()) {
					ordersByUser.put(user, (List<Order>) user.getOrders());
				}
			}
		}

		catch (SQLException ex) {
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

	public Map<Team, Collection<Order>> getOrdersByTeam() {
		return ordersByTeam;
	}

	public void setOrdersByTeam(Map<Team, Collection<Order>> ordersByTeam) {
		this.ordersByTeam = ordersByTeam;
	}

}