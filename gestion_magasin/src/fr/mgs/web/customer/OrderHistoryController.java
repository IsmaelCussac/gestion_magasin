package fr.mgs.web.customer;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import fr.mgs.business.OrderManager;
import fr.mgs.business.UserManager;
import fr.mgs.connection.DataSource;
import fr.mgs.model.order.Order;
import fr.mgs.model.order.OrderLine;
import fr.mgs.model.order.OrderStatus;

@ManagedBean(name = "cstOrderHistory")
@SessionScoped
public class OrderHistoryController {

	private OrderManager orderManager;
	private UserManager userManager;
	private String userId;
	private List<Order> orders;

	@PostConstruct
	public void init() {
		orderManager = new OrderManager();
		userManager = new UserManager();
		try {
			orderManager.init(DataSource.LOCAL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			userManager.init(DataSource.LOCAL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		userId = user.getUsername();

	}

	public List<Order> getOrders() throws SQLException {
		orders = orderManager.findAllOrdersByUser(userId);
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public String duplicateOrder(Order order) throws SQLException {
		
		if (orderManager.hasNotValidatedOrder(userId)) {
			List<Order> orderList = (List<Order>) orderManager.findNotValidatedOrder(userId);
			Order newOrder = orderList.get(0);
			for(OrderLine line : newOrder.getOrderLines()){
				orderManager.removeOrderLine(line.getOrderLinePK());
			}
			newOrder.getOrderLines().clear();
			newOrder.setOrder(userManager.findUser(userId), null, null, order.getOrderLines(), null, OrderStatus.NOT_VALIDATED);
			System.out.println("new order " + newOrder.toString());
			System.out.println("new order " + newOrder.getOrderLines().toString());
			orderManager.updateOrder(newOrder);

		} else {
			Order newOrder = new Order();
			newOrder.setOrder(userManager.findUser(userId), null, null, order.getOrderLines(), null, OrderStatus.NOT_VALIDATED);
			orderManager.addOrder(newOrder);
		}
		
		return "pretty:cstOrder";
	}

}
