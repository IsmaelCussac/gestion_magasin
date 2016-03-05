package fr.mgs.business;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import fr.mgs.connection.DataSource;
import fr.mgs.dao.DAOManager;
import fr.mgs.dao.GenericDAO;
import fr.mgs.dao.Table;
import fr.mgs.model.product.Lot;
import fr.mgs.model.product.Product;
import fr.mgs.model.product.SubCategory;

/**
 * Business class that manage the following DAOs to access database and process
 * data : - LotDAO - ProductDAO - SubCategoryDAO
 * 
 * @author Ismaël
 * @author Ibrahima
 */
public class ProductManager {

	private DAOManager daoManager;
	private GenericDAO<Lot, ?> lotDao;
	private GenericDAO<Product, ?> productDao;
	private GenericDAO<SubCategory, ?> subCategoryDao;

	public ProductManager() {
	}

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init(DataSource ds) throws SQLException {
		daoManager = new DAOManager();
		daoManager.init(ds);
		lotDao = (GenericDAO<Lot, ?>) daoManager.getDAO(Table.LOT);
		productDao = (GenericDAO<Product, ?>) daoManager.getDAO(Table.PRODUCT);
		subCategoryDao = (GenericDAO<SubCategory, ?>) daoManager.getDAO(Table.SUB_CATEGORY);

	}

	// GETTERS - SETTERS

	public DAOManager getDaoManager() {
		return daoManager;
	}

	public void setDaoManager(DAOManager daoManager) {
		this.daoManager = daoManager;
	}

	public GenericDAO<Lot, ?> getLotDao() {
		return lotDao;
	}

	public void setLotDao(GenericDAO<Lot, ?> lotDao) {
		this.lotDao = lotDao;
	}

	public GenericDAO<Product, ?> getProductDao() {
		return productDao;
	}

	public void setProductDao(GenericDAO<Product, ?> productDao) {
		this.productDao = productDao;
	}

	public GenericDAO<SubCategory, ?> getSubCategoryDao() {
		return subCategoryDao;
	}

	public void setSubCategoryDao(GenericDAO<SubCategory, ?> subCategoryDao) {
		this.subCategoryDao = subCategoryDao;
	}

	// METHODS

	/***
	 * This method returns the group of the person "person".
	 * 
	 * @param person
	 * @return the person's group.
	 */

	public Collection<Product> findAll() {
		try {
			// return daoP.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void createProduct(Product p) {
		try {
			// daoP.add(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/***
	 * This method updates the person "p".
	 * 
	 * @param r
	 */
	public void updateProduct(Product p) {
		try {
			// daoP.update(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/***
	 * This method updates the person "p".
	 * 
	 * @param r
	 */
	public void removeProduct(Product p) {
		try {
			// daoP.remove(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/***
	 * This method updates the person "p".
	 * 
	 * @param r
	 */
	public void findProduct(Product p) {
		try {
			// daoP.find(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/***
	 * This method updates the person "p".
	 * 
	 * @param r
	 */
	public void createLot(Lot l) {
		try {
			// daoP.saveLot(l);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateLot(Lot l) {
		try {
			// daoP.updateLot(l);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeLot(Lot lot) {
		try {
			// daoP.removeLot(l);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int findItemQuantity(Product p) {
		List<Lot> lots = new ArrayList<Lot>();
		int itemQuantity = 0;
		try {
			// itemQuantity= daoP.findQuantity(p);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return 20;
		// return itemQuantity;
	}
}
