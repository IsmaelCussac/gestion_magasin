package fr.mgs.web.storekeeper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import fr.mgs.business.OrderManager;
import fr.mgs.business.ProductManager;
import fr.mgs.connection.DataSource;
import fr.mgs.model.order.Order;
import fr.mgs.model.order.OrderLine;
import fr.mgs.model.product.Category;
import fr.mgs.model.product.Product;
import fr.mgs.model.product.SubCategory;

@ManagedBean(name = "skProducts")
@ApplicationScoped
public class ProductSKController {

	private ProductManager productManager;
	private OrderManager orderManager;
	private Order currentOrder;

	@PostConstruct
	public void init() {
		productManager = new ProductManager();
		productManager.init(DataSource.LOCAL);
		orderManager = new OrderManager();
		orderManager.init(DataSource.LOCAL);

	}

	/**
	 * Get all the products for the sub category
	 * 
	 * @param sub
	 *            the sub category
	 * @return the list of product
	 */
	public List<Product> getProducts(SubCategory sub) {
		System.out.println(" Get prod : " + sub.getName());
		System.out.println(productManager.findProductsBySubCategory(sub));
		return (List<Product>) productManager.findProductsBySubCategory(sub);
	}

	/**
	 * 
	 * @param cat
	 * @return
	 */
	public List<SubCategory> getSubCategories(Category cat) {
		System.out.println(" Get sub cat : " + cat.toString());
		return (List<SubCategory>) productManager.findSubCategoriesByCategory(cat);
	}

	/**
	 * 
	 * @return
	 * @throws SQLException 
	 */
	public List<SubCategory> getAllSubCategories() throws SQLException {
		return (List<SubCategory>) productManager.findAllSubCategories();
	}

	
	/**
	 * 
	 * @return
	 */
	public Collection<Category> getAllCategories() {

		System.out.println("Get all cat");
		Collection<Category> categories = new ArrayList<Category>();

		categories.add(Category.PAPER);
		categories.add(Category.PLASTIC);
		categories.add(Category.CULTURE_PLASTIC);

		return categories;
	}

	/**
	 * 
	 * @param prod
	 * @param quantity
	 * @throws SQLException
	 */
	public void addOrder(Product prod, int quantity) throws SQLException {
		OrderLine line = new OrderLine();
		line.setDeliveredQuantity(quantity);
		line.setProduct(prod);
		orderManager.addOrderLine(line);
	}

}
