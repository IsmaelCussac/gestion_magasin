package fr.mgs.models.products;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
* This class describes a level 2 category entity in database. It contains : 
 * - an id
 * - a name 
 * - a level 1 category
 * 
 * @author Ismaël
 *
 */
@Entity(name = "subCategories")
@Table(name = "sub_category_t")
public class SubCategory implements Serializable {

}
