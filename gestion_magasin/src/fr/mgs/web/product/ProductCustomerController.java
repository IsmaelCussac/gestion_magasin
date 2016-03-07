package fr.mgs.web.product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

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
public class ProductCustomerController {

	private Collection<SubCategory> sub;
	private List<Product> products;
	

	private ProductManager productManager;

	public ProductCustomerController() throws SQLException{
		productManager = new ProductManager();
		productManager.init(DataSource.LOCAL);
	}
	
	public List<Product> getProducts() {
		
		sub = new ArrayList<SubCategory>();

		SubCategory sub1 = new SubCategory();
		sub1.setSubCategory("category1", Category.CULTURE_PLASTIC);
		sub.add(sub1);

		SubCategory sub2 = new SubCategory();
		sub2.setSubCategory("category2", Category.CULTURE_PLASTIC);
		sub.add(sub2);

		products = new ArrayList<Product>();

		Product prod1 = new Product();
		prod1.setProductId(1);
		prod1.setProduct("designation1", sub1, 4, 3, 5, true, "picture", 4);
		products.add(prod1);

		Product prod2 = new Product();
		prod2.setProductId(2);
		prod2.setProduct("designation2", sub1, 4, 3, 5, true, "picture", 4);
		products.add(prod2);
		
		return products;
	}
	
	public Collection<SubCategory> getSub(){
		sub = productManager.findSubCategoriesByCategory(Category.PAPER);
		System.out.println("taille collection" + sub.size());
		return sub;
	}



}
