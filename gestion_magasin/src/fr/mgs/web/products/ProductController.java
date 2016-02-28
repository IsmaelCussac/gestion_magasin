package fr.mgs.web.products;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.primefaces.component.log.Log;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import fr.mgs.business.product.CategoryManager;
import fr.mgs.business.product.ProductManager;
import fr.mgs.model.product.Category;
import fr.mgs.model.product.Product;
import fr.mgs.model.product.SubCategory;
@Controller
@RequestMapping("/product")
public class ProductController {
    

    
//    protected final Log logger = LogFactory.getLog(getClass());
    private ProductManager iManagerP = new ProductManager();
    private CategoryManager iManagerC = new CategoryManager();
    
    
    
    /***
     * This method redirects the user to the page that show the person with the personId.
     * @param personId
     * @return
     */
//    		@RequestMapping("/viewProduct")
//    		public  productControllerC(
//    				@RequestParam(value = "id", required = false) Integer personId) {
//    			//Product p = iManagerP.findProduct(product);
//    			ModelAndView mav = new ModelAndView("afficherPerson");
//    			//mav.addObject("person", p);
//    			return mav;
//    		}
    
    /***
     * This method redirects the user to the page that show the list of students.
     * @return
     */
//    @RequestMapping(value = "/productList", method = RequestMethod.GET)
//    public ModelAndView products() {
//        Collection<Product> products = iManagerP.findAll();
//        return new ModelAndView("productList", "products", products);
//    }
    
    /***
     * This method redirect the user to the page that show the inscription form.
     * @param p
     * @param group
     * @return
     */
    //		@RequestMapping(value = "/inscription", method = RequestMethod.GET)
    //		public String inscriptionPerson(@ModelAttribute Person p, @ModelAttribute Group group) {
    //		    return "inscription";
    //		}
    //
    		/***
    		 * This method gets all the information that the user put in the form.
    		 * @param p
    		 * @param result
    		 * @return
    		 */
    		@RequestMapping(value = "/product", method = RequestMethod.POST)
    		public String saveProduct(@ModelAttribute Product p, BindingResult result) {
    		    iManagerP.updateProduct(p);
    		    return "afficherProduct";
    		}
    
    /***
     * This method redirects the user to the page that allows him to modify his informations.
     * @param p
     * @return
     */
    @RequestMapping(value = "/modification", method = RequestMethod.GET)
    public String modifyForm(@ModelAttribute Product p) {
        return "modification";
    }
    
    
    @ModelAttribute("categories")
    public Map<Category, List<SubCategory>> getCategories(){   
        //return iManagerC.findAll();
    	return null;
    }
    
    /***
     * This method returns the person to add.
     * @param personId
     * @return
     */
    @ModelAttribute
    public Product newProduct(
                              @RequestParam(value = "id", required = false) Integer productId) {
        if (productId != null) {
          //  logger.info("find product " + productId);
            //  return iManagerP.find(personId);
        }
        Product p = new Product();
        //  p.setProduct("", "", "", "", null, "", "USER", false);
        return p;
    }
    
    
}
