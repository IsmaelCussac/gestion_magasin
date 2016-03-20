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
	private Map<String, List<StoreItem>> storeItems;
	private Map<Integer, StoreItem> cart;

	@PostConstruct
	public void init(){
		productManager = new ProductManager();
		productManager.init(DataSource.LOCAL);
		orderManager = new OrderManager();
		orderManager.init(DataSource.LOCAL);
		userManager = new UserManager();
		userManager.init(DataSource.LOCAL);
	
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		userId = user.getUsername();

		storeItems = new HashMap<String, List<StoreItem>>();
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
	
	public void addInCart(StoreItem item){
		cart.put(item.getProductId(), item);
	}
	
	public void removeInCart(StoreItem item){
		cart.remove(item.getProductId());
	}
	
	private void clearCart() {
		cart.clear();	
	}

	public void updateCart(StoreItem item) throws SQLException {
		if (item.getQuantity() == 0) {
			removeOrderLineInDB(item);
			removeInCart(item);
		} else {
			addInCart(item);
		}
	}

	// Store methods
	
	public Map<String, List<StoreItem>> getStoreItems() {
		return storeItems;
	}

	public void setStoreItems(Map<String, List<StoreItem>> storeItems) {
		this.storeItems = storeItems;
	}
	
	public void clearStoreItems(){
		getStoreItems().clear();
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
	 * @throws SQLException
	 */
	public List<StoreItem> getStoreItems(SubCategory sub) {
		List<StoreItem> items;

		// si la sous catégorie n'est pas présente dans la map, on récupère la liste de produits et on l'ajoute
		if (!storeItems.containsKey(sub.getName())) {
			List<Product> prods = (List<Product>) productManager.findProductsBySubCategoryVisible(sub);
			items = createNewStoreItemList(prods);
				
		} else {
			items = storeItems.get(sub.getName());
			for (StoreItem item : items) {
				if (cart.containsKey(item.getProductId())) {
					item.setQuantity(cart.get(item.getProductId()).getQuantity());
				}
			}
		}
		storeItems.put(sub.getName(), items);
		return storeItems.get(sub.getName());
	}

	private List<StoreItem> createNewStoreItemList(List<Product> prods) {
		List<StoreItem> items = new ArrayList<StoreItem>();
		for (Product prod : prods) {
			StoreItem orderItem = new StoreItem();
			double quantity = 0;
			if(cart.containsKey(prod.getProductId()))
				quantity = cart.get(prod.getProductId()).getQuantity();
			orderItem.setStoreItem(prod.getProductId(), prod.getDesignation(), prod.getPicture(), quantity, prod.getSubCategory().getName());
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
	
	public void newOrder() throws SQLException {

		if (orderManager.hasNotValidatedOrder(userId)) {
			List<Order> orderList = (List<Order>) orderManager.findNotValidatedOrder(userId);
			currentOrder = orderList.get(0);
			for (OrderLine orderLine : currentOrder.getOrderLines()) {
				StoreItem item = new StoreItem();
				item.setStoreItem(orderLine.getProduct().getProductId(), orderLine.getProduct().getDesignation(),
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
		for (StoreItem item : cart.values()) {
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
		for(StoreItem item : cart.values())
			orderManager.removeOrderLine(new OrderLinePK(item.getProductId(), currentOrder.getOrderId()));
		
		cart.clear();
		storeItems.clear();
	}

	private void resetCurrentOrder() throws SQLException {
		currentOrder = new Order();
		currentOrder.setOrderUser(userManager.findPerson(userId));
		currentOrder.setStatus(OrderStatus.NOT_VALIDATED);
		orderManager.addOrder(currentOrder);
		clearCart();
		storeItems.clear();
	}
	
	public void removeOrderLineInDB(StoreItem item) throws SQLException{
		orderManager.removeOrderLine(new OrderLinePK(item.getProductId(), currentOrder.getOrderId()));
	}
}