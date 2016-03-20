package fr.mgs.web.storekeeper;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import fr.mgs.business.ProductManager;
import fr.mgs.connection.DataSource;
import fr.mgs.model.product.Category;
import fr.mgs.model.product.Lot;
import fr.mgs.model.product.Product;

/**
 * 
 * @author Ibrahima
 *
 */
@ManagedBean(name = "stockInCtl")
@SessionScoped
public class StockInController {

	private Set<Product> items;
	private String scanDefault;
	private Double conditioning;
	private ProductManager productManager;
	private Product selectedProduct;
	private Lot newLot;
	
	@ManagedProperty("#{ListProducts}")
	ListProductController listProducts;

	@PostConstruct
	public void ini() {
		productManager = new ProductManager();
		productManager.init(DataSource.LOCAL);
		items = new HashSet<Product>();
		conditioning = 0.0;
		selectedProduct = new Product();
		newLot = new Lot();
		newLot.setQuantity(0);

	}

	public void saveProducts() {

	}

	public void scan() throws NumberFormatException, SQLException {
		System.out.println(scanDefault);
		Product product = productManager.findProduct(Integer.valueOf(scanDefault));
		if (product != null && isPlastic(product)) {
			Lot lot = new Lot();
			lot.setLotProduct(product);
			lot.setQuantity(product.getConditioning());
			product.getConditioning();
			conditioning = 100.0;
		}
		scanDefault = "";
	}

	public void resetScan() {

	}

	public boolean isPlastic(Product p) {
		return (p.getSubCategory().getCategory().equals(Category.PLASTIC)
				|| p.getSubCategory().getCategory().equals(Category.CULTURE_PLASTIC));
	}

	public void saveLot() throws SQLException {
		newLot.setLotProduct(selectedProduct);
		productManager.addLot(newLot);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Nouveau lot ajouté", ""));

	}

	public ListProductController getListProducts() {
		return listProducts;
	}

	public void setListProducts(ListProductController listProducts) {
		this.listProducts = listProducts;
	}

	public String getScanDefault() {
		return scanDefault;
	}

	public void setScanDefault(String value) {
		this.scanDefault = value;
	}

	public Set<Product> getItems() {
		return items;
	}

	public void setItems(Set<Product> items) {
		this.items = items;
	}

	public Double getConditioning() {
		return conditioning;
	}

	public void setConditioning(Double conditioning) {
		this.conditioning = conditioning;
	}

	public Product getSelectedProduct() {
		return selectedProduct;
	}

	public void setSelectedProduct(Product selectedProduct) {
		this.selectedProduct = selectedProduct;
	}

	public Lot getNewLot() {
		return newLot;
	}

	public void setNewLot(Lot newLot) {
		this.newLot = newLot;
	}
}
