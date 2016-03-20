package fr.mgs.web.storekeeper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import fr.mgs.business.OrderManager;
import fr.mgs.business.UserManager;
import fr.mgs.connection.DataSource;
import fr.mgs.model.order.Order;
import fr.mgs.model.order.OrderStatus;
import fr.mgs.model.user.Team;
import fr.mgs.toolbox.SortMap;

/**
 * 
 * @author Ibrahima
 *
 */
@ManagedBean(name = "outOrders")
@SessionScoped
public class StockOutController {
	private Map<Team, Collection<Order>> deliveredOrdersByTeam;
	private OrderManager orderManager;
	private UserManager userManager;
	private SortMap sortMap;

	@PostConstruct
	private void init() {
		orderManager = new OrderManager();
		orderManager.init(DataSource.LOCAL);
		userManager = new UserManager();
		userManager.init(DataSource.LOCAL);
		sortMap = new SortMap();

	}

	public Map<Team, Collection<Order>> getDeliveredOrdersByTeam() throws SQLException {
		this.deliveredOrdersByTeam = new HashMap<Team, Collection<Order>>();

		for (Team team : userManager.findAllTeams()) {
			if (!team.getUsers().isEmpty()) {
				createTeamDeliveryOrders(team);

			}
		}
		sortMap.getTreeMap().putAll(deliveredOrdersByTeam);
		return sortMap.getTreeMap();
	}

	private void createTeamDeliveryOrders(Team team) {
		Collection<Order> teamDeliveredOrders = new ArrayList<Order>();
		// for looking out of stock orders we display only delivered
		// orders
		for (Order order : orderManager.findOrderByTeam(team)) {
			if (order.getStatus().name() == OrderStatus.DELIVERED.name()) {
				teamDeliveredOrders.add(order);
			}
		}
		if (!teamDeliveredOrders.isEmpty()) {
			this.deliveredOrdersByTeam.put(team, teamDeliveredOrders);

		}
	}
}
