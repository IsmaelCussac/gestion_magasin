package fr.mgs.web.customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;

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
	private double productQuantity;
	private List<OrderLine> orderLines;

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
			}
		}

		else if (orderManager.hasNotValidatedOrder(userId)) {
			ArrayList<Order> orderList = (ArrayList<Order>) orderManager.findNotValidatedOrder(userId);
			return orderList.get(0);
		}

		currentOrder = new Order();
		currentOrder.setOrderUser(userManager.findUser(userId));

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
	 * Get all the products for the sub category
	 * 
	 * @param sub
	 *            the sub category
	 * @return the list of product
	 */
	public List<OrderLine> getProducts(SubCategory sub) {
		List<Product> products = (List<Product>) productManager.findProductsBySubCategory(sub);
		orderLines = new ArrayList<OrderLine>();
		
		for(Product prod : products){
			OrderLine orderLine = new OrderLine();
			orderLine.setOrderLine(currentOrder, prod, 0, 0);
		}
		
		
	}

	/**
	 * 
	 * @param cat
	 * @return
	 */
	public List<SubCategory> getSubCategories(Category cat) {
		return (List<SubCategory>) productManager.findSubCategoriesByCategory(cat);
	}

	public double getProductQuantity(Product product) {
		System.out.println(product.toString());
		

		for (OrderLine line : currentOrder.getOrderLines()) {
			if (line != null && line.getProduct().getProductId() == product.getProductId()) {
				productQuantity = line.getQuantity();
			}
		}
		return productQuantity;
	}

	public void setProductQuantity(ValueChangeEvent event, int productId) {
		System.out.println("change");
		boolean orderLineFound = false;
		for (OrderLine line : currentOrder.getOrderLines()) {
			if (line.getId() == productId) {
				line.setQuantity((Double) event.getNewValue());
				orderLineFound = true;
				System.out.println("new quantity" + line.toString());
			}
		}
		if (!orderLineFound) {
			OrderLine orderLine = new OrderLine();
			try {
				orderLine.setOrderLine(currentOrder, productManager.findProduct(productId),
						(Double) event.getNewValue(), 0);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			currentOrder.addOrderLine(orderLine);
			System.out.println("add order line" + orderLine.toString());
		}
	}

}