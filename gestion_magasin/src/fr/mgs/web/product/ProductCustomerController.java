package fr.mgs.web.product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.mgs.business.ProductManager;
import fr.mgs.connection.DataSource;
import fr.mgs.dao.Table;
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

	private Collection<SubCategory> sub;
	private Collection<Product> products;
	

	private ProductManager productManager;

	public ProductCustomerController() throws SQLException{
		productManager = new ProductManager();
		productManager.init(DataSource.LOCAL);
	}
	
	public Collection<Product> getProducts(SubCategory sub) {
		
		System.out.println(sub.getName());
		products = productManager.findProductsBySubCategory(sub);
		System.out.println("taille collection " + products.size());
		return products;
	}
	
	public Collection<SubCategory> getSubCategories(Category cat){
		sub = productManager.findSubCategoriesByCategory(cat);
		return sub;
	}
	
	public Collection<Category> getAllCategories(){
		Collection<Category> categories = new ArrayList<Category>();
		
		categories.add(Category.PAPER);
		categories.add(Category.PLASTIC);
		categories.add(Category.CULTURE_PLASTIC);
		
		return categories;
	}



}
