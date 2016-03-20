package fr.mgs.web.storekeeper;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import fr.mgs.business.ProductManager;
import fr.mgs.connection.DataSource;
import fr.mgs.model.product.Lot;
import fr.mgs.model.product.Product;
import fr.mgs.model.product.SubCategory;

@ManagedBean(name = "productctrl")
@ApplicationScoped
public class ProductController {

	private ProductManager productManager;

	private Product currentProduct;
	private Lot lot;
	private SubCategory subCategory;

	@PostConstruct
	public void init() {
		productManager = new ProductManager();
		productManager.init(DataSource.LOCAL);

		currentProduct = new Product();
		subCategory = new SubCategory();
		lot = new Lot();

	}

	public Product getCurrentProduct() {
		return currentProduct;
	}

	public void setCurrentProduct(Product currentProduct) {
		this.currentProduct = currentProduct;
	}
	public SubCategory getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}
	
	public Lot getLot() {
		return lot;
	}

	public void setLot(Lot lot) {
		this.lot = lot;
	}
	
	public List<SubCategory> getAllSubCategories() throws SQLException {
		return (List<SubCategory>) productManager.findAllSubCategories();
	}
	
	/*
	 * 
	 *  Ajout product
	 */
	public void addNewProduct() throws SQLException{
		if(!subCategory.getName().equals("")){
			currentProduct.setSubCategory(productManager.findSubCategory(subCategory.getName()));
		}
		productManager.addProduct(currentProduct);
		if(lot.getQuantity() >= 1){
			productManager.addLot(lot);
		}
	}
}
