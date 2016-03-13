package fr.mgs.web.storekeeper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.barcode_coder.java_barcode.Barcode;
import com.barcode_coder.java_barcode.BarcodeFactory;
import com.barcode_coder.java_barcode.BarcodeType;

import fr.mgs.business.OrderManager;
import fr.mgs.business.ProductManager;
import fr.mgs.connection.DataSource;
import fr.mgs.model.order.Order;
import fr.mgs.model.order.OrderLine;
import fr.mgs.model.product.Category;
import fr.mgs.model.product.Product;
import fr.mgs.model.product.SubCategory;

@ManagedBean(name = "ListProducts")
@ApplicationScoped
public class ListProductController {

	private ProductManager productManager;

	/**
	 * initialization of the controller
	 * 
	 * @throws SQLException
	 */
	public ListProductController() throws SQLException {
		
	}
	
	@PostConstruct
	public void init(){
		productManager = new ProductManager();
		try {
			productManager.init(DataSource.LOCAL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get all the products for the sub category
	 * 
	 * @return the list of all products
	 * @throws SQLException 
	 */
	public List<Product> getAllProducts() throws SQLException {
		
		List<Product> ListProduct = (List<Product>) productManager.findAllProducts();
		for(int i = 0 ; i < ListProduct.size(); ++i){
		    Barcode b = BarcodeFactory.createBarcode(BarcodeType.Code128,ListProduct.get(i).getProductBarCode());  
		    b.export("png",2,100,true,"C:/Users/anthony/git/gestion_magasin/gestion_magasin/WebContent/resources/image/"+ListProduct.get(i).getProductBarCode()+".png");  
		}
		return (List<Product>) productManager.findAllProducts();
	}
	

}
