package fr.mgs.web.product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.TabChangeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.mgs.business.OrderManager;
import fr.mgs.business.ProductManager;
import fr.mgs.connection.DataSource;
import fr.mgs.dao.Table;
import fr.mgs.model.order.OrderLine;
import fr.mgs.model.product.Category;
import fr.mgs.model.product.Product;
import fr.mgs.model.product.SubCategory;

/**
 * this class is the product's controller for the customer
 * 
 * @author Mana, Ismael
 *
 */

@ManagedBean(name = "products")
@ApplicationScoped
public class ProductCustomerController {

	private ProductManager productManager;
	private OrderManager orderManager;

	/**
	 * initialization of the controller
	 * @throws SQLException
	 */
	public ProductCustomerController() throws SQLException {
		productManager = new ProductManager();
		productManager.init(DataSource.LOCAL);
		orderManager = new OrderManager();
		orderManager.init(DataSource.LOCAL);
	}

	/**
	 * Get all the products for the sub category
	 * @param sub the sub category 
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
	 * @param event
	 */
	public void onTabChange(TabChangeEvent event) {
		System.out.println("event " + event.getTab().getId());
	}

	/**
	 * 
	 * @param event
	 */
	public void onChange(TabChangeEvent event) {
		System.out.println("Tab Changed :: You've Requested Seeing :: " + event.getTab().getTitle());
	}
	
	/**
	 * 
	 * @param prod
	 * @param quantity
	 * @throws SQLException
	 */
	public void addOrder(Product prod, int quantity) throws SQLException{
		OrderLine line = new OrderLine();
		line.setDeliveredQuantity(quantity);
		line.setProduct(prod);
		orderManager.addOrderLine(line);
	}

}
