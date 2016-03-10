package fr.mgs.web.storekeeper;

import java.io.Serializable;
import java.sql.SQLException;
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

	private String selectedTeamName;

	@PostConstruct
	public void init() {
		try {
			orderManager = new OrderManager();
			orderManager.init(DataSource.LOCAL);
			userManager = new UserManager();
			userManager.init(DataSource.LOCAL);
			prodManager = new ProductManager();
			prodManager.init(DataSource.LOCAL);

			orderDao = new OrderDAO(orderManager.getOrderDao().getConnection());

			for (Team team : userManager.findAllTeams()) {
				if (team.getUsers() != null && !team.getUsers().isEmpty()) {
					ordersByTeam.put(team, orderDao.findOrderByTeam(team));
				}
			}
		}

		catch (SQLException ex) {
			ex.printStackTrace();
		}

	}

	public Map<Team, Collection<Order>> getOrdersByTeam() {
		return ordersByTeam;
	}

	public void setOrdersByTeam(Map<Team, Collection<Order>> ordersByTeam) {
		this.ordersByTeam = ordersByTeam;
	}

	public void setSelectedTeamName(String selectedTeamName) {
		this.selectedTeamName = selectedTeamName;
	}

	public String getSelectedTeamName() {
		return selectedTeamName;
	}

}