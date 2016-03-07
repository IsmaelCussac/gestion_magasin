package fr.mgs.web.product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.primefaces.event.TabChangeEvent;

import fr.mgs.business.ProductManager;
import fr.mgs.connection.DataSource;
import fr.mgs.model.product.Category;
import fr.mgs.model.product.Product;
import fr.mgs.model.product.SubCategory;

/**
 * Controller Tempo
 * 
 * @author Mana
 *
 */

@ManagedBean(name="products")
@ApplicationScoped
public class ProductCustomerController {

	private ProductManager productManager;

	public ProductCustomerController() throws SQLException{
		productManager = new ProductManager();
		productManager.init(DataSource.LOCAL);
	}
	
	public List<Product> getProducts(SubCategory sub) {
		System.out.println(" Get prod : " + sub.getName());
		System.out.println(productManager.findProductsBySubCategory(sub));
		return (List<Product>) productManager.findProductsBySubCategory(sub);
	}
	
	public List<SubCategory> getSubCategories(Category cat){
		System.out.println(" Get sub cat : " + cat.toString());
		return (List<SubCategory>) productManager.findSubCategoriesByCategory(cat);
	}
	
	public Collection<Category> getAllCategories(){
		
		System.out.println("Get all cat");
		Collection<Category> categories = new ArrayList<Category>();
		
		categories.add(Category.PAPER);
		categories.add(Category.PLASTIC);
		categories.add(Category.CULTURE_PLASTIC);
		
		return categories;
	}

	public void onTabChange(TabChangeEvent event) {
        System.out.println("event " + event.getTab().getId());
    }
	
	  public void onChange(TabChangeEvent event) {
	        System.out.println("Tab Changed :: You've Requested Seeing :: "+ event.getTab().getTitle());
	    }


}
