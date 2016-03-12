package fr.mgs.web.customer;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import fr.mgs.business.OrderManager;
import fr.mgs.business.UserManager;
import fr.mgs.connection.DataSource;
import fr.mgs.model.order.Order;
import fr.mgs.model.order.OrderLine;
import fr.mgs.model.order.OrderStatus;

/**
 * 
 * @author Ismaël
 *
 */

@ManagedBean(name = "cstOrderHistory")
@SessionScoped
public class OrderHistoryController {

	private OrderManager orderManager;
	private UserManager userManager;
	private String userId;
	private List<Order> orders;

	@ManagedProperty(value = "#{cstProducts}")
	OrderController orderController;

	public OrderController getOrderController() {
		return orderController;
	}

	public void setOrderController(OrderController orderController) {
		this.orderController = orderController;
	}

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

	public String duplicateOrder(Order order) throws SQLException {
		Order newOrder = new Order();
		if (orderManager.hasNotValidatedOrder(userId)) {

			List<Order> orderList = (List<Order>) orderManager.findNotValidatedOrder(userId);
			Order dupOrder = orderList.get(0);

			for (OrderLine line : dupOrder.getOrderLines()) {
				orderManager.removeOrderLine(line.getOrderLinePK());
			}

			newOrder.setOrderId(dupOrder.getOrderId());
			newOrder.setOrderUser(userManager.findUser(userId));
			newOrder.setStatus(OrderStatus.NOT_VALIDATED);

			Collection<OrderLine> orderLines = order.getOrderLines();
			for (OrderLine line : orderLines) {
				OrderLine newLine = new OrderLine();
				newLine.setOrderLine(newOrder, line.getProduct(), line.getQuantity(), 0);
				newOrder.addOrderLine(newLine);
			}

			orderManager.updateOrder(newOrder);
			orderController.getCart().clear();
			for (OrderLine l : newOrder.getOrderLines()) {
				OrderItem i = new OrderItem();
				i.setOrderItem(l.getProduct().getProductId(), l.getProduct().getDesignation(), l.getProduct().getPicture(),
						l.getQuantity(), l.getProduct().getSubCategory().getName());
				orderController.getCart().put(l.getProduct().getProductId(), i);
			}

		} else {

			newOrder.setOrder(userManager.findUser(userId), null, null, order.getOrderLines(), null,
					OrderStatus.NOT_VALIDATED);
			orderManager.addOrder(newOrder);
		}

		return "pretty:cstOrder";
	}

}
