package fr.mgs.web.storekeeper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

	private List<Lot> itemsLot;
	private String scanDefault;
	private Double conditioning;
	private ProductManager productManager;
	private Product selectedProduct;
	private Lot newLot;

	@ManagedProperty("#{ListProducts}")
	ListProductController listProducts;


	@PostConstruct
	public void init() {
		productManager = new ProductManager();
		productManager.init(DataSource.LOCAL);
		itemsLot = new ArrayList<Lot>();

		conditioning = 0.0;
		selectedProduct = new Product();
		newLot = new Lot();
		newLot.setQuantity(0);

		try {
			for (Product product : listProducts.getAllProducts()) {
				Lot l = new Lot();
				l.setLotProduct(product);
				itemsLot.add(l);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public void saveProducts() throws SQLException {
		for (Iterator<Lot> i = itemsLot.iterator(); i.hasNext();) {
			Lot lKey = (Lot) i.next();
			if (lKey.getQuantity() > 0) {
				productManager.addLot(lKey);
			}

		}
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Lots ajouté", ""));

	}

	public void scan() throws NumberFormatException, SQLException {
		Product prod = productManager.findProduct(Integer.valueOf(scanDefault));

		if (prod != null && isPlastic(prod)) {
			for (Iterator<Lot> i = itemsLot.iterator(); i.hasNext();) {
				Lot lKey = (Lot) i.next();
				if (lKey.getLotProduct().getProductId() == prod.getProductId()) {
					lKey.setQuantity(lKey.getLotProduct().getConditioning());
				}
			}

		}
		scanDefault = "";
	}

	public void resetScan() {
		for (Iterator<Lot> i = itemsLot.iterator(); i.hasNext();) {
			i.next().setQuantity(0.0);
		}
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

	public String getScanDefault() {
		return scanDefault;
	}

	public void setScanDefault(String value) {
		this.scanDefault = value;
	}

	public List<Lot> getItems() {
		return itemsLot;
	}

	public void setItems(List<Lot> items) {
		this.itemsLot = items;
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
