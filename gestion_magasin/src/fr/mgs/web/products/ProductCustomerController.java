package fr.mgs.web.products;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import fr.mgs.models.product.Category;
import fr.mgs.models.product.Product;
import fr.mgs.models.product.SubCategory;

/**
 * Controller Tempo
 * @author Mana
 *
 */

@Controller
public class ProductCustomerController {
	
	private List<SubCategory> listSub = new ArrayList<SubCategory>();
	private SubCategory sub;
	private Set<Product> listProd;
	private Product prod;
	private int count = 0;
	
	@ModelAttribute("categories")
	public List<SubCategory> setSub(){
		setList();
		return listSub;
	}
	
	public void setList(){
		setSub(sub);
		this.listSub.add(sub);
	}

	public void setSub(SubCategory sub) {
		sub.setCategory(Category.PAPER);
		sub.setName("stylo");
		setProds();
		sub.setProducts(listProd);
		count ++;
	}
	
	public void setProds(){
		for(int i = 0; i < 2; i++){
			this.prod.setConditioning(10.2);
			this.prod.setId(count);
			this.prod.setDesignation("prod"+count);
			this.prod.setVisibility(true);
			listProd.add(prod);
		}
	}

}
