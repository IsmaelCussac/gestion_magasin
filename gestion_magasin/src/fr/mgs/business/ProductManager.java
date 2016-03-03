package fr.mgs.business;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fr.mgs.model.product.Lot;
import fr.mgs.model.product.Product;

public class ProductManager{

	// private DaoPerson daoP = new DaoPerson("annuaire");

	private ArrayList<Product> products;
	private Product p;

	public void init() {

		products = new ArrayList<Product>();

	}

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

	public void removeLot(Lot l) {
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
