package fr.mgs.web.customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import fr.mgs.business.OrderManager;
import fr.mgs.business.ProductManager;
import fr.mgs.business.UserManager;
import fr.mgs.connection.DataSource;
import fr.mgs.model.order.Order;
import fr.mgs.model.order.OrderLine;
import fr.mgs.model.order.OrderStatus;
import fr.mgs.model.product.Category;
import fr.mgs.model.product.Product;
import fr.mgs.model.product.SubCategory;

/**
 * this class is the product's controller for the customer
 * 
 * @author Mana, Ismael
 *
 */

@ManagedBean(name = "cstProducts")
@SessionScoped
public class OrderController {

	private ProductManager productManager;
	private OrderManager orderManager;
	private UserManager userManager;

	private String userId;
	private Order currentOrder;
	private Map<String, List<OrderItem>> orderItems;
	private Map<Integer, OrderItem> cart;

	@PostConstruct
	public void init() {
		productManager = new ProductManager();
		try {
			productManager.init(DataSource.LOCAL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		orderManager = new OrderManager();
		try {
			orderManager.init(DataSource.LOCAL);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		userManager = new UserManager();
		try {
			userManager.init(DataSource.LOCAL);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		userId = user.getUsername();

		try {
			currentOrder = newOrder();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		orderItems = new HashMap<String, List<OrderItem>>();
		cart = new HashMap<Integer, OrderItem>();
	}

	// Cart methods

	public Map<Integer, OrderItem> getCart() {
		return cart;
	}

	public void setCart(Map<Integer, OrderItem> cart) {
		this.cart = cart;
	}

	public void updateCart(OrderItem item) throws SQLException {
		if (item.getQuantity() == 0) {
			cart.remove(item.getProductId());
		} else {
			cart.put(item.getProductId(), item);
		}
	}

	// Store methods

	public Collection<Category> getAllCategories() {

		Collection<Category> categories = new ArrayList<Category>();

		categories.add(Category.PAPER);
		categories.add(Category.PLASTIC);
		categories.add(Category.CULTURE_PLASTIC);

		return categories;
	}

	public List<SubCategory> getSubCategories(Category cat) {
		return (List<SubCategory>) productManager.findSubCategoriesByCategory(cat);
	}

	/**
	 * Get all the products for the sub category
	 * 
	 * @param sub
	 *            the sub category
	 * @return the list of product
	 */
	public List<OrderItem> getOrderItems(SubCategory sub) {

		if (!orderItems.containsKey(sub.getName())) {
			List<Product> prods = (List<Product>) productManager.findProductsBySubCategory(sub);
			List<OrderItem> items = new ArrayList<OrderItem>();

			for (Product prod : prods) {
				OrderItem orderItem = new OrderItem();
				orderItem.setOrderItem(prod.getProductId(), prod.getDesignation(), prod.getPicture(), 0, sub.getName());
				items.add(orderItem);
			}
			orderItems.put(sub.getName(), items);
		} else {
			List<OrderItem> items = orderItems.get(sub.getName());
			for (OrderItem item : items) {
				if (cart.containsKey(item.getProductId())) {
					item.setQuantity(cart.get(item.getProductId()).getQuantity());
				}
			}
		}
		return orderItems.get(sub.getName());
	}

	// Order methods

	public Order newOrder() throws SQLException {

		if (currentOrder != null) {
			if (currentOrder.getStatus() == OrderStatus.NOT_VALIDATED) {
				return currentOrder;
			} else {
				resetCurrentOrder();
			}
		} else if (orderManager.hasNotValidatedOrder(userId)) {
			List<Order> orderList = (List<Order>) orderManager.findNotValidatedOrder(userId);
			currentOrder = orderList.get(0);
		} else {
			resetCurrentOrder();
		}
		return currentOrder;
	}

	public void saveOrder() throws SQLException {

	//	currentOrder.getOrderLines().clear();
		for (OrderItem item : cart.values()) {
			OrderLine orderLine = new OrderLine();
			orderLine.setOrderLine(currentOrder, productManager.findProduct(item.getProductId()), item.getQuantity(),
					0);
			currentOrder.addOrderLine(orderLine);
		}
		orderManager.updateOrder(currentOrder);
	}

	public String submitOrder() throws SQLException {
		if (cart.size() > 0) {
			currentOrder.setStatus(OrderStatus.VALIDATED);
			currentOrder.setSubmissionDate(new Date());
			saveOrder();
			resetCurrentOrder();
			return "pretty:cstHistory";
		}

		return "pretty:cstProducts";
	}

	public void deleteOrder() throws SQLException {
		cart.clear();
		orderItems.clear();
		orderManager.removeOrder(currentOrder.getOrderId());
	}

	private void resetCurrentOrder() throws SQLException {
		currentOrder = new Order();
		currentOrder.setOrderUser(userManager.findUser(userId));
		currentOrder.setStatus(OrderStatus.NOT_VALIDATED);
		orderManager.addOrder(currentOrder);
		cart.clear();
		orderItems.clear();
	}

	public Order getCurrentOrder() {
		return currentOrder;
	}

	public void setCurrentOrder(Order currentOrder) {
		this.currentOrder = currentOrder;
	}

}