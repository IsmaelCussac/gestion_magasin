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
 * This class is the product's controller for the customer
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
	private Collection<StoreItem> storeItems;
	private Map<Integer, StoreItem> cart;

	@PostConstruct
	public void init() {
		productManager = new ProductManager();
		productManager.init(DataSource.LOCAL);
		orderManager = new OrderManager();
		orderManager.init(DataSource.LOCAL);
		userManager = new UserManager();
		userManager.init(DataSource.LOCAL);

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		userId = user.getUsername();

		storeItems = new ArrayList<StoreItem>();
		cart = new HashMap<Integer, StoreItem>();

		try {
			newOrder();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// Cart methods

	public Map<Integer, StoreItem> getCart() {
		return cart;
	}

	public void setCart(Map<Integer, StoreItem> cart) {
		this.cart = cart;
	}

	public void addInCart(StoreItem item) {
		cart.put(item.getProductId(), item);
	}

	public void removeInCart(StoreItem item) {
		cart.remove(item.getProductId());
	}

	private void clearCart() {
		cart.clear();
	}

	/**
	 * Update the cart. If quantity = 0, the item is removed from the cart and
	 * in DB
	 * 
	 * @param item
	 * @throws SQLException
	 */
	public void updateCart(StoreItem item) throws SQLException {
		if (item.getQuantity() == 0) {
			removeOrderLineInDB(item);
			removeInCart(item);
		} else {
			addInCart(item);
		}
	}

	// Store methods

	public List<StoreItem> getStoreItems() {
		return (List<StoreItem>) storeItems;
	}

	public void setStoreItems(List<StoreItem> storeItems) {
		this.storeItems = storeItems;
	}

	/**
	 * Get all first level categories
	 * 
	 * @return Collection
	 */
	public Collection<Category> getAllCategories() {

		Collection<Category> categories = new ArrayList<Category>();

		categories.add(Category.PAPER);
		categories.add(Category.PLASTIC);
		categories.add(Category.CULTURE_PLASTIC);

		return categories;
	}

	/**
	 * Get all second level categories
	 * 
	 * @param cat
	 * @return List
	 */
	public List<SubCategory> getSubCategories(Category cat) {
		return (List<SubCategory>) productManager.findSubCategoriesByCategory(cat);
	}

	/**
	 * Get all the products for the sub category, and put it in the store list.
	 * Update the quantity if the item is in the cart
	 * 
	 * @param sub
	 *            the sub category
	 * @return the list of product
	 * @throws SQLException
	 */
	public void loadStoreItems(SubCategory subCat) {
		List<Product> prods = (List<Product>) productManager.findProductsBySubCategoryVisible(subCat);
		storeItems = createNewStoreItemList(prods);
		for (StoreItem item : storeItems) {
			if (cart.containsKey(item.getProductId())) {
				item.setQuantity(cart.get(item.getProductId()).getQuantity());
			}
		}
	}

	/**
	 * Create a storeItem list using a product list and set the quantity to 0
	 * 
	 * @param prods
	 * @return
	 */
	private List<StoreItem> createNewStoreItemList(List<Product> prods) {
		List<StoreItem> items = new ArrayList<StoreItem>();
		for (Product prod : prods) {
			StoreItem orderItem = new StoreItem();
			double quantity = 0;
			if (cart.containsKey(prod.getProductId()))
				quantity = cart.get(prod.getProductId()).getQuantity();
			orderItem.setStoreItem(prod.getProductId(), prod.getDesignation(), quantity,
					prod.getSubCategory().getName());
			items.add(orderItem);
		}
		return items;
	}

	// Order methods

	public Order getCurrentOrder() {
		return currentOrder;
	}

	public void setCurrentOrder(Order currentOrder) {
		this.currentOrder = currentOrder;
	}

	/**
	 * Create new order
	 * 
	 * @throws SQLException
	 */
	public void newOrder() throws SQLException {

		if (orderManager.hasNotValidatedOrder(userId)) {
			List<Order> orderList = (List<Order>) orderManager.findNotValidatedOrder(userId);
			currentOrder = orderList.get(0);
			for (OrderLine orderLine : currentOrder.getOrderLines()) {
				StoreItem item = new StoreItem();
				item.setStoreItem(orderLine.getProduct().getProductId(), orderLine.getProduct().getDesignation(),
						orderLine.getQuantity(), orderLine.getProduct().getSubCategory().getName());
				cart.put(orderLine.getProduct().getProductId(), item);
			}
		} else {
			resetCurrentOrder();
		}
	}

	/**
	 * Save the order in DB
	 * 
	 * @throws SQLException
	 */
	public void saveOrder() throws SQLException {

		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage("buttons:growlSave", new FacesMessage("Commande sauvegardée", "Commande sauvegardée"));

		currentOrder.getOrderLines().clear();
		for (StoreItem item : cart.values()) {
			OrderLine orderLine = new OrderLine();
			orderLine.setOrderLine(currentOrder, productManager.findProduct(item.getProductId()), item.getQuantity(),
					0);
			currentOrder.addOrderLine(orderLine);
		}
		orderManager.updateOrder(currentOrder);
	}

	/**
	 * Submit the order to the store keeper
	 * 
	 * @return
	 * @throws SQLException
	 */
	public String submitOrder() throws SQLException {

		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage("buttons:growlValidate", new FacesMessage("Commande envoyée", "Commande envoyée"));

		if (cart.size() > 0) {
			currentOrder.setStatus(OrderStatus.VALIDATED);
			currentOrder.setSubmissionDate(new Date());
			saveOrder();
			resetCurrentOrder();
			return "pretty:cstHistory";
		}
		return "pretty:cstProducts";
	}

	/**
	 * Clear the order
	 * 
	 * @throws SQLException
	 */
	public void deleteOrder() throws SQLException {

		List<Order> order = (List<Order>) orderManager.findNotValidatedOrder(userId);
		for (OrderLine line : order.get(0).getOrderLines()) {
			orderManager.removeOrderLine(line.getOrderLinePK());
		}
		// orderManager.updateOrder(currentOrder);
		cart.clear();
	}

	/**
	 * Instanciate a new Order and set a NOT_VALIDATED status
	 * 
	 * @throws SQLException
	 */
	private void resetCurrentOrder() throws SQLException {
		currentOrder = new Order();
		currentOrder.setOrderUser(userManager.findPerson(userId));
		currentOrder.setStatus(OrderStatus.NOT_VALIDATED);
		orderManager.addOrder(currentOrder);
		clearCart();
	}

	public void removeOrderLineInDB(StoreItem item) throws SQLException {
		orderManager.removeOrderLine(new OrderLinePK(item.getProductId(), currentOrder.getOrderId()));
	}
}