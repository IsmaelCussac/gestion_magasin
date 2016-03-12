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
import fr.mgs.model.order.OrderLinePK;
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

		orderItems = new HashMap<String, List<OrderItem>>();
		cart = new HashMap<Integer, OrderItem>();

		try {
			newOrder();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
			orderManager.removeOrderLine(new OrderLinePK(item.getProductId(), currentOrder.getOrderId()));
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
	 * @throws SQLException
	 */
	public List<OrderItem> getOrderItems(SubCategory sub) {
		List<OrderItem> items;

		// si la sous catégorie n'est pas présente dans la map, on récupère la liste de produits et on l'ajoute
		if (!orderItems.containsKey(sub.getName())) {
			List<Product> prods = (List<Product>) productManager.findProductsBySubCategory(sub);
			items = new ArrayList<OrderItem>();

			for (Product prod : prods) {
				OrderItem orderItem = new OrderItem();
				double quantity = 0;
				if(cart.containsKey(prod.getProductId()))
					quantity = cart.get(prod.getProductId()).getQuantity();
				orderItem.setOrderItem(prod.getProductId(), prod.getDesignation(), prod.getPicture(), quantity, sub.getName());
				items.add(orderItem);
			}
		} else {
			items = orderItems.get(sub.getName());
			for (OrderItem item : items) {
				if (cart.containsKey(item.getProductId())) {
					item.setQuantity(cart.get(item.getProductId()).getQuantity());
				}
			}
		}

		
		orderItems.put(sub.getName(), items);
		return orderItems.get(sub.getName());
	}

	// Order methods

	public void newOrder() throws SQLException {

		if (orderManager.hasNotValidatedOrder(userId)) {
			List<Order> orderList = (List<Order>) orderManager.findNotValidatedOrder(userId);
			currentOrder = orderList.get(0);
			for (OrderLine orderLine : currentOrder.getOrderLines()) {
				OrderItem item = new OrderItem();
				item.setOrderItem(orderLine.getProduct().getProductId(), orderLine.getProduct().getDesignation(),
						orderLine.getProduct().getPicture(), orderLine.getQuantity(),
						orderLine.getProduct().getSubCategory().getName());
				cart.put(orderLine.getProduct().getProductId(), item);
			}
		} else {
			resetCurrentOrder();
		}
	}

	public void saveOrder() throws SQLException {

		currentOrder.getOrderLines().clear();
		for (OrderItem item : cart.values()) {
			OrderLine orderLine = new OrderLine();
			orderLine.setOrderLine(currentOrder, productManager.findProduct(item.getProductId()), item.getQuantity(),
					0);
			currentOrder.addOrderLine(orderLine);
		}
		System.out.println(currentOrder.getOrderLines());
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
		for(OrderItem item : cart.values())
			orderManager.removeOrderLine(new OrderLinePK(item.getProductId(), currentOrder.getOrderId()));
		
		cart.clear();
		orderItems.clear();
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