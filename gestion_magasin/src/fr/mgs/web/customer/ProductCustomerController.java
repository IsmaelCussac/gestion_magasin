package fr.mgs.web.customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.CellEditEvent;
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
	private String userId;
	private Order currentOrder;
	private Map<String, List<OrderItem>> orderItems;

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
	}

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

	private void resetCurrentOrder() throws SQLException {
		currentOrder = new Order();
		currentOrder.setOrderUser(userManager.findUser(userId));
		currentOrder.setStatus(OrderStatus.NOT_VALIDATED);
		orderManager.addOrder(currentOrder);
	}

	public Order getCurrentOrder() {
		return currentOrder;
	}

	public void setCurrentOrder(Order currentOrder) {
		this.currentOrder = currentOrder;
	}

	public void onCellEdit(CellEditEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();

		if (newValue != null && !newValue.equals(oldValue)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed",
					"Old: " + oldValue + ", New:" + newValue);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

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
				orderItem.setOrderItem(prod.getProductId(), prod.getDesignation(), prod.getPicture(), 0);
				items.add(orderItem);
			}
			orderItems.put(sub.getName(), items);
		}
		return orderItems.get(sub.getName());
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		System.out.println(orderItems.toString());
		
	}

	public void updateOrderLine() throws SQLException {
		System.out.println("update order Lines " + orderItems.toString());
//		boolean orderLineExists = false;
//		for (OrderLine line : currentOrder.getOrderLines()) {
//			if (line.getId() == item.getProductId()) {
//				line.setQuantity(quantity);
//				orderLineExists = true;
//			}
//		}
//		if (!orderLineExists) {
//			OrderLine orderLine = new OrderLine();
//			try {
//				orderLine.setOrderLine(currentOrder, productManager.findProduct(item.getProductId()), quantity, 0);
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//			currentOrder.getOrderLines().add(orderLine);
//		}
	}

	public Map<String, List<OrderItem>> getOrderItems() {
		return orderItems;
	}

	public void saveOrder() throws SQLException {
		orderManager.updateOrder(currentOrder);
	}

	public void submitOrder() throws SQLException {
		currentOrder.setStatus(OrderStatus.VALIDATED);
		currentOrder.setSubmissionDate(new Date());
		saveOrder();
		resetCurrentOrder();
	}

	public void deleteOrder() throws SQLException {
		orderManager.removeOrder(currentOrder.getOrderId());
		resetCurrentOrder();
	}

}