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
import fr.mgs.model.product.Category;
import fr.mgs.model.product.Lot;
import fr.mgs.model.product.Product;
import fr.mgs.model.product.SubCategory;
import fr.mgs.model.user.Team;
import fr.mgs.model.user.User;

/**
 * Business class that manage the following DAOs to access database and process
 * data : - LotDAO - ProductDAO - SubCategoryDAO
 * 
 * @author Ismaël
 * @author Ibrahima
 */
public class ProductManager {

	private DAOManager daoManager;
	private GenericDAO<Lot, Integer> lotDao;
	private GenericDAO<Product, Integer> productDao;
	private GenericDAO<SubCategory, String> subCategoryDao;

	public ProductManager() {
	}

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init(DataSource ds) throws SQLException {
		daoManager = new DAOManager();
		daoManager.init(ds);
		lotDao = (GenericDAO<Lot, Integer>) daoManager.getDAO(Table.LOT);
		productDao = (GenericDAO<Product, Integer>) daoManager.getDAO(Table.PRODUCT);
		subCategoryDao = (GenericDAO<SubCategory, String>) daoManager.getDAO(Table.SUB_CATEGORY);

	}

	// GETTERS - SETTERS

	public DAOManager getDaoManager() {
		return daoManager;
	}

	public void setDaoManager(DAOManager daoManager) {
		this.daoManager = daoManager;
	}

	public GenericDAO<Lot, Integer> getLotDao() {
		return lotDao;
	}

	public void setLotDao(GenericDAO<Lot, Integer> lotDao) {
		this.lotDao = lotDao;
	}

	public GenericDAO<Product, Integer> getProductDao() {
		return productDao;
	}

	public void setProductDao(GenericDAO<Product, Integer> productDao) {
		this.productDao = productDao;
	}

	public GenericDAO<SubCategory, String> getSubCategoryDao() {
		return subCategoryDao;
	}

	public void setSubCategoryDao(GenericDAO<SubCategory, String> subCategoryDao) {
		this.subCategoryDao = subCategoryDao;
	}

	// METHODS

	public void addLot(Lot lot) throws SQLException {
		lotDao.add(lot);
	}

	public void addProduct(Product product) throws SQLException {
		productDao.add(product);
	}
	
	public void addSubCategory(SubCategory subCategory) throws SQLException {
		subCategoryDao.add(subCategory);
	}

	public Lot findLot(int id) throws SQLException {
		return lotDao.find(id);
	}
	
	public Product findProduct(int id) throws SQLException {
		return productDao.find(id);
	}
	
	public SubCategory findSubCategory(String id) throws SQLException {
		return subCategoryDao.find(id);
	}
	
	public void removeLot(int id) throws SQLException{
		lotDao.remove(id);
	}
	
	public void removeProduct(int id) throws SQLException{
		productDao.remove(id);
	}
	
	public void removeSubCategory(String id) throws SQLException{
		subCategoryDao.remove(id);
	}

	public Collection<Lot> findAllLots() throws SQLException {
		return lotDao.findAll();
	}
	
	public Collection<Product> findAllProducts() throws SQLException {
		return productDao.findAll();
	}
	
	public Collection<SubCategory> findAllSubCategories() throws SQLException {
		return subCategoryDao.findAll();
	}

	public boolean lotExists(int id) throws SQLException {
		return lotDao.exists(id);
	}
	
	public boolean productExists(int id) throws SQLException {
		return productDao.exists(id);
	}
	
	public boolean subCategoryExists(String id) throws SQLException {
		return subCategoryDao.exists(id);
	}
	
	public void updateLot(Lot lot) throws SQLException{
		lotDao.update(lot);
	}
	
	public void updateProduct(Product product) throws SQLException{
		productDao.update(product);
	}
	
	public void updateSubCategory(SubCategory subCategory) throws SQLException{
		subCategoryDao.update(subCategory);
	}
	
	public Collection<SubCategory> findSubCategoriesByCategory(Category category){
		return null;
		
	}
	
	public Collection<Product> findProductsBySubCategory(String subCategory){
		return null;
		
	}

	public int findItemQuantity(Product product) {
		
		return 0;
	}
	
	
}
