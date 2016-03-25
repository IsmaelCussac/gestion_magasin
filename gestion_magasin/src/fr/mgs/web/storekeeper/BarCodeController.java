package fr.mgs.web.storekeeper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.DualListModel;

import fr.mgs.business.ProductManager;
import fr.mgs.connection.DataSource;
import fr.mgs.model.product.Product;
import fr.mgs.service.converter.BarCodeItemConverter;

/**
 * Controller used to display the barcode page
 * 
 * @author Ismaël
 *
 */
@ManagedBean(name = "barCode")
@ViewScoped
public class BarCodeController {

	private ProductManager productManager;
	private DualListModel<BarCodeItem> barCodes;
	
	@ManagedProperty("#{barCodeItemConverter}")
    private BarCodeItemConverter barCodeItemConverter;
	
	@PostConstruct
	public void init(){
		productManager = new ProductManager();
		productManager.init(DataSource.LOCAL);
		
		try {
			generateAllBarCodes();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void generateAllBarCodes() throws SQLException {
		
		List<Product> listProduct = (List<Product>) productManager.findAllProducts();
		
		List<BarCodeItem> source = new ArrayList<BarCodeItem>();
		List<BarCodeItem> target = new ArrayList<BarCodeItem>();
		
		for(Product prod : listProduct){
			BarCodeItem barCodeItem = new BarCodeItem();
			barCodeItem.setBarCodeItem(prod.getProductId(), prod.getDesignation(), prod.getSubCategory().getName(), prod.getPicture());
			source.add(barCodeItem);
		}
		barCodes = new DualListModel<BarCodeItem>(source, target);
	}

	public DualListModel<BarCodeItem> getBarCodes() {
		return barCodes;
	}

	public void setBarCodes(DualListModel<BarCodeItem> barCodes) {
		this.barCodes = barCodes;
	}

	public BarCodeItemConverter getBarCodeItemConverter() {
		return barCodeItemConverter;
	}

	public void setBarCodeItemConverter(BarCodeItemConverter barCodeItemConverter) {
		this.barCodeItemConverter = barCodeItemConverter;
	}
	
}
