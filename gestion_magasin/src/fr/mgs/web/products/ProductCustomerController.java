package fr.mgs.web.products;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.mgs.model.product.Category;
import fr.mgs.model.product.Product;
import fr.mgs.model.product.SubCategory;

/**
 * Controller Tempo
 * 
 * @author Mana
 *
 */

//@Controller
//@RequestMapping("/product")
@ManagedBean(name="products")
public class ProductCustomerController {

	private List<SubCategory> listSub;
	private List<Product> listProd;
	// private int count = 0;

	
//	@ModelAttribute("products")
	public Product getProducts() {
		
		listSub = new ArrayList<SubCategory>();

		SubCategory sub1 = new SubCategory();
		sub1.setSubCategory("category1", Category.CULTURE_PLASTIC);
		listSub.add(sub1);

		SubCategory sub2 = new SubCategory();
		sub2.setSubCategory("category2", Category.CULTURE_PLASTIC);
		listSub.add(sub2);

		listProd = new ArrayList<Product>();

		Product prod1 = new Product();
		prod1.setId(1);
		prod1.setProduct("designation1", sub1, 4, 3, 5, true, "picture", 4);
		listProd.add(prod1);

		Product prod2 = new Product();
		prod2.setId(2);
		prod2.setProduct("designation2", sub1, 4, 3, 5, true, "picture", 4);
		listProd.add(prod2);
		
		return prod2;
	}

	// public void setList(){
	// setSub(sub);
	// this.listSub.add(sub);
	// }
	//
	// public void setSub(SubCategory sub) {
	// sub.setCategory(Category.PAPER);
	// sub.setName("stylo");
	// setProds();
	// sub.setProducts(listProd);
	// count ++;
	// }
	//
	// public void setProds(){
	// for(int i = 0; i < 2; i++){
	// this.prod.setConditioning(10.2);
	// this.prod.setId(count);
	// this.prod.setDesignation("prod"+count);
	// this.prod.setVisibility(true);
	// listProd.add(prod);
	// }
	// }

}
