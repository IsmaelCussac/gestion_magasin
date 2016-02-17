package fr.mgs.models.products;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
* This class describes a sub-category entity in database. It contains : 
 * - an id
 * - a name 
 * 
 * @author Ismaël
 *
 */
@Entity(name = "subCategories")
@Table(name = "subcategories_t")
public class SubCategory  implements Serializable {

}
