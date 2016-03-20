package fr.mgs.web.storekeeper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import fr.mgs.business.ProductManager;
import fr.mgs.connection.DataSource;
import fr.mgs.model.product.Category;
import fr.mgs.model.product.Lot;
import fr.mgs.model.product.Product;
import fr.mgs.model.product.SubCategory;

@ManagedBean(name = "skProducts")
@ApplicationScoped
public class ProductListController {

	private ProductManager productManager;
	private Map<String, List<Product>> products;

	private Product currentProduct;

	private SubCategory subCategory;

	@PostConstruct
	public void init() {
		productManager = new ProductManager();
		productManager.init(DataSource.LOCAL);

		products = new HashMap<String, List<Product>>();
		subCategory = new SubCategory();

	}

	public Product getCurrentProduct() {
		return currentProduct;
	}

	public void setCurrentProduct(Product currentProduct) {
		this.currentProduct = currentProduct;
	}

	public Map<String, List<Product>> getStoreProducts() {
		return products;
	}

	public void setStoreProducts(Map<String, List<Product>> products) {
		this.products = products;
	}

	public SubCategory getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}

	public void clearStoreItems() {
		getStoreProducts().clear();
	}

	public Collection<Category> getAllCategories() {

		Collection<Category> categories = new ArrayList<Category>();

		categories.add(Category.PAPER);
		categories.add(Category.PLASTIC);
		categories.add(Category.CULTURE_PLASTIC);

		return categories;
	}

	public List<SubCategory> getSubCategories(Category cat) {
		return (List<SubCategory>) productManager.findSubCategoriesByCategory(cat);
	}
	
	public List<SubCategory> getAllSubCategories() throws SQLException {
		return (List<SubCategory>) productManager.findAllSubCategories();
	}
	
	/**
	 * Get all the products for the sub category
	 * 
	 * @param sub
	 *            the sub category
	 * @return the list of product
	 * @throws SQLException
	 */
	public List<Product> getStoreProducts(SubCategory sub) {
		List<Product> productList;

		// si la sous catégorie n'est pas présente dans la map, on récupère la
		// liste de produits et on l'ajoute
		if (!products.containsKey(sub.getName())) {
			productList = (List<Product>) productManager.findProductsBySubCategory(sub);
			products.put(sub.getName(), productList);
		}
		return products.get(sub.getName());

	}

	public void updateVisibility(Product product) throws SQLException {
		productManager.updateProduct(product);

	}

	public int getQuantity(Product product) {
		if(product == null){
			return 0;
		}
		int sum = 0;
		for (Lot lot : product.getLots()) {
			sum += lot.getQuantity();
		}
		return sum;
	}

	/**
	 * 
	 * @throws SQLException
	 */
	public void updateCurrentProduct() throws SQLException {
		if(!subCategory.getName().equals("")){
			currentProduct.setSubCategory(productManager.findSubCategory(subCategory.getName()));
		}
		productManager.updateProduct(currentProduct);
		clearStoreItems();
	}
	
	public void addNewProduct(){
		currentProduct = new Product();
		currentProduct.setProduct(101, "", null, 0, 0, 0, false, "", 0);
	}

}
