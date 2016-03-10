package fr.mgs.web.storekeeper;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.sun.xml.internal.ws.wsdl.writer.document.Port;

import fr.mgs.business.OrderManager;
import fr.mgs.business.ProductManager;
import fr.mgs.business.UserManager;
import fr.mgs.connection.DataSource;
import fr.mgs.dao.OrderDAO;
import fr.mgs.dao.ProductDAO;
import fr.mgs.model.order.Order;
import fr.mgs.model.order.OrderLine;
import fr.mgs.model.product.Product;
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
public class OrdersView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5914169092116908790L;

	private Map<Team, Collection<Order>> ordersByTeam = new HashMap<Team, Collection<Order>>();

	private Team selectedTeam;
	private OrderManager orderManager;
	private UserManager userManager;
	private OrderDAO orderDao;

	private boolean checkBox = false;
	private int oneTeamSelected = 0;
	private ProductManager prodManager;

	@SuppressWarnings("unchecked")
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

	public void addMessage(Team t) {
		System.out.println("chechek: " + checkBox);
		if (checkBox) {
			selectedTeam = t;
			oneTeamSelected++;
		} else {
			selectedTeam = null;
			--oneTeamSelected;
		}

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_FATAL, "", "Sélectionnez une seule équipe"));

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

	public Team getSelectedTeam() {
		return selectedTeam;
	}

	public void setSelectedTeam(Team selectedTeam) {
		this.selectedTeam = selectedTeam;
	}

	public int getOneTeamSelected() {
		return oneTeamSelected;
	}

	public void setOneTeamSelected(int oneTeamSelected) {
		this.oneTeamSelected = oneTeamSelected;
	}

}