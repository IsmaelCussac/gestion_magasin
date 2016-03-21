package fr.mgs.web.storekeeper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import fr.mgs.business.EventManager;
import fr.mgs.business.ProductManager;
import fr.mgs.connection.DataSource;
import fr.mgs.model.event.Action;
import fr.mgs.model.event.Event;
import fr.mgs.model.product.Category;
import fr.mgs.model.product.Lot;
import fr.mgs.model.product.Product;
import fr.mgs.model.product.SubCategory;

/**
 * 
 * @author Ismaël
 *
 */
@ManagedBean(name = "skProducts")
@ApplicationScoped
public class ProductListController {

	private ProductManager productManager;
	private EventManager eventManager;
	
	private List<Product> storeProducts;
	private Product currentProduct;
	private SubCategory subCategory;
	private UploadedFile image;
	
	private String user;

	@PostConstruct
	public void init() {
		productManager = new ProductManager();
		productManager.init(DataSource.LOCAL);
		
		eventManager = new EventManager();
		eventManager.init(DataSource.LOCAL);

		storeProducts = new ArrayList<Product>();
		subCategory = new SubCategory();
		
		user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

	}

	public Product getCurrentProduct() {
		return currentProduct;
	}

	public void setCurrentProduct(Product currentProduct) {
		this.currentProduct = currentProduct;
	}

	public List<Product> getStoreProducts() {
		return storeProducts;
	}

	public void setStoreProducts(List<Product> storeProducts) {
		this.storeProducts = storeProducts;
	}

	public SubCategory getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}

	public UploadedFile getImage() {
		return image;
	}

	public void setImage(UploadedFile image) {
		System.out.println("setter");
		this.image = image;
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
	public void loadStoreProducts(SubCategory sub) {
		storeProducts = (List<Product>) productManager.findProductsBySubCategory(sub);
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
	//	currentProduct.setPicture(image.getContents());
		productManager.updateProduct(currentProduct);
		clearStoreItems();
	}
	
	public void addNewProduct(){
		currentProduct = new Product();
		currentProduct.setProduct(101, "", null, 0, 0, 0, false, null, 0);
	}
	
	public void handleFileUpload(FileUploadEvent event) {
		System.out.println("ici");
		setImage(event.getFile());
		System.out.println("image: " +image.getSize());
    }
	
	public DefaultStreamedContent byteToImage(byte[] imgBytes) throws IOException {
		ByteArrayInputStream img = new ByteArrayInputStream(imgBytes);
		return new DefaultStreamedContent(img,"image/jpg");
		}
	
	public void addEvent(Product product) throws SQLException{
		Event event = new Event();
//		StringBuilder resume = new StringBuilder();
//		resume.append(user);
//		resume.append(" a ajouté le nouveau produit ");
//		resume.append(product);
		
		event.setEvent(user, product, Action.CREATE, new Date(), "");
		eventManager.addEvent(event);
	}
	
	public void updateEvent(){
		
	}
	
	public void showEvent(){
		
	}
	
	public void hideEvent(){
		
	}

}
