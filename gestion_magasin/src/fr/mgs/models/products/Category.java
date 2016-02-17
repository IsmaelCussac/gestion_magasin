package fr.mgs.models.products;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
* This class describes a category entity in database. It contains : 
 * - an id
 * - a name
 * - a sub-category 
 * 
 * @author Ismaël
 *
 */
@Entity(name = "categories")
@Table(name = "categories_t")
public class Category  implements Serializable {

}
