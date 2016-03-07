package fr.mgs.web.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.bean.ManagedBean;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import fr.mgs.business.ProductManager;
import fr.mgs.model.product.Category;
import fr.mgs.model.product.Product;
import fr.mgs.model.product.SubCategory;

//@Controller
//@RequestMapping("/product")
@ManagedBean(name = "productsList")
public class ProductController {

	// protected final Log logger = LogFactory.getLog(getClass());
	 private ProductManager productManager = new ProductManager();
	// private CategoryManager iManagerC = new CategoryManager();

	private List<SubCategory> listSub;
	private Set<Product> listProd ;
	private Set<Product> listProd2 ;
	Map<Category, List<SubCategory>> categories;
	private int quantity;
	
//	@ModelAttribute("categories")
	public Map<Category, List<SubCategory>> getCategories() {
		listSub = new ArrayList<SubCategory>();
		listProd = new HashSet<Product>();
		listProd2 = new HashSet<Product>();
		categories = new HashMap<Category, List<SubCategory>>();

		SubCategory sub1 = new SubCategory();
		sub1.setSubCategory("category1", Category.CULTURE_PLASTIC);
		
		SubCategory sub2 = new SubCategory();
		sub2.setSubCategory("category2", Category.CULTURE_PLASTIC);

		Product prod1 = new Product();
		prod1.setProductId(1);
		prod1.setProduct("designation1", sub1, 4, 3, 5, true, "picture", 4);
		listProd.add(prod1);

		Product prod2 = new Product();
		prod2.setProductId(2);
		prod2.setProduct("designation2", sub1, 4, 3, 5, true, "picture", 4);
		listProd.add(prod2);

		Product prod3 = new Product();
		prod3.setProductId(3);
		prod3.setProduct("designation3", sub2, 4, 3, 5, true, "picture", 4);
		listProd2.add(prod3);

		Product prod4 = new Product();
		prod4.setProductId(4);
		prod4.setProduct("designation4", sub2, 4, 3, 5, true, "picture", 4);
		listProd2.add(prod4);
		
		sub1.setProducts(listProd);
		sub2.setProducts(listProd2);
		
		listSub.add(sub1);
		listSub.add(sub2);

		categories.put(Category.CULTURE_PLASTIC, listSub);
		// return iManagerC.findAll();
		return categories;
	}
	
	public int getQuantity(Product p){
		//ManagerP.getItemQuantity(p);
		quantity = productManager.findItemQuantity(p);
		return quantity;
	}

	/***
	 * This method returns the person to add.
	 * 
	 * @param personId
	 * @return
	 */
	@ModelAttribute
	public Product newProduct(@RequestParam(value = "id", required = false) Integer productId) {
		if (productId != null) {
			// logger.info("find product " + productId);
			// return iManagerP.find(personId);
		}
		Product p = new Product();
		// p.setProduct("", "", "", "", null, "", "USER", false);
		return p;
	}

}