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
 * Controller used to display order history and duplicate order
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
	private Order newOrder;
	private Order dupOrder;

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

		orderManager.init(DataSource.LOCAL);
		userManager.init(DataSource.LOCAL);

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		userId = user.getUsername();

	}

	public List<Order> getOrders() throws SQLException {
		orders = (List<Order>) orderManager.findAllOrdersByPerson(userId);
		return orders;
	}

	/**
	 * Duplicate an order and redirect to order page
	 * 
	 * @param order
	 * @return
	 * @throws SQLException
	 */
	public String duplicateOrder(Order order) throws SQLException {

		newOrder = new Order();
		if (orderManager.hasNotValidatedOrder(userId)) {
			// on récupère la commande non validée et on supprime les orderline
			List<Order> orderList = (List<Order>) orderManager.findNotValidatedOrder(userId);
			dupOrder = orderList.get(0);
		}
		// orderController.clearStoreItems();
		updateNewOrder(order);
		updateCart();

		return "pretty:cstOrder";
	}

	/**
	 * Copy the duplicated order and insert it in DB
	 * 
	 * @param order
	 * @throws SQLException
	 */
	public void updateNewOrder(Order order) throws SQLException {
		newOrder.setOrderId(dupOrder.getOrderId());
		newOrder.setOrderUser(userManager.findPerson(userId));
		newOrder.setStatus(OrderStatus.NOT_VALIDATED);

		Collection<OrderLine> orderLines = order.getOrderLines();
		for (OrderLine line : orderLines) {
			OrderLine newLine = new OrderLine();
			if (line.getProduct().isVisible()) {
				newLine.setOrderLine(newOrder, line.getProduct(), line.getQuantity(), 0);
				newOrder.addOrderLine(newLine);
			}
		}

		orderManager.updateOrder(newOrder);
	}

	/**
	 * Update the cart using the duplicated order data
	 */
	public void updateCart() {
		orderController.getCart().clear();
		for (OrderLine l : newOrder.getOrderLines()) {
			StoreItem i = new StoreItem();
			i.setStoreItem(l.getProduct().getProductId(), l.getProduct().getDesignation(), l.getQuantity(),
					l.getProduct().getSubCategory().getName());
			orderController.getCart().put(l.getProduct().getProductId(), i);
		}
	}
}