package fr.mgs.web.customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
public class ProductCustomerController {

	private ProductManager productManager;
	private OrderManager orderManager;
	private UserManager userManager;
	private Order currentOrder;
	private String userId;
	private List<OrderItem> orderItems;

	public Order getCurrentOrder() {
		return currentOrder;
	}

	public void setCurrentOrder(Order currentOrder) {
		this.currentOrder = currentOrder;
	}

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

	}

	public Order newOrder() throws SQLException {

		if (currentOrder != null) {
			if (currentOrder.getStatus() == OrderStatus.NOT_VALIDATED) {
				return currentOrder;
			} else {
				currentOrder = new Order();
				currentOrder.setOrderUser(userManager.findUser(userId));
				currentOrder.setStatus(OrderStatus.NOT_VALIDATED);
				orderManager.addOrder(currentOrder);
			}
		}

		else if (orderManager.hasNotValidatedOrder(userId)) {
			List<Order> orderList = (List<Order>) orderManager.findNotValidatedOrder(userId);
			currentOrder = orderList.get(0);
		} else {
			currentOrder = new Order();
			currentOrder.setOrderUser(userManager.findUser(userId));
			currentOrder.setStatus(OrderStatus.NOT_VALIDATED);
			orderManager.addOrder(currentOrder);
		}
		
		return currentOrder;
	}

	/**
	 * 
	 * @return
	 */
	public Collection<Category> getAllCategories() {

		Collection<Category> categories = new ArrayList<Category>();

		categories.add(Category.PAPER);
		categories.add(Category.PLASTIC);
		categories.add(Category.CULTURE_PLASTIC);

		return categories;
	}

	/**
	 * 
	 * @param cat
	 * @return
	 */
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
	public List<OrderItem> getItems(SubCategory sub) {
		orderItems = new ArrayList<OrderItem>();
		List<Product> prods = (List<Product>) productManager.findProductsBySubCategory(sub);
		boolean orderLineExists;
		if (currentOrder.getOrderLines().size() == 0) {
			for (Product prod : prods) {

				OrderItem orderItem = new OrderItem();
				orderItem.setOrderItem(prod.getProductId(), prod.getDesignation(), prod.getPicture(), 0);
				orderItems.add(orderItem);
			}
		} else {
			for (Product prod : prods) {
				orderLineExists = false;
				OrderItem orderItem = new OrderItem();
				for (OrderLine orderLine : currentOrder.getOrderLines()) {

					if (prod.getProductId() == orderLine.getProduct().getProductId()) {
						orderLineExists = true;
						orderItem.setOrderItem(orderLine.getProduct().getProductId(),
								orderLine.getProduct().getDesignation(), orderLine.getProduct().getPicture(),
								orderLine.getQuantity());
						orderItems.add(orderItem);
						break;
					}

				}
				if (!orderLineExists) {
					orderItem.setOrderItem(prod.getProductId(), prod.getDesignation(), prod.getPicture(), 0);
					orderItems.add(orderItem);
				}
			}
		}
		return orderItems;

	}

	public void updateOrderLine(OrderItem item) throws SQLException {
		boolean orderLineExists = false;
		for (OrderLine line : currentOrder.getOrderLines()) {
			if (line.getId() == item.getProductId()) {
				line.setQuantity(item.getQuantity());
				orderLineExists = true;
			}
		}
		if (!orderLineExists) {
			OrderLine orderLine = new OrderLine();
			try {
				orderLine.setOrderLine(currentOrder, productManager.findProduct(item.getProductId()),
						item.getQuantity(), 0);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			currentOrder.getOrderLines().add(orderLine);
//			orderManager.addOrderLine(orderLine);
//			orderManager.updateOrder(currentOrder);
		}
	}
	
	public Collection<OrderLine> getOrderLines(){
		return currentOrder.getOrderLines();
	}

}