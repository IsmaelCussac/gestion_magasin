package fr.mgs.web.storekeeper;

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
import fr.mgs.model.order.OrderLine;
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

	@ManagedProperty("#{ListProducts}")
	ListProductController listProducts;

	private Set<Product> items;
	private String initScan;
	private Double conditioning;
	private ProductManager productManager;

	@PostConstruct
	private void ini() {
		items = new HashSet<Product>();
		productManager = new ProductManager();
		productManager.init(DataSource.LOCAL);
		conditioning = 0.0;
	}

	public void saveProducts() {

	}

	public void initScan() {

	}

	public void scan() throws SQLException {
		Product product = productManager.findProduct(Integer.valueOf(initScan));
		if (product != null && isPlastic(product)) {
			Lot lot = new Lot();
			lot.setLotProduct(product);
			lot.setQuantity(product.getConditioning());
			product.getConditioning();
		}
		conditioning = 0.0;
		initScan = "";
	}

	public void resetScan() {

	}

	public boolean isPlastic(Product p) {
		return (p.getSubCategory().getCategory().equals(Category.PLASTIC)
				|| p.getSubCategory().getCategory().equals(Category.CULTURE_PLASTIC));
	}

	public ListProductController getListProducts() {
		return listProducts;
	}

	public void setListProducts(ListProductController listProducts) {
		this.listProducts = listProducts;
	}

	public String getInitScan() {
		return initScan;
	}

	public void setInitScan(String initScan) {
		this.initScan = initScan;
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
}
