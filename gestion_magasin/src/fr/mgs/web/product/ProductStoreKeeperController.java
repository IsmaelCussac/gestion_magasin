package fr.mgs.web.product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.bean.ManagedBean;

import org.primefaces.event.TabChangeEvent;

import fr.mgs.business.ProductManager;
import fr.mgs.connection.DataSource;
import fr.mgs.model.product.Category;
import fr.mgs.model.product.Product;
import fr.mgs.model.product.SubCategory;

/**
 * this class is the product's controller for the store keeper
 * 
 * @author Mana, Ismael
 *
 */

@ManagedBean(name = "productsList")
public class ProductStoreKeeperController {
	private ProductManager productManager;

	/**
	 * 
	 * @throws SQLException
	 */
	public ProductStoreKeeperController() throws SQLException {
		productManager = new ProductManager();
		productManager.init(DataSource.LOCAL);
	}

	/**
	 * 
	 * @param sub
	 * @return
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

}