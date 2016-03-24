package fr.mgs.web.storekeeper;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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
@ViewScoped
public class StockInController implements Serializable {

	private Set<Lot> itemsLot;
	private String scanDefault;
	private Double conditioning;
	private ProductManager productManager;
	private Product selectedProduct;
	private Lot newLot;
	private Date today;

	List<Product> listProducts;

	@PostConstruct
	public void init() {
		productManager = new ProductManager();
		productManager.init(DataSource.LOCAL);
		itemsLot = new HashSet<Lot>();
		today = new Date();
		conditioning = 0.0;
		selectedProduct = new Product();
		newLot = new Lot();
		newLot.setQuantity(0);

		try {
			listProducts = (List<Product>) productManager.findAllProducts();
			for (Product product : listProducts) {
				if (!isPlastic(product)) {
					for (Lot lot : product.getLots()) {
						itemsLot.add(lot);
					}
				} else {
					Lot l = new Lot();
					l.setLotProduct(product);
					itemsLot.add(l);
				}
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public void saveProducts() throws SQLException {
		boolean found;
		for (Lot lot : itemsLot) {
			found = false;
			Product p = productManager.findProduct(lot.getLotProduct()
					.getProductId());
			for (Lot l : p.getLots()) {
				if (l.getLotId() == lot.getLotId()) {
					l.setQuantity(l.getQuantity() + lot.getQuantity());
					found = true;
				}
			}
			if (!found && lot.getQuantity() != 0)
				p.getLots().add(lot);
			productManager.updateProduct(p);

		}
		itemsLot = new HashSet<>();
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Lots ajouté", ""));

	}

	public void scan() throws NumberFormatException, SQLException {
		Product prod = productManager.findProduct(Integer.valueOf(scanDefault));

		for (Iterator<Lot> i = itemsLot.iterator(); i.hasNext();) {
			Lot lKey = (Lot) i.next();
			if (lKey.getLotProduct().getProductId() == prod.getProductId()) {

				if (isPlastic(prod)) {
					lKey.setQuantity(lKey.getLotProduct().getConditioning());
				} else {
					lKey.setQuantity(lKey.getQuantity()
							+ lKey.getLotProduct().getConditioning());
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
		return (p.getSubCategory().getCategory().equals(Category.PLASTIC) || p
				.getSubCategory().getCategory()
				.equals(Category.CULTURE_PLASTIC));
	}

	public void saveLot() throws SQLException {
		Product p = productManager.findProduct(selectedProduct.getProductId());
		newLot.setLotProduct(selectedProduct);
		newLot.setQuantity(newLot.getQuantity() * p.getConditioning());
		productManager.addLot(newLot);
		newLot = new Lot();
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Nouveau lot ajouté", ""));

	}

	public List<Product> getListProducts() {
		return listProducts;
	}

	public void setListProducts(List<Product> listProducts) {
		this.listProducts = listProducts;
	}

	public String getScanDefault() {
		return scanDefault;
	}

	public void setScanDefault(String value) {
		this.scanDefault = value;
	}

	public Set<Lot> getItems() {
		return itemsLot;
	}

	public void setItems(Set<Lot> items) {
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

	public Date getToday() {
		return today;
	}

	public void setToday(Date today) {
		this.today = today;
	}
}
