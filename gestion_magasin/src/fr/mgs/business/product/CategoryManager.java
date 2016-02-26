package fr.mgs.business.product;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.mgs.models.product.Category;

import fr.mgs.models.product.SubCategory;

public class CategoryManager {
	//private CategoryDao daoC = new CategoryDao();
	
	private ArrayList<SubCategory> subCategories;
	private Category  cat;
	private Map<Category,List<SubCategory>> mapCat  ; 
    
	
	public void init(){
         
        subCategories = new ArrayList<SubCategory>();
        mapCat = new HashMap<Category,List<SubCategory>> ();
       
	}
    
	/***
	 * This method returns the group of the person "person".
	 * @param person
	 * @return the person's group.
	 */
    
    
    public Map<Category, List<SubCategory>> findAll() {
    	List<Category> cats; 
    	List<SubCategory> subCats ;
		try {

		//	cats = cat.values();
	//		for(Category : cat cats ){
	//		subCats = daoC.getSubCategories(cat);
	//		mapCat.put(cat., daoC.getSubCategories(cat));
	//		}			
//		return mapCat ; 

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
    
    
    
    
    public void createCategory(Category c) {
		try {
			//daoP.add(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    /***
     * This method updates the person "p".
     * @param r
     */
    public void updatePCategory(Category c) {
		try {
            //	daoP.update(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /***
     * This method updates the person "p".
     * @param r
     */
    public void removeCategory(Category c) {
		try {
            //	daoP.remove(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /***
     * This method updates the person "p".
     * @param r
     */
    public void findCategory(Category c) {
		try {
            //	daoP.find(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    /***
     * This method updates the person "p".
     * @param r
     */
}
